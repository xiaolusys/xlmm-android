package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/13.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ProductDetailsBean {


  @SerializedName("id") private int mId;
  @SerializedName("detail_content") private DetailContentEntity mDetailContent;
  @SerializedName("sku_info") private List<SkuInfoEntity> mSkuInfo;

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }

  public DetailContentEntity getDetailContent() {
    return mDetailContent;
  }

  public void setDetailContent(DetailContentEntity detailContent) {
    mDetailContent = detailContent;
  }

  public List<SkuInfoEntity> getSkuInfo() {
    return mSkuInfo;
  }

  public void setSkuInfo(List<SkuInfoEntity> skuInfo) {
    mSkuInfo = skuInfo;
  }

  public static class DetailContentEntity {
    @SerializedName("category") private CategoryEntity mCategory;
    @SerializedName("is_newsales") private boolean mIsNewsales;
    @SerializedName("name") private String mName;
    @SerializedName("sale_time") private String mSaleTime;
    @SerializedName("is_single_spec") private boolean mIsSingleSpec;
    @SerializedName("offshelf_time") private String mOffshelfTime;
    @SerializedName("is_recommend") private boolean mIsRecommend;
    @SerializedName("properties") private PropertiesEntity mProperties;
    @SerializedName("is_saleopen") private boolean mIsSaleopen;
    @SerializedName("lowest_agent_price") private double mLowestAgentPrice;
    @SerializedName("is_sale_out") private boolean mIsSaleOut;
    @SerializedName("lowest_std_sale_price") private double mLowestStdSalePrice;
    @SerializedName("watermark_op") private String mWatermarkOp;
    @SerializedName("head_imgs") private List<String> mHeadImgs;
    @SerializedName("content_imgs") private List<String> mContentImgs;
    @SerializedName("item_marks") private List<String> mItemMarks;

    public CategoryEntity getCategory() {
      return mCategory;
    }

    public void setCategory(CategoryEntity category) {
      mCategory = category;
    }

    public boolean isIsNewsales() {
      return mIsNewsales;
    }

    public void setIsNewsales(boolean isNewsales) {
      mIsNewsales = isNewsales;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      mName = name;
    }

    public String getSaleTime() {
      return mSaleTime;
    }

    public void setSaleTime(String saleTime) {
      mSaleTime = saleTime;
    }

    public boolean isIsSingleSpec() {
      return mIsSingleSpec;
    }

    public void setIsSingleSpec(boolean isSingleSpec) {
      mIsSingleSpec = isSingleSpec;
    }

    public String getOffshelfTime() {
      return mOffshelfTime;
    }

    public void setOffshelfTime(String offshelfTime) {
      mOffshelfTime = offshelfTime;
    }

    public boolean isIsRecommend() {
      return mIsRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
      mIsRecommend = isRecommend;
    }

    public PropertiesEntity getProperties() {
      return mProperties;
    }

    public void setProperties(PropertiesEntity properties) {
      mProperties = properties;
    }

    public boolean isIsSaleopen() {
      return mIsSaleopen;
    }

    public void setIsSaleopen(boolean isSaleopen) {
      mIsSaleopen = isSaleopen;
    }

    public double getLowestAgentPrice() {
      return mLowestAgentPrice;
    }

    public void setLowestAgentPrice(double lowestAgentPrice) {
      mLowestAgentPrice = lowestAgentPrice;
    }

    public boolean isIsSaleOut() {
      return mIsSaleOut;
    }

    public void setIsSaleOut(boolean isSaleOut) {
      mIsSaleOut = isSaleOut;
    }

    public double getLowestStdSalePrice() {
      return mLowestStdSalePrice;
    }

    public void setLowestStdSalePrice(double lowestStdSalePrice) {
      mLowestStdSalePrice = lowestStdSalePrice;
    }

    public String getWatermarkOp() {
      return mWatermarkOp;
    }

    public void setWatermarkOp(String watermarkOp) {
      mWatermarkOp = watermarkOp;
    }

    public List<String> getHeadImgs() {
      return mHeadImgs;
    }

    public void setHeadImgs(List<String> headImgs) {
      mHeadImgs = headImgs;
    }

    public List<String> getContentImgs() {
      return mContentImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
      mContentImgs = contentImgs;
    }

    public List<String> getItemMarks() {
      return mItemMarks;
    }

    public void setItemMarks(List<String> itemMarks) {
      mItemMarks = itemMarks;
    }

    public static class CategoryEntity {
      @SerializedName("id") private int mId;

      public int getId() {
        return mId;
      }

      public void setId(int id) {
        mId = id;
      }
    }

    public static class PropertiesEntity {
      @SerializedName("note") private String mNote;
      @SerializedName("color") private String mColor;
      @SerializedName("wash_instructions") private String mWashInstructions;
      @SerializedName("material") private String mMaterial;

      public String getNote() {
        return mNote;
      }

      public void setNote(String note) {
        mNote = note;
      }

      public String getColor() {
        return mColor;
      }

      public void setColor(String color) {
        mColor = color;
      }

      public String getWashInstructions() {
        return mWashInstructions;
      }

      public void setWashInstructions(String washInstructions) {
        mWashInstructions = washInstructions;
      }

      public String getMaterial() {
        return mMaterial;
      }

      public void setMaterial(String material) {
        mMaterial = material;
      }
    }
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
