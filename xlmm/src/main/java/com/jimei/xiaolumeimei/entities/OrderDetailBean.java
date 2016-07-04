package com.jimei.xiaolumeimei.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16-5-25.
 */

public class OrderDetailBean implements Serializable {

    /**
     * id : 333742
     * url : http://staging.xiaolumeimei.com/rest/v2/trades/333742
     * orders : [{"id":369952,"oid":"xo1606085757b5645e2eb","item_id":"40471","title":"新款防晒超大雪纺披肩/西瓜红","sku_id":"163262","num":1,"outer_id":"824291010013","total_fee":19.9,"payment":17.9,"discount_fee":2,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG_14614007618135.jpg","status":2,"status_display":"已付款","refund_status":0,"refund_status_display":"没有退款","refund_id":42916,"kill_title":false,"is_seckill":false}]
     * tid : xd1606085757b55e9506a
     * buyer_nick : meron@小鹿美美
     * buyer_id : 1
     * channel : budget
     * payment : 17.9
     * post_fee : 0.0
     * total_fee : 19.9
     * discount_fee : 2.0
     * status : 2
     * status_display : 已付款
     * buyer_message :
     * trade_type : 0
     * created : 2016-06-08T14:04:20
     * pay_time : 2016-06-08T14:04:20
     * consign_time : null
     * out_sid :
     * logistics_company : {"id":-2,"code":"YUNDA_QR","name":"韵达热敏"}
     * user_adress : {"receiver_address":"哦你信2fh","receiver_district":"天通苑","receiver_city":"朝阳区","receiver_state":"北京","default":"","receiver_name":"狗狗","receiver_mobile":"13469586914","receiver_phone":"","id":113994}
     * extras : {"refund_choices":[{"refund_channel":"budget","name":"极速退款","desc":"申请退款后，退款金额立即退到小鹿钱包，并可立即支付使用，无需等待."}]}
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
    /**
     * id : -2
     * code : YUNDA_QR
     * name : 韵达热敏
     */

    private LogisticsCompanyBean logistics_company;
    /**
     * receiver_address : 哦你信2fh
     * receiver_district : 天通苑
     * receiver_city : 朝阳区
     * receiver_state : 北京
     * default :
     * receiver_name : 狗狗
     * receiver_mobile : 13469586914
     * receiver_phone :
     * id : 113994
     */

    private UserAdressBean user_adress;
    private ExtrasBean extras;
    /**
     * id : 369952
     * oid : xo1606085757b5645e2eb
     * item_id : 40471
     * title : 新款防晒超大雪纺披肩/西瓜红
     * sku_id : 163262
     * num : 1
     * outer_id : 824291010013
     * total_fee : 19.9
     * payment : 17.9
     * discount_fee : 2.0
     * sku_name : 均码
     * pic_path : http://image.xiaolu.so/MG_14614007618135.jpg
     * status : 2
     * status_display : 已付款
     * refund_status : 0
     * refund_status_display : 没有退款
     * refund_id : 42916
     * kill_title : false
     * is_seckill : false
     */

    private ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders;
    /**
     * id : 163316-111983-2-1
     * logistics_company : {"id":-2,"code":"YUNDA_QR","name":"韵达热敏"}
     * process_time : 2016-05-24 11:28:28
     * pay_time : 2016-04-15 23:35:56
     * book_time : null
     * assign_time : 2016-05-24 11:28:28
     * finish_time : null
     * cancel_time : null
     * assign_status_display : 已备货
     * ware_by_display : 广州仓
     * out_sid : 2016-05-24 11:28:28
     */

    private List<PackageOrdersBean> package_orders;

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

    public LogisticsCompanyBean getLogistics_company() {
        return logistics_company;
    }

    public void setLogistics_company(LogisticsCompanyBean logistics_company) {
        this.logistics_company = logistics_company;
    }

    public UserAdressBean getUser_adress() {
        return user_adress;
    }

    public void setUser_adress(UserAdressBean user_adress) {
        this.user_adress = user_adress;
    }

    public ExtrasBean getExtras() {
        return extras;
    }

    public void setExtras(ExtrasBean extras) {
        this.extras = extras;
    }

    public ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders) {
        this.orders = orders;
    }

    public List<PackageOrdersBean> getPackage_orders() {
        return package_orders;
    }

    public void setPackage_orders(List<PackageOrdersBean> package_orders) {
        this.package_orders = package_orders;
    }

    public static class LogisticsCompanyBean {
        private int id;
        private String code;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }

    public static class UserAdressBean {
        private String receiver_address;
        private String receiver_district;
        private String receiver_city;
        private String receiver_state;
        @SerializedName("default")
        private String defaultX;
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

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
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

    public static class ExtrasBean implements Serializable, Parcelable {
        /**
         * refund_channel : budget
         * name : 极速退款
         * desc : 申请退款后，退款金额立即退到小鹿钱包，并可立即支付使用，无需等待.
         */

        @SerializedName("refund_choices")
        private List<RefundChoicesBean> refund_choices;

        public List<RefundChoicesBean> getRefund_choices() {
            return refund_choices;
        }

        public void setRefund_choices(List<RefundChoicesBean> refund_choices) {
            this.refund_choices = refund_choices;
        }


        public static class RefundChoicesBean implements Serializable, Parcelable {
            @SerializedName("refund_channel")
            private String refund_channel;
            @SerializedName("name")
            private String name;
            @SerializedName("desc")
            private String desc;

            public String getRefund_channel() {
                return refund_channel;
            }

            public void setRefund_channel(String refund_channel) {
                this.refund_channel = refund_channel;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.refund_channel);
                dest.writeString(this.name);
                dest.writeString(this.desc);
            }

            public RefundChoicesBean() {
            }

            protected RefundChoicesBean(Parcel in) {
                this.refund_channel = in.readString();
                this.name = in.readString();
                this.desc = in.readString();
            }

            public static final Creator<RefundChoicesBean> CREATOR = new Creator<RefundChoicesBean>() {
                @Override
                public RefundChoicesBean createFromParcel(Parcel source) {
                    return new RefundChoicesBean(source);
                }

                @Override
                public RefundChoicesBean[] newArray(int size) {
                    return new RefundChoicesBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.refund_choices);
        }

        public ExtrasBean() {
        }

        protected ExtrasBean(Parcel in) {
            this.refund_choices = new ArrayList<RefundChoicesBean>();
            in.readList(this.refund_choices, RefundChoicesBean.class.getClassLoader());
        }

        public static final Creator<ExtrasBean> CREATOR = new Creator<ExtrasBean>() {
            @Override
            public ExtrasBean createFromParcel(Parcel source) {
                return new ExtrasBean(source);
            }

            @Override
            public ExtrasBean[] newArray(int size) {
                return new ExtrasBean[size];
            }
        };


    }


    public static class PackageOrdersBean implements Serializable {
        private String id;
        private LogisticCompany logistics_company;
        private String process_time;
        private String pay_time;
        private String book_time;
        private String assign_time;
        private String finish_time;
        private String cancel_time;
        private String assign_status_display;
        private String ware_by_display;
        private String out_sid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LogisticCompany getLogistics_company() {
            return logistics_company;
        }

        public void setLogistics_company(LogisticCompany logistics_company) {
            this.logistics_company = logistics_company;
        }

        public String getProcess_time() {
            return process_time;
        }

        public void setProcess_time(String process_time) {
            this.process_time = process_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getBook_time() {
            return book_time;
        }

        public void setBook_time(String book_time) {
            this.book_time = book_time;
        }

        public String getAssign_time() {
            return assign_time;
        }

        public void setAssign_time(String assign_time) {
            this.assign_time = assign_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getCancel_time() {
            return cancel_time;
        }

        public void setCancel_time(String cancel_time) {
            this.cancel_time = cancel_time;
        }

        public String getAssign_status_display() {
            return assign_status_display;
        }

        public void setAssign_status_display(String assign_status_display) {
            this.assign_status_display = assign_status_display;
        }

        public String getWare_by_display() {
            return ware_by_display;
        }

        public void setWare_by_display(String ware_by_display) {
            this.ware_by_display = ware_by_display;
        }

        public String getOut_sid() {
            return out_sid;
        }

        public void setOut_sid(String out_sid) {
            this.out_sid = out_sid;
        }
    }
}