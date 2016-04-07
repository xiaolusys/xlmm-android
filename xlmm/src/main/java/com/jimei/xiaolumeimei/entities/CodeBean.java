package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wisdom on 16/4/6.
 */
public class CodeBean {

    /**
     * rcode : 1
     * msg : 亲，手机号码错啦！
     */

    @SerializedName("rcode")
    private int rcode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("next")
    private String next;

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
