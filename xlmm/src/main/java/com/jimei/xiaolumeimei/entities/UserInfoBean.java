package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/1/20.
 */
public class UserInfoBean {

    /**
     * count : 1
     * next : null
     * previous : null
     * results : [{"id":1,"url":"http://dev.xiaolumeimei.com/rest/v1/users/1","user_id":"1","username":"xiuqing.mei","nick":"meron","mobile":"18621623915","email":"","phone":"","status":1,"created":"2015-04-18T18:40:33","modified":"2015-10-22T13:06:46","xiaolumm":{"id":1,"cash":100000,"agencylevel":1,"created":"2015-05-12T10:04:07","status":"effect"},"has_usable_password":true}]
     */

    private int count;
    private Object next;
    private Object previous;
    /**
     * id : 1
     * url : http://dev.xiaolumeimei.com/rest/v1/users/1
     * user_id : 1
     * username : xiuqing.mei
     * nick : meron
     * mobile : 18621623915
     * email :
     * phone :
     * "thumbnail": "http://7xogkj.com2.z0.glb.qiniucdn.com/222-ohmydeer.png",
     * status : 1
     * created : 2015-04-18T18:40:33
     * modified : 2015-10-22T13:06:46
     * xiaolumm : {"id":1,"cash":100000,"agencylevel":1,"created":"2015-05-12T10:04:07","status":"effect"}
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
        private int id;
        private String url;
        private String user_id;
        private String username;
        private String nick;
        private String mobile;
        private String email;
        private String phone;
        private String thumbnail;
        private int status;
        private String created;
        private String modified;
        /**
         * id : 1
         * cash : 100000
         * agencylevel : 1
         * created : 2015-05-12T10:04:07
         * status : effect
         */

        private XiaolummEntity xiaolumm;
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

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        public void setXiaolumm(XiaolummEntity xiaolumm) {
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

        public String getThumbnail() {
            return thumbnail;
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

        public XiaolummEntity getXiaolumm() {
            return xiaolumm;
        }

        public boolean isHas_usable_password() {
            return has_usable_password;
        }

        public static class XiaolummEntity {
            private int id;
            private float cash;
            private int agencylevel;
            private String created;
            private String status;

            public void setId(int id) {
                this.id = id;
            }

            public void setCash(float cash) {
                this.cash = cash;
            }

            public void setAgencylevel(int agencylevel) {
                this.agencylevel = agencylevel;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public float getCash() {
                return cash;
            }

            public int getAgencylevel() {
                return agencylevel;
            }

            public String getCreated() {
                return created;
            }

            public String getStatus() {
                return status;
            }

            @Override public String toString() {
                return "XiaolummEntity{" +
                    "id=" + id +
                    ", cash=" + cash +
                    ", agencylevel=" + agencylevel +
                    ", created='" + created + '\'' +
                    ", status='" + status + '\'' +
                    '}';
            }
        }

        @Override public String toString() {
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
    }

    @Override public String toString() {
        return "UserInfoBean{" +
            "count=" + count +
            ", next=" + next +
            ", previous=" + previous +
            ", results=" + results +
            '}';
    }
}
