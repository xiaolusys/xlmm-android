package com.jimei.xiaolumeimei.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailBean {

  /**
   * id : 23092
   * url : http://api.xiaolumeimei.com/rest/v1/products/23092
   * name : 麻花针织百搭打底裤/深灰色
   * outer_id : 821267560013
   * category : {"cid":21,"parent_cid":8,"name":"下装","status":"normal","sort_order":100}
   * pic_path : http://image.xiaolu.so/MG-1451984167745-麻花针织百搭打底裤04.jpg
   * remain_num : 100
   * is_saleout : false
   * head_img : http://image.xiaolu.so/MG-1451984152449-麻花针织百搭打底裤01.jpg
   * is_saleopen : true
   * is_newgood : true
   * std_sale_price : 199
   * agent_price : 39.9
   * sale_time : 2016-01-06
   * offshelf_time : null
   * memo :
   * lowest_price : 39.9
   * product_lowest_price : 39.9
   * product_model : {"id":3901,"name":"麻花针织百搭打底裤","head_imgs":["http://image.xiaolu.so/MG-1451984152449-麻花针织百搭打底裤01.jpg"],"content_imgs":["http://image.xiaolu.so/MG-1451984189097-麻花针织百搭打底裤08.jpg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5}
   * ware_by : 2
   * is_verify : true
   * model_id : 3901
   * normal_skus : [{"id":90509,"outer_id":"1","name":"均码","remain_num":100,"size_of_sku":{"free_num":"NO","result":{"裤腰":"44-100","臀围":"50-114","裤长":"91"}},"is_saleout":false,"std_sale_price":199,"agent_price":39.9}]
   * details : {"head_imgs":["http://image.xiaolu.so/MG-1451984167745-麻花针织百搭打底裤04.jpg"],"content_imgs":[],"mama_discount":100,"is_recommend":false,"buy_limit":false,"per_limit":5,"mama_rebeta":0,"material":"针织","wash_instructions":"最高手洗温度为30度，翻面洗涤，减少摩擦，避免起球。使用中性洗涤剂， 平铺晾干，勿暴晒、熨烫。","note":"麻花针织，修身高弹，纯色显瘦。","color":"黑色,浅灰色,深灰色,墨绿色,咖啡色,酒红色"}
   */

  @SerializedName("id") private int mId;
  @SerializedName("url") private String mUrl;
  @SerializedName("name") private String mName;
  @SerializedName("outer_id") private String mOuterId;
  /**
   * cid : 21
   * parent_cid : 8
   * name : 下装
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
   * id : 3901
   * name : 麻花针织百搭打底裤
   * head_imgs : ["http://image.xiaolu.so/MG-1451984152449-麻花针织百搭打底裤01.jpg"]
   * content_imgs : ["http://image.xiaolu.so/MG-1451984189097-麻花针织百搭打底裤08.jpg"]
   * is_single_spec : false
   * is_sale_out : false
   * buy_limit : false
   * per_limit : 5
   */

  @SerializedName("product_model") private ProductModelEntity mProductModel;
  @SerializedName("ware_by") private int mWareBy;
  @SerializedName("is_verify") private boolean mIsVerify;
  @SerializedName("model_id") private int mModelId;
  /**
   * head_imgs : ["http://image.xiaolu.so/MG-1451984167745-麻花针织百搭打底裤04.jpg"]
   * content_imgs : []
   * mama_discount : 100
   * is_recommend : false
   * buy_limit : false
   * per_limit : 5
   * mama_rebeta : 0
   * material : 针织
   * wash_instructions : 最高手洗温度为30度，翻面洗涤，减少摩擦，避免起球。使用中性洗涤剂， 平铺晾干，勿暴晒、熨烫。
   * note : 麻花针织，修身高弹，纯色显瘦。
   * color : 黑色,浅灰色,深灰色,墨绿色,咖啡色,酒红色
   */

  @SerializedName("details") private DetailsEntity mDetails;
  /**
   * id : 90509
   * outer_id : 1
   * name : 均码
   * remain_num : 100
   * size_of_sku : {"free_num":"NO","result":{"裤腰":"44-100","臀围":"50-114","裤长":"91"}}
   * is_saleout : false
   * std_sale_price : 199
   * agent_price : 39.9
   */

  @SerializedName("normal_skus") private List<NormalSkusEntity> mNormalSkus;

  public void setId(int id) {
    this.mId = id;
  }

  public void setUrl(String url) {
    this.mUrl = url;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public void setOuterId(String outerId) {
    this.mOuterId = outerId;
  }

  public void setCategory(CategoryEntity category) {
    this.mCategory = category;
  }

  public void setPicPath(String picPath) {
    this.mPicPath = picPath;
  }

  public void setRemainNum(int remainNum) {
    this.mRemainNum = remainNum;
  }

  public void setIsSaleout(boolean isSaleout) {
    this.mIsSaleout = isSaleout;
  }

  public void setHeadImg(String headImg) {
    this.mHeadImg = headImg;
  }

  public void setIsSaleopen(boolean isSaleopen) {
    this.mIsSaleopen = isSaleopen;
  }

  public void setIsNewgood(boolean isNewgood) {
    this.mIsNewgood = isNewgood;
  }

  public void setStdSalePrice(int stdSalePrice) {
    this.mStdSalePrice = stdSalePrice;
  }

  public void setAgentPrice(double agentPrice) {
    this.mAgentPrice = agentPrice;
  }

  public void setSaleTime(String saleTime) {
    this.mSaleTime = saleTime;
  }

  public void setOffshelfTime(Object offshelfTime) {
    this.mOffshelfTime = offshelfTime;
  }

  public void setMemo(String memo) {
    this.mMemo = memo;
  }

  public void setLowestPrice(double lowestPrice) {
    this.mLowestPrice = lowestPrice;
  }

  public void setProductLowestPrice(double productLowestPrice) {
    this.mProductLowestPrice = productLowestPrice;
  }

  public void setProductModel(ProductModelEntity productModel) {
    this.mProductModel = productModel;
  }

  public void setWareBy(int wareBy) {
    this.mWareBy = wareBy;
  }

  public void setIsVerify(boolean isVerify) {
    this.mIsVerify = isVerify;
  }

  public void setModelId(int modelId) {
    this.mModelId = modelId;
  }

  public void setDetails(DetailsEntity details) {
    this.mDetails = details;
  }

  public void setNormalSkus(List<NormalSkusEntity> normalSkus) {
    this.mNormalSkus = normalSkus;
  }

  public int getId() {
    return mId;
  }

  public String getUrl() {
    return mUrl;
  }

  public String getName() {
    return mName;
  }

  public String getOuterId() {
    return mOuterId;
  }

  public CategoryEntity getCategory() {
    return mCategory;
  }

  public String getPicPath() {
    return mPicPath;
  }

  public int getRemainNum() {
    return mRemainNum;
  }

  public boolean isIsSaleout() {
    return mIsSaleout;
  }

  public String getHeadImg() {
    return mHeadImg;
  }

  public boolean isIsSaleopen() {
    return mIsSaleopen;
  }

  public boolean isIsNewgood() {
    return mIsNewgood;
  }

  public int getStdSalePrice() {
    return mStdSalePrice;
  }

  public double getAgentPrice() {
    return mAgentPrice;
  }

  public String getSaleTime() {
    return mSaleTime;
  }

  public Object getOffshelfTime() {
    return mOffshelfTime;
  }

  public String getMemo() {
    return mMemo;
  }

  public double getLowestPrice() {
    return mLowestPrice;
  }

  public double getProductLowestPrice() {
    return mProductLowestPrice;
  }

  public ProductModelEntity getProductModel() {
    return mProductModel;
  }

  public int getWareBy() {
    return mWareBy;
  }

  public boolean isIsVerify() {
    return mIsVerify;
  }

  public int getModelId() {
    return mModelId;
  }

  public DetailsEntity getDetails() {
    return mDetails;
  }

  public List<NormalSkusEntity> getNormalSkus() {
    return mNormalSkus;
  }

  public static class CategoryEntity {
    @SerializedName("cid") private int mCid;
    @SerializedName("parent_cid") private int mParentCid;
    @SerializedName("name") private String mName;
    @SerializedName("status") private String mStatus;
    @SerializedName("sort_order") private int mSortOrder;

    public void setCid(int cid) {
      this.mCid = cid;
    }

    public void setParentCid(int parentCid) {
      this.mParentCid = parentCid;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public void setStatus(String status) {
      this.mStatus = status;
    }

    public void setSortOrder(int sortOrder) {
      this.mSortOrder = sortOrder;
    }

    public int getCid() {
      return mCid;
    }

    public int getParentCid() {
      return mParentCid;
    }

    public String getName() {
      return mName;
    }

    public String getStatus() {
      return mStatus;
    }

    public int getSortOrder() {
      return mSortOrder;
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

    public void setId(int id) {
      this.mId = id;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public void setIsSingleSpec(boolean isSingleSpec) {
      this.mIsSingleSpec = isSingleSpec;
    }

    public void setIsSaleOut(boolean isSaleOut) {
      this.mIsSaleOut = isSaleOut;
    }

    public void setBuyLimit(boolean buyLimit) {
      this.mBuyLimit = buyLimit;
    }

    public void setPerLimit(int perLimit) {
      this.mPerLimit = perLimit;
    }

    public void setHeadImgs(List<String> headImgs) {
      this.mHeadImgs = headImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
      this.mContentImgs = contentImgs;
    }

    public int getId() {
      return mId;
    }

    public String getName() {
      return mName;
    }

    public boolean isIsSingleSpec() {
      return mIsSingleSpec;
    }

    public boolean isIsSaleOut() {
      return mIsSaleOut;
    }

    public boolean isBuyLimit() {
      return mBuyLimit;
    }

    public int getPerLimit() {
      return mPerLimit;
    }

    public List<String> getHeadImgs() {
      return mHeadImgs;
    }

    public List<String> getContentImgs() {
      return mContentImgs;
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

    public void setMamaDiscount(int mamaDiscount) {
      this.mMamaDiscount = mamaDiscount;
    }

    public void setIsRecommend(boolean isRecommend) {
      this.mIsRecommend = isRecommend;
    }

    public void setBuyLimit(boolean buyLimit) {
      this.mBuyLimit = buyLimit;
    }

    public void setPerLimit(int perLimit) {
      this.mPerLimit = perLimit;
    }

    public void setMamaRebeta(int mamaRebeta) {
      this.mMamaRebeta = mamaRebeta;
    }

    public void setMaterial(String material) {
      this.mMaterial = material;
    }

    public void setWashInstructions(String washInstructions) {
      this.mWashInstructions = washInstructions;
    }

    public void setNote(String note) {
      this.mNote = note;
    }

    public void setColor(String color) {
      this.mColor = color;
    }

    public void setHeadImgs(List<String> headImgs) {
      this.mHeadImgs = headImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
      this.mContentImgs = contentImgs;
    }

    public int getMamaDiscount() {
      return mMamaDiscount;
    }

    public boolean isIsRecommend() {
      return mIsRecommend;
    }

    public boolean isBuyLimit() {
      return mBuyLimit;
    }

    public int getPerLimit() {
      return mPerLimit;
    }

    public int getMamaRebeta() {
      return mMamaRebeta;
    }

    public String getMaterial() {
      return mMaterial;
    }

    public String getWashInstructions() {
      return mWashInstructions;
    }

    public String getNote() {
      return mNote;
    }

    public String getColor() {
      return mColor;
    }

    public List<String> getHeadImgs() {
      return mHeadImgs;
    }

    public List<String> getContentImgs() {
      return mContentImgs;
    }
  }

  public static class NormalSkusEntity {
    @SerializedName("id") private int mId;
    @SerializedName("outer_id") private String mOuterId;
    @SerializedName("name") private String mName;
    @SerializedName("remain_num") private int mRemainNum;
    /**
     * free_num : NO
     * result : {"裤腰":"44-100","臀围":"50-114","裤长":"91"}
     */

    @SerializedName("size_of_sku") private SizeOfSkuEntity mSizeOfSku;
    @SerializedName("is_saleout") private boolean mIsSaleout;
    @SerializedName("std_sale_price") private int mStdSalePrice;
    @SerializedName("agent_price") private double mAgentPrice;

    public void setId(int id) {
      this.mId = id;
    }

    public void setOuterId(String outerId) {
      this.mOuterId = outerId;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public void setRemainNum(int remainNum) {
      this.mRemainNum = remainNum;
    }

    public void setSizeOfSku(SizeOfSkuEntity sizeOfSku) {
      this.mSizeOfSku = sizeOfSku;
    }

    public void setIsSaleout(boolean isSaleout) {
      this.mIsSaleout = isSaleout;
    }

    public void setStdSalePrice(int stdSalePrice) {
      this.mStdSalePrice = stdSalePrice;
    }

    public void setAgentPrice(double agentPrice) {
      this.mAgentPrice = agentPrice;
    }

    public int getId() {
      return mId;
    }

    public String getOuterId() {
      return mOuterId;
    }

    public String getName() {
      return mName;
    }

    public int getRemainNum() {
      return mRemainNum;
    }

    public SizeOfSkuEntity getSizeOfSku() {
      return mSizeOfSku;
    }

    public boolean isIsSaleout() {
      return mIsSaleout;
    }

    public int getStdSalePrice() {
      return mStdSalePrice;
    }

    public double getAgentPrice() {
      return mAgentPrice;
    }

    public static class SizeOfSkuEntity {
      @SerializedName("free_num") private String mFreeNum;
      /**
       * 裤腰 : 44-100
       * 臀围 : 50-114
       * 裤长 : 91
       */

      @SerializedName("result") private ResultEntity mResult;

      public void setFreeNum(String freeNum) {
        this.mFreeNum = freeNum;
      }

      public void setResult(ResultEntity result) {
        this.mResult = result;
      }

      public String getFreeNum() {
        return mFreeNum;
      }

      public ResultEntity getResult() {
        return mResult;
      }

      public static class ResultEntity {
        @SerializedName("裤腰") private String m裤腰;
        @SerializedName("臀围") private String m臀围;
        @SerializedName("裤长") private String m裤长;

        public void set裤腰(String 裤腰) {
          this.m裤腰 = 裤腰;
        }

        public void set臀围(String 臀围) {
          this.m臀围 = 臀围;
        }

        public void set裤长(String 裤长) {
          this.m裤长 = 裤长;
        }

        public String get裤腰() {
          return m裤腰;
        }

        public String get臀围() {
          return m臀围;
        }

        public String get裤长() {
          return m裤长;
        }
      }
    }
  }
}
