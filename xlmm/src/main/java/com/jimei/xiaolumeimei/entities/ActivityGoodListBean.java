package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/12/9.
 */

public class ActivityGoodListBean {

    /**
     * category :
     * productsHorizental : []
     * activityId : 379
     * title : 精品提升生活品质
     * topics : []
     * shareBtn : http://img.xiaolumeimei.com/top101478569751361fenxianggeihaoyou.jpg
     * banner : http://img.xiaolumeimei.com/top101478599336231lADOgy22Ic0B680C7g_750_491(1).jpg
     * productsVertical : [{"lowestPrice":88,"pic":"http://img.xiaolumeimei.com/MG_14751366480901.jpg","stdPrice":269,"productName":"整箱20包装竹纤维家庭抑菌纸","modelId":24118},{"lowestPrice":128,"pic":"http://img.xiaolumeimei.com/nine_pic1478231432433","stdPrice":398,"productName":"特供泰国皇家足贴4袋*10贴","modelId":24140},{"lowestPrice":128,"pic":"http://img.xiaolumeimei.com/MG_1478181912280.jpg","stdPrice":640,"productName":"纤之韵美体瘦身胶囊（轻盈1号）0.45g*60","modelId":23488},{"lowestPrice":128,"pic":"http://img.xiaolumeimei.com/nine_pic1478166507744","stdPrice":398,"productName":"特级椴树蜂蜜礼盒装350g","modelId":24158},{"lowestPrice":99,"pic":"http://img.xiaolumeimei.com/MG_1478767867113.jpg","stdPrice":278,"productName":"两条装超萌卡通浴巾","modelId":24449},{"lowestPrice":79,"pic":"http://img.xiaolumeimei.com/MG_1477041648883.jpg","stdPrice":498,"productName":"全棉卡通多功能睡袋（建议身高90-125cm）","modelId":23546},{"lowestPrice":69,"pic":"http://img.xiaolumeimei.com/nine_pic1478072108633","stdPrice":398,"productName":"儿童白鸭绒羽绒外套","modelId":24133},{"lowestPrice":69,"pic":"http://image.xiaolu.so/MG_1469528470702.jpg","stdPrice":599,"productName":"多功能防风抱被","modelId":17587},{"lowestPrice":89,"pic":"http://image.xiaolu.so/MG_1469519837234.jpg","stdPrice":399,"productName":"多功能全棉信封式睡袋","modelId":17585},{"lowestPrice":148,"pic":"http://img.xiaolumeimei.com/nine_pic1478180693784","stdPrice":488,"productName":"晴天花园果粒茶礼盒4罐装","modelId":24160},{"lowestPrice":258,"pic":"http://img.xiaolumeimei.com/nine_pic1478137568689","stdPrice":568,"productName":"加拿大进口花旗参（大片100g）/西洋参","modelId":19119}]
     * coupons : []
     */

    private int activityId;
    private String title;
    private List<ProductsBean> productsVertical;
    private List<ProductsBean> productsHorizental;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductsBean> getProductsHorizental() {
        return productsHorizental;
    }

    public void setProductsHorizental(List<ProductsBean> productsHorizental) {
        this.productsHorizental = productsHorizental;
    }


    public List<ProductsBean> getProductsVertical() {
        return productsVertical;
    }

    public void setProductsVertical(List<ProductsBean> productsVertical) {
        this.productsVertical = productsVertical;
    }


    public static class ProductsBean {
        /**
         * lowestPrice : 88.0
         * pic : http://img.xiaolumeimei.com/MG_14751366480901.jpg
         * stdPrice : 269.0
         * productName : 整箱20包装竹纤维家庭抑菌纸
         * modelId : 24118
         */

        private double lowestPrice;
        private String pic;
        private int modelId;

        public double getLowestPrice() {
            return lowestPrice;
        }

        public void setLowestPrice(double lowestPrice) {
            this.lowestPrice = lowestPrice;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }
    }
}
