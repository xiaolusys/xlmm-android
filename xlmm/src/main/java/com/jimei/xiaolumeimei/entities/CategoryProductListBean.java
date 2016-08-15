package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/8/6.
 */
public class CategoryProductListBean {

    /**
     * count : 12
     * next : http://m.xiaolumeimei.com/rest/v2/modelproducts?category_id=63&page=2
     * previous : null
     * results : [{"id":16066,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/16066","name":"舒适透气铁锚绣花男童T恤","category_id":63,"lowest_agent_price":19.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://image.xiaolu.so/MG_1467620813336.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/16066","watermark_op":""},{"id":16090,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/16090","name":"时尚简约卡通T恤衫","category_id":63,"lowest_agent_price":19.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://image.xiaolu.so/MG_14686401898033.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/16090","watermark_op":""},{"id":17969,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17969","name":"小红猪圆领毛衣","category_id":63,"lowest_agent_price":69.9,"lowest_std_sale_price":299,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470128136388.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17969","watermark_op":""},{"id":17974,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17974","name":"荷叶袖缤纷印花上衣","category_id":63,"lowest_agent_price":39.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470121340724--.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17974","watermark_op":""},{"id":17982,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17982","name":"简约舒适纯棉T恤","category_id":63,"lowest_agent_price":29.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470207620381.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17982","watermark_op":""},{"id":17990,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17990","name":"可爱卡通印花打底衫","category_id":63,"lowest_agent_price":39.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470128235030.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17990","watermark_op":""},{"id":17992,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17992","name":"清新蝴蝶刺绣连衣裙","category_id":63,"lowest_agent_price":59.9,"lowest_std_sale_price":199,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470129657830.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17992","watermark_op":""},{"id":17994,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17994","name":"可爱卡通印花T恤","category_id":63,"lowest_agent_price":19.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470195132541--.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17994","watermark_op":""},{"id":17996,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17996","name":"休闲百搭印花T恤","category_id":63,"lowest_agent_price":29.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470196092089--.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17996","watermark_op":""},{"id":17999,"url":"http://m.xiaolumeimei.com/rest/v2/modelproducts/17999","name":"简约纯色棉质衬衣","category_id":63,"lowest_agent_price":39.9,"lowest_std_sale_price":99,"is_saleout":false,"is_favorite":false,"sale_state":"on","head_img":"http://img.xiaolumeimei.com/MG_1470201567207-.jpg","web_url":"http://m.xiaolumeimei.com/mall/product/details/17999","watermark_op":""}]
     */

    private int count;
    private String next;
    private String previous;
    /**
     * id : 16066
     * url : http://m.xiaolumeimei.com/rest/v2/modelproducts/16066
     * name : 舒适透气铁锚绣花男童T恤
     * category_id : 63
     * lowest_agent_price : 19.9
     * lowest_std_sale_price : 99
     * is_saleout : false
     * is_favorite : false
     * sale_state : on
     * head_img : http://image.xiaolu.so/MG_1467620813336.jpg
     * web_url : http://m.xiaolumeimei.com/mall/product/details/16066
     * watermark_op :
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
        private String url;
        private String name;
        private int category_id;
        private double lowest_agent_price;
        private int lowest_std_sale_price;
        private boolean is_saleout;
        private boolean is_favorite;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public int getLowest_std_sale_price() {
            return lowest_std_sale_price;
        }

        public void setLowest_std_sale_price(int lowest_std_sale_price) {
            this.lowest_std_sale_price = lowest_std_sale_price;
        }

        public boolean isIs_saleout() {
            return is_saleout;
        }

        public void setIs_saleout(boolean is_saleout) {
            this.is_saleout = is_saleout;
        }

        public boolean isIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(boolean is_favorite) {
            this.is_favorite = is_favorite;
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
