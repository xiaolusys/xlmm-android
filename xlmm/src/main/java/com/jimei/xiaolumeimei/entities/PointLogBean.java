package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/1/21.
 */
public class PointLogBean {


    /**
     * count : 125
     * next : http://api.xiaolumeimei.com/rest/v1/integrallog?page=2
     * previous : null
     * results : [{"id":7615,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":79,"log_status":1,"log_type":1,"in_out":1,"created":"2015-08-25T20:52:59","modified":"2016-05-14T14:04:34"},{"id":30422,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":79,"log_status":1,"log_type":1,"in_out":1,"created":"2015-09-06T16:18:17","modified":"2016-05-14T14:05:24"},{"id":33621,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":29,"log_status":1,"log_type":1,"in_out":1,"created":"2015-09-07T23:30:56","modified":"2016-05-14T14:05:28"},{"id":105562,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":39,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-14T14:16:49","modified":"2016-05-14T14:06:35"},{"id":105690,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":39,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-14T15:24:38","modified":"2016-05-14T14:06:36"},{"id":133685,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":49,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-31T20:06:59","modified":"2016-05-14T14:07:05"},{"id":133711,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":39,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-31T20:23:28","modified":"2016-05-14T14:07:05"},{"id":133749,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":39,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-31T20:46:17","modified":"2016-05-14T14:07:05"},{"id":133750,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":39,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-31T20:48:58","modified":"2016-05-14T14:07:05"},{"id":133773,"integral_user":1,"mobile":"18621623915","order_info":{},"log_value":39,"log_status":1,"log_type":1,"in_out":1,"created":"2015-10-31T21:09:36","modified":"2016-05-14T14:07:05"}]
     */

    private int count;
    private String next;
    private Object previous;
    /**
     * id : 7615
     * integral_user : 1
     * mobile : 18621623915
     * order_info : {}
     * log_value : 79
     * log_status : 1
     * log_type : 1
     * in_out : 1
     * created : 2015-08-25T20:52:59
     * modified : 2016-05-14T14:04:34
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

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
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
        private int integral_user;
        private String mobile;
        private int log_value;
        private int log_status;
        private int log_type;
        private int in_out;
        private String created;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIntegral_user() {
            return integral_user;
        }

        public void setIntegral_user(int integral_user) {
            this.integral_user = integral_user;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getLog_value() {
            return log_value;
        }

        public void setLog_value(int log_value) {
            this.log_value = log_value;
        }

        public int getLog_status() {
            return log_status;
        }

        public void setLog_status(int log_status) {
            this.log_status = log_status;
        }

        public int getLog_type() {
            return log_type;
        }

        public void setLog_type(int log_type) {
            this.log_type = log_type;
        }

        public int getIn_out() {
            return in_out;
        }

        public void setIn_out(int in_out) {
            this.in_out = in_out;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
    }
}
