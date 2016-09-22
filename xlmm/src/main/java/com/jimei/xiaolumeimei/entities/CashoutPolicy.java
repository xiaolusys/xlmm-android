package com.jimei.xiaolumeimei.entities;

/**
 * Created by wisdom on 16/9/20.
 */

public class CashoutPolicy {

    /**
     * message : 最低提现额度2元，最高提现额度200元。小额提现不超过6元无需审核，每日可提2次。超过6元提现须经财务审核，一般审核期为工作日内24小时-48小时。
     * audit_cashout_amount : 6
     * min_cashout_amount : 2
     * daily_cashout_tries : 2
     * max_cashout_amount : 200
     */

    private String message;
    private double audit_cashout_amount;
    private double min_cashout_amount;
    private int daily_cashout_tries;
    private double max_cashout_amount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAudit_cashout_amount() {
        return audit_cashout_amount;
    }

    public void setAudit_cashout_amount(double audit_cashout_amount) {
        this.audit_cashout_amount = audit_cashout_amount;
    }

    public double getMin_cashout_amount() {
        return min_cashout_amount;
    }

    public void setMin_cashout_amount(double min_cashout_amount) {
        this.min_cashout_amount = min_cashout_amount;
    }

    public int getDaily_cashout_tries() {
        return daily_cashout_tries;
    }

    public void setDaily_cashout_tries(int daily_cashout_tries) {
        this.daily_cashout_tries = daily_cashout_tries;
    }

    public double getMax_cashout_amount() {
        return max_cashout_amount;
    }

    public void setMax_cashout_amount(double max_cashout_amount) {
        this.max_cashout_amount = max_cashout_amount;
    }
}
