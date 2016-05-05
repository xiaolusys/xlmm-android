package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wisdom on 16/5/5.
 */
public class BrandListBean {


    /**
     * count : 5
     * next : null
     * previous : null
     * results : [{"id":1,"product_id":41369,"product_name":"甜美花朵印花连衣裙/白色","product_img":"http://image.xiaolu.so/MG_1461988366231_2.jpg","product_lowest_price":79.9,"product_std_sale_price":399},{"id":2,"product_id":41370,"product_name":"卡通KT猫中长睡裙/粉色","product_img":"http://image.xiaolu.so/MG_1461988366231_2.jpg","product_lowest_price":49.9,"product_std_sale_price":198},{"id":3,"product_id":41371,"product_name":"中睡裙","product_img":"http://image.xiaolu.so/MG_1461988366231_2.jpg","product_lowest_price":39.9,"product_std_sale_price":99},{"id":4,"product_id":41372,"product_name":"短睡裙","product_img":"http://image.xiaolu.so/MG_1461988366231_2.jpg","product_lowest_price":39.9,"product_std_sale_price":99},{"id":5,"product_id":41373,"product_name":"连衣裙","product_img":"http://image.xiaolu.so/MG_1461988366231_2.jpg","product_lowest_price":39.9,"product_std_sale_price":99}]
     */

    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private Object next;
    @SerializedName("previous")
    private Object previous;
    /**
     * id : 1
     * product_id : 41369
     * product_name : 甜美花朵印花连衣裙/白色
     * product_img : http://image.xiaolu.so/MG_1461988366231_2.jpg
     * product_lowest_price : 79.9
     * product_std_sale_price : 399.0
     */

    @SerializedName("results")
    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        @SerializedName("id")
        private int id;
        @SerializedName("product_id")
        private int product_id;
        @SerializedName("product_name")
        private String product_name;
        @SerializedName("product_img")
        private String product_img;
        @SerializedName("product_lowest_price")
        private double product_lowest_price;
        @SerializedName("product_std_sale_price")
        private double product_std_sale_price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public double getProduct_lowest_price() {
            return product_lowest_price;
        }

        public void setProduct_lowest_price(double product_lowest_price) {
            this.product_lowest_price = product_lowest_price;
        }

        public double getProduct_std_sale_price() {
            return product_std_sale_price;
        }

        public void setProduct_std_sale_price(double product_std_sale_price) {
            this.product_std_sale_price = product_std_sale_price;
        }
    }
}
