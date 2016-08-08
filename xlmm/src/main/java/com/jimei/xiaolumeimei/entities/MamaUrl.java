package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/7/13.
 */
public class MamaUrl {


    /**
     * count : 1
     * next : null
     * previous : null
     * results : [{"id":1,"version":"1.0","is_valid":true,"extra":{"renew":"http://m.xiaolumeimei.com/mall/activity/shop/charge","act_info":" http://m.xiaolumeimei.com/pages/featuredEvent.html","invite":"http://m.xiaolumeimei.com/pages/agency-invitation-res.html","fans_explain":"http://m.xiaolumeimei.com/pages/fans-explain.html","exam":"http://m.xiaolumeimei.com/mall/activity/exam"},"created":"2016-07-13T17:31:59","modified":"2016-07-13T18:23:35"}]
     */

    private int count;
    private Object next;
    private Object previous;
    /**
     * id : 1
     * version : 1.0
     * is_valid : true
     * extra : {"renew":"http://m.xiaolumeimei.com/mall/activity/shop/charge","act_info":" http://m.xiaolumeimei.com/pages/featuredEvent.html","invite":"http://m.xiaolumeimei.com/pages/agency-invitation-res.html","fans_explain":"http://m.xiaolumeimei.com/pages/fans-explain.html","exam":"http://m.xiaolumeimei.com/mall/activity/exam"}
     * created : 2016-07-13T17:31:59
     * modified : 2016-07-13T18:23:35
     */

    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
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
        private String version;
        private boolean is_valid;
        /**
         * renew : http://m.xiaolumeimei.com/mall/activity/shop/charge
         * act_info :  http://m.xiaolumeimei.com/pages/featuredEvent.html
         * invite : http://m.xiaolumeimei.com/pages/agency-invitation-res.html
         * fans_explain : http://m.xiaolumeimei.com/pages/fans-explain.html
         * exam : http://m.xiaolumeimei.com/mall/activity/exam
         */

        private ExtraBean extra;
        private String created;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public boolean isIs_valid() {
            return is_valid;
        }

        public void setIs_valid(boolean is_valid) {
            this.is_valid = is_valid;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
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

        public static class ExtraBean {
            private String renew;
            private String act_info;
            private String invite;
            private String fans_explain;
            private String exam;

            public ResultsBean.picturesBean getPicturesBean() {
                return picturesBean;
            }

            private picturesBean picturesBean;

            public String getNotice() {
                return notice;
            }

            private String notice;

            public String getRenew() {
                return renew;
            }

            public void setRenew(String renew) {
                this.renew = renew;
            }

            public String getAct_info() {
                return act_info;
            }

            public void setAct_info(String act_info) {
                this.act_info = act_info;
            }

            public String getInvite() {
                return invite;
            }

            public void setInvite(String invite) {
                this.invite = invite;
            }

            public String getFans_explain() {
                return fans_explain;
            }

            public void setFans_explain(String fans_explain) {
                this.fans_explain = fans_explain;
            }

            public String getExam() {
                return exam;
            }

            public void setExam(String exam) {
                this.exam = exam;
            }
        }
        public static class picturesBean{
            public String exam_pic;

            public String getExam_pic() {
                return exam_pic;
            }
        }
    }
}
