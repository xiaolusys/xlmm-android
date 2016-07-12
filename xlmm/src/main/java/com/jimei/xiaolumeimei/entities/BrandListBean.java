package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by wisdom on 16/5/5.
 */
public class BrandListBean {

    @SerializedName("products") private List<ProductsBean> products;

    public List<ProductsBean> getProducts() {
        return products;
    }

    public static class ProductsBean {
        @SerializedName("id") private int id;
        @SerializedName("product_id") private int productId;
        @SerializedName("model_id") private int modelId;
        @SerializedName("product_name") private String productName;
        @SerializedName("product_img") private String productImg;
        @SerializedName("product_lowest_price") private int productLowestPrice;
        @SerializedName("product_std_sale_price") private int productStdSalePrice;
        @SerializedName("web_url") private String webUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public int getProductLowestPrice() {
            return productLowestPrice;
        }

        public void setProductLowestPrice(int productLowestPrice) {
            this.productLowestPrice = productLowestPrice;
        }

        public int getProductStdSalePrice() {
            return productStdSalePrice;
        }

        public void setProductStdSalePrice(int productStdSalePrice) {
            this.productStdSalePrice = productStdSalePrice;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }
    }
}
