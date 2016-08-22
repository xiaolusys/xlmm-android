package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMChooselistBean {

  private int count;
  private String next;
  private String previous;
  private List<ResultsBean> results;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public String getPrevious() {
    return previous;
  }

  public void setPrevious(String previous) {
    this.previous = previous;
  }

  public List<ResultsBean> getResults() {
    return results;
  }

  public void setResults(List<ResultsBean> results) {
    this.results = results;
  }

  public static class ResultsBean {
    private int id;
    private String pic_path;
    private String name;
    private double std_sale_price;
    private double agent_price;
    private int remain_num;
    private int in_customer_shop;
    private int shop_product_num;
    private int model_id;
    /**
     * agencylevel_desc : VIP1
     * agencylevel : 2
     * next_agencylevel : 12
     * next_rebet_amount : 14
     * next_agencylevel_desc : VIP2
     * next_rebet_amount_des : 佣 ￥14.00
     * rebet_amount_des : 佣 ￥12.00
     * sale_num_des : 1525人在卖
     * sale_num : 1525
     * rebet_amount : 12
     */

    private LevelInfoBean level_info;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getPic_path() {
      return pic_path;
    }

    public void setPic_path(String pic_path) {
      this.pic_path = pic_path;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public double getStd_sale_price() {
      return std_sale_price;
    }

    public void setStd_sale_price(double std_sale_price) {
      this.std_sale_price = std_sale_price;
    }

    public double getAgent_price() {
      return agent_price;
    }

    public void setAgent_price(double agent_price) {
      this.agent_price = agent_price;
    }

    public int getRemain_num() {
      return remain_num;
    }

    public void setRemain_num(int remain_num) {
      this.remain_num = remain_num;
    }

    public int getIn_customer_shop() {
      return in_customer_shop;
    }

    public void setIn_customer_shop(int in_customer_shop) {
      this.in_customer_shop = in_customer_shop;
    }

    public int getShop_product_num() {
      return shop_product_num;
    }

    public void setShop_product_num(int shop_product_num) {
      this.shop_product_num = shop_product_num;
    }

    public LevelInfoBean getLevel_info() {
      return level_info;
    }

    public void setLevel_info(LevelInfoBean level_info) {
      this.level_info = level_info;
    }

    public int getModel_id() {
      return model_id;
    }

    public void setModel_id(int model_id) {
      this.model_id = model_id;
    }

    public static class LevelInfoBean {
      private String agencylevel_desc;
      private int agencylevel;
      private int next_agencylevel;
      private double next_rebet_amount;
      private String next_agencylevel_desc;
      private String next_rebet_amount_des;
      private String rebet_amount_des;
      private String sale_num_des;
      private int sale_num;
      private double rebet_amount;

      public String getAgencylevel_desc() {
        return agencylevel_desc;
      }

      public void setAgencylevel_desc(String agencylevel_desc) {
        this.agencylevel_desc = agencylevel_desc;
      }

      public int getAgencylevel() {
        return agencylevel;
      }

      public void setAgencylevel(int agencylevel) {
        this.agencylevel = agencylevel;
      }

      public int getNext_agencylevel() {
        return next_agencylevel;
      }

      public void setNext_agencylevel(int next_agencylevel) {
        this.next_agencylevel = next_agencylevel;
      }

      public double getNext_rebet_amount() {
        return next_rebet_amount;
      }

      public void setNext_rebet_amount(int next_rebet_amount) {
        this.next_rebet_amount = next_rebet_amount;
      }

      public String getNext_agencylevel_desc() {
        return next_agencylevel_desc;
      }

      public void setNext_agencylevel_desc(String next_agencylevel_desc) {
        this.next_agencylevel_desc = next_agencylevel_desc;
      }

      public String getNext_rebet_amount_des() {
        return next_rebet_amount_des;
      }

      public void setNext_rebet_amount_des(String next_rebet_amount_des) {
        this.next_rebet_amount_des = next_rebet_amount_des;
      }

      public String getRebet_amount_des() {
        return rebet_amount_des;
      }

      public void setRebet_amount_des(String rebet_amount_des) {
        this.rebet_amount_des = rebet_amount_des;
      }

      public String getSale_num_des() {
        return sale_num_des;
      }

      public void setSale_num_des(String sale_num_des) {
        this.sale_num_des = sale_num_des;
      }

      public int getSale_num() {
        return sale_num;
      }

      public void setSale_num(int sale_num) {
        this.sale_num = sale_num;
      }

      public double getRebet_amount() {
        return rebet_amount;
      }

      public void setRebet_amount(int rebet_amount) {
        this.rebet_amount = rebet_amount;
      }
    }
  }
}
