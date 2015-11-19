package so.xiaolu.xiaolu.jsonbean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 15-11-19.
 */
public class ProductSkuBean {
    public String id;
    public String outer_id;
    public String name;
    public String remain_num;
    public SkuSizeBean size_of_sku;
    public String result;
    public boolean is_saleout;
    public float std_sale_price;
    public float agent_price;

    public class SkuSizeBean {
        public String free_num;
        public HashMap<String, String> result;
    }
}
