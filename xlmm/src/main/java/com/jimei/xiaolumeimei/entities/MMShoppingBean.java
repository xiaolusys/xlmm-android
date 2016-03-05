package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/03.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMShoppingBean {

  /**
   * customer : 1
   * name : null
   * shop_link : http://192.168.1.31/static/wap/mmshop.html?mm_linkid=44&ufrom=web
   * id : 3
   * thumbnail : http://7xogkj.com2.z0.glb.qiniucdn.com/222-ohmydeer.png
   * desc : 外贸原单，天天精选，买买买！
   */

  @SerializedName("shop_info") private ShopInfoEntity mShopInfo;

  public void setShopInfo(ShopInfoEntity shopInfo) {
    this.mShopInfo = shopInfo;
  }

  public ShopInfoEntity getShopInfo() {
    return mShopInfo;
  }

  public static class ShopInfoEntity {
    @SerializedName("customer") private int mCustomer;
    @SerializedName("name") private Object mName;
    @SerializedName("shop_link") private String mShopLink;
    @SerializedName("id") private int mId;
    @SerializedName("thumbnail") private String mThumbnail;
    @SerializedName("desc") private String mDesc;

    public void setCustomer(int customer) {
      this.mCustomer = customer;
    }

    public void setName(Object name) {
      this.mName = name;
    }

    public void setShopLink(String shopLink) {
      this.mShopLink = shopLink;
    }

    public void setId(int id) {
      this.mId = id;
    }

    public void setThumbnail(String thumbnail) {
      this.mThumbnail = thumbnail;
    }

    public void setDesc(String desc) {
      this.mDesc = desc;
    }

    public int getCustomer() {
      return mCustomer;
    }

    public Object getName() {
      return mName;
    }

    public String getShopLink() {
      return mShopLink;
    }

    public int getId() {
      return mId;
    }

    public String getThumbnail() {
      return mThumbnail;
    }

    public String getDesc() {
      return mDesc;
    }
  }
}
