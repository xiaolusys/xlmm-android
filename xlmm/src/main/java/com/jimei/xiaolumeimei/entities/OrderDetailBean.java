package com.jimei.xiaolumeimei.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16-5-25.
 */

public class OrderDetailBean implements Serializable {

    /**
     * id : 348481
     * url : http://m.xiaolumeimei.com/rest/v2/trades/348481
     * orders : [{"id":387662,"oid":"xo160525574544aa02e57","item_id":"37324","title":"帅气卡通五分短裤/灰色","sku_id":"148904","num":1,"outer_id":"917229320118","total_fee":29.9,"payment":27.9,"discount_fee":2,"sku_name":"150","pic_path":"http://image.xiaolu.so/MG_1459151663881主图1.png","status":2,"status_display":"已付款","refund_status":3,"refund_status_display":"申请退款","refund_id":45167,"kill_title":false,"is_seckill":false}]
     * tid : xd160525574544a75a94a
     * buyer_nick : meron@小鹿美美
     * buyer_id : 1
     * channel : budget
     * payment : 27.9
     * post_fee : 0.0
     * total_fee : 29.9
     * discount_fee : 2.0
     * status : 2
     * status_display : 已付款
     * buyer_message :
     * trade_type : 0
     * created : 2016-05-25T14:22:33
     * pay_time : 2016-05-25T14:22:34
     * consign_time : null
     * out_sid :
     * logistics_company : null
     * user_adress : {"receiver_address":"仓库","receiver_district":"松江区","receiver_city":"市辖区","receiver_state":"上海市","receiver_name":"测试","receiver_mobile":"15645658665","receiver_phone":"","id":117350}
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
    private String buyer_message;
    private int trade_type;
    private String created;
    private String pay_time;
    private Object consign_time;
    private String out_sid;

    private LogisticCompany logistics_company;

    /**
     * receiver_address : 仓库
     * receiver_district : 松江区
     * receiver_city : 市辖区
     * receiver_state : 上海市
     * receiver_name : 测试
     * receiver_mobile : 15645658665
     * receiver_phone :
     * id : 117350
     */

    private UserAdressBean user_adress;
    /**
     * id : 387662
     * oid : xo160525574544aa02e57
     * item_id : 37324
     * title : 帅气卡通五分短裤/灰色
     * sku_id : 148904
     * num : 1
     * outer_id : 917229320118
     * total_fee : 29.9
     * payment : 27.9
     * discount_fee : 2.0
     * sku_name : 150
     * pic_path : http://image.xiaolu.so/MG_1459151663881主图1.png
     * status : 2
     * status_display : 已付款
     * refund_status : 3
     * refund_status_display : 申请退款
     * refund_id : 45167
     * kill_title : false
     * is_seckill : false
     */

    private ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders;
    /**
     * code : YUNDA_QR
     * name : 韵达热敏
     */

    private String code;
    private String name;

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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(double post_fee) {
        this.post_fee = post_fee;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public double getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(double discount_fee) {
        this.discount_fee = discount_fee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_display() {
        return status_display;
    }

    public void setStatus_display(String status_display) {
        this.status_display = status_display;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public int getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(int trade_type) {
        this.trade_type = trade_type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public Object getConsign_time() {
        return consign_time;
    }

    public void setConsign_time(Object consign_time) {
        this.consign_time = consign_time;
    }

    public String getOut_sid() {
        return out_sid;
    }

    public void setOut_sid(String out_sid) {
        this.out_sid = out_sid;
    }

    public UserAdressBean getUser_adress() {
        return user_adress;
    }

    public void setUser_adress(UserAdressBean user_adress) {
        this.user_adress = user_adress;
    }

    public ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders) {
        this.orders = orders;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogisticCompany getLogistics_company() {
        return logistics_company;
    }

    public void setLogistics_company(LogisticCompany logistics_company) {
        this.logistics_company = logistics_company;
    }

    public static class UserAdressBean implements Serializable{
        private String receiver_address;
        private String receiver_district;
        private String receiver_city;
        private String receiver_state;
        private String receiver_name;
        private String receiver_mobile;
        private String receiver_phone;
        private int id;

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public String getReceiver_district() {
            return receiver_district;
        }

        public void setReceiver_district(String receiver_district) {
            this.receiver_district = receiver_district;
        }

        public String getReceiver_city() {
            return receiver_city;
        }

        public void setReceiver_city(String receiver_city) {
            this.receiver_city = receiver_city;
        }

        public String getReceiver_state() {
            return receiver_state;
        }

        public void setReceiver_state(String receiver_state) {
            this.receiver_state = receiver_state;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_mobile() {
            return receiver_mobile;
        }

        public void setReceiver_mobile(String receiver_mobile) {
            this.receiver_mobile = receiver_mobile;
        }

        public String getReceiver_phone() {
            return receiver_phone;
        }

        public void setReceiver_phone(String receiver_phone) {
            this.receiver_phone = receiver_phone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}