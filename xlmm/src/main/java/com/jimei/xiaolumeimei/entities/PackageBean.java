package com.jimei.xiaolumeimei.entities;

import java.io.Serializable;

/**
 * Created by wisdom on 16/5/18.
 */
public class PackageBean implements Serializable {


    /**
     * title : 新款防晒超大雪纺披肩/红色
     * pic_path : http://image.xiaolu.so/MG_14614007497992.jpg
     * num : 1
     * payment : 17.9
     * assign_status_display : 已取消
     * ware_by_display : 1号仓
     * out_sid :
     * logistics_company_name :
     * logistics_company_code :
     * process_time : 2016-06-22 11:37:41
     * pay_time : 2016-06-22T11:37:41
     * book_time : null
     * assign_time : null
     * finish_time : null
     * cancel_time : null
     * package_group_key : 3-1-0-
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
    private String pay_time;
    private String book_time;
    private String assign_time;
    private String finish_time;
    private String cancel_time;
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

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public String getAssign_time() {
        return assign_time;
    }

    public void setAssign_time(String assign_time) {
        this.assign_time = assign_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getPackage_group_key() {
        return package_group_key;
    }

    public void setPackage_group_key(String package_group_key) {
        this.package_group_key = package_group_key;
    }
}
