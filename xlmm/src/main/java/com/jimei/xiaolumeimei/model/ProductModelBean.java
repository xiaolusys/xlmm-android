package com.jimei.xiaolumeimei.model;

import java.util.List;

/**
 * Created by yann on 15-11-19.
 */
public class ProductModelBean {
    public String id;
    public String name;
    public List<String> head_imgs;
    public List<String> content_imgs;
    public boolean is_single_spec;
    public boolean is_sale_out;
    public boolean buy_limit;
    public int per_limit;

}
