package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMStoreBean {

  /**
   * status : 1
   * agent_price : 79.9
   * name : 韩版开叉针织宽松连衣裙/黄色
   * std_sale_price : 199.0
   * remain_num : 60
   * pic_path : http://image.xiaolu.so/MG_1457755208532头图背景1.png
   * sale_num : 480
   * id : 35821
   * rebet_amount : 15.980000000000002
   */

  @SerializedName("status") private int mStatus;
  @SerializedName("agent_price") private double mAgentPrice;
  @SerializedName("name") private String mName;
  @SerializedName("std_sale_price") private double mStdSalePrice;
  @SerializedName("remain_num") private int mRemainNum;
  @SerializedName("pic_path") private String mPicPath;
  @SerializedName("sale_num") private int mSaleNum;
  @SerializedName("id") private int mId;
  @SerializedName("rebet_amount") private double mRebetAmount;

  public int getStatus() {
    return mStatus;
  }

  public void setStatus(int status) {
    mStatus = status;
  }

  public double getAgentPrice() {
    return mAgentPrice;
  }

  public void setAgentPrice(double agentPrice) {
    mAgentPrice = agentPrice;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public double getStdSalePrice() {
    return mStdSalePrice;
  }

  public void setStdSalePrice(double stdSalePrice) {
    mStdSalePrice = stdSalePrice;
  }

  public int getRemainNum() {
    return mRemainNum;
  }

  public void setRemainNum(int remainNum) {
    mRemainNum = remainNum;
  }

  public String getPicPath() {
    return mPicPath;
  }

  public void setPicPath(String picPath) {
    mPicPath = picPath;
  }

  public int getSaleNum() {
    return mSaleNum;
  }

  public void setSaleNum(int saleNum) {
    mSaleNum = saleNum;
  }

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }

  public double getRebetAmount() {
    return mRebetAmount;
  }

  public void setRebetAmount(double rebetAmount) {
    mRebetAmount = rebetAmount;
  }
}
