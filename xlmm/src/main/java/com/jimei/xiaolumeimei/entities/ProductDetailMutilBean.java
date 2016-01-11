package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailMutilBean {

  /**
   * id : 22860
   * url : http://m.xiaolumeimei.com/rest/v1/products/22860.json
   * name : 时尚休闲两件套/墨绿+灰
   * outer_id : 822274010021
   * category : {"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100}
   * pic_path : http://image.xiaolu.so/MG-1449719333426-3.png
   * remain_num : 250
   * is_saleout : false
   * head_img : http://image.xiaolu.so/MG-1449719329844-主图.png
   * is_saleopen : true
   * is_newgood : true
   * std_sale_price : 449
   * agent_price : 89.9
   * sale_time : 2016-01-07
   * offshelf_time : null
   * memo :
   * lowest_price : 89.9
   * product_lowest_price : 89.9
   * product_model : {"id":3827,"name":"时尚休闲两件套","head_imgs":["http://image.xiaolu.so/MG-1449719329844-主图.png"],"content_imgs":["http://image.xiaolu.so/MG-1451986117663-826新内容页童装_01.png","http://image.xiaolu.so/MG-1451986124296-826新内容页童装_02.png","http://image.xiaolu.so/MG-1451986131117-826新内容页童装_03.png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5}
   * ware_by : 2
   * is_verify : false
   * model_id : 3827
   * watermark_op :
   * normal_skus : [{"id":89467,"outer_id":"1","name":"S","remain_num":50,"size_of_sku":{"free_num":"NO","result":{"肩宽":"36","胸围":"92","袖长":"57","腰围":"64","衣长":"62","裤腰":"90","裤长":"96"}},"is_saleout":false,"std_sale_price":449,"agent_price":89.9},{"id":89468,"outer_id":"2","name":"M","remain_num":50,"size_of_sku":{"free_num":"NO","result":{"肩宽":"37","胸围":"96","袖长":"58","腰围":"68","衣长":"63","裤腰":"94","裤长":"97"}},"is_saleout":false,"std_sale_price":449,"agent_price":89.9},{"id":89469,"outer_id":"3","name":"L","remain_num":50,"size_of_sku":{"free_num":"NO","result":{"肩宽":"38","胸围":"100","袖长":"59","腰围":"72","衣长":"64","裤腰":"98","裤长":"98"}},"is_saleout":false,"std_sale_price":449,"agent_price":89.9},{"id":89470,"outer_id":"4","name":"XL","remain_num":50,"size_of_sku":{"free_num":"NO","result":{"肩宽":"39","胸围":"104","袖长":"60","腰围":"76","衣长":"65","裤腰":"102","裤长":"99"}},"is_saleout":false,"std_sale_price":449,"agent_price":89.9},{"id":89471,"outer_id":"5","name":"XXL","remain_num":50,"size_of_sku":{"free_num":"NO","result":{"肩宽":"40","胸围":"108","袖长":"61","腰围":"80","衣长":"66","裤腰":"106","裤长":"100"}},"is_saleout":false,"std_sale_price":449,"agent_price":89.9}]
   * details : {"head_imgs":["http://image.xiaolu.so/MG-1449719333426-3.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmnm3ib6B0DJLIPyiaqs5uuCnJcfNiaDWtx5wdvONteZXUfsuze8eRoGwOw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmlgtMBJAd1Ft9mVczDJtBU8icx1L9OW7pOYcsEdZA66mriakcQiabbRX6Q/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmiardPZhMDr8Ln7ddFvqvdvRUXicBlw20gLxX8sf5ZmUjw8QrCnlz0aMQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmENDUGA8piaaOz7h1dNSfnpZOCPynW6V2Yj6dBASDaB7lkOxiautibr9Rg/0?wx_fmt=png"],"mama_discount":100,"is_recommend":false,"buy_limit":false,"per_limit":5,"mama_rebeta":0,"material":"棉混纺","wash_instructions":"洗涤时请深色、浅色衣物分开洗涤。最高洗涤温度不要超过40度，
   * 不可漂白。 有涂层、印花表面不能进行熨烫，会导致表面剥落。不可干洗，悬挂晾干。","note":"袖口、领口、衣摆采用螺纹针织松紧法，长袖圆领套头式，松紧裤腰，方便实用的斜插袋，时尚个性的字母印花。","color":"墨绿+灰,白+黑,黑+酒红,黄绿+藏青"}
   */

  @SerializedName("id") private int mId;
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
   * id : 3827
   * name : 时尚休闲两件套
   * head_imgs : ["http://image.xiaolu.so/MG-1449719329844-主图.png"]
   * content_imgs : ["http://image.xiaolu.so/MG-1451986117663-826新内容页童装_01.png","http://image.xiaolu.so/MG-1451986124296-826新内容页童装_02.png","http://image.xiaolu.so/MG-1451986131117-826新内容页童装_03.png"]
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
   * head_imgs : ["http://image.xiaolu.so/MG-1449719333426-3.png"]
   * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmnm3ib6B0DJLIPyiaqs5uuCnJcfNiaDWtx5wdvONteZXUfsuze8eRoGwOw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmlgtMBJAd1Ft9mVczDJtBU8icx1L9OW7pOYcsEdZA66mriakcQiabbRX6Q/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmiardPZhMDr8Ln7ddFvqvdvRUXicBlw20gLxX8sf5ZmUjw8QrCnlz0aMQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmENDUGA8piaaOz7h1dNSfnpZOCPynW6V2Yj6dBASDaB7lkOxiautibr9Rg/0?wx_fmt=png"]
   * mama_discount : 100
   * is_recommend : false
   * buy_limit : false
   * per_limit : 5
   * mama_rebeta : 0
   * material : 棉混纺
   * wash_instructions : 洗涤时请深色、浅色衣物分开洗涤。最高洗涤温度不要超过40度， 不可漂白。 有涂层、印花表面不能进行熨烫，会导致表面剥落。不可干洗，悬挂晾干。
   * note : 袖口、领口、衣摆采用螺纹针织松紧法，长袖圆领套头式，松紧裤腰，方便实用的斜插袋，时尚个性的字母印花。
   * color : 墨绿+灰,白+黑,黑+酒红,黄绿+藏青
   */

  @SerializedName("details") private DetailsEntity mDetails;
  /**
   * id : 89467
   * outer_id : 1
   * name : S
   * remain_num : 50
   * size_of_sku : {"free_num":"NO","result":{"肩宽":"36","胸围":"92","袖长":"57","腰围":"64","衣长":"62","裤腰":"90","裤长":"96"}}
   * is_saleout : false
   * std_sale_price : 449
   * agent_price : 89.9
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
    @SerializedName("id") private int mId;
    @SerializedName("outer_id") private String mOuterId;
    @SerializedName("name") private String mName;
    @SerializedName("remain_num") private int mRemainNum;
    /**
     * free_num : NO
     * result : {"肩宽":"36","胸围":"92","袖长":"57","腰围":"64","衣长":"62","裤腰":"90","裤长":"96"}
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
      @SerializedName("free_num") private String mFreeNum;
      /**
       * 肩宽 : 36
       * 胸围 : 92
       * 袖长 : 57
       * 腰围 : 64
       * 衣长 : 62
       * 裤腰 : 90
       * 裤长 : 96
       */

      @SerializedName("result") private ResultEntity mResult;

      public String getFreeNum() {
        return mFreeNum;
      }

      public void setFreeNum(String freeNum) {
        this.mFreeNum = freeNum;
      }

      public ResultEntity getResult() {
        return mResult;
      }

      public void setResult(ResultEntity result) {
        this.mResult = result;
      }

      public static class ResultEntity {
        @SerializedName("肩宽") private String m肩宽;
        @SerializedName("胸围") private String m胸围;
        @SerializedName("袖长") private String m袖长;
        @SerializedName("腰围") private String m腰围;
        @SerializedName("衣长") private String m衣长;
        @SerializedName("裤腰") private String m裤腰;
        @SerializedName("裤长") private String m裤长;

        public String get肩宽() {
          return m肩宽;
        }

        public void set肩宽(String 肩宽) {
          this.m肩宽 = 肩宽;
        }

        public String get胸围() {
          return m胸围;
        }

        public void set胸围(String 胸围) {
          this.m胸围 = 胸围;
        }

        public String get袖长() {
          return m袖长;
        }

        public void set袖长(String 袖长) {
          this.m袖长 = 袖长;
        }

        public String get腰围() {
          return m腰围;
        }

        public void set腰围(String 腰围) {
          this.m腰围 = 腰围;
        }

        public String get衣长() {
          return m衣长;
        }

        public void set衣长(String 衣长) {
          this.m衣长 = 衣长;
        }

        public String get裤腰() {
          return m裤腰;
        }

        public void set裤腰(String 裤腰) {
          this.m裤腰 = 裤腰;
        }

        public String get裤长() {
          return m裤长;
        }

        public void set裤长(String 裤长) {
          this.m裤长 = 裤长;
        }
      }
    }
  }
}

