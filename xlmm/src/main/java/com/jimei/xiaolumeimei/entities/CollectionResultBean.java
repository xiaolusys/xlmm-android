package com.jimei.xiaolumeimei.entities;

/**
 * Created by wisdom on 16/7/28.
 */
public class CollectionResultBean {

    /**
     * code : 0
     * info : 添加成功
     * {"code": 0, "info": u"取消收藏成功"}
     * {"code": 1, "info": u"参数错误"}
     * {"code": 7, "info": u"用户未找到"}
     */

    private int code;
    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
