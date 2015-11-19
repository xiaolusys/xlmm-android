package so.xiaolu.xiaolu.jsonbean;

import java.util.List;

/**
 * Created by root on 15-11-17.
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
}
