package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductBean {

  public String id;
  public String url;
  public String name;
  public String outer_id;
  //    public Category category;
  public String pic_path;
  public int remain_num;
  public boolean is_saleout;
  public String head_img;
  public boolean is_saleopen;
  public boolean is_newgood;
  public float std_sale_price;
  public float agent_price;
  public String sale_time;
  public String offshelf_time;
  public String memo;
  public float lowest_price;
  public float product_lowest_price;
  public ProductModelBean product_model;
  public String ware_by;
  public boolean is_verify;
  public String model_id;
  public List<ProductSkuBean> normal_skus;

  @Override public String toString() {
    return "ProductBean{" +
        "id='" + id + '\'' +
        ", url='" + url + '\'' +
        ", name='" + name + '\'' +
        ", outer_id='" + outer_id + '\'' +
        ", pic_path='" + pic_path + '\'' +
        ", remain_num=" + remain_num +
        ", is_saleout=" + is_saleout +
        ", head_img='" + head_img + '\'' +
        ", is_saleopen=" + is_saleopen +
        ", is_newgood=" + is_newgood +
        ", std_sale_price=" + std_sale_price +
        ", agent_price=" + agent_price +
        ", sale_time='" + sale_time + '\'' +
        ", offshelf_time='" + offshelf_time + '\'' +
        ", memo='" + memo + '\'' +
        ", lowest_price=" + lowest_price +
        ", product_lowest_price=" + product_lowest_price +
        ", product_model=" + product_model +
        ", ware_by='" + ware_by + '\'' +
        ", is_verify=" + is_verify +
        ", model_id='" + model_id + '\'' +
        ", normal_skus=" + normal_skus +
        '}';
  }
}
