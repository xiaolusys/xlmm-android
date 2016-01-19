package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AllOrdersBean {

  @Override public String toString() {
    return "AllOrdersBean{" +
        "mCount=" + mCount +
        ", mNext='" + mNext + '\'' +
        ", mPrevious=" + mPrevious +
        ", mResults=" + mResults +
        '}';
  }


  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private Object mPrevious;


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

    @Override public String toString() {
      return "ResultsEntity{" +
          "mId=" + mId +
          ", mUrl='" + mUrl + '\'' +
          ", mTid='" + mTid + '\'' +
          ", mBuyerNick='" + mBuyerNick + '\'' +
          ", mBuyerId=" + mBuyerId +
          ", mChannel='" + mChannel + '\'' +
          ", mPayment=" + mPayment +
          ", mPostFee=" + mPostFee +
          ", mTotalFee=" + mTotalFee +
          ", mDiscountFee=" + mDiscountFee +
          ", mStatus=" + mStatus +
          ", mStatusDisplay='" + mStatusDisplay + '\'' +
          ", mOrderPic='" + mOrderPic + '\'' +
          ", mBuyerMessage='" + mBuyerMessage + '\'' +
          ", mTradeType=" + mTradeType +
          ", mCreated='" + mCreated + '\'' +
          ", mPayTime=" + mPayTime +
          ", mConsignTime=" + mConsignTime +
          ", mOutSid='" + mOutSid + '\'' +
          ", mLogisticsCompany=" + mLogisticsCompany +
          ", mReceiverName='" + mReceiverName + '\'' +
          ", mReceiverState='" + mReceiverState + '\'' +
          ", mReceiverCity='" + mReceiverCity + '\'' +
          ", mReceiverDistrict='" + mReceiverDistrict + '\'' +
          ", mReceiverAddress='" + mReceiverAddress + '\'' +
          ", mReceiverMobile='" + mReceiverMobile + '\'' +
          ", mReceiverPhone='" + mReceiverPhone + '\'' +
          ", mOrders=" + mOrders +
          '}';
    }

    @SerializedName("id") private int mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("tid") private String mTid;
    @SerializedName("buyer_nick") private String mBuyerNick;
    @SerializedName("buyer_id") private int mBuyerId;
    @SerializedName("channel") private String mChannel;
    @SerializedName("payment") private double mPayment;
    @SerializedName("post_fee") private int mPostFee;
    @SerializedName("total_fee") private double mTotalFee;
    @SerializedName("discount_fee") private int mDiscountFee;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("order_pic") private String mOrderPic;
    @SerializedName("buyer_message") private String mBuyerMessage;
    @SerializedName("trade_type") private int mTradeType;
    @SerializedName("created") private String mCreated;
    @SerializedName("pay_time") private Object mPayTime;
    @SerializedName("consign_time") private Object mConsignTime;
    @SerializedName("out_sid") private String mOutSid;
    @SerializedName("logistics_company") private Object mLogisticsCompany;
    @SerializedName("receiver_name") private String mReceiverName;
    @SerializedName("receiver_state") private String mReceiverState;
    @SerializedName("receiver_city") private String mReceiverCity;
    @SerializedName("receiver_district") private String mReceiverDistrict;
    @SerializedName("receiver_address") private String mReceiverAddress;
    @SerializedName("receiver_mobile") private String mReceiverMobile;
    @SerializedName("receiver_phone") private String mReceiverPhone;
    /**
     * id : 302107
     * oid : xo16011156933d7f55da7
     * item_id : 30786
     * title : 韩版百搭皮裙/黑色
     * sku_id : 120694
     * num : 1
     * outer_id : 821277480051
     * total_fee : 49.9
     * payment : 49.9
     * discount_fee : 0
     * sku_name : XL
     * pic_path : http://image.xiaolu.so/MG-1452155820775-韩版百搭皮裙3.png
     * status : 7
     * status_display : 交易关闭
     * refund_status : 0
     * refund_status_display : 没有退款
     * refund_id : null
     * kill_title : false
     */

    @SerializedName("orders") private List<OrdersEntity> mOrders;

    public void setId(int id) {
      this.mId = id;
    }

    public void setUrl(String url) {
      this.mUrl = url;
    }

    public void setTid(String tid) {
      this.mTid = tid;
    }

    public void setBuyerNick(String buyerNick) {
      this.mBuyerNick = buyerNick;
    }

    public void setBuyerId(int buyerId) {
      this.mBuyerId = buyerId;
    }

    public void setChannel(String channel) {
      this.mChannel = channel;
    }

    public void setPayment(double payment) {
      this.mPayment = payment;
    }

    public void setPostFee(int postFee) {
      this.mPostFee = postFee;
    }

    public void setTotalFee(double totalFee) {
      this.mTotalFee = totalFee;
    }

    public void setDiscountFee(int discountFee) {
      this.mDiscountFee = discountFee;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public void setStatusDisplay(String statusDisplay) {
      this.mStatusDisplay = statusDisplay;
    }

    public void setOrderPic(String orderPic) {
      this.mOrderPic = orderPic;
    }

    public void setBuyerMessage(String buyerMessage) {
      this.mBuyerMessage = buyerMessage;
    }

    public void setTradeType(int tradeType) {
      this.mTradeType = tradeType;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public void setPayTime(Object payTime) {
      this.mPayTime = payTime;
    }

    public void setConsignTime(Object consignTime) {
      this.mConsignTime = consignTime;
    }

    public void setOutSid(String outSid) {
      this.mOutSid = outSid;
    }

    public void setLogisticsCompany(Object logisticsCompany) {
      this.mLogisticsCompany = logisticsCompany;
    }

    public void setReceiverName(String receiverName) {
      this.mReceiverName = receiverName;
    }

    public void setReceiverState(String receiverState) {
      this.mReceiverState = receiverState;
    }

    public void setReceiverCity(String receiverCity) {
      this.mReceiverCity = receiverCity;
    }

    public void setReceiverDistrict(String receiverDistrict) {
      this.mReceiverDistrict = receiverDistrict;
    }

    public void setReceiverAddress(String receiverAddress) {
      this.mReceiverAddress = receiverAddress;
    }

    public void setReceiverMobile(String receiverMobile) {
      this.mReceiverMobile = receiverMobile;
    }

    public void setReceiverPhone(String receiverPhone) {
      this.mReceiverPhone = receiverPhone;
    }

    public void setOrders(List<OrdersEntity> orders) {
      this.mOrders = orders;
    }

    public int getId() {
      return mId;
    }

    public String getUrl() {
      return mUrl;
    }

    public String getTid() {
      return mTid;
    }

    public String getBuyerNick() {
      return mBuyerNick;
    }

    public int getBuyerId() {
      return mBuyerId;
    }

    public String getChannel() {
      return mChannel;
    }

    public double getPayment() {
      return mPayment;
    }

    public int getPostFee() {
      return mPostFee;
    }

    public double getTotalFee() {
      return mTotalFee;
    }

    public int getDiscountFee() {
      return mDiscountFee;
    }

    public int getStatus() {
      return mStatus;
    }

    public String getStatusDisplay() {
      return mStatusDisplay;
    }

    public String getOrderPic() {
      return mOrderPic;
    }

    public String getBuyerMessage() {
      return mBuyerMessage;
    }

    public int getTradeType() {
      return mTradeType;
    }

    public String getCreated() {
      return mCreated;
    }

    public Object getPayTime() {
      return mPayTime;
    }

    public Object getConsignTime() {
      return mConsignTime;
    }

    public String getOutSid() {
      return mOutSid;
    }

    public Object getLogisticsCompany() {
      return mLogisticsCompany;
    }

    public String getReceiverName() {
      return mReceiverName;
    }

    public String getReceiverState() {
      return mReceiverState;
    }

    public String getReceiverCity() {
      return mReceiverCity;
    }

    public String getReceiverDistrict() {
      return mReceiverDistrict;
    }

    public String getReceiverAddress() {
      return mReceiverAddress;
    }

    public String getReceiverMobile() {
      return mReceiverMobile;
    }

    public String getReceiverPhone() {
      return mReceiverPhone;
    }

    public List<OrdersEntity> getOrders() {
      return mOrders;
    }

    public static class OrdersEntity {

      @Override public String toString() {
        return "OrdersEntity{" +
            "mId=" + mId +
            ", mOid='" + mOid + '\'' +
            ", mItemId='" + mItemId + '\'' +
            ", mTitle='" + mTitle + '\'' +
            ", mSkuId='" + mSkuId + '\'' +
            ", mNum=" + mNum +
            ", mOuterId='" + mOuterId + '\'' +
            ", mTotalFee=" + mTotalFee +
            ", mPayment=" + mPayment +
            ", mDiscountFee=" + mDiscountFee +
            ", mSkuName='" + mSkuName + '\'' +
            ", mPicPath='" + mPicPath + '\'' +
            ", mStatus=" + mStatus +
            ", mStatusDisplay='" + mStatusDisplay + '\'' +
            ", mRefundStatus=" + mRefundStatus +
            ", mRefundStatusDisplay='" + mRefundStatusDisplay + '\'' +
            ", mRefundId=" + mRefundId +
            ", mKillTitle=" + mKillTitle +
            '}';
      }

      @SerializedName("id") private int mId;
      @SerializedName("oid") private String mOid;
      @SerializedName("item_id") private String mItemId;
      @SerializedName("title") private String mTitle;
      @SerializedName("sku_id") private String mSkuId;
      @SerializedName("num") private int mNum;
      @SerializedName("outer_id") private String mOuterId;
      @SerializedName("total_fee") private double mTotalFee;
      @SerializedName("payment") private double mPayment;
      @SerializedName("discount_fee") private int mDiscountFee;
      @SerializedName("sku_name") private String mSkuName;
      @SerializedName("pic_path") private String mPicPath;
      @SerializedName("status") private int mStatus;
      @SerializedName("status_display") private String mStatusDisplay;
      @SerializedName("refund_status") private int mRefundStatus;
      @SerializedName("refund_status_display") private String mRefundStatusDisplay;
      @SerializedName("refund_id") private Object mRefundId;
      @SerializedName("kill_title") private boolean mKillTitle;

      public void setId(int id) {
        this.mId = id;
      }

      public void setOid(String oid) {
        this.mOid = oid;
      }

      public void setItemId(String itemId) {
        this.mItemId = itemId;
      }

      public void setTitle(String title) {
        this.mTitle = title;
      }

      public void setSkuId(String skuId) {
        this.mSkuId = skuId;
      }

      public void setNum(int num) {
        this.mNum = num;
      }

      public void setOuterId(String outerId) {
        this.mOuterId = outerId;
      }

      public void setTotalFee(double totalFee) {
        this.mTotalFee = totalFee;
      }

      public void setPayment(double payment) {
        this.mPayment = payment;
      }

      public void setDiscountFee(int discountFee) {
        this.mDiscountFee = discountFee;
      }

      public void setSkuName(String skuName) {
        this.mSkuName = skuName;
      }

      public void setPicPath(String picPath) {
        this.mPicPath = picPath;
      }

      public void setStatus(int status) {
        this.mStatus = status;
      }

      public void setStatusDisplay(String statusDisplay) {
        this.mStatusDisplay = statusDisplay;
      }

      public void setRefundStatus(int refundStatus) {
        this.mRefundStatus = refundStatus;
      }

      public void setRefundStatusDisplay(String refundStatusDisplay) {
        this.mRefundStatusDisplay = refundStatusDisplay;
      }

      public void setRefundId(Object refundId) {
        this.mRefundId = refundId;
      }

      public void setKillTitle(boolean killTitle) {
        this.mKillTitle = killTitle;
      }

      public int getId() {
        return mId;
      }

      public String getOid() {
        return mOid;
      }

      public String getItemId() {
        return mItemId;
      }

      public String getTitle() {
        return mTitle;
      }

      public String getSkuId() {
        return mSkuId;
      }

      public int getNum() {
        return mNum;
      }

      public String getOuterId() {
        return mOuterId;
      }

      public double getTotalFee() {
        return mTotalFee;
      }

      public double getPayment() {
        return mPayment;
      }

      public int getDiscountFee() {
        return mDiscountFee;
      }

      public String getSkuName() {
        return mSkuName;
      }

      public String getPicPath() {
        return mPicPath;
      }

      public int getStatus() {
        return mStatus;
      }

      public String getStatusDisplay() {
        return mStatusDisplay;
      }

      public int getRefundStatus() {
        return mRefundStatus;
      }

      public String getRefundStatusDisplay() {
        return mRefundStatusDisplay;
      }

      public Object getRefundId() {
        return mRefundId;
      }

      public boolean isKillTitle() {
        return mKillTitle;
      }
    }
  }
}