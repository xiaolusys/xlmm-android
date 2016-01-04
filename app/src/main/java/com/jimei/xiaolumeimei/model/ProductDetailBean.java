package com.jimei.xiaolumeimei.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailBean {

  @SerializedName("id") private int mId;
  @SerializedName("url") private String mUrl;
  @SerializedName("name") private String mName;
  @SerializedName("outer_id") private String mOuterId;
  /**
   * cid : 20
   * parent_cid : 8
   * name : 上装
   * status : normal
   * sort_order : 100
   */

  @SerializedName("category") private CategoryEntity mCategory;
  @SerializedName("pic_path") private String mPicPath;
  @SerializedName("remain_num") private int mRemainNum;
  @SerializedName("is_saleout") private boolean mIsSaleout;
  @SerializedName("head_img") private String mHeadImg;
  @SerializedName("is_saleopen") private boolean mIsSaleopen;
  @SerializedName("is_newgood") private boolean mIsNewgood;
  @SerializedName("std_sale_price") private int mStdSalePrice;
  @SerializedName("agent_price") private double mAgentPrice;
  @SerializedName("sale_time") private String mSaleTime;
  @SerializedName("offshelf_time") private Object mOffshelfTime;
  @SerializedName("memo") private String mMemo;
  @SerializedName("lowest_price") private double mLowestPrice;
  @SerializedName("product_lowest_price") private double mProductLowestPrice;
  /**
   * id : 6915
   * name : 威摩士时尚针织衫
   * head_imgs : ["http://image.xiaolu.so/MG-1451563781947-12.jpg"]
   * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvfVbcriceDwraqo1wVTvY7fsY4L1xyAZsz8hQuOjic2HdacY5GU1sVheXX5kLd6kwz5aicFV652Smwg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QOobIs34MxCvefoZRY7qhhlwdtludicrMPzo2qYoMuSpPqGtJ9KJPxWEQ/0?wx_fmt=jpeg"]
   * is_single_spec : true
   * is_sale_out : false
   * buy_limit : false
   * per_limit : 5
   */

  @SerializedName("product_model") private ProductModelEntity mProductModel;
  @SerializedName("ware_by") private int mWareBy;
  @SerializedName("is_verify") private boolean mIsVerify;
  @SerializedName("model_id") private int mModelId;
  /**
   * head_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QOJNKwXyz1tG7Smuc9HY1o135DrK1XWK86iawyYINpl6w2cmhCglItNlw/0?wx_fmt=png"]
   * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvfVbcriceDwraqo1wVTvY7fsY4L1xyAZsz8hQuOjic2HdacY5GU1sVheXX5kLd6kwz5aicFV652Smwg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QOobIs34MxCvefoZRY7qhhlwdtludicrMPzo2qYoMuSpPqGtJ9KJPxWEQ/0?wx_fmt=jpeg"]
   * mama_discount : 100
   * is_recommend : false
   * buy_limit : false
   * per_limit : 5
   * mama_rebeta : 0
   * material :
   * wash_instructions :
   * note : 秒杀款一经售出，概不退换
   * color :
   */

  @SerializedName("details") private DetailsEntity mDetails;
  /**
   * id : 50844
   * outer_id : 1
   * name : M
   * remain_num : 2
   * size_of_sku : {"free_num":0,"result":"None"}
   * is_saleout : true
   * std_sale_price : 299
   * agent_price : 49.9
   */

  @SerializedName("normal_skus") private List<NormalSkusEntity> mNormalSkus;

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    this.mId = id;
  }

  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String url) {
    this.mUrl = url;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getOuterId() {
    return mOuterId;
  }

  public void setOuterId(String outerId) {
    this.mOuterId = outerId;
  }

  public CategoryEntity getCategory() {
    return mCategory;
  }

  public void setCategory(CategoryEntity category) {
    this.mCategory = category;
  }

  public String getPicPath() {
    return mPicPath;
  }

  public void setPicPath(String picPath) {
    this.mPicPath = picPath;
  }

  public int getRemainNum() {
    return mRemainNum;
  }

  public void setRemainNum(int remainNum) {
    this.mRemainNum = remainNum;
  }

  public boolean isIsSaleout() {
    return mIsSaleout;
  }

  public void setIsSaleout(boolean isSaleout) {
    this.mIsSaleout = isSaleout;
  }

  public String getHeadImg() {
    return mHeadImg;
  }

  public void setHeadImg(String headImg) {
    this.mHeadImg = headImg;
  }

  public boolean isIsSaleopen() {
    return mIsSaleopen;
  }

  public void setIsSaleopen(boolean isSaleopen) {
    this.mIsSaleopen = isSaleopen;
  }

  public boolean isIsNewgood() {
    return mIsNewgood;
  }

  public void setIsNewgood(boolean isNewgood) {
    this.mIsNewgood = isNewgood;
  }

  public int getStdSalePrice() {
    return mStdSalePrice;
  }

  public void setStdSalePrice(int stdSalePrice) {
    this.mStdSalePrice = stdSalePrice;
  }

  public double getAgentPrice() {
    return mAgentPrice;
  }

  public void setAgentPrice(double agentPrice) {
    this.mAgentPrice = agentPrice;
  }

  public String getSaleTime() {
    return mSaleTime;
  }

  public void setSaleTime(String saleTime) {
    this.mSaleTime = saleTime;
  }

  public Object getOffshelfTime() {
    return mOffshelfTime;
  }

  public void setOffshelfTime(Object offshelfTime) {
    this.mOffshelfTime = offshelfTime;
  }

  public String getMemo() {
    return mMemo;
  }

  public void setMemo(String memo) {
    this.mMemo = memo;
  }

  public double getLowestPrice() {
    return mLowestPrice;
  }

  public void setLowestPrice(double lowestPrice) {
    this.mLowestPrice = lowestPrice;
  }

  public double getProductLowestPrice() {
    return mProductLowestPrice;
  }

  public void setProductLowestPrice(double productLowestPrice) {
    this.mProductLowestPrice = productLowestPrice;
  }

  public ProductModelEntity getProductModel() {
    return mProductModel;
  }

  public void setProductModel(ProductModelEntity productModel) {
    this.mProductModel = productModel;
  }

  public int getWareBy() {
    return mWareBy;
  }

  public void setWareBy(int wareBy) {
    this.mWareBy = wareBy;
  }

  public boolean isIsVerify() {
    return mIsVerify;
  }

  public void setIsVerify(boolean isVerify) {
    this.mIsVerify = isVerify;
  }

  public int getModelId() {
    return mModelId;
  }

  public void setModelId(int modelId) {
    this.mModelId = modelId;
  }

  public DetailsEntity getDetails() {
    return mDetails;
  }

  public void setDetails(DetailsEntity details) {
    this.mDetails = details;
  }

  public List<NormalSkusEntity> getNormalSkus() {
    return mNormalSkus;
  }

  public void setNormalSkus(List<NormalSkusEntity> normalSkus) {
    this.mNormalSkus = normalSkus;
  }

  public static class CategoryEntity {
    @SerializedName("cid") private int mCid;
    @SerializedName("parent_cid") private int mParentCid;
    @SerializedName("name") private String mName;
    @SerializedName("status") private String mStatus;
    @SerializedName("sort_order") private int mSortOrder;

    public int getCid() {
      return mCid;
    }

    public void setCid(int cid) {
      this.mCid = cid;
    }

    public int getParentCid() {
      return mParentCid;
    }

    public void setParentCid(int parentCid) {
      this.mParentCid = parentCid;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public String getStatus() {
      return mStatus;
    }

    public void setStatus(String status) {
      this.mStatus = status;
    }

    public int getSortOrder() {
      return mSortOrder;
    }

    public void setSortOrder(int sortOrder) {
      this.mSortOrder = sortOrder;
    }
  }

  public static class ProductModelEntity {
    @SerializedName("id") private int mId;
    @SerializedName("name") private String mName;
    @SerializedName("is_single_spec") private boolean mIsSingleSpec;
    @SerializedName("is_sale_out") private boolean mIsSaleOut;
    @SerializedName("buy_limit") private boolean mBuyLimit;
    @SerializedName("per_limit") private int mPerLimit;
    @SerializedName("head_imgs") private List<String> mHeadImgs;
    @SerializedName("content_imgs") private List<String> mContentImgs;

    public int getId() {
      return mId;
    }

    public void setId(int id) {
      this.mId = id;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public boolean isIsSingleSpec() {
      return mIsSingleSpec;
    }

    public void setIsSingleSpec(boolean isSingleSpec) {
      this.mIsSingleSpec = isSingleSpec;
    }

    public boolean isIsSaleOut() {
      return mIsSaleOut;
    }

    public void setIsSaleOut(boolean isSaleOut) {
      this.mIsSaleOut = isSaleOut;
    }

    public boolean isBuyLimit() {
      return mBuyLimit;
    }

    public void setBuyLimit(boolean buyLimit) {
      this.mBuyLimit = buyLimit;
    }

    public int getPerLimit() {
      return mPerLimit;
    }

    public void setPerLimit(int perLimit) {
      this.mPerLimit = perLimit;
    }

    public List<String> getHeadImgs() {
      return mHeadImgs;
    }

    public void setHeadImgs(List<String> headImgs) {
      this.mHeadImgs = headImgs;
    }

    public List<String> getContentImgs() {
      return mContentImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
      this.mContentImgs = contentImgs;
    }
  }

  public static class DetailsEntity {
    @SerializedName("mama_discount") private int mMamaDiscount;
    @SerializedName("is_recommend") private boolean mIsRecommend;
    @SerializedName("buy_limit") private boolean mBuyLimit;
    @SerializedName("per_limit") private int mPerLimit;
    @SerializedName("mama_rebeta") private int mMamaRebeta;
    @SerializedName("material") private String mMaterial;
    @SerializedName("wash_instructions") private String mWashInstructions;
    @SerializedName("note") private String mNote;
    @SerializedName("color") private String mColor;
    @SerializedName("head_imgs") private List<String> mHeadImgs;
    @SerializedName("content_imgs") private List<String> mContentImgs;

    public int getMamaDiscount() {
      return mMamaDiscount;
    }

    public void setMamaDiscount(int mamaDiscount) {
      this.mMamaDiscount = mamaDiscount;
    }

    public boolean isIsRecommend() {
      return mIsRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
      this.mIsRecommend = isRecommend;
    }

    public boolean isBuyLimit() {
      return mBuyLimit;
    }

    public void setBuyLimit(boolean buyLimit) {
      this.mBuyLimit = buyLimit;
    }

    public int getPerLimit() {
      return mPerLimit;
    }

    public void setPerLimit(int perLimit) {
      this.mPerLimit = perLimit;
    }

    public int getMamaRebeta() {
      return mMamaRebeta;
    }

    public void setMamaRebeta(int mamaRebeta) {
      this.mMamaRebeta = mamaRebeta;
    }

    public String getMaterial() {
      return mMaterial;
    }

    public void setMaterial(String material) {
      this.mMaterial = material;
    }

    public String getWashInstructions() {
      return mWashInstructions;
    }

    public void setWashInstructions(String washInstructions) {
      this.mWashInstructions = washInstructions;
    }

    public String getNote() {
      return mNote;
    }

    public void setNote(String note) {
      this.mNote = note;
    }

    public String getColor() {
      return mColor;
    }

    public void setColor(String color) {
      this.mColor = color;
    }

    public List<String> getHeadImgs() {
      return mHeadImgs;
    }

    public void setHeadImgs(List<String> headImgs) {
      this.mHeadImgs = headImgs;
    }

    public List<String> getContentImgs() {
      return mContentImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
      this.mContentImgs = contentImgs;
    }
  }

  public static class NormalSkusEntity {
    @SerializedName("id") private int mId;
    @SerializedName("outer_id") private String mOuterId;
    @SerializedName("name") private String mName;
    @SerializedName("remain_num") private int mRemainNum;
    /**
     * free_num : 0
     * result : None
     */

    @SerializedName("size_of_sku") private SizeOfSkuEntity mSizeOfSku;
    @SerializedName("is_saleout") private boolean mIsSaleout;
    @SerializedName("std_sale_price") private int mStdSalePrice;
    @SerializedName("agent_price") private double mAgentPrice;

    public int getId() {
      return mId;
    }

    public void setId(int id) {
      this.mId = id;
    }

    public String getOuterId() {
      return mOuterId;
    }

    public void setOuterId(String outerId) {
      this.mOuterId = outerId;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public int getRemainNum() {
      return mRemainNum;
    }

    public void setRemainNum(int remainNum) {
      this.mRemainNum = remainNum;
    }

    public SizeOfSkuEntity getSizeOfSku() {
      return mSizeOfSku;
    }

    public void setSizeOfSku(SizeOfSkuEntity sizeOfSku) {
      this.mSizeOfSku = sizeOfSku;
    }

    public boolean isIsSaleout() {
      return mIsSaleout;
    }

    public void setIsSaleout(boolean isSaleout) {
      this.mIsSaleout = isSaleout;
    }

    public int getStdSalePrice() {
      return mStdSalePrice;
    }

    public void setStdSalePrice(int stdSalePrice) {
      this.mStdSalePrice = stdSalePrice;
    }

    public double getAgentPrice() {
      return mAgentPrice;
    }

    public void setAgentPrice(double agentPrice) {
      this.mAgentPrice = agentPrice;
    }

    public static class SizeOfSkuEntity {
      @SerializedName("free_num") private int mFreeNum;
      @SerializedName("result") private String mResult;

      public int getFreeNum() {
        return mFreeNum;
      }

      public void setFreeNum(int freeNum) {
        this.mFreeNum = freeNum;
      }

      public String getResult() {
        return mResult;
      }

      public void setResult(String result) {
        this.mResult = result;
      }
    }
  }
}
