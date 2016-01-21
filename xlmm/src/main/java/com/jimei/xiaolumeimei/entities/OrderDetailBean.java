package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wl on 15-12-16.
 */

public class OrderDetailBean  {

    /**
     * id : 203351
     * url : http://m.xiaolumeimei.com/rest/v1/trades/203351
     * orders : [{"id":217722,"oid":"xo151031563437358da18","item_id":"23747","title":"鍔犵粧鏃跺皻鍗。杩愬姩涓変欢濂�/绱孩鑹�","sku_id":"93158","num":1,"outer_id":"822242710022","total_fee":169.9,"payment":169.9,"discount_fee":0,"sku_name":"3XL","pic_path":"https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHw3Is7CVATZLkcH8QWibA9kliby0w6k7VSAj9Ae9lkgQQPFHGmKr4qickA0xdZUV5ZAJBesbezN7bqeQ/0?wx_fmt=png","status":7,"status_display":"浜ゆ槗鍏抽棴","refund_status":0,"refund_status_display":"娌℃湁閫\u20ac娆�","refund_id":null,"kill_title":false},{"id":217723,"oid":"xo1510315634373596d7b","item_id":"23747","title":"鍔犵粧鏃跺皻鍗。杩愬姩涓変欢濂�/绱孩鑹�","sku_id":"93156","num":2,"outer_id":"822242710022","total_fee":339.8,"payment":339.8,"discount_fee":0,"sku_name":"XL","pic_path":"https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHw3Is7CVATZLkcH8QWibA9kliby0w6k7VSAj9Ae9lkgQQPFHGmKr4qickA0xdZUV5ZAJBesbezN7bqeQ/0?wx_fmt=png","status":7,"status_display":"浜ゆ槗鍏抽棴","refund_status":0,"refund_status_display":"娌℃湁閫\u20ac娆�","refund_id":null,"kill_title":false},{"id":217724,"oid":"xo151031563437359e908","item_id":"23747","title":"鍔犵粧鏃跺皻鍗。杩愬姩涓変欢濂�/绱孩鑹�","sku_id":"93155","num":1,"outer_id":"822242710022","total_fee":169.9,"payment":169.9,"discount_fee":0,"sku_name":"L","pic_path":"https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHw3Is7CVATZLkcH8QWibA9kliby0w6k7VSAj9Ae9lkgQQPFHGmKr4qickA0xdZUV5ZAJBesbezN7bqeQ/0?wx_fmt=png","status":7,"status_display":"浜ゆ槗鍏抽棴","refund_status":0,"refund_status_display":"娌℃湁閫\u20ac娆�","refund_id":null,"kill_title":false},{"id":217725,"oid":"xo15103156343735a5b6d","item_id":"23747","title":"鍔犵粧鏃跺皻鍗。杩愬姩涓変欢濂�/绱孩鑹�","sku_id":"93154","num":1,"outer_id":"822242710022","total_fee":169.9,"payment":169.9,"discount_fee":0,"sku_name":"M","pic_path":"https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHw3Is7CVATZLkcH8QWibA9kliby0w6k7VSAj9Ae9lkgQQPFHGmKr4qickA0xdZUV5ZAJBesbezN7bqeQ/0?wx_fmt=png","status":7,"status_display":"浜ゆ槗鍏抽棴","refund_status":0,"refund_status_display":"娌℃湁閫\u20ac娆�","refund_id":null,"kill_title":false}]
     * tid : xd1510315634372e8beac
     * buyer_nick :
     * buyer_id : 15977
     * channel : alipay
     * payment : 849.5
     * post_fee : 0.0
     * total_fee : 849.5
     * discount_fee : 0.0
     * status : 7
     * status_display : 浜ゆ槗鍏抽棴
     * order_pic : https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHw3Is7CVATZLkcH8QWibA9kliby0w6k7VSAj9Ae9lkgQQPFHGmKr4qickA0xdZUV5ZAJBesbezN7bqeQ/0?wx_fmt=png
     * buyer_message :
     * trade_type : 0
     * created : 2015-10-31T11:36:21
     * pay_time : null
     * consign_time : null
     * out_sid :
     * logistics_company : null
     * receiver_name : chen
     * receiver_state : 鍖椾含
     * receiver_city : 鏈濋槼鍖�
     * receiver_district : 涓夌幆浠ュ唴
     * receiver_address : 123
     * receiver_mobile : 13845909581
     * receiver_phone :
     */

    private int id;
    private String url;
    private String tid;
    private String buyer_nick;
    private int buyer_id;
    private String channel;
    private double payment;
    private double post_fee;
    private double total_fee;
    private double discount_fee;
    private int status;
    private String status_display;
    private String order_pic;
    private String buyer_message;
    private int trade_type;
    private String created;
    private Object pay_time;
    private Object consign_time;
    private String out_sid;
    private Object logistics_company;
    private String receiver_name;
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_address;
    private String receiver_mobile;
    private String receiver_phone;
    /**
     * id : 217722
     * oid : xo151031563437358da18
     * item_id : 23747
     * title : 鍔犵粧鏃跺皻鍗。杩愬姩涓変欢濂�/绱孩鑹�
     * sku_id : 93158
     * num : 1
     * outer_id : 822242710022
     * total_fee : 169.9
     * payment : 169.9
     * discount_fee : 0.0
     * sku_name : 3XL
     * pic_path : https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHw3Is7CVATZLkcH8QWibA9kliby0w6k7VSAj9Ae9lkgQQPFHGmKr4qickA0xdZUV5ZAJBesbezN7bqeQ/0?wx_fmt=png
     * status : 7
     * status_display : 浜ゆ槗鍏抽棴
     * refund_status : 0
     * refund_status_display : 娌℃湁閫€娆�
     * refund_id : null
     * kill_title : false
     */

    private List<AllOrdersBean.ResultsEntity.OrdersEntity> orders;

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public void setPost_fee(double post_fee) {
        this.post_fee = post_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public void setDiscount_fee(double discount_fee) {
        this.discount_fee = discount_fee;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus_display(String status_display) {
        this.status_display = status_display;
    }

    public void setOrder_pic(String order_pic) {
        this.order_pic = order_pic;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public void setTrade_type(int trade_type) {
        this.trade_type = trade_type;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setPay_time(Object pay_time) {
        this.pay_time = pay_time;
    }

    public void setConsign_time(Object consign_time) {
        this.consign_time = consign_time;
    }

    public void setOut_sid(String out_sid) {
        this.out_sid = out_sid;
    }

    public void setLogistics_company(Object logistics_company) {
        this.logistics_company = logistics_company;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public void setOrders(List<AllOrdersBean.ResultsEntity.OrdersEntity> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTid() {
        return tid;
    }

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public String getChannel() {
        return channel;
    }

    public double getPayment() {
        return payment;
    }

    public double getPost_fee() {
        return post_fee;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public double getDiscount_fee() {
        return discount_fee;
    }

    public int getStatus() {
        return status;
    }

    public String getStatus_display() {
        return status_display;
    }

    public String getOrder_pic() {
        return order_pic;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public int getTrade_type() {
        return trade_type;
    }

    public String getCreated() {
        return created;
    }

    public Object getPay_time() {
        return pay_time;
    }

    public Object getConsign_time() {
        return consign_time;
    }

    public String getOut_sid() {
        return out_sid;
    }

    public Object getLogistics_company() {
        return logistics_company;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public List<AllOrdersBean.ResultsEntity.OrdersEntity> getOrders() {
        return orders;
    }

    /*public static class OrdersEntity {
        private int id;
        private String oid;
        private String item_id;
        private String title;
        private String sku_id;
        private int num;
        private String outer_id;
        private double total_fee;
        private double payment;
        private double discount_fee;
        private String sku_name;
        private String pic_path;
        private int status;
        private String status_display;
        private int refund_status;
        private String refund_status_display;
        private Object refund_id;
        private boolean kill_title;

        public void setId(int id) {
            this.id = id;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setOuter_id(String outer_id) {
            this.outer_id = outer_id;
        }

        public void setTotal_fee(double total_fee) {
            this.total_fee = total_fee;
        }

        public void setPayment(double payment) {
            this.payment = payment;
        }

        public void setDiscount_fee(double discount_fee) {
            this.discount_fee = discount_fee;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setStatus_display(String status_display) {
            this.status_display = status_display;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public void setRefund_status_display(String refund_status_display) {
            this.refund_status_display = refund_status_display;
        }

        public void setRefund_id(Object refund_id) {
            this.refund_id = refund_id;
        }

        public void setKill_title(boolean kill_title) {
            this.kill_title = kill_title;
        }

        public int getId() {
            return id;
        }

        public String getOid() {
            return oid;
        }

        public String getItem_id() {
            return item_id;
        }

        public String getTitle() {
            return title;
        }

        public String getSku_id() {
            return sku_id;
        }

        public int getNum() {
            return num;
        }

        public String getOuter_id() {
            return outer_id;
        }

        public double getTotal_fee() {
            return total_fee;
        }

        public double getPayment() {
            return payment;
        }

        public double getDiscount_fee() {
            return discount_fee;
        }

        public String getSku_name() {
            return sku_name;
        }

        public String getPic_path() {
            return pic_path;
        }

        public int getStatus() {
            return status;
        }

        public String getStatus_display() {
            return status_display;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public String getRefund_status_display() {
            return refund_status_display;
        }

        public Object getRefund_id() {
            return refund_id;
        }

        public boolean isKill_title() {
            return kill_title;
        }
    }*/
}