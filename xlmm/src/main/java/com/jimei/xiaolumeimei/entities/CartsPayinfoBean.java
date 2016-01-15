package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by 优尼世界 on 2016/01/15.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayinfoBean {

  @Override public String toString() {
    return "CartsPayinfoBean{" +
        "mWalletCash=" + mWalletCash +
        ", mTotalPayment=" + mTotalPayment +
        ", mWalletPayable=" + mWalletPayable +
        ", mWeixinPayable=" + mWeixinPayable +
        ", mCouponTicket=" + mCouponTicket +
        ", mUuid='" + mUuid + '\'' +
        ", mAlipayPayable=" + mAlipayPayable +
        ", mDiscountFee=" + mDiscountFee +
        ", mCartIds='" + mCartIds + '\'' +
        ", mTotalFee=" + mTotalFee +
        ", mPostFee=" + mPostFee +
        ", mCartList=" + mCartList +
        '}';
  }

  @SerializedName("wallet_cash") private int mWalletCash;
  @SerializedName("total_payment") private double mTotalPayment;
  @SerializedName("wallet_payable") private boolean mWalletPayable;
  @SerializedName("weixin_payable") private boolean mWeixinPayable;
  @SerializedName("coupon_ticket") private Object mCouponTicket;
  @SerializedName("uuid") private String mUuid;
  @SerializedName("alipay_payable") private boolean mAlipayPayable;
  @SerializedName("discount_fee") private double mDiscountFee;
  @SerializedName("cart_ids") private String mCartIds;
  @SerializedName("total_fee") private double mTotalFee;
  @SerializedName("post_fee") private double mPostFee;
  /**
   * id : 327836
   * url : http://api.xiaolumeimei.com/rest/v1/carts/327836
   * buyer_id : 684126
   * buyer_nick : itxuye
   * item_id : 31165
   * title : 气质蕾丝连衣裙/白色
   * price : 69.9
   * std_sale_price : 349.0
   * sku_id : 122215
   * num : 1
   * total_fee : 69.9
   * sku_name : L
   * pic_path : http://image.xiaolu.so/MG-1452567228289-镂空修身蕾丝连衣裙02.jpg
   * created : 2016-01-15T11:30:59
   * status : 0
   */

  @SerializedName("cart_list") private List<CartListEntity> mCartList;

  public void setWalletCash(int walletCash) {
    this.mWalletCash = walletCash;
  }

  public void setTotalPayment(double totalPayment) {
    this.mTotalPayment = totalPayment;
  }

  public void setWalletPayable(boolean walletPayable) {
    this.mWalletPayable = walletPayable;
  }

  public void setWeixinPayable(boolean weixinPayable) {
    this.mWeixinPayable = weixinPayable;
  }

  public void setCouponTicket(Object couponTicket) {
    this.mCouponTicket = couponTicket;
  }

  public void setUuid(String uuid) {
    this.mUuid = uuid;
  }

  public void setAlipayPayable(boolean alipayPayable) {
    this.mAlipayPayable = alipayPayable;
  }

  public void setDiscountFee(double discountFee) {
    this.mDiscountFee = discountFee;
  }

  public void setCartIds(String cartIds) {
    this.mCartIds = cartIds;
  }

  public void setTotalFee(double totalFee) {
    this.mTotalFee = totalFee;
  }

  public void setPostFee(double postFee) {
    this.mPostFee = postFee;
  }

  public void setCartList(List<CartListEntity> cartList) {
    this.mCartList = cartList;
  }

  public int getWalletCash() {
    return mWalletCash;
  }

  public double getTotalPayment() {
    return mTotalPayment;
  }

  public boolean isWalletPayable() {
    return mWalletPayable;
  }

  public boolean isWeixinPayable() {
    return mWeixinPayable;
  }

  public Object getCouponTicket() {
    return mCouponTicket;
  }

  public String getUuid() {
    return mUuid;
  }

  public boolean isAlipayPayable() {
    return mAlipayPayable;
  }

  public double getDiscountFee() {
    return mDiscountFee;
  }

  public String getCartIds() {
    return mCartIds;
  }

  public double getTotalFee() {
    return mTotalFee;
  }

  public double getPostFee() {
    return mPostFee;
  }

  public List<CartListEntity> getCartList() {
    return mCartList;
  }

  public static class CartListEntity {
    @SerializedName("id") private int mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("buyer_id") private int mBuyerId;
    @SerializedName("buyer_nick") private String mBuyerNick;
    @SerializedName("item_id") private String mItemId;
    @SerializedName("title") private String mTitle;
    @SerializedName("price") private double mPrice;
    @SerializedName("std_sale_price") private double mStdSalePrice;
    @SerializedName("sku_id") private String mSkuId;
    @SerializedName("num") private int mNum;
    @SerializedName("total_fee") private double mTotalFee;
    @SerializedName("sku_name") private String mSkuName;
    @SerializedName("pic_path") private String mPicPath;
    @SerializedName("created") private String mCreated;
    @SerializedName("status") private int mStatus;

    public void setId(int id) {
      this.mId = id;
    }

    public void setUrl(String url) {
      this.mUrl = url;
    }

    public void setBuyerId(int buyerId) {
      this.mBuyerId = buyerId;
    }

    public void setBuyerNick(String buyerNick) {
      this.mBuyerNick = buyerNick;
    }

    public void setItemId(String itemId) {
      this.mItemId = itemId;
    }

    public void setTitle(String title) {
      this.mTitle = title;
    }

    public void setPrice(double price) {
      this.mPrice = price;
    }

    public void setStdSalePrice(double stdSalePrice) {
      this.mStdSalePrice = stdSalePrice;
    }

    public void setSkuId(String skuId) {
      this.mSkuId = skuId;
    }

    public void setNum(int num) {
      this.mNum = num;
    }

    public void setTotalFee(double totalFee) {
      this.mTotalFee = totalFee;
    }

    public void setSkuName(String skuName) {
      this.mSkuName = skuName;
    }

    public void setPicPath(String picPath) {
      this.mPicPath = picPath;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public int getId() {
      return mId;
    }

    public String getUrl() {
      return mUrl;
    }

    public int getBuyerId() {
      return mBuyerId;
    }

    public String getBuyerNick() {
      return mBuyerNick;
    }

    public String getItemId() {
      return mItemId;
    }

    public String getTitle() {
      return mTitle;
    }

    public double getPrice() {
      return mPrice;
    }

    public double getStdSalePrice() {
      return mStdSalePrice;
    }

    public String getSkuId() {
      return mSkuId;
    }

    public int getNum() {
      return mNum;
    }

    public double getTotalFee() {
      return mTotalFee;
    }

    public String getSkuName() {
      return mSkuName;
    }

    public String getPicPath() {
      return mPicPath;
    }

    public String getCreated() {
      return mCreated;
    }

    public int getStatus() {
      return mStatus;
    }
  }
}
