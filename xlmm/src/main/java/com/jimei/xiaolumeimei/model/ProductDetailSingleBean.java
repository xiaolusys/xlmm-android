package com.jimei.xiaolumeimei.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailSingleBean {

  /**
   * id : 4124
   * url : http://api.xiaolumeimei.com/rest/v1/products/4124.json
   * name : 韩版五角星圆领防晒衫-白色
   * outer_id : 8020333301
   * category : {"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50}
   * pic_path : https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZPJ5nva4C3d6brNftWxic89b7mHIkZVdlHlm41vbicZHoSlkvAYqZThmg/0?wx_fmt=jpeg
   * remain_num : 7
   * is_saleout : false
   * head_img : https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZPJ5nva4C3d6brNftWxic89b7mHIkZVdlHlm41vbicZHoSlkvAYqZThmg/0?wx_fmt=jpeg
   * is_saleopen : false
   * is_newgood : false
   * std_sale_price : 199
   * agent_price : 39
   * sale_time : 2015-05-22
   * offshelf_time : null
   * memo :
   * lowest_price : 39
   * product_lowest_price : 39
   * product_model : null
   * ware_by : 1
   * is_verify : false
   * model_id : null
   * watermark_op :
   * normal_skus : [{"id":17070,"outer_id":"1","name":"100","remain_num":1,"size_of_sku":{"free_num":1,"result":"None"},"is_saleout":false,"std_sale_price":199,"agent_price":39},{"id":17071,"outer_id":"2","name":"110","remain_num":0,"size_of_sku":{"free_num":0,"result":"None"},"is_saleout":true,"std_sale_price":199,"agent_price":39},{"id":17072,"outer_id":"3","name":"120","remain_num":1,"size_of_sku":{"free_num":1,"result":"None"},"is_saleout":false,"std_sale_price":199,"agent_price":39},{"id":17073,"outer_id":"4","name":"130","remain_num":3,"size_of_sku":{"free_num":3,"result":"None"},"is_saleout":false,"std_sale_price":199,"agent_price":39},{"id":17074,"outer_id":"5","name":"140","remain_num":2,"size_of_sku":{"free_num":2,"result":"None"},"is_saleout":false,"std_sale_price":199,"agent_price":39}]
   * details : {"head_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZPJ5nva4C3d6brNftWxic89b7mHIkZVdlHlm41vbicZHoSlkvAYqZThmg/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZp7brxz5BX3tT7CoAgWJzvzU3bNE2tA2fuP61aic4ASgksL3CSWXHMfA/0?wx_fmt=jpeg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZDQE1gLLohPSrFFke1FXPDHnUaDhtLRB3l3fwzoBTD1Vu3YSibhnW8gw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZlj6crf7zZ62x3sCTsKfVSFBlD5ia51dmKAVSkJZBhJDyJVCHzOqPGNA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZNEWYjKBSaqGoGgNFv04T6PDFkrRpr83ibxDkTAMrjWNTglZ4WXBSThw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZpdym8gKSDGozZHB62PEUEgiacZ68HPicayxFdcfakewdhAkLjRFctbibg/0?wx_fmt=jpeg"],"mama_discount":100,"is_recommend":false,"buy_limit":false,"per_limit":5,"mama_rebeta":0,"material":null,"wash_instructions":null,"note":null,"color":null}
   */

  @SerializedName("id") private int mId;
  @SerializedName("url") private String mUrl;
  @SerializedName("name") private String mName;
  @SerializedName("outer_id") private String mOuterId;
  /**
   * cid : 5
   * parent_cid : 4
   * name : 童装
   * status : normal
   * sort_order : 50
   */

  @SerializedName("category") private CategoryEntity mCategory;
  @SerializedName("pic_path") private String mPicPath;
  @SerializedName("remain_num") private int mRemainNum;
  @SerializedName("is_saleout") private boolean mIsSaleout;
  @SerializedName("head_img") private String mHeadImg;
  @SerializedName("is_saleopen") private boolean mIsSaleopen;
  @SerializedName("is_newgood") private boolean mIsNewgood;
  @SerializedName("std_sale_price") private int mStdSalePrice;
  @SerializedName("agent_price") private int mAgentPrice;
  @SerializedName("sale_time") private String mSaleTime;
  @SerializedName("offshelf_time") private Object mOffshelfTime;
  @SerializedName("memo") private String mMemo;
  @SerializedName("lowest_price") private int mLowestPrice;
  @SerializedName("product_lowest_price") private int mProductLowestPrice;
  @SerializedName("product_model") private Object mProductModel;
  @SerializedName("ware_by") private int mWareBy;
  @SerializedName("is_verify") private boolean mIsVerify;
  @SerializedName("model_id") private Object mModelId;
  @SerializedName("watermark_op") private String mWatermarkOp;
  /**
   * head_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZPJ5nva4C3d6brNftWxic89b7mHIkZVdlHlm41vbicZHoSlkvAYqZThmg/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZp7brxz5BX3tT7CoAgWJzvzU3bNE2tA2fuP61aic4ASgksL3CSWXHMfA/0?wx_fmt=jpeg"]
   * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZDQE1gLLohPSrFFke1FXPDHnUaDhtLRB3l3fwzoBTD1Vu3YSibhnW8gw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZlj6crf7zZ62x3sCTsKfVSFBlD5ia51dmKAVSkJZBhJDyJVCHzOqPGNA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZNEWYjKBSaqGoGgNFv04T6PDFkrRpr83ibxDkTAMrjWNTglZ4WXBSThw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs3DID6OmflBW9SDFBHYZhZpdym8gKSDGozZHB62PEUEgiacZ68HPicayxFdcfakewdhAkLjRFctbibg/0?wx_fmt=jpeg"]
   * mama_discount : 100
   * is_recommend : false
   * buy_limit : false
   * per_limit : 5
   * mama_rebeta : 0
   * material : null
   * wash_instructions : null
   * note : null
   * color : null
   */

  @SerializedName("details") private DetailsEntity mDetails;
  /**
   * id : 17070
   * outer_id : 1
   * name : 100
   * remain_num : 1
   * size_of_sku : {"free_num":1,"result":"None"}
   * is_saleout : false
   * std_sale_price : 199
   * agent_price : 39
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

  public void setAgentPrice(int agentPrice) {
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

  public void setLowestPrice(int lowestPrice) {
    this.mLowestPrice = lowestPrice;
  }

  public void setProductLowestPrice(int productLowestPrice) {
    this.mProductLowestPrice = productLowestPrice;
  }

  public void setProductModel(Object productModel) {
    this.mProductModel = productModel;
  }

  public void setWareBy(int wareBy) {
    this.mWareBy = wareBy;
  }

  public void setIsVerify(boolean isVerify) {
    this.mIsVerify = isVerify;
  }

  public void setModelId(Object modelId) {
    this.mModelId = modelId;
  }

  public void setWatermarkOp(String watermarkOp) {
    this.mWatermarkOp = watermarkOp;
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

  public int getAgentPrice() {
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

  public int getLowestPrice() {
    return mLowestPrice;
  }

  public int getProductLowestPrice() {
    return mProductLowestPrice;
  }

  public Object getProductModel() {
    return mProductModel;
  }

  public int getWareBy() {
    return mWareBy;
  }

  public boolean isIsVerify() {
    return mIsVerify;
  }

  public Object getModelId() {
    return mModelId;
  }

  public String getWatermarkOp() {
    return mWatermarkOp;
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

  public static class DetailsEntity {
    @SerializedName("mama_discount") private int mMamaDiscount;
    @SerializedName("is_recommend") private boolean mIsRecommend;
    @SerializedName("buy_limit") private boolean mBuyLimit;
    @SerializedName("per_limit") private int mPerLimit;
    @SerializedName("mama_rebeta") private int mMamaRebeta;
    @SerializedName("material") private Object mMaterial;
    @SerializedName("wash_instructions") private Object mWashInstructions;
    @SerializedName("note") private Object mNote;
    @SerializedName("color") private Object mColor;
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

    public void setMaterial(Object material) {
      this.mMaterial = material;
    }

    public void setWashInstructions(Object washInstructions) {
      this.mWashInstructions = washInstructions;
    }

    public void setNote(Object note) {
      this.mNote = note;
    }

    public void setColor(Object color) {
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

    public Object getMaterial() {
      return mMaterial;
    }

    public Object getWashInstructions() {
      return mWashInstructions;
    }

    public Object getNote() {
      return mNote;
    }

    public Object getColor() {
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
     * free_num : 1
     * result : None
     */

    @SerializedName("size_of_sku") private SizeOfSkuEntity mSizeOfSku;
    @SerializedName("is_saleout") private boolean mIsSaleout;
    @SerializedName("std_sale_price") private int mStdSalePrice;
    @SerializedName("agent_price") private int mAgentPrice;

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

    public void setAgentPrice(int agentPrice) {
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

    public int getAgentPrice() {
      return mAgentPrice;
    }

    public static class SizeOfSkuEntity {
      @SerializedName("free_num") private int mFreeNum;
      @SerializedName("result") private String mResult;

      public void setFreeNum(int freeNum) {
        this.mFreeNum = freeNum;
      }

      public void setResult(String result) {
        this.mResult = result;
      }

      public int getFreeNum() {
        return mFreeNum;
      }

      public String getResult() {
        return mResult;
      }
    }
  }
}
