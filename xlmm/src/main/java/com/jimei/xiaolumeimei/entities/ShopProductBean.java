package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by itxuye on 16/4/2.
 */
public class ShopProductBean {


    /**
     * count : 11
     * next : http://api.xiaolumeimei.com/rest/v1/pmt/cushoppros/shop_product?page=2
     * previous : null
     * results : [{"id":16081,"product":35637,"pro_status":1,"name":"韩版刺绣士兵牛仔裤/图片色","pic_path":"http://image.xiaolu.so/MG_1457665554132韩版刺绣士兵牛仔裤01.png","std_sale_price":199,"agent_price":49.9,"carry_amount":9.98,"position":230,"sale_num":954,"modified":"2016-04-02T11:47:07","created":"2016-04-02T11:47:07","offshelf_time":"2016-03-15T10:00:00"},{"id":16079,"product":35673,"pro_status":1,"name":"可爱米奇印花牛仔裤/牛仔蓝","pic_path":"http://image.xiaolu.so/MG_1457680823698春季儿童卡通印花牛仔裤01.png","std_sale_price":159,"agent_price":59.9,"carry_amount":11.98,"position":229,"sale_num":1158,"modified":"2016-04-02T11:47:26","created":"2016-04-02T11:47:01","offshelf_time":"2016-03-16T10:00:00"},{"id":16077,"product":36189,"pro_status":1,"name":"高腰显瘦阔腿裤/黑色","pic_path":"http://image.xiaolu.so/MG_1458265324707头图背景1.png","std_sale_price":168,"agent_price":49,"carry_amount":9.8,"position":228,"sale_num":2281,"modified":"2016-04-02T11:47:26","created":"2016-04-02T11:46:58","offshelf_time":"2016-04-03T10:00:00"},{"id":16075,"product":36218,"pro_status":1,"name":"收腰提臀铅笔裤/101字母款","pic_path":"http://image.xiaolu.so/MG_1458268786673头图背景1.png","std_sale_price":198,"agent_price":49,"carry_amount":9.8,"position":227,"sale_num":4760,"modified":"2016-04-02T11:46:56","created":"2016-04-02T11:46:56","offshelf_time":"2016-04-03T10:00:00"},{"id":16074,"product":37300,"pro_status":1,"name":"四条装提花内裤/四条装","pic_path":"http://image.xiaolu.so/MG_1459428078579头图背景拷贝.png","std_sale_price":199,"agent_price":49.9,"carry_amount":9.98,"position":226,"sale_num":3806,"modified":"2016-04-02T11:46:54","created":"2016-04-02T11:46:54","offshelf_time":"2016-04-03T10:00:00"},{"id":287,"product":35656,"pro_status":1,"name":"卡通造型纯棉连衣裙/熊猫","pic_path":"http://image.xiaolu.so/MG_1457665755535韩版立体耳朵连衣裙02.png","std_sale_price":399,"agent_price":59.9,"carry_amount":11.98,"position":225,"sale_num":966,"modified":"2016-04-02T11:47:19","created":"2016-03-12T11:03:55","offshelf_time":"2016-03-15T10:00:00"},{"id":16063,"product":37457,"pro_status":1,"name":"百搭短款纯色开衫/红色","pic_path":"http://image.xiaolu.so/MG_1459339628711百搭短款纯色开衫2.png","std_sale_price":298,"agent_price":89.9,"carry_amount":17.98,"position":224,"sale_num":1153,"modified":"2016-04-02T11:47:19","created":"2016-04-02T11:46:28","offshelf_time":"2016-04-03T10:00:00"},{"id":16056,"product":37466,"pro_status":1,"name":"韩版宽松显瘦开衫/酒红色","pic_path":"http://image.xiaolu.so/MG_1459390916643韩版宽松显瘦开衫3.png","std_sale_price":198,"agent_price":69.9,"carry_amount":13.98,"position":223,"sale_num":386,"modified":"2016-04-02T11:47:32","created":"2016-04-02T11:46:17","offshelf_time":"2016-04-03T10:00:00"},{"id":396,"product":35729,"pro_status":1,"name":"韩版撞色拼接印花套装/粉色","pic_path":"http://image.xiaolu.so/MG_1457754244749韩版女童拼色小狗套装04.png","std_sale_price":299,"agent_price":69.9,"carry_amount":13.98,"position":222,"sale_num":1144,"modified":"2016-04-02T11:47:32","created":"2016-03-15T10:25:44","offshelf_time":"2016-04-03T10:00:00"},{"id":16059,"product":37463,"pro_status":1,"name":"时尚流苏显瘦外套/红色","pic_path":"http://image.xiaolu.so/MG_1459390865584时尚流苏显瘦外套2.png","std_sale_price":298,"agent_price":79.9,"carry_amount":15.98,"position":68,"sale_num":389,"modified":"2016-04-02T11:47:28","created":"2016-04-02T11:46:21","offshelf_time":"2016-04-03T10:00:00"}]
     */

    private int count;
    private String next;
    private String previous;
    /**
     * id : 16081
     * product : 35637
     * pro_status : 1
     * name : 韩版刺绣士兵牛仔裤/图片色
     * pic_path : http://image.xiaolu.so/MG_1457665554132韩版刺绣士兵牛仔裤01.png
     * std_sale_price : 199.0
     * agent_price : 49.9
     * carry_amount : 9.98
     * position : 230
     * sale_num : 954
     * modified : 2016-04-02T11:47:07
     * created : 2016-04-02T11:47:07
     * offshelf_time : 2016-03-15T10:00:00
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
        private int product;
        private int pro_status;
        private String name;
        private String pic_path;
        private double std_sale_price;
        private double agent_price;
        private double carry_amount;
        private int position;
        private int sale_num;
        private String modified;
        private String created;
        private String offshelf_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }

        public int getPro_status() {
            return pro_status;
        }

        public void setPro_status(int pro_status) {
            this.pro_status = pro_status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public double getStd_sale_price() {
            return std_sale_price;
        }

        public void setStd_sale_price(double std_sale_price) {
            this.std_sale_price = std_sale_price;
        }

        public double getAgent_price() {
            return agent_price;
        }

        public void setAgent_price(double agent_price) {
            this.agent_price = agent_price;
        }

        public double getCarry_amount() {
            return carry_amount;
        }

        public void setCarry_amount(double carry_amount) {
            this.carry_amount = carry_amount;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getSale_num() {
            return sale_num;
        }

        public void setSale_num(int sale_num) {
            this.sale_num = sale_num;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getOffshelf_time() {
            return offshelf_time;
        }

        public void setOffshelf_time(String offshelf_time) {
            this.offshelf_time = offshelf_time;
        }
    }
}
