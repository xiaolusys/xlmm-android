package com.jimei.xiaolumeimei.model;

import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class Promote_Today {

    //今日推荐商品列表


    /**
     * id : 28599
     * url : http://m.xiaolu.so/rest/v1/products/28599.json
     * name : 加厚印花拼接棉麻衬衫/灰色
     * outer_id : 818257940041
     * category : {"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100}
     * pic_path : http://image.xiaolu.so/MG-1450162336120-加厚印花拼接棉麻衬衫02.jpg
     * remain_num : 200
     * is_saleout : false
     * head_img : http://image.xiaolu.so/MG-1450162334574-加厚印花拼接棉麻衬衫01.jpg
     * is_saleopen : true
     * is_newgood : true
     * std_sale_price : 399
     * agent_price : 79.9
     * sale_time : 2015-12-29
     * offshelf_time : null
     * memo :
     * lowest_price : 79.9
     * product_lowest_price : 79.9
     * product_model : {"id":6031,"name":"加厚印花拼接棉麻衬衫","head_imgs":["http://image.xiaolu.so/MG-1450162334574-加厚印花拼接棉麻衬衫01.jpg"],"content_imgs":["http://image.xiaolu.so/MG-1450162362333-加厚印花拼接棉麻衬衫05.jpg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5}
     * ware_by : 2
     * is_verify : true
     * model_id : 6031
     */

    private List<FemaleListEntity> female_list;
    /**
     * id : 10044
     * url : http://m.xiaolu.so/rest/v1/products/10044.json
     * name : 秒杀  花朵蝴蝶结网纱公主裙/粉色
     * outer_id : 9020290601
     * category : {"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50}
     * pic_path : https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXjW96brMltibQfyhtiammUhIia7ugiaP8sTbu5nFZPblXWibuNBRwicvccRHQ/0?wx_fmt=png
     * remain_num : 5
     * is_saleout : true
     * head_img : http://image.xiaolu.so/MG-1451029547476-9.png
     * is_saleopen : true
     * is_newgood : true
     * std_sale_price : 249
     * agent_price : 39.9
     * sale_time : 2015-12-29
     * offshelf_time : null
     * memo :
     * lowest_price : 39.9
     * product_lowest_price : 39.9
     * product_model : {"id":6631,"name":"秒杀  花朵蝴蝶结网纱公主裙","head_imgs":["http://image.xiaolu.so/MG-1451029547476-9.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXmV5XtFAHCc13xF7zFgVMXZF4ExGicbO8Yr4iaN91icnxZ3tGbuGmUvLNw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXDKwiavmvPwdUdziaLqgw5Bqicey7Qx6Ilet7JVH1EQCVgGhW4U3kzrGRA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXnTsicISs0aRpfvXUbPWMTWEGW2f0SwGicsex9iaB1XfsJs17r1ribwXvsg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXIR2vaD5boknrZVXMnYUEn3wk4ksX15hv7HGe1tGicEUVyTuEEg4NVNw/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":true,"buy_limit":false,"per_limit":5}
     * ware_by : 1
     * is_verify : false
     * model_id : 6631
     */

    private List<ChildListEntity> child_list;

    public void setFemale_list(List<FemaleListEntity> female_list) {
        this.female_list = female_list;
    }

    public void setChild_list(List<ChildListEntity> child_list) {
        this.child_list = child_list;
    }

    public List<FemaleListEntity> getFemale_list() {
        return female_list;
    }

    public List<ChildListEntity> getChild_list() {
        return child_list;
    }

    public static class FemaleListEntity {
        private int id;
        private String url;
        private String name;
        private String outer_id;
        /**
         * cid : 18
         * parent_cid : 8
         * name : 外套
         * status : normal
         * sort_order : 100
         */

        private CategoryEntity category;
        private String pic_path;
        private int remain_num;
        private boolean is_saleout;
        private String head_img;
        private boolean is_saleopen;
        private boolean is_newgood;
        private double std_sale_price;
        private double agent_price;
        private String sale_time;
        private Object offshelf_time;
        private String memo;
        private double lowest_price;
        private double product_lowest_price;
        /**
         * id : 6031
         * name : 加厚印花拼接棉麻衬衫
         * head_imgs : ["http://image.xiaolu.so/MG-1450162334574-加厚印花拼接棉麻衬衫01.jpg"]
         * content_imgs : ["http://image.xiaolu.so/MG-1450162362333-加厚印花拼接棉麻衬衫05.jpg"]
         * is_single_spec : false
         * is_sale_out : false
         * buy_limit : false
         * per_limit : 5
         */

        private ProductModelEntity product_model;
        private int ware_by;
        private boolean is_verify;
        private int model_id;

        public void setId(int id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOuter_id(String outer_id) {
            this.outer_id = outer_id;
        }

        public void setCategory(CategoryEntity category) {
            this.category = category;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public void setRemain_num(int remain_num) {
            this.remain_num = remain_num;
        }

        public void setIs_saleout(boolean is_saleout) {
            this.is_saleout = is_saleout;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public void setIs_saleopen(boolean is_saleopen) {
            this.is_saleopen = is_saleopen;
        }

        public void setIs_newgood(boolean is_newgood) {
            this.is_newgood = is_newgood;
        }

        public void setStd_sale_price(int std_sale_price) {
            this.std_sale_price = std_sale_price;
        }

        public void setAgent_price(double agent_price) {
            this.agent_price = agent_price;
        }

        public void setSale_time(String sale_time) {
            this.sale_time = sale_time;
        }

        public void setOffshelf_time(Object offshelf_time) {
            this.offshelf_time = offshelf_time;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public void setLowest_price(double lowest_price) {
            this.lowest_price = lowest_price;
        }

        public void setProduct_lowest_price(double product_lowest_price) {
            this.product_lowest_price = product_lowest_price;
        }

        public void setProduct_model(ProductModelEntity product_model) {
            this.product_model = product_model;
        }

        public void setWare_by(int ware_by) {
            this.ware_by = ware_by;
        }

        public void setIs_verify(boolean is_verify) {
            this.is_verify = is_verify;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

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

        public CategoryEntity getCategory() {
            return category;
        }

        public String getPic_path() {
            return pic_path;
        }

        public int getRemain_num() {
            return remain_num;
        }

        public boolean isIs_saleout() {
            return is_saleout;
        }

        public String getHead_img() {
            return head_img;
        }

        public boolean isIs_saleopen() {
            return is_saleopen;
        }

        public boolean isIs_newgood() {
            return is_newgood;
        }

        public double getStd_sale_price() {
            return std_sale_price;
        }

        public double getAgent_price() {
            return agent_price;
        }

        public String getSale_time() {
            return sale_time;
        }

        public Object getOffshelf_time() {
            return offshelf_time;
        }

        public String getMemo() {
            return memo;
        }

        public double getLowest_price() {
            return lowest_price;
        }

        public double getProduct_lowest_price() {
            return product_lowest_price;
        }

        public ProductModelEntity getProduct_model() {
            return product_model;
        }

        public int getWare_by() {
            return ware_by;
        }

        public boolean isIs_verify() {
            return is_verify;
        }

        public int getModel_id() {
            return model_id;
        }

        public static class CategoryEntity {
            private int cid;
            private int parent_cid;
            private String name;
            private String status;
            private int sort_order;

            public void setCid(int cid) {
                this.cid = cid;
            }

            public void setParent_cid(int parent_cid) {
                this.parent_cid = parent_cid;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public int getCid() {
                return cid;
            }

            public int getParent_cid() {
                return parent_cid;
            }

            public String getName() {
                return name;
            }

            public String getStatus() {
                return status;
            }

            public int getSort_order() {
                return sort_order;
            }
        }

        public static class ProductModelEntity {
            private int id;
            private String name;
            private boolean is_single_spec;
            private boolean is_sale_out;
            private boolean buy_limit;
            private int per_limit;
            private List<String> head_imgs;
            private List<String> content_imgs;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setIs_single_spec(boolean is_single_spec) {
                this.is_single_spec = is_single_spec;
            }

            public void setIs_sale_out(boolean is_sale_out) {
                this.is_sale_out = is_sale_out;
            }

            public void setBuy_limit(boolean buy_limit) {
                this.buy_limit = buy_limit;
            }

            public void setPer_limit(int per_limit) {
                this.per_limit = per_limit;
            }

            public void setHead_imgs(List<String> head_imgs) {
                this.head_imgs = head_imgs;
            }

            public void setContent_imgs(List<String> content_imgs) {
                this.content_imgs = content_imgs;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public boolean isIs_single_spec() {
                return is_single_spec;
            }

            public boolean isIs_sale_out() {
                return is_sale_out;
            }

            public boolean isBuy_limit() {
                return buy_limit;
            }

            public int getPer_limit() {
                return per_limit;
            }

            public List<String> getHead_imgs() {
                return head_imgs;
            }

            public List<String> getContent_imgs() {
                return content_imgs;
            }
        }
    }

    public static class ChildListEntity {
        private int id;
        private String url;
        private String name;
        private String outer_id;
        /**
         * cid : 5
         * parent_cid : 4
         * name : 童装
         * status : normal
         * sort_order : 50
         */

        private CategoryEntity category;
        private String pic_path;
        private int remain_num;
        private boolean is_saleout;
        private String head_img;
        private boolean is_saleopen;
        private boolean is_newgood;
        private int std_sale_price;
        private double agent_price;
        private String sale_time;
        private Object offshelf_time;
        private String memo;
        private double lowest_price;
        private double product_lowest_price;
        /**
         * id : 6631
         * name : 秒杀  花朵蝴蝶结网纱公主裙
         * head_imgs : ["http://image.xiaolu.so/MG-1451029547476-9.png"]
         * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXmV5XtFAHCc13xF7zFgVMXZF4ExGicbO8Yr4iaN91icnxZ3tGbuGmUvLNw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXDKwiavmvPwdUdziaLqgw5Bqicey7Qx6Ilet7JVH1EQCVgGhW4U3kzrGRA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXnTsicISs0aRpfvXUbPWMTWEGW2f0SwGicsex9iaB1XfsJs17r1ribwXvsg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXIR2vaD5boknrZVXMnYUEn3wk4ksX15hv7HGe1tGicEUVyTuEEg4NVNw/0?wx_fmt=png"]
         * is_single_spec : false
         * is_sale_out : true
         * buy_limit : false
         * per_limit : 5
         */

        private ProductModelEntity product_model;
        private int ware_by;
        private boolean is_verify;
        private int model_id;

        public void setId(int id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOuter_id(String outer_id) {
            this.outer_id = outer_id;
        }

        public void setCategory(CategoryEntity category) {
            this.category = category;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public void setRemain_num(int remain_num) {
            this.remain_num = remain_num;
        }

        public void setIs_saleout(boolean is_saleout) {
            this.is_saleout = is_saleout;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public void setIs_saleopen(boolean is_saleopen) {
            this.is_saleopen = is_saleopen;
        }

        public void setIs_newgood(boolean is_newgood) {
            this.is_newgood = is_newgood;
        }

        public void setStd_sale_price(int std_sale_price) {
            this.std_sale_price = std_sale_price;
        }

        public void setAgent_price(double agent_price) {
            this.agent_price = agent_price;
        }

        public void setSale_time(String sale_time) {
            this.sale_time = sale_time;
        }

        public void setOffshelf_time(Object offshelf_time) {
            this.offshelf_time = offshelf_time;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public void setLowest_price(double lowest_price) {
            this.lowest_price = lowest_price;
        }

        public void setProduct_lowest_price(double product_lowest_price) {
            this.product_lowest_price = product_lowest_price;
        }

        public void setProduct_model(ProductModelEntity product_model) {
            this.product_model = product_model;
        }

        public void setWare_by(int ware_by) {
            this.ware_by = ware_by;
        }

        public void setIs_verify(boolean is_verify) {
            this.is_verify = is_verify;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

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

        public CategoryEntity getCategory() {
            return category;
        }

        public String getPic_path() {
            return pic_path;
        }

        public int getRemain_num() {
            return remain_num;
        }

        public boolean isIs_saleout() {
            return is_saleout;
        }

        public String getHead_img() {
            return head_img;
        }

        public boolean isIs_saleopen() {
            return is_saleopen;
        }

        public boolean isIs_newgood() {
            return is_newgood;
        }

        public int getStd_sale_price() {
            return std_sale_price;
        }

        public double getAgent_price() {
            return agent_price;
        }

        public String getSale_time() {
            return sale_time;
        }

        public Object getOffshelf_time() {
            return offshelf_time;
        }

        public String getMemo() {
            return memo;
        }

        public double getLowest_price() {
            return lowest_price;
        }

        public double getProduct_lowest_price() {
            return product_lowest_price;
        }

        public ProductModelEntity getProduct_model() {
            return product_model;
        }

        public int getWare_by() {
            return ware_by;
        }

        public boolean isIs_verify() {
            return is_verify;
        }

        public int getModel_id() {
            return model_id;
        }

        public static class CategoryEntity {
            private int cid;
            private int parent_cid;
            private String name;
            private String status;
            private int sort_order;

            public void setCid(int cid) {
                this.cid = cid;
            }

            public void setParent_cid(int parent_cid) {
                this.parent_cid = parent_cid;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public int getCid() {
                return cid;
            }

            public int getParent_cid() {
                return parent_cid;
            }

            public String getName() {
                return name;
            }

            public String getStatus() {
                return status;
            }

            public int getSort_order() {
                return sort_order;
            }
        }

        public static class ProductModelEntity {
            private int id;
            private String name;
            private boolean is_single_spec;
            private boolean is_sale_out;
            private boolean buy_limit;
            private int per_limit;
            private List<String> head_imgs;
            private List<String> content_imgs;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setIs_single_spec(boolean is_single_spec) {
                this.is_single_spec = is_single_spec;
            }

            public void setIs_sale_out(boolean is_sale_out) {
                this.is_sale_out = is_sale_out;
            }

            public void setBuy_limit(boolean buy_limit) {
                this.buy_limit = buy_limit;
            }

            public void setPer_limit(int per_limit) {
                this.per_limit = per_limit;
            }

            public void setHead_imgs(List<String> head_imgs) {
                this.head_imgs = head_imgs;
            }

            public void setContent_imgs(List<String> content_imgs) {
                this.content_imgs = content_imgs;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public boolean isIs_single_spec() {
                return is_single_spec;
            }

            public boolean isIs_sale_out() {
                return is_sale_out;
            }

            public boolean isBuy_limit() {
                return buy_limit;
            }

            public int getPer_limit() {
                return per_limit;
            }

            public List<String> getHead_imgs() {
                return head_imgs;
            }

            public List<String> getContent_imgs() {
                return content_imgs;
            }
        }
    }


}
