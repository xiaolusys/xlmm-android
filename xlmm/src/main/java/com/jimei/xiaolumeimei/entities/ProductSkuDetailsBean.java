package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ProductSkuDetailsBean {

  @SerializedName("sku_info") private List<SkuInfoEntity> mSkuInfo;
  @SerializedName("productname") private String mProductname;

  public String getProductname() {
    return mProductname;
  }

  public List<SkuInfoEntity> getSkuInfo() {
    return mSkuInfo;
  }

  public void setSkuInfo(List<SkuInfoEntity> skuInfo) {
    mSkuInfo = skuInfo;
  }

  public static class SkuInfoEntity {
    @SerializedName("agent_price") private double mAgentPrice;
    @SerializedName("product_id") private int mProductId;
    @SerializedName("std_sale_price") private double mStdSalePrice;
    @SerializedName("lowest_price") private double mLowestPrice;
    @SerializedName("outer_id") private String mOuterId;
    @SerializedName("type") private String mType;
    @SerializedName("product_img") private String mProductImg;
    @SerializedName("name") private String mName;

    @SerializedName("sku_items") private List<SkuItemsEntity> mSkuItems;

    public double getAgentPrice() {
      return mAgentPrice;
    }

    public void setAgentPrice(double agentPrice) {
      mAgentPrice = agentPrice;
    }

    public int getProductId() {
      return mProductId;
    }

    public void setProductId(int productId) {
      mProductId = productId;
    }

    public double getStdSalePrice() {
      return mStdSalePrice;
    }

    public void setStdSalePrice(double stdSalePrice) {
      mStdSalePrice = stdSalePrice;
    }

    public double getLowestPrice() {
      return mLowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
      mLowestPrice = lowestPrice;
    }

    public String getOuterId() {
      return mOuterId;
    }

    public void setOuterId(String outerId) {
      mOuterId = outerId;
    }

    public String getType() {
      return mType;
    }

    public void setType(String type) {
      mType = type;
    }

    public String getProductImg() {
      return mProductImg;
    }

    public void setProductImg(String productImg) {
      mProductImg = productImg;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      mName = name;
    }

    public List<SkuItemsEntity> getSkuItems() {
      return mSkuItems;
    }

    public void setSkuItems(List<SkuItemsEntity> skuItems) {
      mSkuItems = skuItems;
    }

    public static class SkuItemsEntity {
      @SerializedName("agent_price") private double mAgentPrice;
      @SerializedName("sku_id") private int mSkuId;
      @SerializedName("name") private String mName;
      @SerializedName("std_sale_price") private double mStdSalePrice;
      @SerializedName("type") private String mType;
      @SerializedName("free_num") private int mFreeNum;
      @SerializedName("is_saleout") private boolean mIsSaleout;

      public double getAgentPrice() {
        return mAgentPrice;
      }

      public void setAgentPrice(double agentPrice) {
        mAgentPrice = agentPrice;
      }

      public int getSkuId() {
        return mSkuId;
      }

      public void setSkuId(int skuId) {
        mSkuId = skuId;
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

      public String getType() {
        return mType;
      }

      public void setType(String type) {
        mType = type;
      }

      public int getFreeNum() {
        return mFreeNum;
      }

      public void setFreeNum(int freeNum) {
        mFreeNum = freeNum;
      }

      public boolean isIsSaleout() {
        return mIsSaleout;
      }

      public void setIsSaleout(boolean isSaleout) {
        mIsSaleout = isSaleout;
      }
    }
  }
}
