package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/11/24.
 */

public class BoutiqueListBean {


    /**
     * count : 20
     * next : http://m.xiaolumeimei.com/rest/v2/modelproducts/boutique?page=2
     * previous : null
     * results : [{"id":23487,"name":"券｜美体瘦身胶囊（轻盈1号）","category_id":88,"lowest_agent_price":98,"lowest_std_sale_price":128,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/MG_1478181912280.jpg","web_url":"https://m.xiaolumeimei.com/mall/product/details/23487","watermark_op":""},{"id":23953,"name":"券｜4罐装果粒茶礼盒","category_id":88,"lowest_agent_price":78,"lowest_std_sale_price":158,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1478180693784","web_url":"https://m.xiaolumeimei.com/mall/product/details/23953","watermark_op":""},{"id":24153,"name":"券｜泰国进口足贴4袋装4x10贴","category_id":126,"lowest_agent_price":72,"lowest_std_sale_price":136,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1478231432433","web_url":"https://m.xiaolumeimei.com/mall/product/details/24153","watermark_op":""},{"id":24154,"name":"券｜白鸭绒羽绒服+防风包被通用","category_id":66,"lowest_agent_price":46,"lowest_std_sale_price":398,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1479376007849","web_url":"https://m.xiaolumeimei.com/mall/product/details/24154","watermark_op":""},{"id":24155,"name":"券｜竹纤维家庭抑菌纸20包+洗洁精3瓶装通用","category_id":94,"lowest_agent_price":52,"lowest_std_sale_price":269,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1480043294962","web_url":"https://m.xiaolumeimei.com/mall/product/details/24155","watermark_op":""},{"id":24156,"name":"券｜加拿大进口花旗参大片100g","category_id":90,"lowest_agent_price":158,"lowest_std_sale_price":568,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1478146853776","web_url":"https://m.xiaolumeimei.com/mall/product/details/24156","watermark_op":""},{"id":24159,"name":"券｜特级椴树峰蜜礼盒装350g","category_id":90,"lowest_agent_price":55,"lowest_std_sale_price":128,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1478179829888","web_url":"https://m.xiaolumeimei.com/mall/product/details/24159","watermark_op":""},{"id":24509,"name":"券｜2条装超萌竹纤维卡通浴巾","category_id":94,"lowest_agent_price":55,"lowest_std_sale_price":278,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1479087645278","web_url":"https://m.xiaolumeimei.com/mall/product/details/24509","watermark_op":""},{"id":24611,"name":"券｜0-4岁全棉卡通多功能睡袋","category_id":94,"lowest_agent_price":52,"lowest_std_sale_price":79,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1479216577763","web_url":"https://m.xiaolumeimei.com/mall/product/details/24611","watermark_op":""},{"id":24658,"name":"券｜0-6岁全棉信封式防踢被","category_id":94,"lowest_agent_price":55,"lowest_std_sale_price":89,"onshelf_time":null,"offshelf_time":null,"is_saleout":false,"sale_state":"will","head_img":"http://img.xiaolumeimei.com/nine_pic1479361569325","web_url":"https://m.xiaolumeimei.com/mall/product/details/24658","watermark_op":""}]
     */

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
        /**
         * id : 23487
         * name : 券｜美体瘦身胶囊（轻盈1号）
         * category_id : 88
         * lowest_agent_price : 98
         * lowest_std_sale_price : 128
         * onshelf_time : null
         * offshelf_time : null
         * is_saleout : false
         * sale_state : will
         * head_img : http://img.xiaolumeimei.com/MG_1478181912280.jpg
         * web_url : https://m.xiaolumeimei.com/mall/product/details/23487
         * watermark_op :
         */

        private int id;
        private String name;
        private int category_id;
        private double lowest_agent_price;
        private double lowest_std_sale_price;
        private String onshelf_time;
        private String offshelf_time;
        private boolean is_saleout;
        private String sale_state;
        private String head_img;
        private String web_url;
        private String watermark_op;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public double getLowest_agent_price() {
            return lowest_agent_price;
        }

        public void setLowest_agent_price(double lowest_agent_price) {
            this.lowest_agent_price = lowest_agent_price;
        }

        public double getLowest_std_sale_price() {
            return lowest_std_sale_price;
        }

        public void setLowest_std_sale_price(double lowest_std_sale_price) {
            this.lowest_std_sale_price = lowest_std_sale_price;
        }

        public String getOnshelf_time() {
            return onshelf_time;
        }

        public void setOnshelf_time(String onshelf_time) {
            this.onshelf_time = onshelf_time;
        }

        public String getOffshelf_time() {
            return offshelf_time;
        }

        public void setOffshelf_time(String offshelf_time) {
            this.offshelf_time = offshelf_time;
        }

        public boolean isIs_saleout() {
            return is_saleout;
        }

        public void setIs_saleout(boolean is_saleout) {
            this.is_saleout = is_saleout;
        }

        public String getSale_state() {
            return sale_state;
        }

        public void setSale_state(String sale_state) {
            this.sale_state = sale_state;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getWatermark_op() {
            return watermark_op;
        }

        public void setWatermark_op(String watermark_op) {
            this.watermark_op = watermark_op;
        }
    }
}
