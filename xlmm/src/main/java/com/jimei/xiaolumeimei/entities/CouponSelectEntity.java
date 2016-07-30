package com.jimei.xiaolumeimei.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wisdom on 16/7/23.
 */
public class CouponSelectEntity  implements Serializable{

    /**
     * info : 购物车为空!
     * code : 1
     * usable_coupon : []
     * disable_coupon : []
     */

    private String info;
    private int code;
    private ArrayList<CouponEntity> usable_coupon;
    private ArrayList<CouponEntity> disable_coupon;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<CouponEntity> getUsable_coupon() {
        return usable_coupon;
    }

    public void setUsable_coupon(ArrayList<CouponEntity> usable_coupon) {
        this.usable_coupon = usable_coupon;
    }

    public ArrayList<CouponEntity> getDisable_coupon() {
        return disable_coupon;
    }

    public void setDisable_coupon(ArrayList<CouponEntity> disable_coupon) {
        this.disable_coupon = disable_coupon;
    }
}
