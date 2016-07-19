package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye on 2016/7/18.
 */
public class MaMaReNewBean {

  @SerializedName("product") private ProductBean product;

  @SerializedName("uuid") private String uuid;

  @SerializedName("payinfos") private List<PayinfosBean> payinfos;

  public ProductBean getProduct() {
    return product;
  }

  public void setProduct(ProductBean product) {
    this.product = product;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public List<PayinfosBean> getPayinfos() {
    return payinfos;
  }

  public void setPayinfos(List<PayinfosBean> payinfos) {
    this.payinfos = payinfos;
  }

  public static class ProductBean {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("outer_id") private String outerId;
    @SerializedName("pic_path") private String picPath;
    @SerializedName("head_img") private String headImg;
    @SerializedName("std_sale_price") private double stdSalePrice;
    @SerializedName("agent_price") private double agentPrice;
    @SerializedName("sale_time") private String saleTime;
    @SerializedName("offshelf_time") private String offshelfTime;
    @SerializedName("product_lowest_price") private double productLowestPrice;
    @SerializedName("normal_skus") private List<NormalSkusBean> normalSkus;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getOuterId() {
      return outerId;
    }

    public void setOuterId(String outerId) {
      this.outerId = outerId;
    }

    public String getPicPath() {
      return picPath;
    }

    public void setPicPath(String picPath) {
      this.picPath = picPath;
    }

    public String getHeadImg() {
      return headImg;
    }

    public void setHeadImg(String headImg) {
      this.headImg = headImg;
    }

    public double getStdSalePrice() {
      return stdSalePrice;
    }

    public void setStdSalePrice(double stdSalePrice) {
      this.stdSalePrice = stdSalePrice;
    }

    public double getAgentPrice() {
      return agentPrice;
    }

    public void setAgentPrice(double agentPrice) {
      this.agentPrice = agentPrice;
    }

    public String getSaleTime() {
      return saleTime;
    }

    public void setSaleTime(String saleTime) {
      this.saleTime = saleTime;
    }

    public String getOffshelfTime() {
      return offshelfTime;
    }

    public void setOffshelfTime(String offshelfTime) {
      this.offshelfTime = offshelfTime;
    }

    public double getProductLowestPrice() {
      return productLowestPrice;
    }

    public void setProductLowestPrice(double productLowestPrice) {
      this.productLowestPrice = productLowestPrice;
    }

    public List<NormalSkusBean> getNormalSkus() {
      return normalSkus;
    }

    public void setNormalSkus(List<NormalSkusBean> normalSkus) {
      this.normalSkus = normalSkus;
    }

    public static class NormalSkusBean {
      @SerializedName("id") private int id;
      @SerializedName("outer_id") private String outerId;
      @SerializedName("name") private String name;
      @SerializedName("remain_num") private int remainNum;
      @SerializedName("size_of_sku") private SizeOfSkuBean sizeOfSku;
      @SerializedName("is_saleout") private boolean isSaleout;
      @SerializedName("std_sale_price") private double stdSalePrice;
      @SerializedName("agent_price") private double agentPrice;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public String getOuterId() {
        return outerId;
      }

      public void setOuterId(String outerId) {
        this.outerId = outerId;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public int getRemainNum() {
        return remainNum;
      }

      public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
      }

      public SizeOfSkuBean getSizeOfSku() {
        return sizeOfSku;
      }

      public void setSizeOfSku(SizeOfSkuBean sizeOfSku) {
        this.sizeOfSku = sizeOfSku;
      }

      public boolean isIsSaleout() {
        return isSaleout;
      }

      public void setIsSaleout(boolean isSaleout) {
        this.isSaleout = isSaleout;
      }

      public double getStdSalePrice() {
        return stdSalePrice;
      }

      public void setStdSalePrice(double stdSalePrice) {
        this.stdSalePrice = stdSalePrice;
      }

      public double getAgentPrice() {
        return agentPrice;
      }

      public void setAgentPrice(double agentPrice) {
        this.agentPrice = agentPrice;
      }

      public static class SizeOfSkuBean {
        @SerializedName("free_num") private String freeNum;
        @SerializedName("result") private ResultBean result;

        public String getFreeNum() {
          return freeNum;
        }

        public void setFreeNum(String freeNum) {
          this.freeNum = freeNum;
        }

        public ResultBean getResult() {
          return result;
        }

        public void setResult(ResultBean result) {
          this.result = result;
        }

        public static class ResultBean {
        }
      }
    }
  }

  public static class PayinfosBean {
    @SerializedName("wallet_cash") private double walletCash;
    @SerializedName("total_payment") private double totalPayment;
    @SerializedName("order_type") private int orderType;
    @SerializedName("alipay_payable") private boolean alipayPayable;
    @SerializedName("discount_fee") private double discountFee;
    @SerializedName("wallet_payable") private boolean walletPayable;
    @SerializedName("weixin_payable") private boolean weixinPayable;
    @SerializedName("total_fee") private double totalFee;
    @SerializedName("post_fee") private double postFee;

    public double getWalletCash() {
      return walletCash;
    }

    public void setWalletCash(double walletCash) {
      this.walletCash = walletCash;
    }

    public double getTotalPayment() {
      return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
      this.totalPayment = totalPayment;
    }

    public int getOrderType() {
      return orderType;
    }

    public void setOrderType(int orderType) {
      this.orderType = orderType;
    }

    public boolean isAlipayPayable() {
      return alipayPayable;
    }

    public void setAlipayPayable(boolean alipayPayable) {
      this.alipayPayable = alipayPayable;
    }

    public double getDiscountFee() {
      return discountFee;
    }

    public void setDiscountFee(double discountFee) {
      this.discountFee = discountFee;
    }

    public boolean isWalletPayable() {
      return walletPayable;
    }

    public void setWalletPayable(boolean walletPayable) {
      this.walletPayable = walletPayable;
    }

    public boolean isWeixinPayable() {
      return weixinPayable;
    }

    public void setWeixinPayable(boolean weixinPayable) {
      this.weixinPayable = weixinPayable;
    }

    public double getTotalFee() {
      return totalFee;
    }

    public void setTotalFee(double totalFee) {
      this.totalFee = totalFee;
    }

    public double getPostFee() {
      return postFee;
    }

    public void setPostFee(double postFee) {
      this.postFee = postFee;
    }
  }
}
