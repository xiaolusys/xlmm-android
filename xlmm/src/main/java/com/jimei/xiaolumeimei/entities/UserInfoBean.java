package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class UserInfoBean {


  /**
   * id : 1
   * url : http://m.xiaolumeimei.com/rest/v1/users/1
   * user_id : 1
   * username : xiuqing.mei
   * nick : 小鹿妈妈
   * mobile : 18621623915
   * email :
   * phone :
   * thumbnail : http://7xogkj.com2.z0.glb.qiniucdn.com/222-ohmydeer.png
   * status : 1
   * created : 2015-04-09T11:08:12
   * modified : 2016-02-22T04:01:42
   * xiaolumm : {"id":44,"cash":1943361,"agencylevel":2,"created":"2015-04-11T12:20:23","status":"effect"}
   * has_usable_password : true
   * user_budget : {"budget_cash":5730.75,"is_cash_out":1}
   * is_attention_public : 1
   * coupon_num : 2
   * waitpay_num : 0
   * waitgoods_num : 16
   * refunds_num : 21
   * score : 0
   */

  @SerializedName("id")
  private int id;
  @SerializedName("url")
  private String url;
  @SerializedName("user_id")
  private String userId;
  @SerializedName("username")
  private String username;
  @SerializedName("nick")
  private String nick;
  @SerializedName("mobile")
  private String mobile;
  @SerializedName("email")
  private String email;
  @SerializedName("phone")
  private String phone;
  @SerializedName("thumbnail")
  private String thumbnail;
  @SerializedName("status")
  private int status;
  @SerializedName("created")
  private String created;
  @SerializedName("modified")
  private String modified;
  /**
   * id : 44
   * cash : 1943361
   * agencylevel : 2
   * created : 2015-04-11T12:20:23
   * status : effect
   */

  @SerializedName("xiaolumm")
  private XiaolummEntity xiaolumm;
  @SerializedName("has_usable_password")
  private boolean hasUsablePassword;
  /**
   * budget_cash : 5730.75
   * is_cash_out : 1
   */

  @SerializedName("user_budget")
  private UserBudgetEntity userBudget;
  @SerializedName("is_attention_public")
  private int isAttentionPublic;
  @SerializedName("coupon_num")
  private int couponNum;
  @SerializedName("waitpay_num")
  private int waitpayNum;
  @SerializedName("waitgoods_num")
  private int waitgoodsNum;
  @SerializedName("refunds_num")
  private int refundsNum;
  @SerializedName("score")
  private int score;

  public void setId(int id) {
    this.id = id;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public void setModified(String modified) {
    this.modified = modified;
  }

  public void setXiaolumm(XiaolummEntity xiaolumm) {
    this.xiaolumm = xiaolumm;
  }

  public void setHasUsablePassword(boolean hasUsablePassword) {
    this.hasUsablePassword = hasUsablePassword;
  }

  public void setUserBudget(UserBudgetEntity userBudget) {
    this.userBudget = userBudget;
  }

  public void setIsAttentionPublic(int isAttentionPublic) {
    this.isAttentionPublic = isAttentionPublic;
  }

  public void setCouponNum(int couponNum) {
    this.couponNum = couponNum;
  }

  public void setWaitpayNum(int waitpayNum) {
    this.waitpayNum = waitpayNum;
  }

  public void setWaitgoodsNum(int waitgoodsNum) {
    this.waitgoodsNum = waitgoodsNum;
  }

  public void setRefundsNum(int refundsNum) {
    this.refundsNum = refundsNum;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public String getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getNick() {
    return nick;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public int getStatus() {
    return status;
  }

  public String getCreated() {
    return created;
  }

  public String getModified() {
    return modified;
  }

  public XiaolummEntity getXiaolumm() {
    return xiaolumm;
  }

  public boolean isHasUsablePassword() {
    return hasUsablePassword;
  }

  public UserBudgetEntity getUserBudget() {
    return userBudget;
  }

  public int getIsAttentionPublic() {
    return isAttentionPublic;
  }

  public int getCouponNum() {
    return couponNum;
  }

  public int getWaitpayNum() {
    return waitpayNum;
  }

  public int getWaitgoodsNum() {
    return waitgoodsNum;
  }

  public int getRefundsNum() {
    return refundsNum;
  }

  public int getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "UserInfoBean{" +
            "id=" + id +
            ", url='" + url + '\'' +
            ", userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", nick='" + nick + '\'' +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", thumbnail='" + thumbnail + '\'' +
            ", status=" + status +
            ", created='" + created + '\'' +
            ", modified='" + modified + '\'' +
            ", xiaolumm=" + xiaolumm +
            ", hasUsablePassword=" + hasUsablePassword +
            ", userBudget=" + userBudget +
            ", isAttentionPublic=" + isAttentionPublic +
            ", couponNum=" + couponNum +
            ", waitpayNum=" + waitpayNum +
            ", waitgoodsNum=" + waitgoodsNum +
            ", refundsNum=" + refundsNum +
            ", score=" + score +
            '}';
  }

  public static class XiaolummEntity {
    @SerializedName("id")
    private int id;
    @SerializedName("cash")
    private int cash;
    @SerializedName("agencylevel")
    private int agencylevel;
    @SerializedName("created")
    private String created;
    @SerializedName("status")
    private String status;

    public void setId(int id) {
      this.id = id;
    }

    public void setCash(int cash) {
      this.cash = cash;
    }

    public void setAgencylevel(int agencylevel) {
      this.agencylevel = agencylevel;
    }

    public void setCreated(String created) {
      this.created = created;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public int getId() {
      return id;
    }

    public int getCash() {
      return cash;
    }

    public int getAgencylevel() {
      return agencylevel;
    }

    public String getCreated() {
      return created;
    }

    public String getStatus() {
      return status;
    }

    @Override
    public String toString() {
      return "XiaolummEntity{" +
              "id=" + id +
              ", cash=" + cash +
              ", agencylevel=" + agencylevel +
              ", created='" + created + '\'' +
              ", status='" + status + '\'' +
              '}';
    }
  }

  public static class UserBudgetEntity {
    @SerializedName("budget_cash")
    private double budgetCash;
    @SerializedName("is_cash_out")
    private int isCashOut;

    public void setBudgetCash(double budgetCash) {
      this.budgetCash = budgetCash;
    }

    public void setIsCashOut(int isCashOut) {
      this.isCashOut = isCashOut;
    }

    public double getBudgetCash() {
      return budgetCash;
    }

    public int getIsCashOut() {
      return isCashOut;
    }

    @Override
    public String toString() {
      return "UserBudgetEntity{" +
              "budgetCash=" + budgetCash +
              ", isCashOut=" + isCashOut +
              '}';
    }
  }
}
