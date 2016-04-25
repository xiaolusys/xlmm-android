package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/23.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BrandpromotionBean {

  /**
   * count : 50
   * next : null
   * previous : null
   * results : [{"id":36572,"url":"http://staging.xiaolumeimei.com/rest/v1/products/36572","name":"春款V领防晒空调衫/黑色","outer_id":"818135070011","category":{"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100},"pic_path":"http://image.xiaolu.so/MG_1458629408690头图背景1.png","remain_num":80,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG_1458629404057头图背景.png","is_saleopen":true,"is_newgood":false,"std_sale_price":309,"agent_price":69.9,"sale_time":"2016-03-23","offshelf_time":"2016-03-25T10:00:00","memo":"","lowest_price":69.9,"product_lowest_price":69.9,"product_model":{"id":9853,"name":"春款V领防晒空调衫","head_imgs":["http://image.xiaolu.so/MG_1458629404057头图背景.png"],"content_imgs":["http://image.xiaolu.so/MG_1458629412932详情页_01.jpg","http://image.xiaolu.so/MG_1458629418368详情页_02.jpg","http://image.xiaolu.so/MG_1458629418929详情页_03.jpg","http://image.xiaolu.so/MG_1458629419756详情页_04.jpg","http://image.xiaolu.so/MG_1458629420065详情页_05.jpg","http://image.xiaolu.so/MG_1458629420386详情页_06.jpg","http://image.xiaolu.so/MG_1458629421296详情页_07.jpg","http://image.xiaolu.so/MG_1458629422168详情页_08.jpg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":true,"model_id":9853,"watermark_op":""}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private Object mNext;
  @SerializedName("previous") private Object mPrevious;

  @Override public String toString() {
    return "BrandpromotionBean{" +
        "mCount=" + mCount +
        ", mNext=" + mNext +
        ", mPrevious=" + mPrevious +
        ", mResults=" + mResults +
        '}';
  }

  /**
   * id : 36572
   * url : http://staging.xiaolumeimei.com/rest/v1/products/36572
   * name : 春款V领防晒空调衫/黑色
   * outer_id : 818135070011
   * category : {"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100}
   * pic_path : http://image.xiaolu.so/MG_1458629408690头图背景1.png
   * remain_num : 80
   * is_saleout : false
   * head_img : http://image.xiaolu.so/MG_1458629404057头图背景.png
   * is_saleopen : true
   * is_newgood : false
   * std_sale_price : 309.0
   * agent_price : 69.9
   * sale_time : 2016-03-23
   * offshelf_time : 2016-03-25T10:00:00
   * memo :
   * lowest_price : 69.9
   * product_lowest_price : 69.9
   * product_model : {"id":9853,"name":"春款V领防晒空调衫","head_imgs":["http://image.xiaolu.so/MG_1458629404057头图背景.png"],"content_imgs":["http://image.xiaolu.so/MG_1458629412932详情页_01.jpg","http://image.xiaolu.so/MG_1458629418368详情页_02.jpg","http://image.xiaolu.so/MG_1458629418929详情页_03.jpg","http://image.xiaolu.so/MG_1458629419756详情页_04.jpg","http://image.xiaolu.so/MG_1458629420065详情页_05.jpg","http://image.xiaolu.so/MG_1458629420386详情页_06.jpg","http://image.xiaolu.so/MG_1458629421296详情页_07.jpg","http://image.xiaolu.so/MG_1458629422168详情页_08.jpg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5}
   * ware_by : 2
   * is_verify : true
   * model_id : 9853
   * watermark_op :
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public int getCount() {
    return mCount;
  }

  public void setCount(int count) {
    mCount = count;
  }

  public Object getNext() {
    return mNext;
  }

  public void setNext(Object next) {
    mNext = next;
  }

  public Object getPrevious() {
    return mPrevious;
  }

  public void setPrevious(Object previous) {
    mPrevious = previous;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public void setResults(List<ResultsEntity> results) {
    mResults = results;
  }

  public static class ResultsEntity {
    @SerializedName("id") private int mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("name") private String mName;
    @SerializedName("outer_id") private String mOuterId;
    /**
     * cid : 18
     * parent_cid : 8
     * name : 外套
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
    @SerializedName("offshelf_time") private String mOffshelfTime;
    @SerializedName("memo") private String mMemo;
    @SerializedName("lowest_price") private double mLowestPrice;
    @SerializedName("product_lowest_price") private double mProductLowestPrice;
    /**
     * id : 9853
     * name : 春款V领防晒空调衫
     * head_imgs : ["http://image.xiaolu.so/MG_1458629404057头图背景.png"]
     * content_imgs : ["http://image.xiaolu.so/MG_1458629412932详情页_01.jpg","http://image.xiaolu.so/MG_1458629418368详情页_02.jpg","http://image.xiaolu.so/MG_1458629418929详情页_03.jpg","http://image.xiaolu.so/MG_1458629419756详情页_04.jpg","http://image.xiaolu.so/MG_1458629420065详情页_05.jpg","http://image.xiaolu.so/MG_1458629420386详情页_06.jpg","http://image.xiaolu.so/MG_1458629421296详情页_07.jpg","http://image.xiaolu.so/MG_1458629422168详情页_08.jpg"]
     * is_single_spec : true
     * is_sale_out : false
     * buy_limit : false
     * per_limit : 5
     */

    @SerializedName("product_model") private ProductModelEntity mProductModel;
    @SerializedName("ware_by") private int mWareBy;
    @SerializedName("is_verify") private boolean mIsVerify;
    @SerializedName("model_id") private int mModelId;
    @SerializedName("watermark_op") private String mWatermarkOp;

    public int getId() {
      return mId;
    }

    public void setId(int id) {
      mId = id;
    }

    public String getUrl() {
      return mUrl;
    }

    public void setUrl(String url) {
      mUrl = url;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      mName = name;
    }

    public String getOuterId() {
      return mOuterId;
    }

    public void setOuterId(String outerId) {
      mOuterId = outerId;
    }

    public CategoryEntity getCategory() {
      return mCategory;
    }

    public void setCategory(CategoryEntity category) {
      mCategory = category;
    }

    public String getPicPath() {
      return mPicPath;
    }

    public void setPicPath(String picPath) {
      mPicPath = picPath;
    }

    public int getRemainNum() {
      return mRemainNum;
    }

    public void setRemainNum(int remainNum) {
      mRemainNum = remainNum;
    }

    public boolean isIsSaleout() {
      return mIsSaleout;
    }

    public void setIsSaleout(boolean isSaleout) {
      mIsSaleout = isSaleout;
    }

    public String getHeadImg() {
      return mHeadImg;
    }

    public void setHeadImg(String headImg) {
      mHeadImg = headImg;
    }

    public boolean isIsSaleopen() {
      return mIsSaleopen;
    }

    public void setIsSaleopen(boolean isSaleopen) {
      mIsSaleopen = isSaleopen;
    }

    public boolean isIsNewgood() {
      return mIsNewgood;
    }

    public void setIsNewgood(boolean isNewgood) {
      mIsNewgood = isNewgood;
    }

    public double getStdSalePrice() {
      return mStdSalePrice;
    }

    public void setStdSalePrice(double stdSalePrice) {
      mStdSalePrice = stdSalePrice;
    }

    public double getAgentPrice() {
      return mAgentPrice;
    }

    public void setAgentPrice(double agentPrice) {
      mAgentPrice = agentPrice;
    }

    public String getSaleTime() {
      return mSaleTime;
    }

    public void setSaleTime(String saleTime) {
      mSaleTime = saleTime;
    }

    public String getOffshelfTime() {
      return mOffshelfTime;
    }

    public void setOffshelfTime(String offshelfTime) {
      mOffshelfTime = offshelfTime;
    }

    public String getMemo() {
      return mMemo;
    }

    public void setMemo(String memo) {
      mMemo = memo;
    }

    public double getLowestPrice() {
      return mLowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
      mLowestPrice = lowestPrice;
    }

    public double getProductLowestPrice() {
      return mProductLowestPrice;
    }

    public void setProductLowestPrice(double productLowestPrice) {
      mProductLowestPrice = productLowestPrice;
    }

    public ProductModelEntity getProductModel() {
      return mProductModel;
    }

    public void setProductModel(ProductModelEntity productModel) {
      mProductModel = productModel;
    }

    public int getWareBy() {
      return mWareBy;
    }

    public void setWareBy(int wareBy) {
      mWareBy = wareBy;
    }

    public boolean isIsVerify() {
      return mIsVerify;
    }

    public void setIsVerify(boolean isVerify) {
      mIsVerify = isVerify;
    }

    public int getModelId() {
      return mModelId;
    }

    public void setModelId(int modelId) {
      mModelId = modelId;
    }

    public String getWatermarkOp() {
      return mWatermarkOp;
    }

    public void setWatermarkOp(String watermarkOp) {
      mWatermarkOp = watermarkOp;
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
        mCid = cid;
      }

      public int getParentCid() {
        return mParentCid;
      }

      public void setParentCid(int parentCid) {
        mParentCid = parentCid;
      }

      public String getName() {
        return mName;
      }

      public void setName(String name) {
        mName = name;
      }

      public String getStatus() {
        return mStatus;
      }

      public void setStatus(String status) {
        mStatus = status;
      }

      public int getSortOrder() {
        return mSortOrder;
      }

      public void setSortOrder(int sortOrder) {
        mSortOrder = sortOrder;
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
        mId = id;
      }

      public String getName() {
        return mName;
      }

      public void setName(String name) {
        mName = name;
      }

      public boolean isIsSingleSpec() {
        return mIsSingleSpec;
      }

      public void setIsSingleSpec(boolean isSingleSpec) {
        mIsSingleSpec = isSingleSpec;
      }

      public boolean isIsSaleOut() {
        return mIsSaleOut;
      }

      public void setIsSaleOut(boolean isSaleOut) {
        mIsSaleOut = isSaleOut;
      }

      public boolean isBuyLimit() {
        return mBuyLimit;
      }

      public void setBuyLimit(boolean buyLimit) {
        mBuyLimit = buyLimit;
      }

      public int getPerLimit() {
        return mPerLimit;
      }

      public void setPerLimit(int perLimit) {
        mPerLimit = perLimit;
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
    }

    @Override public String toString() {
      return "ResultsEntity{" +
          "mId=" + mId +
          ", mUrl='" + mUrl + '\'' +
          ", mName='" + mName + '\'' +
          ", mOuterId='" + mOuterId + '\'' +
          ", mCategory=" + mCategory +
          ", mPicPath='" + mPicPath + '\'' +
          ", mRemainNum=" + mRemainNum +
          ", mIsSaleout=" + mIsSaleout +
          ", mHeadImg='" + mHeadImg + '\'' +
          ", mIsSaleopen=" + mIsSaleopen +
          ", mIsNewgood=" + mIsNewgood +
          ", mStdSalePrice=" + mStdSalePrice +
          ", mAgentPrice=" + mAgentPrice +
          ", mSaleTime='" + mSaleTime + '\'' +
          ", mOffshelfTime='" + mOffshelfTime + '\'' +
          ", mMemo='" + mMemo + '\'' +
          ", mLowestPrice=" + mLowestPrice +
          ", mProductLowestPrice=" + mProductLowestPrice +
          ", mProductModel=" + mProductModel +
          ", mWareBy=" + mWareBy +
          ", mIsVerify=" + mIsVerify +
          ", mModelId=" + mModelId +
          ", mWatermarkOp='" + mWatermarkOp + '\'' +
          '}';
    }
  }
}
