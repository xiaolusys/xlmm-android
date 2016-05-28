package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wisdom on 16/5/18.
 */
public class PackageBean implements Serializable {

    /**
     * title : 蝙蝠袖蕾丝拼接衬衣/乳白色
     * pic_path : http://image.xiaolu.so/MG_14612426673693.jpg
     * num : 1
     * payment : 47.27
     * assign_status_display : 外贸厂调货中...
     * ware_by_display : 广州/上海2号仓
     * out_sid :
     * logistics_company_name :
     * logistics_company_code :
     * process_time :
     * package_group_key : 0-2-
     */

    private String title;
    private String pic_path;
    private int num;
    private double payment;
    private String assign_status_display;
    private String ware_by_display;
    private String out_sid;
    private String logistics_company_name;
    private String logistics_company_code;
    private String process_time;
    private String package_group_key;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getAssign_status_display() {
        return assign_status_display;
    }

    public void setAssign_status_display(String assign_status_display) {
        this.assign_status_display = assign_status_display;
    }

    public String getWare_by_display() {
        return ware_by_display;
    }

    public void setWare_by_display(String ware_by_display) {
        this.ware_by_display = ware_by_display;
    }

    public String getOut_sid() {
        return out_sid;
    }

    public void setOut_sid(String out_sid) {
        this.out_sid = out_sid;
    }

    public String getLogistics_company_name() {
        return logistics_company_name;
    }

    public void setLogistics_company_name(String logistics_company_name) {
        this.logistics_company_name = logistics_company_name;
    }

    public String getLogistics_company_code() {
        return logistics_company_code;
    }

    public void setLogistics_company_code(String logistics_company_code) {
        this.logistics_company_code = logistics_company_code;
    }

    public String getProcess_time() {
        return process_time;
    }

    public void setProcess_time(String process_time) {
        this.process_time = process_time;
    }

    public String getPackage_group_key() {
        return package_group_key;
    }

    public void setPackage_group_key(String package_group_key) {
        this.package_group_key = package_group_key;
    }
}
