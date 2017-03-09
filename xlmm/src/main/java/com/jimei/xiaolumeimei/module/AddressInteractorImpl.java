package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.IdCardBean;
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
    public Subscription create_addressWithId(String state, String city, String district, String address,
                                             String name, String mobile, String defaulta,
                                             String identification_no, String face, String back,
                                             ServiceResponse<AddressResultBean> response) {

        if (face == null || back == null || "".equals(face) || "".equals(back)) {
            return service.create_addressWithId(state, city, district, address, name, mobile,
                defaulta, identification_no)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            return service.create_addressWithId(state, city, district, address, name, mobile,
                defaulta, identification_no, face, back)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }

    }

    @Override
    public Subscription update_addressWithId(String id, String state, String city, String district,
                                             String address, String name, String mobile, String defalut,
                                             String identification_no, String face, String back,
                                             ServiceResponse<AddressResultBean> response) {
        if ("".equals(identification_no) || identification_no == null) {
            return service.update_address(id, state, city, district,
                address, name, mobile, defalut)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            if (face == null || back == null || "".equals(face) || "".equals(back)) {
                return service.update_addressWithId(id, state, city, district,
                    address, name, mobile, defalut, identification_no)
                    .compose(new DefaultTransform<>())
                    .subscribe(response);
            } else {
                return service.update_addressWithId(id, state, city, district,
                    address, name, mobile, defalut, identification_no, face, back)
                    .compose(new DefaultTransform<>())
                    .subscribe(response);
            }
        }
    }

    @Override
    public Subscription update_address(String id, String state, String city, String district, String address,
                                       String name, String mobile, String logistic_company_code,
                                       String referal_trade_id, ServiceResponse<AddressResultBean> response) {
        return service.update_address(id, state, city, district,
            address, name, mobile, logistic_company_code, referal_trade_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);

    }

    @Override
    public Subscription idCardIndentify(String side, String card_base64, ServiceResponse<IdCardBean> response) {
        return service.idCardIndentify(side, card_base64)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
