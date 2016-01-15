package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailBean {

  @SerializedName("id") private String mId;
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
  @SerializedName("std_sale_price") private int mStdSalePrice;
  @SerializedName("agent_price") private double mAgentPrice;
  @SerializedName("sale_time") private String mSaleTime;
  @SerializedName("offshelf_time") private Object mOffshelfTime;
  @SerializedName("memo") private String mMemo;
  @SerializedName("lowest_price") private double mLowestPrice;
  @SerializedName("product_lowest_price") private double mProductLowestPrice;
  /**
   * id : 4397
   * name : 六粒扣中长款针织外套
   * head_imgs : ["http://image.xiaolu.so/MG-1452585002171-1.jpg"]
   * content_imgs : ["http://image.xiaolu.so/MG-1452509854473-827新内容页bbb_01.jpg","http://image.xiaolu.so/MG-1452509859635-827新内容页bbb_02.jpg","http://image.xiaolu.so/MG-1452509863681-827新内容页bbb_03.jpg"]
   * is_single_spec : false
   * is_sale_out : false
   * buy_limit : false
   * per_limit : 5
   */

  @SerializedName("product_model") private ProductModelEntity mProductModel;
  @SerializedName("ware_by") private int mWareBy;
  @SerializedName("is_verify") private boolean mIsVerify;
  @SerializedName("model_id") private int mModelId;
  @SerializedName("watermark_op") private String mWatermarkOp;
  /**
   * head_imgs : ["http://image.xiaolu.so/MG-1452509571614-2.jpg"]
   * content_imgs : []
   * mama_discount : 100
   * is_recommend : false
   * buy_limit : false
   * per_limit : 5
   * mama_rebeta : 0
   * material : 针织混纺
   * wash_instructions : 最高手洗温度为30度，翻面洗涤，减少摩擦，避免起球。使用中性洗涤剂， 平铺晾干，勿暴晒、熨烫。
   * note : 时尚六粒扣设计，常规袖，修身显瘦。
   * color : 黑色,粉色,米色,黄色,酒红色
   */

  @SerializedName("details") private DetailsEntity mDetails;
  /**
   * id : 96343
   * outer_id : 1
   * name : 均码
   * remain_num : 100
   * size_of_sku : {"free_num":"NO","result":{"肩宽":"37","胸围":"92","袖长":"55","衣长":"75"}}
   * is_saleout : false
   * std_sale_price : 349
   * agent_price : 69.9
   */

  @SerializedName("normal_skus") private List<NormalSkusEntity> mNormalSkus;

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

  public String getWatermarkOp() {
    return mWatermarkOp;
  }

  public void setWatermarkOp(String watermarkOp) {
    this.mWatermarkOp = watermarkOp;
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
    @SerializedName("id") private String mId;
    @SerializedName("outer_id") private String mOuterId;
    @SerializedName("name") private String mName;
    @SerializedName("remain_num") private int mRemainNum;
    /**
     * free_num : NO
     * result : {"肩宽":"37","胸围":"92","袖长":"55","衣长":"75"}
     */

    @SerializedName("size_of_sku") private SizeOfSkuEntity mSizeOfSku;
    @SerializedName("is_saleout") private boolean mIsSaleout;
    @SerializedName("std_sale_price") private int mStdSalePrice;
    @SerializedName("agent_price") private double mAgentPrice;

    public String getId() {
      return mId;
    }

    public void setId(String id) {
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
      @SerializedName("free_num") private String mFreeNum;
      /**
       * 肩宽 : 37
       * 胸围 : 92
       * 袖长 : 55
       * 衣长 : 75
       */

      //@SerializedName("result") private ResultEntity mResult;

      public String getFreeNum() {
        return mFreeNum;
      }

      public void setFreeNum(String freeNum) {
        this.mFreeNum = freeNum;
      }

      //public ResultEntity getResult() {
      //  return mResult;
      //}
      //
      //public void setResult(ResultEntity result) {
      //  this.mResult = result;
      //}

      //public static class ResultEntity {
      //  @SerializedName("肩宽") private String m肩宽;
      //  @SerializedName("胸围") private String m胸围;
      //  @SerializedName("袖长") private String m袖长;
      //  @SerializedName("衣长") private String m衣长;
      //
      //  public String get肩宽() {
      //    return m肩宽;
      //  }
      //
      //  public void set肩宽(String 肩宽) {
      //    this.m肩宽 = 肩宽;
      //  }
      //
      //  public String get胸围() {
      //    return m胸围;
      //  }
      //
      //  public void set胸围(String 胸围) {
      //    this.m胸围 = 胸围;
      //  }
      //
      //  public String get袖长() {
      //    return m袖长;
      //  }
      //
      //  public void set袖长(String 袖长) {
      //    this.m袖长 = 袖长;
      //  }
      //
      //  public String get衣长() {
      //    return m衣长;
      //  }
      //
      //  public void set衣长(String 衣长) {
      //    this.m衣长 = 衣长;
      //  }
      //}
    }
  }
}

