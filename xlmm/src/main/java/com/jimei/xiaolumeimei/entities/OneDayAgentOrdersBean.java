package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/2/18.
 */
public class OneDayAgentOrdersBean {

  /**
   * shops : [{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c3d93413e0a","wxordernick":"蔡恒花(601)","order_cash":49.9,"rebeta_cash":49.9,"ticheng_cash":4.99,"shoptime":"2016-02-17T10:22:19","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555252520451.png","time_display":"10:22"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c3df710c2f1","wxordernick":"汪小姐(027)","order_cash":149.8,"rebeta_cash":149.8,"ticheng_cash":14.98,"shoptime":"2016-02-17T10:48:42","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG-1449297299115-韩版蕾丝拼接针织连衣裙02.png","time_display":"10:48"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c3ff7162b27","wxordernick":"仝敏(229)","order_cash":139.8,"rebeta_cash":139.8,"ticheng_cash":13.98,"shoptime":"2016-02-17T13:05:04","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG-1452841719559-毛呢针织拼接连衣裙3.png","time_display":"13:05"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c40b4f70d08","wxordernick":"芳","order_cash":59.9,"rebeta_cash":59.9,"ticheng_cash":5.99,"shoptime":"2016-02-17T13:55:56","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_1454578001043蕾丝接拼高领毛衣03.png","time_display":"13:55"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c41517445f2","wxordernick":"魏蓉","order_cash":59.9,"rebeta_cash":59.9,"ticheng_cash":5.99,"shoptime":"2016-02-17T14:37:25","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555902055801.png","time_display":"14:37"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c4327f3fd5f","wxordernick":"施佳婕(282)","order_cash":79.9,"rebeta_cash":79.9,"ticheng_cash":7.99,"shoptime":"2016-02-17T16:43:38","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14554406822983.png","time_display":"16:43"},{"linkid":44,"linkname":"小鹿美美特卖服务号","wxorderid":"xd16021756c457c863639","wxordernick":"若雪(199)","order_cash":109.9,"rebeta_cash":109.9,"ticheng_cash":10.99,"shoptime":"2016-02-17T19:49:10","status":0,"get_status_display":"已付款","pic_path":"http://image.xiaolu.so/MG_14555073304281.png","time_display":"19:49"}]
   * clicks : 397
   * click_money : 0
   */

  private int clicks;
  private int click_money;
  /**
   * linkid : 44
   * linkname : 小鹿美美特卖服务号
   * wxorderid : xd16021756c3d93413e0a
   * wxordernick : 蔡恒花(601)
   * order_cash : 49.9
   * rebeta_cash : 49.9
   * ticheng_cash : 4.99
   * shoptime : 2016-02-17T10:22:19
   * status : 0
   * get_status_display : 已付款
   * pic_path : http://image.xiaolu.so/MG_14555252520451.png
   * time_display : 10:22
   *  "dayly_amount": 65.7
   */

  private List<ShopsEntity> shops;

  public void setClicks(int clicks) {
    this.clicks = clicks;
  }

  public void setClick_money(int click_money) {
    this.click_money = click_money;
  }

  public void setShops(List<ShopsEntity> shops) {
    this.shops = shops;
  }

  public int getClicks() {
    return clicks;
  }

  public int getClick_money() {
    return click_money;
  }

  public List<ShopsEntity> getShops() {
    return shops;
  }

  public static class ShopsEntity {
    private int linkid;
    private String linkname;
    private String wxorderid;
    private String wxordernick;
    private double order_cash;
    private double rebeta_cash;
    private double ticheng_cash;
    private String shoptime;
    private int status;
    private String get_status_display;
    private String pic_path;
    private String time_display;
    private double dayly_amount;

    public void setLinkid(int linkid) {
      this.linkid = linkid;
    }

    public void setLinkname(String linkname) {
      this.linkname = linkname;
    }

    public void setWxorderid(String wxorderid) {
      this.wxorderid = wxorderid;
    }

    public void setWxordernick(String wxordernick) {
      this.wxordernick = wxordernick;
    }

    public void setOrder_cash(double order_cash) {
      this.order_cash = order_cash;
    }

    public void setRebeta_cash(double rebeta_cash) {
      this.rebeta_cash = rebeta_cash;
    }

    public void setTicheng_cash(double ticheng_cash) {
      this.ticheng_cash = ticheng_cash;
    }

    public void setShoptime(String shoptime) {
      this.shoptime = shoptime;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public void setGet_status_display(String get_status_display) {
      this.get_status_display = get_status_display;
    }

    public void setPic_path(String pic_path) {
      this.pic_path = pic_path;
    }

    public void setTime_display(String time_display) {
      this.time_display = time_display;
    }

    public void setDayly_amount(double dayly_amount) {
      this.dayly_amount = dayly_amount;
    }

    public int getLinkid() {
      return linkid;
    }

    public String getLinkname() {
      return linkname;
    }

    public String getWxorderid() {
      return wxorderid;
    }

    public String getWxordernick() {
      return wxordernick;
    }

    public double getOrder_cash() {
      return order_cash;
    }

    public double getRebeta_cash() {
      return rebeta_cash;
    }

    public double getTicheng_cash() {
      return ticheng_cash;
    }

    public String getShoptime() {
      return shoptime;
    }

    public int getStatus() {
      return status;
    }

    public String getGet_status_display() {
      return get_status_display;
    }

    public String getPic_path() {
      return pic_path;
    }

    public String getTime_display() {
      return time_display;
    }

    public double getDayly_amount() {
      return dayly_amount;
    }
  }
}
