package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductListOldBean {

  @SerializedName("count") private int mCount;
  @SerializedName("next") private Object mNext;
  @SerializedName("downshelf_deadline") private String mDownshelfDeadline;
  @SerializedName("upshelf_starttime") private String mUpshelfStarttime;
  @SerializedName("results") private List<ResultsEntity> mResults;

  public String getUpshelfStarttime() {
    return mUpshelfStarttime;
  }

  public String getDownshelfDeadline() {
    return mDownshelfDeadline;
  }

  public int getCount() {
    return mCount;
  }

  public void setCount(int count) {
    this.mCount = count;
  }

  public Object getNext() {
    return mNext;
  }

  public void setNext(Object next) {
    this.mNext = next;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public void setResults(List<ResultsEntity> results) {
    this.mResults = results;
  }

  public static class ResultsEntity {
    @SerializedName("id") private String mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("name") private String mName;
    @SerializedName("outer_id") private String mOuterId;
    /**
     * cid : 22
     * parent_cid : 8
     * name : 套装
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
    @SerializedName("std_sale_price") private double mStdSalePrice;
    @SerializedName("agent_price") private double mAgentPrice;
    @SerializedName("sale_time") private String mSaleTime;
    @SerializedName("offshelf_time") private Object mOffshelfTime;
    @SerializedName("memo") private String mMemo;
    @SerializedName("lowest_price") private double mLowestPrice;
    @SerializedName("product_lowest_price") private double mProductLowestPrice;
    /**
     * id : 6811
     * name : 秒杀 韩版撞色时尚条纹系列套装
     * head_imgs : ["http://image.xiaolu.so/MG-1451396496306-0秒杀.png"]
     * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQib9pascibFYl1iaMlc587ox9fqU2J5Jbq1tMbWAQte8PZ8oCLhRw0pYnQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQUtcla1fCLlg82yUAOnibDRW04Dwag0cWibOlq4ZiaiaTmymhuD3IJibibecw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQWIBdubgwcDXyLhtcSOhQhrz6rjogibZrHztQbClVcXKp9L0yetvianIw/0?wx_fmt=jpeg"]
     * is_single_spec : false
     * is_sale_out : true
     * buy_limit : false
     * per_limit : 5
     */

    @SerializedName("product_model") private ProductModelEntity mProductModel;
    @SerializedName("ware_by") private int mWareBy;
    @SerializedName("is_verify") private boolean mIsVerify;
    @SerializedName("model_id") private int mModelId;
    @SerializedName("web_url") private String mWebUrl;

    public String getWebUrl() {
      return mWebUrl;
    }

    public String getId() {
      return mId;
    }

    public void setId(String id) {
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

    public double getStdSalePrice() {
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
      @SerializedName("id") private String mId;
      @SerializedName("name") private String mName;
      @SerializedName("is_single_spec") private boolean mIsSingleSpec;
      @SerializedName("is_sale_out") private boolean mIsSaleOut;
      @SerializedName("buy_limit") private boolean mBuyLimit;
      @SerializedName("per_limit") private int mPerLimit;
      @SerializedName("head_imgs") private List<String> mHeadImgs;
      @SerializedName("content_imgs") private List<String> mContentImgs;

      public String getId() {
        return mId;
      }

      public void setId(String id) {
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
  }
}
