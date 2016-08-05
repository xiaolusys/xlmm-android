package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/8/3.
 */
public class CategoryBean {

    /**
     * childs : [{"grade":0,"cat_pic":"","cid":67,"name":"哈衣","parent_cid":1},{"grade":0,"cat_pic":"","cid":68,"name":"下装","parent_cid":1},{"grade":0,"cat_pic":"","cid":72,"name":"配饰","parent_cid":1},{"grade":0,"cat_pic":"","cid":74,"name":"亲子装","parent_cid":1},{"grade":0,"cat_pic":"","cid":75,"name":"内衣","parent_cid":1},{"grade":0,"cat_pic":"","cid":66,"name":"外套","parent_cid":1},{"grade":0,"cat_pic":"","cid":65,"name":"套装","parent_cid":1},{"grade":0,"cat_pic":"","cid":64,"name":"连衣裙","parent_cid":1},{"grade":0,"cat_pic":"","cid":63,"name":"上装","parent_cid":1}]
     * name : 童装
     * cid : 1
     * grade : 1
     * parent_cid : 0
     * cat_pic :
     */

    private String name;
    private int cid;
    private int grade;
    private int parent_cid;
    private String cat_pic;
    /**
     * grade : 0
     * cat_pic :
     * cid : 67
     * name : 哈衣
     * parent_cid : 1
     */

    private List<ChildsBean> childs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getParent_cid() {
        return parent_cid;
    }

    public void setParent_cid(int parent_cid) {
        this.parent_cid = parent_cid;
    }

    public String getCat_pic() {
        return cat_pic;
    }

    public void setCat_pic(String cat_pic) {
        this.cat_pic = cat_pic;
    }

    public List<ChildsBean> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildsBean> childs) {
        this.childs = childs;
    }

    public static class ChildsBean {
        private int grade;
        private String cat_pic;
        private int cid;
        private String name;
        private int parent_cid;

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getCat_pic() {
            return cat_pic;
        }

        public void setCat_pic(String cat_pic) {
            this.cat_pic = cat_pic;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParent_cid() {
            return parent_cid;
        }

        public void setParent_cid(int parent_cid) {
            this.parent_cid = parent_cid;
        }
    }
}
