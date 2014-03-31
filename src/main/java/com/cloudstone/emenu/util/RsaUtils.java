/**
 * @(#)RsaUtils.java, 2013-6-23. 
 *
 */
package com.cloudstone.emenu.util;

import java.security.Key;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * @author xuhongfeng
 */
public class RsaUtils {

    public static String encrypt(String password) {
        //TODO
        return password;
    }

    public static byte[] decrypt(byte[] encrypted, PublicKey key) throws Exception {
        return blockCipher(encrypted, Cipher.DECRYPT_MODE, key);
    }

    public static byte[] encrypt(byte[] plain, PublicKey key) throws Exception {
        return blockCipher(plain, Cipher.ENCRYPT_MODE, key);
    }

    private static byte[] blockCipher(byte[] bytes, int mode, Key key)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(mode, key);
        // string initialize 2 buffers.
        // scrambled will hold intermediate results
        byte[] scrambled = new byte[0];

        // toReturn will hold the total result
        byte[] toReturn = new byte[0];
        // if we encrypt we use 100 byte long blocks. Decryption requires 128
        // byte long blocks (because of RSA)
        int length = (mode == Cipher.ENCRYPT_MODE) ? 100 : 128;

        // another buffer. this one will hold the bytes that have to be modified
        // in this step
        byte[] buffer = new byte[length];

        for (int i = 0; i < bytes.length; i++) {

            // if we filled our buffer array we have our block ready for de- or
            // encryption
            if ((i > 0) && (i % length == 0)) {
                // execute the operation
                scrambled = cipher.doFinal(buffer);
                // add the result to our total result.
                toReturn = append(toReturn, scrambled);
                // here we calculate the length of the next buffer required
                int newlength = length;

                // if newlength would be longer than remaining bytes in the
                // bytes array we shorten it.
                if (i + length > bytes.length) {
                    newlength = bytes.length - i;
                }
                // clean the buffer array
                buffer = new byte[newlength];
            }
            // copy byte into our buffer.
            buffer[i % length] = bytes[i];
        }

        // this step is needed if we had a trailing buffer. should only happen
        // when encrypting.
        // example: we encrypt 110 bytes. 100 bytes per run means we "forgot"
        // the last 10 bytes. they are in the buffer array
        scrambled = cipher.doFinal(buffer);

        // final step before we can return the modified data.
        toReturn = append(toReturn, scrambled);

        return toReturn;
    }

    private static byte[] append(byte[] prefix, byte[] suffix) {
        byte[] toReturn = new byte[prefix.length + suffix.length];
        for (int i = 0; i < prefix.length; i++) {
            toReturn[i] = prefix[i];
        }
        for (int i = 0; i < suffix.length; i++) {
            toReturn[i + prefix.length] = suffix[i];
        }
        return toReturn;
    }
}
