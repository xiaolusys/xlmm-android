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
public class AllRefundsBean {
    public List<RefundBaseInfo> refunds_list;

    public static class RefundBaseInfo implements Parcelable {
        public  String refund_no;
        public  int trade_id;
        public  int order_id;
        public  int buyer_id;
        public  int item_id;
        public  String title;
        public  int sku_id;
        public  String sku_name;
        public  int refund_num;
        public  String buyer_nick;
        public  String mobile;
        public  String phone;
        public  String proof_pic;
        public  float total_fee;
        public  float payment;
        public  String company_name;
        public  String sid;
        public  String reason;
        public  String desc;
        public  String feedback;
        public  boolean has_good_return;
        public  boolean has_good_change;
        public  String good_status;
        public  String status;
        public  float refund_fee;

        /**
         * 默认构造方法
         */
        public RefundBaseInfo() {
            // TODO Auto-generated constructor stub
        }

        public RefundBaseInfo(Parcel in) {
            readFromParcel(in);
        }
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(refund_no);
            dest.writeInt(trade_id);
            dest.writeInt(order_id);
            dest.writeInt(buyer_id);
            dest.writeInt(item_id);
            dest.writeString(title);
            dest.writeInt(sku_id);
            dest.writeString(sku_name);
            dest.writeInt(refund_num);
            dest.writeString(buyer_nick);
            dest.writeString(mobile);
            dest.writeString(phone);
            dest.writeString(proof_pic);
            dest.writeFloat(total_fee);
            dest.writeFloat(payment);
            dest.writeString(company_name);
            dest.writeString(sid);
            dest.writeString(reason);
            dest.writeString(desc);
            dest.writeString(feedback);
            dest.writeInt(has_good_return ? 1 : 0);
            dest.writeInt(has_good_change ? 1 : 0);
            dest.writeString(good_status);
            dest.writeString(status);
            dest.writeFloat(refund_fee);

        }

        private void readFromParcel(Parcel in) {

            refund_no = in.readString();
            trade_id = in.readInt();
            order_id = in.readInt();
            buyer_id = in.readInt();
            item_id = in.readInt();
            title = in.readString();
            sku_id = in.readInt();
            sku_name = in.readString();
            refund_num = in.readInt();
            buyer_nick = in.readString();
            mobile = in.readString();
            phone = in.readString();
            proof_pic = in.readString();
            total_fee = in.readFloat();
            payment = in.readFloat();
            company_name = in.readString();
            sid = in.readString();
            reason = in.readString();
            desc = in.readString();
            feedback = in.readString();
            has_good_return= (in.readInt() == 1) ? true : false;
            has_good_change= (in.readInt() == 1) ? true : false;
            good_status = in.readString();
            status = in.readString();
            refund_fee = in.readFloat();

        }

        public static final Parcelable.Creator<RefundBaseInfo> CREATOR = new Parcelable.Creator<RefundBaseInfo>() {
            public RefundBaseInfo createFromParcel(Parcel in) {
                return new RefundBaseInfo(in);
            }

            public RefundBaseInfo[] newArray(int size) {
                return new RefundBaseInfo[size];
            }
        };

        public String getRefund_no() {
            return refund_no;
        }

        public void setRefund_no(String refund_no) {
            this.refund_no = refund_no;
        }

        public int getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(int trade_id) {
            this.trade_id = trade_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSku_id() {
            return sku_id;
        }

        public void setSku_id(int sku_id) {
            this.sku_id = sku_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public int getRefund_num() {
            return refund_num;
        }

        public void setRefund_num(int refund_num) {
            this.refund_num = refund_num;
        }

        public String getBuyer_nick() {
            return buyer_nick;
        }

        public void setBuyer_nick(String buyer_nick) {
            this.buyer_nick = buyer_nick;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProof_pic() {
            return proof_pic;
        }

        public void setProof_pic(String proof_pic) {
            this.proof_pic = proof_pic;
        }

        public float getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(float total_fee) {
            this.total_fee = total_fee;
        }

        public float getPayment() {
            return payment;
        }

        public void setPayment(float payment) {
            this.payment = payment;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public boolean getHas_good_return() {
            return has_good_return;
        }

        public void setHas_good_return(boolean has_good_return) {
            this.has_good_return = has_good_return;
        }

        public boolean getHas_good_change() {
            return has_good_change;
        }

        public void setHas_good_change(boolean has_good_change) {
            this.has_good_change = has_good_change;
        }

        public String getGood_status() {
            return good_status;
        }

        public void setGood_status(String good_status) {
            this.good_status = good_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public float getRefund_fee() {
            return refund_fee;
        }

        public void setRefund_fee(float refund_fee) {
            this.refund_fee = refund_fee;
        }
    }






}