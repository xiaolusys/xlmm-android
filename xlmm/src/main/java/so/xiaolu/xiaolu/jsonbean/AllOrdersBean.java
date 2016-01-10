package so.xiaolu.xiaolu.jsonbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wl on 15-12-16.
 */
public class AllOrdersBean {
    public List<OrderBaseInfo> orders_list;

    public static class OrderBaseInfo implements Parcelable {
        public int tid;
        public String buyer_nick;
        public int buyer_id;
        public int ringchannel;
        public float payment;
        public float post_fee;
        public float total_fee;
        public float discount_fee;
        public int status;
        public String buyer_message;
        public int trade_type;
        public String pay_time;
        public String consign_time;
        public int out_sid;
        public String receiver_name;
        public int receiver_state;
        public String receiver_city;
        public String receiver_district;
        public String receiver_address;
        public String receiver_mobile;
        public String receiver_phone;

        /**
         * 默认构造方法
         */
        public OrderBaseInfo() {
            // TODO Auto-generated constructor stub
        }

        public OrderBaseInfo(Parcel in) {
            readFromParcel(in);
        }
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(tid);
            dest.writeString(buyer_nick);
            dest.writeInt(buyer_id);
            dest.writeInt(ringchannel);
            dest.writeFloat(payment);
            dest.writeFloat(post_fee);
            dest.writeFloat(total_fee);
            dest.writeFloat(discount_fee);
            dest.writeInt(status);
            dest.writeString(buyer_message);
            dest.writeInt(trade_type);
            dest.writeString(pay_time);
            dest.writeString(consign_time);
            dest.writeInt(out_sid);
            dest.writeString(receiver_name);
            dest.writeInt(receiver_state);
            dest.writeString(receiver_city);
            dest.writeString(receiver_district);
            dest.writeString(receiver_address);
            dest.writeString(receiver_mobile);
            dest.writeString(receiver_phone);

        }

        private void readFromParcel(Parcel in) {

            tid = in.readInt();
            buyer_nick = in.readString();
              buyer_id = in.readInt();
              ringchannel = in.readInt();
            payment = in.readFloat();
              post_fee = in.readFloat();
              total_fee = in.readFloat();
              discount_fee = in.readFloat();
              status = in.readInt();
              buyer_message = in.readString();
              trade_type = in.readInt();
              pay_time = in.readString();
              consign_time = in.readString();
              out_sid = in.readInt();
              receiver_name = in.readString();
              receiver_state = in.readInt();
              receiver_city = in.readString();
              receiver_district = in.readString();
              receiver_address = in.readString();
              receiver_mobile = in.readString();
             receiver_phone = in.readString();

        }

        public static final Parcelable.Creator<OrderBaseInfo> CREATOR = new Parcelable.Creator<OrderBaseInfo>() {
            public OrderBaseInfo createFromParcel(Parcel in) {
                return new OrderBaseInfo(in);
            }

            public OrderBaseInfo[] newArray(int size) {
                return new OrderBaseInfo[size];
            }
        };

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
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

        public int getRingchannel() {
            return ringchannel;
        }

        public void setRingchannel(int ringchannel) {
            this.ringchannel = ringchannel;
        }

        public float getPayment() {
            return payment;
        }

        public void setPayment(float payment) {
            this.payment = payment;
        }

        public float getPost_fee() {
            return post_fee;
        }

        public void setPost_fee(float post_fee) {
            this.post_fee = post_fee;
        }

        public float getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(float total_fee) {
            this.total_fee = total_fee;
        }

        public float getDiscount_fee() {
            return discount_fee;
        }

        public void setDiscount_fee(float discount_fee) {
            this.discount_fee = discount_fee;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getConsign_time() {
            return consign_time;
        }

        public void setConsign_time(String consign_time) {
            this.consign_time = consign_time;
        }

        public int getOut_sid() {
            return out_sid;
        }

        public void setOut_sid(int out_sid) {
            this.out_sid = out_sid;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public int getReceiver_state() {
            return receiver_state;
        }

        public void setReceiver_state(int receiver_state) {
            this.receiver_state = receiver_state;
        }

        public String getReceiver_city() {
            return receiver_city;
        }

        public void setReceiver_city(String receiver_city) {
            this.receiver_city = receiver_city;
        }

        public String getReceiver_district() {
            return receiver_district;
        }

        public void setReceiver_district(String receiver_district) {
            this.receiver_district = receiver_district;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
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

    }






}