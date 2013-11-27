/**
 * @(#)LicenceHelper.java, Oct 5, 2013.
 */
package com.cloudstone.emenu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Licence;

/**
 * @author xuhongfeng
 */
@Component
public class LicenceHelper {
    private static final Logger LOG = LoggerFactory
            .getLogger(LicenceHelper.class);

    public CheckResult checkLicence() {
        Licence licence = getLicence();
        if (licence == null) {
            return buildFailed("授权证书不存在或已过期");
        }
        
        boolean checkMac = false;
        for (String mac:getMacList()) {
            LOG.info("Mac :  " + mac);
            if (mac.equalsIgnoreCase(licence.getUuid())) {
                LOG.info("check mac success");
                checkMac = true;
                break;
            }
        }
        if (!checkMac) {
            LOG.error("check mac failed, licence.mac:" + licence.getUuid());
            return buildFailed("授权证书与机器硬件信息不匹配");
        }
        
        if (licence.getType() == Licence.TYPE_FOR_EVER) {
            return buildSuccess();
        }
        long now = System.currentTimeMillis();
        if (now > licence.getLicenceTime()) {
            LOG.error("licence expired, now=" + now + ", licenceTime=" + licence.getLicenceTime());
            return buildFailed("授权证书已过期");
        }
        return buildSuccess();
    }
    
    private CheckResult buildSuccess() {
        CheckResult r = new CheckResult();
        r.licence = getLicence();
        r.success = true;
        return r;
    }
    
    private CheckResult buildFailed(String error) {
        CheckResult r = new CheckResult();
        r.licence = getLicence();
        r.success = false;
        r.message = error;
        return r;
    }
    
    public static class CheckResult {
        private boolean success;
        private String message;
        private Licence licence;
        
        public boolean isSuccess() {
            return success;
        }
        public void setSuccess(boolean success) {
            this.success = success;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public Licence getLicence() {
            return licence;
        }
        public void setLicence(Licence licence) {
            this.licence = licence;
        }
    }
    
    private Licence licence;
    public Licence getLicence() {
        if (licence != null) {
            return licence;
        }
        
        File licenceFile = lisenceFile();

        if (!licenceFile.exists()) {
            LOG.error("licence file not found");
            return null;
        }
        try {
            byte[] encrypted = FileUtils.readFileToByteArray(licenceFile);
            byte[] plain = decrypt(encrypted);
            String json = new String(plain);
            licence = JsonUtils.fromJson(json, Licence.class);
            return licence;
        } catch (Throwable e) {
            LOG.error("", e);
            return null;
        }
    }
    
    private File lisenceFile() {
        String dirPath = System.getProperty(Const.PARAM_CLOUDSTONE_DATA_DIR);
        File dir = new File(dirPath);
        File licenceFile = new File(dir, "emenu.licence");
        
        return licenceFile;
    }
    
    public void saveLisence(InputStream in) throws IOException {
        byte[] buf = new byte[4096];
        int read = -1;
        File file = lisenceFile();
        FileOutputStream out = new FileOutputStream(file);
        while ( (read = in.read(buf)) != -1) {
            out.write(buf, 0, read);
        }
        out.close();
        
        this.licence = null;
    }
    
    private byte[] decrypt(byte[] encrypted) throws Exception {
        PublicKey publicKey = getPublicKey();
        return RsaUtils.decrypt(encrypted, publicKey);
    }
    
    private byte[] encrypt(byte[] plain) throws Exception {
        PublicKey publicKey = getPublicKey();
        return RsaUtils.encrypt(plain, publicKey);
    }
    
    private PublicKey getPublicKey() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("emenu.pub");
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private List<String> getMacList() {
        List<String> list = new LinkedList<String>();
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface
                    .getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isVirtual()) {
                    StringBuilder sb = new StringBuilder();
                    byte[] mac = ni.getHardwareAddress();
                    if (mac != null) {
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        list.add(sb.toString());
                    }
                }
            }
        } catch (Throwable e) {
            LOG.error("", e);
        }
        return list;
    }
    
    public String getSerial() throws Exception {
        List<String> list = getMacList();
        if (list.size() == 0) {
            return null;
        }
        String mac = list.get(0);
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("cloudstone");
        return encryptor.encrypt(mac);
    }
}
