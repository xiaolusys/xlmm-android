package com.jimei.xiaolumeimei.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangyan on 15-11-16.
 */
public class IndexBean implements Serializable {
  public List<product> female_list;
  public List<product> child_list;

  public List<product> getFemale_list() {
    return female_list;
  }

  public List<product> getChild_list() {
    return child_list;
  }

  public static class product {
    public int id;
    public String url;
    public String name;
    public String outer_id;
    public Category category;
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
    public Productmodel product_model;
    public String ware_by;
    public boolean is_verify;
    public int model_id;

    public int getId() {
      return id;
    }

    public String getUrl() {
      return url;
    }

    public String getName() {
      return name;
    }

    public String getOuter_id() {
      return outer_id;
    }

    public Category getCategory() {
      return category;
    }

    public String getPic_path() {
      return pic_path;
    }

    public int getRemain_num() {
      return remain_num;
    }

    public boolean is_saleout() {
      return is_saleout;
    }

    public String getHead_img() {
      return head_img;
    }

    public boolean is_saleopen() {
      return is_saleopen;
    }

    public boolean is_newgood() {
      return is_newgood;
    }

    public float getStd_sale_price() {
      return std_sale_price;
    }

    public float getAgent_price() {
      return agent_price;
    }

    public String getSale_time() {
      return sale_time;
    }

    public String getOffshelf_time() {
      return offshelf_time;
    }

    public String getMemo() {
      return memo;
    }

    public float getLowest_price() {
      return lowest_price;
    }

    public float getProduct_lowest_price() {
      return product_lowest_price;
    }

    public Productmodel getProduct_model() {
      return product_model;
    }

    public String getWare_by() {
      return ware_by;
    }

    public boolean is_verify() {
      return is_verify;
    }

    public int getModel_id() {
      return model_id;
    }
  }

  public static class Category {
    public String cid;
    public String parent_cid;
    public String name;
    public String status;
    public String sort_order;

    public String getCid() {
      return cid;
    }

    public String getParent_cid() {
      return parent_cid;
    }

    public String getName() {
      return name;
    }

    public String getStatus() {
      return status;
    }

    public String getSort_order() {
      return sort_order;
    }
  }

  public static class Productmodel {

    public int id;
    public String name;
    public List<String> head_imgs;
    public List<String> content_imgs;
    public boolean is_single_spec;
    public boolean buy_limit;
    public int per_limit;

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public List<String> getHead_imgs() {
      return head_imgs;
    }

    public List<String> getContent_imgs() {
      return content_imgs;
    }

    public boolean is_single_spec() {
      return is_single_spec;
    }

    public boolean isBuy_limit() {
      return buy_limit;
    }

    public int getPer_limit() {
      return per_limit;
    }
  }
}
