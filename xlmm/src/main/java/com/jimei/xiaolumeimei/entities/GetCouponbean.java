package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by itxuye on 2016/6/30.
 */
public class GetCouponbean {
  @Override public String toString() {
    return "GetCouponbean{" +
        "info='" + info + '\'' +
        ", code=" + code +
        ", coupons=" + coupons +
        '}';
  }

  private String info;
  private int code;
  private List<CouponsBean> coupons;

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public List<CouponsBean> getCoupons() {
    return coupons;
  }

  public void setCoupons(List<CouponsBean> coupons) {
    this.coupons = coupons;
  }

  public static class CouponsBean {
    @Override public String toString() {
      return "CouponsBean{" +
          "id=" + id +
          ", template_id=" + template_id +
          ", coupon_type=" + coupon_type +
          ", coupon_type_display='" + coupon_type_display + '\'' +
          ", title='" + title + '\'' +
          ", customer=" + customer +
          ", coupon_no='" + coupon_no + '\'' +
          ", coupon_value=" + coupon_value +
          ", valid=" + valid +
          ", deadline='" + deadline + '\'' +
          ", start_use_time='" + start_use_time + '\'' +
          ", status=" + status +
          ", created='" + created + '\'' +
          ", use_fee=" + use_fee +
          ", use_fee_des='" + use_fee_des + '\'' +
          ", pros_desc='" + pros_desc + '\'' +
          ", start_time='" + start_time + '\'' +
          ", poll_status=" + poll_status +
          ", wisecrack='" + wisecrack + '\'' +
          ", nick='" + nick + '\'' +
          ", head_img='" + head_img + '\'' +
          '}';
    }

    private int id;
    private int template_id;
    private int coupon_type;
    private String coupon_type_display;
    private String title;
    private int customer;
    private String coupon_no;
    private double coupon_value;
    private boolean valid;
    private String deadline;
    private String start_use_time;
    private int status;
    private String created;
    private double use_fee;
    private String use_fee_des;
    private String pros_desc;
    private String start_time;
    private int poll_status;
    private String wisecrack;
    private String nick;
    private String head_img;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getTemplate_id() {
      return template_id;
    }

    public void setTemplate_id(int template_id) {
      this.template_id = template_id;
    }

    public int getCoupon_type() {
      return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
      this.coupon_type = coupon_type;
    }

    public String getCoupon_type_display() {
      return coupon_type_display;
    }

    public void setCoupon_type_display(String coupon_type_display) {
      this.coupon_type_display = coupon_type_display;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public int getCustomer() {
      return customer;
    }

    public void setCustomer(int customer) {
      this.customer = customer;
    }

    public String getCoupon_no() {
      return coupon_no;
    }

    public void setCoupon_no(String coupon_no) {
      this.coupon_no = coupon_no;
    }

    public double getCoupon_value() {
      return coupon_value;
    }

    public void setCoupon_value(double coupon_value) {
      this.coupon_value = coupon_value;
    }

    public boolean isValid() {
      return valid;
    }

    public void setValid(boolean valid) {
      this.valid = valid;
    }

    public String getDeadline() {
      return deadline;
    }

    public void setDeadline(String deadline) {
      this.deadline = deadline;
    }

    public String getStart_use_time() {
      return start_use_time;
    }

    public void setStart_use_time(String start_use_time) {
      this.start_use_time = start_use_time;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getCreated() {
      return created;
    }

    public void setCreated(String created) {
      this.created = created;
    }

    public double getUse_fee() {
      return use_fee;
    }

    public void setUse_fee(double use_fee) {
      this.use_fee = use_fee;
    }

    public String getUse_fee_des() {
      return use_fee_des;
    }

    public void setUse_fee_des(String use_fee_des) {
      this.use_fee_des = use_fee_des;
    }

    public String getPros_desc() {
      return pros_desc;
    }

    public void setPros_desc(String pros_desc) {
      this.pros_desc = pros_desc;
    }

    public String getStart_time() {
      return start_time;
    }

    public void setStart_time(String start_time) {
      this.start_time = start_time;
    }

    public int getPoll_status() {
      return poll_status;
    }

    public void setPoll_status(int poll_status) {
      this.poll_status = poll_status;
    }

    public String getWisecrack() {
      return wisecrack;
    }

    public void setWisecrack(String wisecrack) {
      this.wisecrack = wisecrack;
    }

    public String getNick() {
      return nick;
    }

    public void setNick(String nick) {
      this.nick = nick;
    }

    public String getHead_img() {
      return head_img;
    }

    public void setHead_img(String head_img) {
      this.head_img = head_img;
    }
  }
}
