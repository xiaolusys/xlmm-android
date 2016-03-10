package com.jimei.xiaolumeimei.entities;

/**
 * Created by wulei on 3/10/16.
 */
public class MamaFortune {

    /**
     * mama_id : 5
     * mama_name :
     * mam_level : 0
     * cash_value : 2.19
     * fans_num : 1
     * invite_num : -1
     * order_num : 0
     * carry_value : 2.39
     * active_value_num : 5
     * today_visitor_num : 0
     */

    private MamaFortuneEntity mama_fortune;

    public void setMama_fortune(MamaFortuneEntity mama_fortune) {
        this.mama_fortune = mama_fortune;
    }

    public MamaFortuneEntity getMama_fortune() {
        return mama_fortune;
    }

    public static class MamaFortuneEntity {
        private int mama_id;
        private String mama_name;
        private int mam_level;
        private double cash_value;
        private int fans_num;
        private int invite_num;
        private int order_num;
        private double carry_value;
        private int active_value_num;
        private int today_visitor_num;

        public void setMama_id(int mama_id) {
            this.mama_id = mama_id;
        }

        public void setMama_name(String mama_name) {
            this.mama_name = mama_name;
        }

        public void setMam_level(int mam_level) {
            this.mam_level = mam_level;
        }

        public void setCash_value(double cash_value) {
            this.cash_value = cash_value;
        }

        public void setFans_num(int fans_num) {
            this.fans_num = fans_num;
        }

        public void setInvite_num(int invite_num) {
            this.invite_num = invite_num;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }

        public void setCarry_value(double carry_value) {
            this.carry_value = carry_value;
        }

        public void setActive_value_num(int active_value_num) {
            this.active_value_num = active_value_num;
        }

        public void setToday_visitor_num(int today_visitor_num) {
            this.today_visitor_num = today_visitor_num;
        }

        public int getMama_id() {
            return mama_id;
        }

        public String getMama_name() {
            return mama_name;
        }

        public int getMam_level() {
            return mam_level;
        }

        public double getCash_value() {
            return cash_value;
        }

        public int getFans_num() {
            return fans_num;
        }

        public int getInvite_num() {
            return invite_num;
        }

        public int getOrder_num() {
            return order_num;
        }

        public double getCarry_value() {
            return carry_value;
        }

        public int getActive_value_num() {
            return active_value_num;
        }

        public int getToday_visitor_num() {
            return today_visitor_num;
        }

        @Override
        public String toString() {
            return "MamaFortuneEntity{" +
                    "mama_id=" + mama_id +
                    ", mama_name='" + mama_name + '\'' +
                    ", mam_level=" + mam_level +
                    ", cash_value=" + cash_value +
                    ", fans_num=" + fans_num +
                    ", invite_num=" + invite_num +
                    ", order_num=" + order_num +
                    ", carry_value=" + carry_value +
                    ", active_value_num=" + active_value_num +
                    ", today_visitor_num=" + today_visitor_num +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MamaFortune{" +
                "mama_fortune=" + mama_fortune +
                '}';
    }
}
