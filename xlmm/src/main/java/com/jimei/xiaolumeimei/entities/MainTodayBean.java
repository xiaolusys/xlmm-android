package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 17/2/14.
 */

public class MainTodayBean {

    /**
     * items : [{"model_id":"24907","name":"MKII澳洲进口深度滋养绵羊油","hour":6,"profit":{"max":25,"min":10},"price":49,"pic":"http://img.xiaolumeimei.com/MG_1487045176818-1.jpg","start_time":"2017-02-14T06:00:00"}]
     * hour : 6
     */

    private int hour;
    private List<ItemsBean> items;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * model_id : 24907
         * name : MKII澳洲进口深度滋养绵羊油
         * hour : 6
         * profit : {"max":25,"min":10}
         * price : 49
         * pic : http://img.xiaolumeimei.com/MG_1487045176818-1.jpg
         * start_time : 2017-02-14T06:00:00
         */

        private String model_id;
        private String name;
        private int hour;
        private ProfitBean profit;
        private int price;
        private String pic;
        private String start_time;

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public ProfitBean getProfit() {
            return profit;
        }

        public void setProfit(ProfitBean profit) {
            this.profit = profit;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public static class ProfitBean {
            /**
             * max : 25
             * min : 10
             */

            private int max;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }
    }
}
