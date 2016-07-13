package com.jimei.xiaolumeimei.entities;

/**
 * Created by wisdom on 16/7/13.
 */
public class MamaUrl {

    /**
     * renew : http://m.xiaolumeimei.com/mall/activity/shop/charge
     * act_info :  http://m.xiaolumeimei.com/pages/featuredEvent.html
     * invite : http://m.xiaolumeimei.com/pages/agency-invitation-res.html
     * fans_explain : http://m.xiaolumeimei.com/pages/fans-explain.html
     * exam : http://m.xiaolumeimei.com/mall/activity/exam
     */

    private String renew;
    private String act_info;
    private String invite;
    private String fans_explain;
    private String exam;

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public String getAct_info() {
        return act_info;
    }

    public void setAct_info(String act_info) {
        this.act_info = act_info;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getFans_explain() {
        return fans_explain;
    }

    public void setFans_explain(String fans_explain) {
        this.fans_explain = fans_explain;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }
}
