package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/1/21.
 */
public class MembershipPointBean {

    /**
     * count : 1
     * next : null
     * previous : null
     * results : [{"id":7,"integral_user":33,"integral_value":198}]
     */

    private int count;
    private String next;
    private String previous;
    /**
     * id : 7
     * integral_user : 33
     * integral_value : 198
     */

    private List<ResultsEntity> results;

    public void setCount(int count) {
        this.count = count;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private int id;
        private int integral_user;
        private int integral_value;

        public void setId(int id) {
            this.id = id;
        }

        public void setIntegral_user(int integral_user) {
            this.integral_user = integral_user;
        }

        public void setIntegral_value(int integral_value) {
            this.integral_value = integral_value;
        }

        public int getId() {
            return id;
        }

        public int getIntegral_user() {
            return integral_user;
        }

        public int getIntegral_value() {
            return integral_value;
        }

        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "id=" + id +
                    ", integral_user=" + integral_user +
                    ", integral_value=" + integral_value +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MembershipPointBean{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }
}
