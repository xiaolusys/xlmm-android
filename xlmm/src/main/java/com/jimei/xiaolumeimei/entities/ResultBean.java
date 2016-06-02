package com.jimei.xiaolumeimei.entities;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/5.
 */
public class ResultBean {


    /**
     * message : 更换成功
     * code : 0
     */

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MMHavaChooseResultBean{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
