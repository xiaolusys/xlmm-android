package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/1/21.
 */
public class PointLogBean {

    @Override
    public String toString() {
        return "PointLogBean{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }

    /**
     * count : 0
     * next : null
     * previous : null
     * results : [{"integral_user":33,"mobile":"13567777","log_value":2,"log_status":2,"log_type":2,"in_out":1}]
     */

    private int count;
    private Object next;
    private Object previous;
    /**
     * integral_user : 33
     * mobile : 13567777
     * log_value : 2
     * log_status : 2
     * log_type : 2
     * in_out : 1
     */

    private List<ResultsEntity> results;

    public void setCount(int count) {
        this.count = count;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public Object getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private int integral_user;
        private String mobile;
        private int log_value;
        private int log_status;
        private int log_type;
        private int in_out;

        public void setIntegral_user(int integral_user) {
            this.integral_user = integral_user;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setLog_value(int log_value) {
            this.log_value = log_value;
        }

        public void setLog_status(int log_status) {
            this.log_status = log_status;
        }

        public void setLog_type(int log_type) {
            this.log_type = log_type;
        }

        public void setIn_out(int in_out) {
            this.in_out = in_out;
        }

        public int getIntegral_user() {
            return integral_user;
        }

        public String getMobile() {
            return mobile;
        }

        public int getLog_value() {
            return log_value;
        }

        public int getLog_status() {
            return log_status;
        }

        public int getLog_type() {
            return log_type;
        }

        public int getIn_out() {
            return in_out;
        }

        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "integral_user=" + integral_user +
                    ", mobile='" + mobile + '\'' +
                    ", log_value=" + log_value +
                    ", log_status=" + log_status +
                    ", log_type=" + log_type +
                    ", in_out=" + in_out +
                    '}';
        }
    }
}
