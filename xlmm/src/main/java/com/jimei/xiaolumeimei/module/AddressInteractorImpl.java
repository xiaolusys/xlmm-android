package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.AddressService;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public class AddressInteractorImpl implements AddressInteractor {
    private final AddressService service;

    @Inject
    public AddressInteractorImpl(AddressService service) {
        this.service = service;
    }

    @Override
    public Subscription getAddressList(ServiceResponse<List<AddressBean>> response) {
        return service.getAddressList()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription delete_address(String id, ServiceResponse<AddressResultBean> response) {
        return service.delete_address(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription create_addressWithId(String receiver_state, String receiver_city,
                                             String receiver_district, String receiver_address,
                                             String receiver_name, String receiver_mobile,
                                             String defaulta, String identification_no,
                                             ServiceResponse<AddressResultBean> response) {
        return service.create_addressWithId(receiver_state, receiver_city, receiver_district,
            receiver_address, receiver_name, receiver_mobile, defaulta, identification_no)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription update_addressWithId(String id, String receiver_state, String receiver_city,
                                             String receiver_district, String receiver_address,
                                             String receiver_name, String receiver_mobile,
                                             String defalut, String identification_no,
                                             ServiceResponse<AddressResultBean> response) {
        if ("".equals(identification_no) || identification_no == null) {
            return service.update_address(id, receiver_state, receiver_city, receiver_district,
                receiver_address, receiver_name, receiver_mobile, defalut)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }
        return service.update_addressWithId(id, receiver_state, receiver_city, receiver_district,
            receiver_address, receiver_name, receiver_mobile, defalut, identification_no)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription update_address(String id, String receiver_state, String receiver_city,
                                       String receiver_district, String receiver_address,
                                       String receiver_name, String receiver_mobile,
                                       String logistic_company_code, String referal_trade_id,
                                       String identification_no, ServiceResponse<AddressResultBean> response) {
        if (identification_no == null || "".equals(identification_no)) {
            return service.update_address(id, receiver_state, receiver_city, receiver_district,
                receiver_address, receiver_name, receiver_mobile, logistic_company_code, referal_trade_id)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            return service.update_address(id, receiver_state, receiver_city, receiver_district,
                receiver_address, receiver_name, receiver_mobile, logistic_company_code, referal_trade_id, identification_no)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }
    }
}
