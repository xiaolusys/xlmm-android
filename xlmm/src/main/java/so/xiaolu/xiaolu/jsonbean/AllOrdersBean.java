package so.xiaolu.xiaolu.jsonbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wl on 15-12-16.
 */
public class AllOrdersBean {
    public List<AllOrders> orders_list;

    public class AllOrders {
        public Order order_info;
        public List<Goods> goods_list;
    }
        public static class Order {
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

        }



        public static class Goods {
            public String id;
            public String url;
            public String name;
            public String head_img;
            public float std_sale_price;
            public float agent_price;
            public String model_id;
            public int num;
        }



}