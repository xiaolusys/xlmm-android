package com.jimei.xiaolumeimei.entities;

/**
 * Created by wisdom on 16/8/19.
 */
public class MiPushOrderCarryBean {

    /**
     * content : u'有新订单了'
     * avatar : http://img
     * type : mama_ordercarry_boardcast
     */

    private String content;
    private String avatar;
    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
