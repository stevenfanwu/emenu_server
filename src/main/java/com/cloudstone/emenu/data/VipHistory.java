
package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class VipHistory extends IEntity {
    private int vipid;
    private double recharge;// >0 recharge <0 usage
    private double left;
    private long opTime;

    public VipHistory() {
        super();
    }

    public VipHistory(VipHistory other) {
        super(other);
        this.vipid = other.vipid;
        this.recharge = other.recharge;
        this.left = other.left;
        this.opTime = other.opTime;
    }

    public int getVipid() {
        return vipid;
    }

    public void setVipid(int vipid) {
        this.vipid = vipid;
    }

    public double getRecharge() {
        return recharge;
    }

    public void setRecharge(double recharge) {
        this.recharge = recharge;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }
}
