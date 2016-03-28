package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by 优尼世界 on 2016/01/15.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayinfoBean {
  ///**
  // * wallet_cash : 16196.83
  // * sku : {"id":137095,"outer_id":"8302-5","name":"XL","remain_num":20,"size_of_sku":{"free_num":"NO","result":{"肩宽":"40","胸围":"104","袖长":"60","衣长":"76"}},"is_saleout":false,"std_sale_price":158,"agent_price":129.9,"product":{"id":34665,"url":"http://api.xiaolumeimei.com/rest/v1/products/34665","name":"显瘦西装领大衣/藏青色","outer_id":"818036340011","category":{"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100},"pic_path":"http://image.xiaolu.so/MG_14571739338513.png","remain_num":100,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG_1457173926774头图背景.png","is_saleopen":true,"is_newgood":true,"std_sale_price":158,"agent_price":129.9,"sale_time":"2016-03-16","offshelf_time":"2016-03-18T10:00:00","memo":"","lowest_price":129.9,"product_lowest_price":129.9,"product_model":{"id":9050,"name":"显瘦西装领大衣","head_imgs":["http://image.xiaolu.so/MG_1457173926774头图背景.png"],"content_imgs":["http://image.xiaolu.so/MG_1457173948499模版_01.jpg","http://image.xiaolu.so/MG_1457173948796模版_02.jpg","http://image.xiaolu.so/MG_1457173948938模版_03.jpg","http://image.xiaolu.so/MG_1457173949060模版_04.jpg","http://image.xiaolu.so/MG_1457173949202模版_05.jpg","http://image.xiaolu.so/MG_1457173949302模版_06.jpg","http://image.xiaolu.so/MG_1457173949428模版_07.jpg","http://image.xiaolu.so/MG_1457173949609模版_08.jpg","http://image.xiaolu.so/MG_1457173949691模版_09.jpg","http://image.xiaolu.so/MG_1457173949769模版_10.jpg","http://image.xiaolu.so/MG_1457173949998模版_11.jpg","http://image.xiaolu.so/MG_1457173950075模版_12.jpg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":9050,"watermark_op":""}}
  // * budget_payable : true
  // * total_payment : 129.9
  // * uuid : xd16031656e94f5cd2189
  // * alipay_payable : true
  // * discount_fee : 0.0
  // * pay_extras : [{"type":0,"pid":1,"name":"APP支付减2元","value":2}]
  // * wallet_payable : true
  // * weixin_payable : false
  // * coupon_message :
  // * total_fee : 129.9
  // * post_fee : 0.0
  // * coupon_ticket : null
  // * budget_cash : 662615
  // * apple_payable : false
  // */
  //
  //@SerializedName("wallet_cash") private double mWalletCash;
  ///**
  // * id : 137095
  // * outer_id : 8302-5
  // * name : XL
  // * remain_num : 20
  // * size_of_sku : {"free_num":"NO","result":{"肩宽":"40","胸围":"104","袖长":"60","衣长":"76"}}
  // * is_saleout : false
  // * std_sale_price : 158.0
  // * agent_price : 129.9
  // * product : {"id":34665,"url":"http://api.xiaolumeimei.com/rest/v1/products/34665","name":"显瘦西装领大衣/藏青色","outer_id":"818036340011","category":{"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100},"pic_path":"http://image.xiaolu.so/MG_14571739338513.png","remain_num":100,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG_1457173926774头图背景.png","is_saleopen":true,"is_newgood":true,"std_sale_price":158,"agent_price":129.9,"sale_time":"2016-03-16","offshelf_time":"2016-03-18T10:00:00","memo":"","lowest_price":129.9,"product_lowest_price":129.9,"product_model":{"id":9050,"name":"显瘦西装领大衣","head_imgs":["http://image.xiaolu.so/MG_1457173926774头图背景.png"],"content_imgs":["http://image.xiaolu.so/MG_1457173948499模版_01.jpg","http://image.xiaolu.so/MG_1457173948796模版_02.jpg","http://image.xiaolu.so/MG_1457173948938模版_03.jpg","http://image.xiaolu.so/MG_1457173949060模版_04.jpg","http://image.xiaolu.so/MG_1457173949202模版_05.jpg","http://image.xiaolu.so/MG_1457173949302模版_06.jpg","http://image.xiaolu.so/MG_1457173949428模版_07.jpg","http://image.xiaolu.so/MG_1457173949609模版_08.jpg","http://image.xiaolu.so/MG_1457173949691模版_09.jpg","http://image.xiaolu.so/MG_1457173949769模版_10.jpg","http://image.xiaolu.so/MG_1457173949998模版_11.jpg","http://image.xiaolu.so/MG_1457173950075模版_12.jpg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":9050,"watermark_op":""}
  // */
  //
  //@SerializedName("sku") private SkuEntity mSku;
  //@SerializedName("budget_payable") private boolean mBudgetPayable;
  //@SerializedName("total_payment") private double mTotalPayment;
  //@SerializedName("uuid") private String mUuid;
  //@SerializedName("alipay_payable") private boolean mAlipayPayable;
  //@SerializedName("discount_fee") private double mDiscountFee;
  //@SerializedName("wallet_payable") private boolean mWalletPayable;
  //@SerializedName("weixin_payable") private boolean mWeixinPayable;
  //@SerializedName("coupon_message") private String mCouponMessage;
  //@SerializedName("total_fee") private double mTotalFee;
  //@SerializedName("post_fee") private double mPostFee;
  //@SerializedName("coupon_ticket") private Object mCouponTicket;
  //@SerializedName("budget_cash") private int mBudgetCash;
  //@SerializedName("apple_payable") private boolean mApplePayable;
  ///**
  // * type : 0
  // * pid : 1
  // * name : APP支付减2元
  // * value : 2
  // */
  //
  @SerializedName("pay_extras") private List<payExtrasEntityApp> mPayExtras;
  @SerializedName("wallet_cash") private float mWalletCash;
  @SerializedName("total_payment") private double mTotalPayment;
  //
  //public double getWalletCash() {
  //  return mWalletCash;
  //}
  //
  //public void setWalletCash(double walletCash) {
  //  mWalletCash = walletCash;
  //}
  //
  //public SkuEntity getSku() {
  //  return mSku;
  //}
  //
  //public void setSku(SkuEntity sku) {
  //  mSku = sku;
  //}
  //
  //public boolean isBudgetPayable() {
  //  return mBudgetPayable;
  //}
  //
  //public void setBudgetPayable(boolean budgetPayable) {
  //  mBudgetPayable = budgetPayable;
  //}
  //
  //public double getTotalPayment() {
  //  return mTotalPayment;
  //}
  //
  //public void setTotalPayment(double totalPayment) {
  //  mTotalPayment = totalPayment;
  //}
  //
  //public String getUuid() {
  //  return mUuid;
  //}
  //
  //public void setUuid(String uuid) {
  //  mUuid = uuid;
  //}
  //
  //public boolean isAlipayPayable() {
  //  return mAlipayPayable;
  //}
  //
  //public void setAlipayPayable(boolean alipayPayable) {
  //  mAlipayPayable = alipayPayable;
  //}
  //
  //public double getDiscountFee() {
  //  return mDiscountFee;
  //}
  //
  //public void setDiscountFee(double discountFee) {
  //  mDiscountFee = discountFee;
  //}
  //
  //public boolean isWalletPayable() {
  //  return mWalletPayable;
  //}
  //
  //public void setWalletPayable(boolean walletPayable) {
  //  mWalletPayable = walletPayable;
  //}
  //
  //public boolean isWeixinPayable() {
  //  return mWeixinPayable;
  //}
  //
  //public void setWeixinPayable(boolean weixinPayable) {
  //  mWeixinPayable = weixinPayable;
  //}
  //
  //public String getCouponMessage() {
  //  return mCouponMessage;
  //}
  //
  //public void setCouponMessage(String couponMessage) {
  //  mCouponMessage = couponMessage;
  //}
  //
  //public double getTotalFee() {
  //  return mTotalFee;
  //}
  //
  //public void setTotalFee(double totalFee) {
  //  mTotalFee = totalFee;
  //}
  //
  //public double getPostFee() {
  //  return mPostFee;
  //}
  //
  //public void setPostFee(double postFee) {
  //  mPostFee = postFee;
  //}
  //
  //public Object getCouponTicket() {
  //  return mCouponTicket;
  //}
  //
  //public void setCouponTicket(Object couponTicket) {
  //  mCouponTicket = couponTicket;
  //}
  //
  //public int getBudgetCash() {
  //  return mBudgetCash;
  //}
  //
  //public void setBudgetCash(int budgetCash) {
  //  mBudgetCash = budgetCash;
  //}
  //
  //public boolean isApplePayable() {
  //  return mApplePayable;
  //}
  //
  //public void setApplePayable(boolean applePayable) {
  //  mApplePayable = applePayable;
  //}
  //
  //public List<payExtrasEntityApp> getPayExtras() {
  //  return mPayExtras;
  //}
  //
  //public void setPayExtras(List<payExtrasEntityApp> payExtras) {
  //  mPayExtras = payExtras;
  //}
  //
  //public static class SkuEntity {
  //  @SerializedName("id") private int mId;
  //  @SerializedName("outer_id") private String mOuterId;
  //  @SerializedName("name") private String mName;
  //  @SerializedName("remain_num") private int mRemainNum;
  //  /**
  //   * free_num : NO
  //   * result : {"肩宽":"40","胸围":"104","袖长":"60","衣长":"76"}
  //   */
  //
  //  @SerializedName("size_of_sku") private SizeOfSkuEntity mSizeOfSku;
  //  @SerializedName("is_saleout") private boolean mIsSaleout;
  //  @SerializedName("std_sale_price") private double mStdSalePrice;
  //  @SerializedName("agent_price") private double mAgentPrice;
  //  /**
  //   * id : 34665
  //   * url : http://api.xiaolumeimei.com/rest/v1/products/34665
  //   * name : 显瘦西装领大衣/藏青色
  //   * outer_id : 818036340011
  //   * category : {"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100}
  //   * pic_path : http://image.xiaolu.so/MG_14571739338513.png
  //   * remain_num : 100
  //   * is_saleout : false
  //   * head_img : http://image.xiaolu.so/MG_1457173926774头图背景.png
  //   * is_saleopen : true
  //   * is_newgood : true
  //   * std_sale_price : 158.0
  //   * agent_price : 129.9
  //   * sale_time : 2016-03-16
  //   * offshelf_time : 2016-03-18T10:00:00
  //   * memo :
  //   * lowest_price : 129.9
  //   * product_lowest_price : 129.9
  //   * product_model : {"id":9050,"name":"显瘦西装领大衣","head_imgs":["http://image.xiaolu.so/MG_1457173926774头图背景.png"],"content_imgs":["http://image.xiaolu.so/MG_1457173948499模版_01.jpg","http://image.xiaolu.so/MG_1457173948796模版_02.jpg","http://image.xiaolu.so/MG_1457173948938模版_03.jpg","http://image.xiaolu.so/MG_1457173949060模版_04.jpg","http://image.xiaolu.so/MG_1457173949202模版_05.jpg","http://image.xiaolu.so/MG_1457173949302模版_06.jpg","http://image.xiaolu.so/MG_1457173949428模版_07.jpg","http://image.xiaolu.so/MG_1457173949609模版_08.jpg","http://image.xiaolu.so/MG_1457173949691模版_09.jpg","http://image.xiaolu.so/MG_1457173949769模版_10.jpg","http://image.xiaolu.so/MG_1457173949998模版_11.jpg","http://image.xiaolu.so/MG_1457173950075模版_12.jpg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5}
  //   * ware_by : 1
  //   * is_verify : false
  //   * model_id : 9050
  //   * watermark_op :
  //   */
  //
  //  @SerializedName("product") private ProductEntity mProduct;
  //
  //  public int getId() {
  //    return mId;
  //  }
  //
  //  public void setId(int id) {
  //    mId = id;
  //  }
  //
  //  public String getOuterId() {
  //    return mOuterId;
  //  }
  //
  //  public void setOuterId(String outerId) {
  //    mOuterId = outerId;
  //  }
  //
  //  public String getName() {
  //    return mName;
  //  }
  //
  //  public void setName(String name) {
  //    mName = name;
  //  }
  //
  //  public int getRemainNum() {
  //    return mRemainNum;
  //  }
  //
  //  public void setRemainNum(int remainNum) {
  //    mRemainNum = remainNum;
  //  }
  //
  //  public SizeOfSkuEntity getSizeOfSku() {
  //    return mSizeOfSku;
  //  }
  //
  //  public void setSizeOfSku(SizeOfSkuEntity sizeOfSku) {
  //    mSizeOfSku = sizeOfSku;
  //  }
  //
  //  public boolean isIsSaleout() {
  //    return mIsSaleout;
  //  }
  //
  //  public void setIsSaleout(boolean isSaleout) {
  //    mIsSaleout = isSaleout;
  //  }
  //
  //  public double getStdSalePrice() {
  //    return mStdSalePrice;
  //  }
  //
  //  public void setStdSalePrice(double stdSalePrice) {
  //    mStdSalePrice = stdSalePrice;
  //  }
  //
  //  public double getAgentPrice() {
  //    return mAgentPrice;
  //  }
  //
  //  public void setAgentPrice(double agentPrice) {
  //    mAgentPrice = agentPrice;
  //  }
  //
  //  public ProductEntity getProduct() {
  //    return mProduct;
  //  }
  //
  //  public void setProduct(ProductEntity product) {
  //    mProduct = product;
  //  }
  //
  //  public static class SizeOfSkuEntity {
  //    @SerializedName("free_num") private String mFreeNum;
  //    /**
  //     * 肩宽 : 40
  //     * 胸围 : 104
  //     * 袖长 : 60
  //     * 衣长 : 76
  //     */
  //
  //    @SerializedName("result") private ResultEntity mResult;
  //
  //    public String getFreeNum() {
  //      return mFreeNum;
  //    }
  //
  //    public void setFreeNum(String freeNum) {
  //      mFreeNum = freeNum;
  //    }
  //
  //    public ResultEntity getResult() {
  //      return mResult;
  //    }
  //
  //    public void setResult(ResultEntity result) {
  //      mResult = result;
  //    }
  //
  //    public static class ResultEntity {
  //      @SerializedName("肩宽") private String m肩宽;
  //      @SerializedName("胸围") private String m胸围;
  //      @SerializedName("袖长") private String m袖长;
  //      @SerializedName("衣长") private String m衣长;
  //
  //      public String get肩宽() {
  //        return m肩宽;
  //      }
  //
  //      public void set肩宽(String 肩宽) {
  //        m肩宽 = 肩宽;
  //      }
  //
  //      public String get胸围() {
  //        return m胸围;
  //      }
  //
  //      public void set胸围(String 胸围) {
  //        m胸围 = 胸围;
  //      }
  //
  //      public String get袖长() {
  //        return m袖长;
  //      }
  //
  //      public void set袖长(String 袖长) {
  //        m袖长 = 袖长;
  //      }
  //
  //      public String get衣长() {
  //        return m衣长;
  //      }
  //
  //      public void set衣长(String 衣长) {
  //        m衣长 = 衣长;
  //      }
  //    }
  //  }
  //
  //  public static class ProductEntity {
  //    @SerializedName("id") private int mId;
  //    @SerializedName("url") private String mUrl;
  //    @SerializedName("name") private String mName;
  //    @SerializedName("outer_id") private String mOuterId;
  //    /**
  //     * cid : 18
  //     * parent_cid : 8
  //     * name : 外套
  //     * status : normal
  //     * sort_order : 100
  //     */
  //
  //    @SerializedName("category") private CategoryEntity mCategory;
  //    @SerializedName("pic_path") private String mPicPath;
  //    @SerializedName("remain_num") private int mRemainNum;
  //    @SerializedName("is_saleout") private boolean mIsSaleout;
  //    @SerializedName("head_img") private String mHeadImg;
  //    @SerializedName("is_saleopen") private boolean mIsSaleopen;
  //    @SerializedName("is_newgood") private boolean mIsNewgood;
  //    @SerializedName("std_sale_price") private double mStdSalePrice;
  //    @SerializedName("agent_price") private double mAgentPrice;
  //    @SerializedName("sale_time") private String mSaleTime;
  //    @SerializedName("offshelf_time") private String mOffshelfTime;
  //    @SerializedName("memo") private String mMemo;
  //    @SerializedName("lowest_price") private double mLowestPrice;
  //    @SerializedName("product_lowest_price") private double mProductLowestPrice;
  //    /**
  //     * id : 9050
  //     * name : 显瘦西装领大衣
  //     * head_imgs : ["http://image.xiaolu.so/MG_1457173926774头图背景.png"]
  //     * content_imgs : ["http://image.xiaolu.so/MG_1457173948499模版_01.jpg","http://image.xiaolu.so/MG_1457173948796模版_02.jpg","http://image.xiaolu.so/MG_1457173948938模版_03.jpg","http://image.xiaolu.so/MG_1457173949060模版_04.jpg","http://image.xiaolu.so/MG_1457173949202模版_05.jpg","http://image.xiaolu.so/MG_1457173949302模版_06.jpg","http://image.xiaolu.so/MG_1457173949428模版_07.jpg","http://image.xiaolu.so/MG_1457173949609模版_08.jpg","http://image.xiaolu.so/MG_1457173949691模版_09.jpg","http://image.xiaolu.so/MG_1457173949769模版_10.jpg","http://image.xiaolu.so/MG_1457173949998模版_11.jpg","http://image.xiaolu.so/MG_1457173950075模版_12.jpg"]
  //     * is_single_spec : false
  //     * is_sale_out : false
  //     * buy_limit : false
  //     * per_limit : 5
  //     */
  //
  //    @SerializedName("product_model") private ProductModelEntity mProductModel;
  //    @SerializedName("ware_by") private int mWareBy;
  //    @SerializedName("is_verify") private boolean mIsVerify;
  //    @SerializedName("model_id") private int mModelId;
  //    @SerializedName("watermark_op") private String mWatermarkOp;
  //
  //    public int getId() {
  //      return mId;
  //    }
  //
  //    public void setId(int id) {
  //      mId = id;
  //    }
  //
  //    public String getUrl() {
  //      return mUrl;
  //    }
  //
  //    public void setUrl(String url) {
  //      mUrl = url;
  //    }
  //
  //    public String getName() {
  //      return mName;
  //    }
  //
  //    public void setName(String name) {
  //      mName = name;
  //    }
  //
  //    public String getOuterId() {
  //      return mOuterId;
  //    }
  //
  //    public void setOuterId(String outerId) {
  //      mOuterId = outerId;
  //    }
  //
  //    public CategoryEntity getCategory() {
  //      return mCategory;
  //    }
  //
  //    public void setCategory(CategoryEntity category) {
  //      mCategory = category;
  //    }
  //
  //    public String getPicPath() {
  //      return mPicPath;
  //    }
  //
  //    public void setPicPath(String picPath) {
  //      mPicPath = picPath;
  //    }
  //
  //    public int getRemainNum() {
  //      return mRemainNum;
  //    }
  //
  //    public void setRemainNum(int remainNum) {
  //      mRemainNum = remainNum;
  //    }
  //
  //    public boolean isIsSaleout() {
  //      return mIsSaleout;
  //    }
  //
  //    public void setIsSaleout(boolean isSaleout) {
  //      mIsSaleout = isSaleout;
  //    }
  //
  //    public String getHeadImg() {
  //      return mHeadImg;
  //    }
  //
  //    public void setHeadImg(String headImg) {
  //      mHeadImg = headImg;
  //    }
  //
  //    public boolean isIsSaleopen() {
  //      return mIsSaleopen;
  //    }
  //
  //    public void setIsSaleopen(boolean isSaleopen) {
  //      mIsSaleopen = isSaleopen;
  //    }
  //
  //    public boolean isIsNewgood() {
  //      return mIsNewgood;
  //    }
  //
  //    public void setIsNewgood(boolean isNewgood) {
  //      mIsNewgood = isNewgood;
  //    }
  //
  //    public double getStdSalePrice() {
  //      return mStdSalePrice;
  //    }
  //
  //    public void setStdSalePrice(double stdSalePrice) {
  //      mStdSalePrice = stdSalePrice;
  //    }
  //
  //    public double getAgentPrice() {
  //      return mAgentPrice;
  //    }
  //
  //    public void setAgentPrice(double agentPrice) {
  //      mAgentPrice = agentPrice;
  //    }
  //
  //    public String getSaleTime() {
  //      return mSaleTime;
  //    }
  //
  //    public void setSaleTime(String saleTime) {
  //      mSaleTime = saleTime;
  //    }
  //
  //    public String getOffshelfTime() {
  //      return mOffshelfTime;
  //    }
  //
  //    public void setOffshelfTime(String offshelfTime) {
  //      mOffshelfTime = offshelfTime;
  //    }
  //
  //    public String getMemo() {
  //      return mMemo;
  //    }
  //
  //    public void setMemo(String memo) {
  //      mMemo = memo;
  //    }
  //
  //    public double getLowestPrice() {
  //      return mLowestPrice;
  //    }
  //
  //    public void setLowestPrice(double lowestPrice) {
  //      mLowestPrice = lowestPrice;
  //    }
  //
  //    public double getProductLowestPrice() {
  //      return mProductLowestPrice;
  //    }
  //
  //    public void setProductLowestPrice(double productLowestPrice) {
  //      mProductLowestPrice = productLowestPrice;
  //    }
  //
  //    public ProductModelEntity getProductModel() {
  //      return mProductModel;
  //    }
  //
  //    public void setProductModel(ProductModelEntity productModel) {
  //      mProductModel = productModel;
  //    }
  //
  //    public int getWareBy() {
  //      return mWareBy;
  //    }
  //
  //    public void setWareBy(int wareBy) {
  //      mWareBy = wareBy;
  //    }
  //
  //    public boolean isIsVerify() {
  //      return mIsVerify;
  //    }
  //
  //    public void setIsVerify(boolean isVerify) {
  //      mIsVerify = isVerify;
  //    }
  //
  //    public int getModelId() {
  //      return mModelId;
  //    }
  //
  //    public void setModelId(int modelId) {
  //      mModelId = modelId;
  //    }
  //
  //    public String getWatermarkOp() {
  //      return mWatermarkOp;
  //    }
  //
  //    public void setWatermarkOp(String watermarkOp) {
  //      mWatermarkOp = watermarkOp;
  //    }
  //
  //    public static class CategoryEntity {
  //      @SerializedName("cid") private int mCid;
  //      @SerializedName("parent_cid") private int mParentCid;
  //      @SerializedName("name") private String mName;
  //      @SerializedName("status") private String mStatus;
  //      @SerializedName("sort_order") private int mSortOrder;
  //
  //      public int getCid() {
  //        return mCid;
  //      }
  //
  //      public void setCid(int cid) {
  //        mCid = cid;
  //      }
  //
  //      public int getParentCid() {
  //        return mParentCid;
  //      }
  //
  //      public void setParentCid(int parentCid) {
  //        mParentCid = parentCid;
  //      }
  //
  //      public String getName() {
  //        return mName;
  //      }
  //
  //      public void setName(String name) {
  //        mName = name;
  //      }
  //
  //      public String getStatus() {
  //        return mStatus;
  //      }
  //
  //      public void setStatus(String status) {
  //        mStatus = status;
  //      }
  //
  //      public int getSortOrder() {
  //        return mSortOrder;
  //      }
  //
  //      public void setSortOrder(int sortOrder) {
  //        mSortOrder = sortOrder;
  //      }
  //    }
  //
  //    public static class ProductModelEntity {
  //      @SerializedName("id") private int mId;
  //      @SerializedName("name") private String mName;
  //      @SerializedName("is_single_spec") private boolean mIsSingleSpec;
  //      @SerializedName("is_sale_out") private boolean mIsSaleOut;
  //      @SerializedName("buy_limit") private boolean mBuyLimit;
  //      @SerializedName("per_limit") private int mPerLimit;
  //      @SerializedName("head_imgs") private List<String> mHeadImgs;
  //      @SerializedName("content_imgs") private List<String> mContentImgs;
  //
  //      public int getId() {
  //        return mId;
  //      }
  //
  //      public void setId(int id) {
  //        mId = id;
  //      }
  //
  //      public String getName() {
  //        return mName;
  //      }
  //
  //      public void setName(String name) {
  //        mName = name;
  //      }
  //
  //      public boolean isIsSingleSpec() {
  //        return mIsSingleSpec;
  //      }
  //
  //      public void setIsSingleSpec(boolean isSingleSpec) {
  //        mIsSingleSpec = isSingleSpec;
  //      }
  //
  //      public boolean isIsSaleOut() {
  //        return mIsSaleOut;
  //      }
  //
  //      public void setIsSaleOut(boolean isSaleOut) {
  //        mIsSaleOut = isSaleOut;
  //      }
  //
  //      public boolean isBuyLimit() {
  //        return mBuyLimit;
  //      }
  //
  //      public void setBuyLimit(boolean buyLimit) {
  //        mBuyLimit = buyLimit;
  //      }
  //
  //      public int getPerLimit() {
  //        return mPerLimit;
  //      }
  //
  //      public void setPerLimit(int perLimit) {
  //        mPerLimit = perLimit;
  //      }
  //
  //      public List<String> getHeadImgs() {
  //        return mHeadImgs;
  //      }
  //
  //      public void setHeadImgs(List<String> headImgs) {
  //        mHeadImgs = headImgs;
  //      }
  //
  //      public List<String> getContentImgs() {
  //        return mContentImgs;
  //      }
  //
  //      public void setContentImgs(List<String> contentImgs) {
  //        mContentImgs = contentImgs;
  //      }
  //    }
  //  }
  //}
  //
  //public static class payExtrasEntityApp {
  //  @SerializedName("type") private int mType;
  //  @SerializedName("pid") private int mPid;
  //  @SerializedName("name") private String mName;
  //  @SerializedName("value") private int mValue;
  //
  //  public int getType() {
  //    return mType;
  //  }
  //
  //  public void setType(int type) {
  //    mType = type;
  //  }
  //
  //  public int getPid() {
  //    return mPid;
  //  }
  //
  //  public void setPid(int pid) {
  //    mPid = pid;
  //  }
  //
  //  public String getName() {
  //    return mName;
  //  }
  //
  //  public void setName(String name) {
  //    mName = name;
  //  }
  //
  //  public int getValue() {
  //    return mValue;
  //  }
  //
  //  public void setValue(int value) {
  //    mValue = value;
  //  }
  //}
  @SerializedName("wallet_payable") private boolean mWalletPayable;
  @SerializedName("weixin_payable") private boolean mWeixinPayable;
  @SerializedName("coupon_ticket") private Object mCouponTicket;
  @SerializedName("uuid") private String mUuid;
  @SerializedName("budget_cash") private String budget_cash;
  @SerializedName("budget_payable") private boolean budget_payable;
  @SerializedName("coupon_message") private String mCoupon_message;
  @SerializedName("alipay_payable") private boolean mAlipayPayable;
  @SerializedName("discount_fee") private double mDiscountFee;
  @SerializedName("cart_ids") private String mCartIds;
  @SerializedName("total_fee") private double mTotalFee;
  @SerializedName("post_fee") private double mPostFee;
  /**
   * id : 327836
   * url : http://api.xiaolumeimei.com/rest/v1/carts/327836
   * buyer_id : 684126
   * buyer_nick : itxuye
   * item_id : 31165
   * title : 气质蕾丝连衣裙/白色
   * price : 69.9
   * std_sale_price : 349.0
   * sku_id : 122215
   * num : 1
   * total_fee : 69.9
   * sku_name : L
   * pic_path : http://image.xiaolu.so/MG-1452567228289-镂空修身蕾丝连衣裙02.jpg
   * created : 2016-01-15T11:30:59
   * status : 0
   */

  @SerializedName("cart_list") private List<CartListEntity> mCartList;

  public List<payExtrasEntityApp> getmPayExtras() {
    return mPayExtras;
  }

  public void setmPayExtras(List<payExtrasEntityApp> mPayExtras) {
    this.mPayExtras = mPayExtras;
  }

  @Override public String toString() {
    return "CartsPayinfoBean{" +
        "mWalletCash=" + mWalletCash +
        ", mTotalPayment=" + mTotalPayment +
        ", mWalletPayable=" + mWalletPayable +
        ", mWeixinPayable=" + mWeixinPayable +
        ", mCouponTicket=" + mCouponTicket +
        ", mUuid='" + mUuid + '\'' +
        ", mAlipayPayable=" + mAlipayPayable +
        ", mDiscountFee=" + mDiscountFee +
        ", mCartIds='" + mCartIds + '\'' +
        ", mTotalFee=" + mTotalFee +
        ", mPostFee=" + mPostFee +
        ", mCartList=" + mCartList +
        '}';
  }

  public boolean isBudget_payable() {
    return budget_payable;
  }

  public void setBudget_payable(boolean budget_payable) {
    this.budget_payable = budget_payable;
  }

  public String getBudget_cash() {
    return budget_cash;
  }

  public void setBudget_cash(String budget_cash) {
    this.budget_cash = budget_cash;
  }

  public String getmCoupon_message() {
    return mCoupon_message;
  }

  public void setmCoupon_message(String mCoupon_message) {
    this.mCoupon_message = mCoupon_message;
  }

  public float getWalletCash() {
    return mWalletCash;
  }

  public void setWalletCash(int walletCash) {
    this.mWalletCash = walletCash;
  }

  public double getTotalPayment() {
    return mTotalPayment;
  }

  public void setTotalPayment(double totalPayment) {
    this.mTotalPayment = totalPayment;
  }

  public boolean isWalletPayable() {
    return mWalletPayable;
  }

  public void setWalletPayable(boolean walletPayable) {
    this.mWalletPayable = walletPayable;
  }

  public boolean isWeixinPayable() {
    return mWeixinPayable;
  }

  public void setWeixinPayable(boolean weixinPayable) {
    this.mWeixinPayable = weixinPayable;
  }

  public Object getCouponTicket() {
    return mCouponTicket;
  }

  public void setCouponTicket(Object couponTicket) {
    this.mCouponTicket = couponTicket;
  }

  public String getUuid() {
    return mUuid;
  }

  public void setUuid(String uuid) {
    this.mUuid = uuid;
  }

  public boolean isAlipayPayable() {
    return mAlipayPayable;
  }

  public void setAlipayPayable(boolean alipayPayable) {
    this.mAlipayPayable = alipayPayable;
  }

  public double getDiscountFee() {
    return mDiscountFee;
  }

  public void setDiscountFee(double discountFee) {
    this.mDiscountFee = discountFee;
  }

  public String getCartIds() {
    return mCartIds;
  }

  public void setCartIds(String cartIds) {
    this.mCartIds = cartIds;
  }

  public double getTotalFee() {
    return mTotalFee;
  }

  public void setTotalFee(double totalFee) {
    this.mTotalFee = totalFee;
  }

  public double getPostFee() {
    return mPostFee;
  }

  public void setPostFee(double postFee) {
    this.mPostFee = postFee;
  }

  public List<CartListEntity> getCartList() {
    return mCartList;
  }

  public void setCartList(List<CartListEntity> cartList) {
    this.mCartList = cartList;
  }

  public static class CartListEntity {
    @SerializedName("id") private int mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("buyer_id") private int mBuyerId;
    @SerializedName("buyer_nick") private String mBuyerNick;
    @SerializedName("item_id") private String mItemId;
    @SerializedName("title") private String mTitle;
    @SerializedName("price") private double mPrice;
    @SerializedName("std_sale_price") private double mStdSalePrice;
    @SerializedName("sku_id") private String mSkuId;
    @SerializedName("num") private int mNum;
    @SerializedName("total_fee") private double mTotalFee;
    @SerializedName("sku_name") private String mSkuName;
    @SerializedName("pic_path") private String mPicPath;
    @SerializedName("created") private String mCreated;
    @SerializedName("status") private int mStatus;

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

    public int getBuyerId() {
      return mBuyerId;
    }

    public void setBuyerId(int buyerId) {
      this.mBuyerId = buyerId;
    }

    public String getBuyerNick() {
      return mBuyerNick;
    }

    public void setBuyerNick(String buyerNick) {
      this.mBuyerNick = buyerNick;
    }

    public String getItemId() {
      return mItemId;
    }

    public void setItemId(String itemId) {
      this.mItemId = itemId;
    }

    public String getTitle() {
      return mTitle;
    }

    public void setTitle(String title) {
      this.mTitle = title;
    }

    public double getPrice() {
      return mPrice;
    }

    public void setPrice(double price) {
      this.mPrice = price;
    }

    public double getStdSalePrice() {
      return mStdSalePrice;
    }

    public void setStdSalePrice(double stdSalePrice) {
      this.mStdSalePrice = stdSalePrice;
    }

    public String getSkuId() {
      return mSkuId;
    }

    public void setSkuId(String skuId) {
      this.mSkuId = skuId;
    }

    public int getNum() {
      return mNum;
    }

    public void setNum(int num) {
      this.mNum = num;
    }

    public double getTotalFee() {
      return mTotalFee;
    }

    public void setTotalFee(double totalFee) {
      this.mTotalFee = totalFee;
    }

    public String getSkuName() {
      return mSkuName;
    }

    public void setSkuName(String skuName) {
      this.mSkuName = skuName;
    }

    public String getPicPath() {
      return mPicPath;
    }

    public void setPicPath(String picPath) {
      this.mPicPath = picPath;
    }

    public String getCreated() {
      return mCreated;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public int getStatus() {
      return mStatus;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }
  }

  public static class payExtrasEntityApp {
    @SerializedName("type") private int mType;
    @SerializedName("pid") private int mPid;
    @SerializedName("name") private String mName;
    @SerializedName("value") private double mValue;

    public int getType() {
      return mType;
    }

    public void setType(int type) {
      mType = type;
    }

    public int getPid() {
      return mPid;
    }

    public void setPid(int pid) {
      mPid = pid;
    }

    public String getName() {
      return mName;
    }

    public void setName(String name) {
      mName = name;
    }

    public double getValue() {
      return mValue;
    }

    public void setValue(double value) {
      mValue = value;
    }
  }
}
