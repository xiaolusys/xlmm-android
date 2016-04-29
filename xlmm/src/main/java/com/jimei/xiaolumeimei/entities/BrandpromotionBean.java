package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/23.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BrandpromotionBean {


  /**
   * count : 4
   * next :
   * previous :
   * results : [{"id":1,"product_id":41369,"product_name":"甜美花朵印花连衣裙/白色","product_img":"https://cbu01.alicdn.com/img/ibank/2016/433/719/2930917334_733750456.400x400.jpg","product_lowest_price":79.9,"product_std_sale_price":399},{"id":2,"product_id":41370,"product_name":"卡通KT猫中长睡裙/粉色","product_img":"https://cbu01.alicdn.com/img/ibank/2016/633/552/2885255336_793671471.400x400.jpg","product_lowest_price":49.9,"product_std_sale_price":198},{"id":3,"product_id":41348,"product_name":"百搭修身短款牛仔上衣/浅色弹力","product_img":"//img.alicdn.com/bao/uploaded/i4/TB1L3D7LXXXXXcPXFXXXXXXXXXX_!!0-item_pic.jpg","product_lowest_price":99.9,"product_std_sale_price":299},{"id":4,"product_id":41341,"product_name":"显瘦宽松雪纺套装/枚红色+黑色","product_img":"https://cbu01.alicdn.com/img/ibank/2016/595/089/2963980595_927999585.400x400.jpg","product_lowest_price":89.9,"product_std_sale_price":299}]
   */

  private int count;
  private String next;
  private String previous;
  /**
   * id : 1
   * product_id : 41369
   * product_name : 甜美花朵印花连衣裙/白色
   * product_img : https://cbu01.alicdn.com/img/ibank/2016/433/719/2930917334_733750456.400x400.jpg
   * product_lowest_price : 79.9
   * product_std_sale_price : 399.0
   */

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
    private int product_id;
    private String product_name;
    private String product_img;
    private double product_lowest_price;
    private double product_std_sale_price;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getProduct_id() {
      return product_id;
    }

    public void setProduct_id(int product_id) {
      this.product_id = product_id;
    }

    public String getProduct_name() {
      return product_name;
    }

    public void setProduct_name(String product_name) {
      this.product_name = product_name;
    }

    public String getProduct_img() {
      return product_img;
    }

    public void setProduct_img(String product_img) {
      this.product_img = product_img;
    }

    public double getProduct_lowest_price() {
      return product_lowest_price;
    }

    public void setProduct_lowest_price(double product_lowest_price) {
      this.product_lowest_price = product_lowest_price;
    }

    public double getProduct_std_sale_price() {
      return product_std_sale_price;
    }

    public void setProduct_std_sale_price(double product_std_sale_price) {
      this.product_std_sale_price = product_std_sale_price;
    }
  }
}
