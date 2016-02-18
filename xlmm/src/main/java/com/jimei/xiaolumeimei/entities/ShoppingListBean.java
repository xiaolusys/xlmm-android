package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ShoppingListBean {

  /**
   * count : 8751
   * next : http://api.xiaolumeimei.com/rest/v1/pmt/shopping?page=2
   * previous : null
   * results : [{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021856c52c83beae4","wxordernick":"赵红梅","order_cash":69.9,"rebeta_cash":69.9,"ticheng_cash":2.09,"shoptime":"2016-02-18T10:29:45","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG-1449798444842-weqwwwwweq.jpg","time_display":"10:29"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021856c529386be5f","wxordernick":"冉宇琳(686)","order_cash":69.9,"rebeta_cash":69.9,"ticheng_cash":6.99,"shoptime":"2016-02-18T10:20:28","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555265004992.png","time_display":"10:20"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021856c52076411e6","wxordernick":"陈惠琴(392)","order_cash":49.9,"rebeta_cash":49.9,"ticheng_cash":4.99,"shoptime":"2016-02-18T09:38:18","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555252520451.png","time_display":"09:38"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021856c49b92c0afa","wxordernick":"燕子","order_cash":59.9,"rebeta_cash":59.9,"ticheng_cash":5.99,"shoptime":"2016-02-18T00:11:12","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_1455520357246头图背景恢复的.png","time_display":"00:11"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c457c863639","wxordernick":"若雪(199)","order_cash":109.9,"rebeta_cash":109.9,"ticheng_cash":10.99,"shoptime":"2016-02-17T19:49:10","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555073304281.png","time_display":"19:49"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c4327f3fd5f","wxordernick":"施佳婕(282)","order_cash":79.9,"rebeta_cash":79.9,"ticheng_cash":7.99,"shoptime":"2016-02-17T16:43:38","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14554406822983.png","time_display":"16:43"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c41517445f2","wxordernick":"魏蓉","order_cash":59.9,"rebeta_cash":59.9,"ticheng_cash":5.99,"shoptime":"2016-02-17T14:37:25","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555902055801.png","time_display":"14:37"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c40b4f70d08","wxordernick":"芳","order_cash":59.9,"rebeta_cash":59.9,"ticheng_cash":5.99,"shoptime":"2016-02-17T13:55:56","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_1454578001043蕾丝接拼高领毛衣03.png","time_display":"13:55"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c3ff7162b27","wxordernick":"仝敏(229)","order_cash":139.8,"rebeta_cash":139.8,"ticheng_cash":13.98,"shoptime":"2016-02-17T13:05:04","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG-1452841719559-毛呢针织拼接连衣裙3.png","time_display":"13:05"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c3df710c2f1","wxordernick":"汪小姐(027)","order_cash":149.8,"rebeta_cash":149.8,"ticheng_cash":14.98,"shoptime":"2016-02-17T10:48:42","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG-1449297299115-韩版蕾丝拼接针织连衣裙02.png","time_display":"10:48"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private Object mPrevious;
  /**
   * linkid : 44
   * linkname : 小鹿美美特卖服务号
   * wxorderid : xd16021856c52c83beae4
   * wxordernick : 赵红梅
   * order_cash : 69.9
   * rebeta_cash : 69.9
   * ticheng_cash : 2.09
   * shoptime : 2016-02-18T10:29:45
   * status : 0
   * get_status_display : 已付款
   * pic_path : http://image.xiaolu.so/MG-1449798444842-weqwwwwweq.jpg
   * time_display : 10:29
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public void setCount(int count) {
    this.mCount = count;
  }

  public void setNext(String next) {
    this.mNext = next;
  }

  public void setPrevious(Object previous) {
    this.mPrevious = previous;
  }

  public void setResults(List<ResultsEntity> results) {
    this.mResults = results;
  }

  public int getCount() {
    return mCount;
  }

  public String getNext() {
    return mNext;
  }

  public Object getPrevious() {
    return mPrevious;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public static class ResultsEntity {
    @SerializedName("linkid") private int mLinkid;
    @SerializedName("linkname") private String mLinkname;
    @SerializedName("wxorderid") private String mWxorderid;
    @SerializedName("wxordernick") private String mWxordernick;
    @SerializedName("order_cash") private double mOrderCash;
    @SerializedName("rebeta_cash") private double mRebetaCash;
    @SerializedName("ticheng_cash") private double mTichengCash;
    @SerializedName("shoptime") private String mShoptime;
    @SerializedName("status") private int mStatus;
    @SerializedName("get_status_display") private String mGetStatusDisplay;
    @SerializedName("pic_path") private String mPicPath;
    @SerializedName("time_display") private String mTimeDisplay;

    public void setLinkid(int linkid) {
      this.mLinkid = linkid;
    }

    public void setLinkname(String linkname) {
      this.mLinkname = linkname;
    }

    public void setWxorderid(String wxorderid) {
      this.mWxorderid = wxorderid;
    }

    public void setWxordernick(String wxordernick) {
      this.mWxordernick = wxordernick;
    }

    public void setOrderCash(double orderCash) {
      this.mOrderCash = orderCash;
    }

    public void setRebetaCash(double rebetaCash) {
      this.mRebetaCash = rebetaCash;
    }

    public void setTichengCash(double tichengCash) {
      this.mTichengCash = tichengCash;
    }

    public void setShoptime(String shoptime) {
      this.mShoptime = shoptime;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public void setGetStatusDisplay(String getStatusDisplay) {
      this.mGetStatusDisplay = getStatusDisplay;
    }

    public void setPicPath(String picPath) {
      this.mPicPath = picPath;
    }

    public void setTimeDisplay(String timeDisplay) {
      this.mTimeDisplay = timeDisplay;
    }

    public int getLinkid() {
      return mLinkid;
    }

    public String getLinkname() {
      return mLinkname;
    }

    public String getWxorderid() {
      return mWxorderid;
    }

    public String getWxordernick() {
      return mWxordernick;
    }

    public double getOrderCash() {
      return mOrderCash;
    }

    public double getRebetaCash() {
      return mRebetaCash;
    }

    public double getTichengCash() {
      return mTichengCash;
    }

    public String getShoptime() {
      return mShoptime;
    }

    public int getStatus() {
      return mStatus;
    }

    public String getGetStatusDisplay() {
      return mGetStatusDisplay;
    }

    public String getPicPath() {
      return mPicPath;
    }

    public String getTimeDisplay() {
      return mTimeDisplay;
    }
  }
}
