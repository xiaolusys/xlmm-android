package com.jimei.xiaolumeimei.entities;

/**
 * Created by wulei on 2016/2/4.
 */
public class AgentInfoBean {

  /**
   * share_mmcode : /media/mm/mm-1.jpg
   * recommend_num : 1
   * clk_num : 0
   * shop_num : 0
   * mobile : 18801806068
   * xlmm : 1
   * mmclog : {"mco":0,"mci":-30,"nmci":0,"ymco":0,"pdc":0,"ymci":0}
   * cash : 1000.0
   * mama_link : http://xiaolu.so/m/1/
   * all_shop_num : 0
   */

  private String share_mmcode;
  private int recommend_num;
  private int clk_num;
  private int shop_num;
  private String mobile;
  private int xlmm;
  /**
   * mco : 0.0
   * mci : -30.0
   * nmci : 0.0
   * ymco : 0.0
   * pdc : 0.0
   * ymci : 0.0
   */

  private MmclogEntity mmclog;
  private double cash;
  private String mama_link;
  private int all_shop_num;

  public void setShare_mmcode(String share_mmcode) {
    this.share_mmcode = share_mmcode;
  }

  public void setRecommend_num(int recommend_num) {
    this.recommend_num = recommend_num;
  }

  public void setClk_num(int clk_num) {
    this.clk_num = clk_num;
  }

  public void setShop_num(int shop_num) {
    this.shop_num = shop_num;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setXlmm(int xlmm) {
    this.xlmm = xlmm;
  }

  public void setMmclog(MmclogEntity mmclog) {
    this.mmclog = mmclog;
  }

  public void setCash(double cash) {
    this.cash = cash;
  }

  public void setMama_link(String mama_link) {
    this.mama_link = mama_link;
  }

  public void setAll_shop_num(int all_shop_num) {
    this.all_shop_num = all_shop_num;
  }

  public String getShare_mmcode() {
    return share_mmcode;
  }

  public int getRecommend_num() {
    return recommend_num;
  }

  public int getClk_num() {
    return clk_num;
  }

  public int getShop_num() {
    return shop_num;
  }

  public String getMobile() {
    return mobile;
  }

  public int getXlmm() {
    return xlmm;
  }

  public MmclogEntity getMmclog() {
    return mmclog;
  }

  public double getCash() {
    return cash;
  }

  public String getMama_link() {
    return mama_link;
  }

  public int getAll_shop_num() {
    return all_shop_num;
  }

  public static class MmclogEntity {
    private double mco;
    private double mci;
    private double nmci;
    private double ymco;
    private double pdc;
    private double ymci;

    public void setMco(double mco) {
      this.mco = mco;
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
        "share_mmcode='" + share_mmcode + '\'' +
        ", recommend_num=" + recommend_num +
        ", clk_num=" + clk_num +
        ", shop_num=" + shop_num +
        ", mobile='" + mobile + '\'' +
        ", xlmm=" + xlmm +
        ", mmclog=" + mmclog +
        ", cash=" + cash +
        ", mama_link='" + mama_link + '\'' +
        ", all_shop_num=" + all_shop_num +
        '}';
  }
}
