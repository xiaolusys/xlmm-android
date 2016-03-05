package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
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
    public String id;
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

    public String getId() {
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

    /**
     * id : 7789
     * name : 七分袖蕾丝雪纺衫
     * head_imgs : ["http://image.xiaolu.so/MG_14539595037370.png"]
     * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvdRz7ZHuZAVjA05LLAOS35cZd07x435G9oPEsbHTUZ56t1hc5erNibHhOeU7OOOCAxvJ408zhvCow/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvdRz7ZHuZAVjA05LLAOS35YOj7MmEaob3Q0XIxeX7FJIn7YIRZQSPcsvUzLwniaDL97DCktZCD8QQ/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvdRz7ZHuZAVjA05LLAOS35m8dAI2r2S8JJMLZ99Ab0LicRH0LxoNmVILKFGa8ElPYFyjRIEL1jIzQ/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvdRz7ZHuZAVjA05LLAOS35a8jiaMEf1r2a9n3A6icsgUJxoCrBupSKoWwOW25Ic45XDJibqdOTIhDVQ/0?wx_fmt=jpeg"]
     * is_single_spec : false
     * is_sale_out : false
     * buy_limit : false
     * per_limit : 5
     */

    @SerializedName("id") private int mId;
    @SerializedName("name") private String mName;
    @SerializedName("is_single_spec") private boolean mIsSingleSpec;
    @SerializedName("is_sale_out") private boolean mIsSaleOut;
    @SerializedName("buy_limit") private boolean mBuyLimit;
    @SerializedName("per_limit") private int mPerLimit;
    @SerializedName("head_imgs") private List<String> mHeadImgs;
    @SerializedName("content_imgs") private List<String> mContentImgs;

    public void setId(int id) {
      this.mId = id;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public void setIsSingleSpec(boolean isSingleSpec) {
      this.mIsSingleSpec = isSingleSpec;
    }

    public void setIsSaleOut(boolean isSaleOut) {
      this.mIsSaleOut = isSaleOut;
    }

    public void setBuyLimit(boolean buyLimit) {
      this.mBuyLimit = buyLimit;
    }

    public void setPerLimit(int perLimit) {
      this.mPerLimit = perLimit;
    }

    public void setHeadImgs(List<String> headImgs) {
      this.mHeadImgs = headImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
      this.mContentImgs = contentImgs;
    }

    public int getId() {
      return mId;
    }

    public String getName() {
      return mName;
    }

    public boolean isIsSingleSpec() {
      return mIsSingleSpec;
    }

    public boolean isIsSaleOut() {
      return mIsSaleOut;
    }

    public boolean isBuyLimit() {
      return mBuyLimit;
    }

    public int getPerLimit() {
      return mPerLimit;
    }

    public List<String> getHeadImgs() {
      return mHeadImgs;
    }

    public List<String> getContentImgs() {
      return mContentImgs;
    }
  }
}
