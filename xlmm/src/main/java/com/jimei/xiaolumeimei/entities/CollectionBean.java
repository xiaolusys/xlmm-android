package com.jimei.xiaolumeimei.entities;

import java.io.Serializable;

/**
 * Created by wisdom on 16/7/28.
 */
public class CollectionBean implements Serializable {

    /**
     * id : 13
     * modelproduct : {"name":"韩版圆领印花宽松T","shelf_status":0,"lowest_std_sale_price":239,"web_url":"http://192.168.1.11:9000/mall/product/details/1","lowest_agent_price":46,"head_img":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsgljqcRPLwCxWPfFe7jrml86L9bwgWXmAWwiahG3Epmzk8icfqO9U8aNT1Gt5qJVMZQ18B1iaD0gowA/0?wx_fmt=png","id":1}
     * created : 2016-07-28T09:45:05
     */

    private int id;
    /**
     * name : 韩版圆领印花宽松T
     * shelf_status : 0
     * lowest_std_sale_price : 239.0
     * web_url : http://192.168.1.11:9000/mall/product/details/1
     * lowest_agent_price : 46.0
     * head_img : https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsgljqcRPLwCxWPfFe7jrml86L9bwgWXmAWwiahG3Epmzk8icfqO9U8aNT1Gt5qJVMZQ18B1iaD0gowA/0?wx_fmt=png
     * id : 1
     */

    private ModelproductBean modelproduct;
    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelproductBean getModelproduct() {
        return modelproduct;
    }

    public void setModelproduct(ModelproductBean modelproduct) {
        this.modelproduct = modelproduct;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public static class ModelproductBean implements Serializable {
        private String name;
        private int shelf_status;
        private double lowest_std_sale_price;
        private String web_url;
        private double lowest_agent_price;
        private String head_img;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getShelf_status() {
            return shelf_status;
        }

        public void setShelf_status(int shelf_status) {
            this.shelf_status = shelf_status;
        }

        public double getLowest_std_sale_price() {
            return lowest_std_sale_price;
        }

        public void setLowest_std_sale_price(double lowest_std_sale_price) {
            this.lowest_std_sale_price = lowest_std_sale_price;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public double getLowest_agent_price() {
            return lowest_agent_price;
        }

        public void setLowest_agent_price(double lowest_agent_price) {
            this.lowest_agent_price = lowest_agent_price;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
