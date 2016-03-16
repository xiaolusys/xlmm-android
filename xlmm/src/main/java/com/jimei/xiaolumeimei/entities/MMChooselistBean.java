package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMChooselistBean {

  /**
   * agent_price : 79.9
   * in_customer_shop : 0
   * name : 韩版加绒卫衣套装/米黄
   * std_sale_price : 399.0
   * lock_num : 1
   * pic_path : http://image.xiaolu.so/MG_145465538426903.png
   * id : 32496
   * rebet_amount : 7.990000000000001
   */

  @SerializedName("agent_price") private double mAgentPrice;
  @SerializedName("in_customer_shop") private int mInCustomerShop;
  @SerializedName("name") private String mName;
  @SerializedName("std_sale_price") private double mStdSalePrice;
  @SerializedName("lock_num") private int mLockNum;
  @SerializedName("pic_path") private String mPicPath;

  public int getmSaleNum() {
    return mSaleNum;
  }

  public void setmSaleNum(int mSaleNum) {
    this.mSaleNum = mSaleNum;
  }

  @SerializedName("sale_num") private int mSaleNum;
  @SerializedName("id") private int mId;
  @SerializedName("rebet_amount") private double mRebetAmount;

  public void setAgentPrice(double agentPrice) {
    this.mAgentPrice = agentPrice;
  }

  public void setInCustomerShop(int inCustomerShop) {
    this.mInCustomerShop = inCustomerShop;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public void setStdSalePrice(double stdSalePrice) {
    this.mStdSalePrice = stdSalePrice;
  }

  public void setLockNum(int lockNum) {
    this.mLockNum = lockNum;
  }

  public void setPicPath(String picPath) {
    this.mPicPath = picPath;
  }

  public void setId(int id) {
    this.mId = id;
  }

  public void setRebetAmount(double rebetAmount) {
    this.mRebetAmount = rebetAmount;
  }

  public double getAgentPrice() {
    return mAgentPrice;
  }

  public int getInCustomerShop() {
    return mInCustomerShop;
  }

  public String getName() {
    return mName;
  }

  public double getStdSalePrice() {
    return mStdSalePrice;
  }

  public int getLockNum() {
    return mLockNum;
  }

  public String getPicPath() {
    return mPicPath;
  }

  public int getId() {
    return mId;
  }

  public double getRebetAmount() {
    return mRebetAmount;
  }
}
