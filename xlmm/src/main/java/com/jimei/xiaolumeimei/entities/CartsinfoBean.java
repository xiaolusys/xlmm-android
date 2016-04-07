package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 优尼世界 on 2016/01/15.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsinfoBean {

  /**
   * status : 0
   * sku_id : 105934
   * title : 加绒加厚高腰仿皮裤/黑色
   * price : 69.9
   * buyer_nick : itxuye
   * num : 1
   * remain_time : 2016-01-15T11:02:06
   * std_sale_price : 349.0
   * total_fee : 69.9
   * item_id : 27008
   * pic_path : http://image.xiaolu.so/MG-1448851276930-1.png
   * sku_name : M
   * id : 327707
   * buyer_id : 684126
   */

  @SerializedName("status") private String mStatus;
  @SerializedName("sku_id") private String mSkuId;
  @SerializedName("title") private String mTitle;
  @SerializedName("price") private double mPrice;
  @SerializedName("buyer_nick") private String mBuyerNick;
  @SerializedName("num") private String mNum;
  @SerializedName("remain_time") private String mRemaStringime;
  @SerializedName("std_sale_price") private float mStdSalePrice;
  @SerializedName("total_fee") private float mTotalFee;
  @SerializedName("item_id") private String mItemId;
  @SerializedName("pic_path") private String mPicPath;
  @SerializedName("sku_name") private String mSkuName;
  @SerializedName("id") private String mId;
  @SerializedName("buyer_id") private String mBuyerId;
  /**
   * is_sale_out : false
   */

  @SerializedName("is_sale_out") private boolean mIsSaleOut;

  public String getStatus() {
    return mStatus;
  }

  public void setStatus(String status) {
    this.mStatus = status;
  }

  public String getSkuId() {
    return mSkuId;
  }

  public void setSkuId(String skuId) {
    this.mSkuId = skuId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    this.mTitle = title;
  }

  public double getPrice() {
    return mPrice;
  }

  public void setPrice(double price) {
    this.mPrice = price;
  }

  public String getBuyerNick() {
    return mBuyerNick;
  }

  public void setBuyerNick(String buyerNick) {
    this.mBuyerNick = buyerNick;
  }

  public String getNum() {
    return mNum;
  }

  public void setNum(String num) {
    this.mNum = num;
  }

  public String getRemaStringime() {
    return mRemaStringime;
  }

  public void setRemaStringime(String remaStringime) {
    this.mRemaStringime = remaStringime;
  }

  public double getStdSalePrice() {
    return mStdSalePrice;
  }

  public void setStdSalePrice(float stdSalePrice) {
    this.mStdSalePrice = stdSalePrice;
  }

  public double getTotalFee() {
    return mTotalFee;
  }

  public void setTotalFee(float totalFee) {
    this.mTotalFee = totalFee;
  }

  public String getItemId() {
    return mItemId;
  }

  public void setItemId(String itemId) {
    this.mItemId = itemId;
  }

  public String getPicPath() {
    return mPicPath;
  }

  public void setPicPath(String picPath) {
    this.mPicPath = picPath;
  }

  public String getSkuName() {
    return mSkuName;
  }

  public void setSkuName(String skuName) {
    this.mSkuName = skuName;
  }

  public String getId() {
    return mId;
  }

  public void setId(String id) {
    this.mId = id;
  }

  public String getBuyerId() {
    return mBuyerId;
  }

  public void setBuyerId(String buyerId) {
    this.mBuyerId = buyerId;
  }

  @Override public String toString() {
    return "CartsinfoBean{" +
        "mStatus='" + mStatus + '\'' +
        ", mSkuId='" + mSkuId + '\'' +
        ", mTitle='" + mTitle + '\'' +
        ", mPrice=" + mPrice +
        ", mBuyerNick='" + mBuyerNick + '\'' +
        ", mNum='" + mNum + '\'' +
        ", mRemaStringime='" + mRemaStringime + '\'' +
        ", mStdSalePrice=" + mStdSalePrice +
        ", mTotalFee=" + mTotalFee +
        ", mItemId='" + mItemId + '\'' +
        ", mPicPath='" + mPicPath + '\'' +
        ", mSkuName='" + mSkuName + '\'' +
        ", mId='" + mId + '\'' +
        ", mBuyerId='" + mBuyerId + '\'' +
        '}';
  }

  public boolean isIsSaleOut() {
    return mIsSaleOut;
  }

  public void setIsSaleOut(boolean isSaleOut) {
    mIsSaleOut = isSaleOut;
  }
}
