package com.jimei.xiaolumeimei.entities;

/**
 * Created by wisdom on 16/6/22.
 */
public class RedBagBean {


    /**
     * code : 0
     * title : 订单分享
     * share_times_limit : 15
     * post_img : http://image.xiaolu.so/MG_14664737825781.jpg?imageMogr2/strip/format/jpg/quality/100/interlace/1/thumbnail/1866/crop/1866x746
     * msg : 分享成功
     * share_link : http://m.xiaolumeimei.com/mall/order/redpacket?uniq_id=xd160507572d97858cf9a&ufrom=
     */

    private int code;
    private String title;
    private int share_times_limit;
    private String post_img;
    private String msg;
    private String share_link;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getShare_times_limit() {
        return share_times_limit;
    }

    public void setShare_times_limit(int share_times_limit) {
        this.share_times_limit = share_times_limit;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
