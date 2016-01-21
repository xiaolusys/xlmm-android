package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/1/20.
 */
public class UserInfoBean {

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }

    /**
     * count : 1
     * next : null
     * previous : null
     * results : [{"id":33,"url":"http://192.168.1.31:9000/rest/v1/users/33","user_id":"125","username":"lei.wu","nick":"lei.wu","mobile":"13739234100","email":" ","phone":" ","status":1,"created":"2016-01-19T11:04:02","modified":"2016-01-20T19:27:37","xiaolumm":null,"has_usable_password":true}]
     */

    private int count;
    private Object next;
    private Object previous;
    /**
     * id : 33
     * url : http://192.168.1.31:9000/rest/v1/users/33
     * user_id : 125
     * username : lei.wu
     * nick : lei.wu
     * mobile : 13739234100
     * email :
     * phone :
     * status : 1
     * created : 2016-01-19T11:04:02
     * modified : 2016-01-20T19:27:37
     * xiaolumm : null
     * has_usable_password : true
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
        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "id=" + id +
                    ", url='" + url + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", username='" + username + '\'' +
                    ", nick='" + nick + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", status=" + status +
                    ", created='" + created + '\'' +
                    ", modified='" + modified + '\'' +
                    ", xiaolumm=" + xiaolumm +
                    ", has_usable_password=" + has_usable_password +
                    '}';
        }

        private int id;
        private String url;
        private String user_id;
        private String username;
        private String nick;
        private String mobile;
        private String email;
        private String phone;
        private int status;
        private String created;
        private String modified;
        private Object xiaolumm;
        private boolean has_usable_password;

        public void setId(int id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public void setXiaolumm(Object xiaolumm) {
            this.xiaolumm = xiaolumm;
        }

        public void setHas_usable_password(boolean has_usable_password) {
            this.has_usable_password = has_usable_password;
        }

        public int getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUsername() {
            return username;
        }

        public String getNick() {
            return nick;
        }

        public String getMobile() {
            return mobile;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public int getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public Object getXiaolumm() {
            return xiaolumm;
        }

        public boolean isHas_usable_password() {
            return has_usable_password;
        }
    }
}
