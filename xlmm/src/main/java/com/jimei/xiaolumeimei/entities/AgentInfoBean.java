package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wulei on 2016/2/4.
 */
public class AgentInfoBean {

  /**
   * share_mmcode : /media/mm/mm-9.jpg
   * recommend_num : 1
   * xlmm : 9
   * mama_link : http://xiaolu.so/m/9/
   * clk_num : 0
   * fans_num : 0
   * mobile : 18801806068
   * cash : 0.0
   * clk_money : 0
   * shop_num : 0
   * mmclog : {"mco":0,"clki":0,"mci":0,"nmci":0,"ymco":0,"pdc":0,"ymci":0}
   * all_shop_num : 0
   */

  @SerializedName("share_mmcode") private String shareMmcode;
  @SerializedName("recommend_num") private int recommendNum;
  @SerializedName("xlmm") private int xlmm;
  @SerializedName("mama_link") private String mamaLink;
  @SerializedName("clk_num") private int clkNum;
  @SerializedName("fans_num") private int fansNum;
  @SerializedName("mobile") private String mobile;
  @SerializedName("cash") private double cash;
  @SerializedName("clk_money") private int clkMoney;
  @SerializedName("shop_num") private int shopNum;
  /**
   * mco : 0.0
   * clki : 0.0
   * mci : 0.0
   * nmci : 0.0
   * ymco : 0.0
   * pdc : 0.0
   * ymci : 0.0
   */

  @SerializedName("mmclog") private MmclogEntity mmclog;
  @SerializedName("all_shop_num") private int allShopNum;

  public void setShareMmcode(String shareMmcode) {
    this.shareMmcode = shareMmcode;
  }

  public void setRecommendNum(int recommendNum) {
    this.recommendNum = recommendNum;
  }

  public void setXlmm(int xlmm) {
    this.xlmm = xlmm;
  }

  public void setMamaLink(String mamaLink) {
    this.mamaLink = mamaLink;
  }

  public void setClkNum(int clkNum) {
    this.clkNum = clkNum;
  }

  public void setFansNum(int fansNum) {
    this.fansNum = fansNum;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setCash(double cash) {
    this.cash = cash;
  }

  public void setClkMoney(int clkMoney) {
    this.clkMoney = clkMoney;
  }

  public void setShopNum(int shopNum) {
    this.shopNum = shopNum;
  }

  public void setMmclog(MmclogEntity mmclog) {
    this.mmclog = mmclog;
  }

  public void setAllShopNum(int allShopNum) {
    this.allShopNum = allShopNum;
  }

  public String getShareMmcode() {
    return shareMmcode;
  }

  public int getRecommendNum() {
    return recommendNum;
  }

  public int getXlmm() {
    return xlmm;
  }

  public String getMamaLink() {
    return mamaLink;
  }

  public int getClkNum() {
    return clkNum;
  }

  public int getFansNum() {
    return fansNum;
  }

  public String getMobile() {
    return mobile;
  }

  public double getCash() {
    return cash;
  }

  public int getClkMoney() {
    return clkMoney;
  }

  public int getShopNum() {
    return shopNum;
  }

  public MmclogEntity getMmclog() {
    return mmclog;
  }

  public int getAllShopNum() {
    return allShopNum;
  }

  public static class MmclogEntity {
    @SerializedName("mco") private double mco;
    @SerializedName("clki") private double clki;
    @SerializedName("mci") private double mci;
    @SerializedName("nmci") private double nmci;
    @SerializedName("ymco") private double ymco;
    @SerializedName("pdc") private double pdc;
    @SerializedName("ymci") private double ymci;

    public void setMco(double mco) {
      this.mco = mco;
    }

    public void setClki(double clki) {
      this.clki = clki;
    }

    public void setMci(double mci) {
      this.mci = mci;
    }

    public void setNmci(double nmci) {
      this.nmci = nmci;
    }

    public void setYmco(double ymco) {
      this.ymco = ymco;
    }

    public void setPdc(double pdc) {
      this.pdc = pdc;
    }

    public void setYmci(double ymci) {
      this.ymci = ymci;
    }

    public double getMco() {
      return mco;
    }

    public double getClki() {
      return clki;
    }

    public double getMci() {
      return mci;
    }

    public double getNmci() {
      return nmci;
    }

    public double getYmco() {
      return ymco;
    }

    public double getPdc() {
      return pdc;
    }

    public double getYmci() {
      return ymci;
    }
  }

  @Override public String toString() {
    return "AgentInfoBean{" +
        "shareMmcode='" + shareMmcode + '\'' +
        ", recommendNum=" + recommendNum +
        ", xlmm=" + xlmm +
        ", mamaLink='" + mamaLink + '\'' +
        ", clkNum=" + clkNum +
        ", fansNum=" + fansNum +
        ", mobile='" + mobile + '\'' +
        ", cash=" + cash +
        ", clkMoney=" + clkMoney +
        ", shopNum=" + shopNum +
        ", mmclog=" + mmclog +
        ", allShopNum=" + allShopNum +
        '}';
  }
}
