package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.xlmmService.RetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.api.AddressService;

import java.util.List;

import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressModel {

    private static AddressModel ourInstance = new AddressModel();
    private static AddressService addressService;

    private static AddressService getService() {
        if (addressService == null) {
            addressService = RetrofitClient.createAdapter().create(AddressService.class);
        }
        return addressService;
    }

    private AddressModel() {
    }

    public static AddressModel getInstance() {
        return ourInstance;
    }

    //获取地址列表
    public Observable<List<AddressBean>> getAddressList() {
        return getService()
                .getAddressList()
                .compose(new DefaultTransform<>());
    }

    //删除某个地址
    public Observable<AddressResultBean> delete_address(String id) {
        return getService()
                .delete_address(id)
                .compose(new DefaultTransform<>());
    }

    //创建新的地址
    public Observable<AddressResultBean> create_addressWithId(String receiver_state, String receiver_city,
                                                              String receiver_district, String receiver_address,
                                                              String receiver_name, String receiver_mobile,
                                                              String defaulta, String identification_no) {
        return getService()
                .create_addressWithId(receiver_state, receiver_city, receiver_district,
                        receiver_address, receiver_name, receiver_mobile, defaulta, identification_no)
                .compose(new DefaultTransform<>());
    }

    //更新地址
    public Observable<AddressResultBean> update_address(String id, String receiver_state, String receiver_city,
                                                        String receiver_district, String receiver_address,
                                                        String receiver_name, String receiver_mobile, String defalut) {
        return getService()
                .update_address(id, receiver_state, receiver_city, receiver_district,
                        receiver_address, receiver_name, receiver_mobile, defalut)
                .compose(new DefaultTransform<>());
    }

    //更新地址
    public Observable<AddressResultBean> update_addressWithId(String id, String receiver_state, String receiver_city,
                                                              String receiver_district, String receiver_address,
                                                              String receiver_name, String receiver_mobile,
                                                              String defalut, String identification_no) {
        return getService()
                .update_addressWithId(id, receiver_state, receiver_city, receiver_district,
                        receiver_address, receiver_name, receiver_mobile, defalut, identification_no)
                .compose(new DefaultTransform<>());
    }

    //更新地址
    public Observable<AddressResultBean> update_address(String id, String receiver_state, String receiver_city,
                                                        String receiver_district, String receiver_address,
                                                        String receiver_name, String receiver_mobile,
                                                        String logistic_company_code, String referal_trade_id, String identification_no) {
        if (identification_no == null || "".equals(identification_no)) {
            return getService()
                    .update_address(id, receiver_state, receiver_city, receiver_district,
                            receiver_address, receiver_name, receiver_mobile, logistic_company_code, referal_trade_id)
                    .compose(new DefaultTransform<>());
        } else {
            return getService()
                    .update_address(id, receiver_state, receiver_city, receiver_district,
                            receiver_address, receiver_name, receiver_mobile, logistic_company_code
                            , referal_trade_id, identification_no)
                    .compose(new DefaultTransform<>());
        }

    }
}
