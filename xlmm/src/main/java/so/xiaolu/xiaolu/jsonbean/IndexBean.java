package so.xiaolu.xiaolu.jsonbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by huangyan on 15-11-16.
 */
public class IndexBean implements Serializable {
    public List<product> female_list;
    public List<product> child_list;


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
        public String model_id;
    }


    public static class Category {
        public String cid;
        public String parent_cid;
        public String name;
        public String status;
        public String sort_order;
    }

    public static class Productmodel {
        public String id;
        public String name;
        public List<String> head_imgs;
        public List<String> content_imgs;
        public boolean is_single_spec;
        public boolean buy_limit;
        public int per_limit;
    }

}
