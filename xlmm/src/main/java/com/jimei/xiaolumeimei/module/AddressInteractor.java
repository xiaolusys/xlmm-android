package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.IdCardBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public interface AddressInteractor {
    Subscription getAddressList(ServiceResponse<List<AddressBean>> response);

    Subscription delete_address(String id, ServiceResponse<AddressResultBean> response);

    Subscription create_addressWithId(String receiver_state, String receiver_city,
                                      String receiver_district, String receiver_address,
                                      String receiver_name, String receiver_mobile,
                                      String defaulta, String identification_no,
                                      ServiceResponse<AddressResultBean> response);

    Subscription update_addressWithId(String id, String receiver_state, String receiver_city,
                                      String receiver_district, String receiver_address,
                                      String receiver_name, String receiver_mobile,
                                      String defalut, String identification_no,
                                      ServiceResponse<AddressResultBean> response);

    Subscription update_address(String id, String receiver_state, String receiver_city,
                                String receiver_district, String receiver_address,
                                String receiver_name, String receiver_mobile,
                                String logistic_company_code, String referal_trade_id,
                                String identification_no, ServiceResponse<AddressResultBean> response);

    Subscription idCardIndentify(String side, String card_base64, ServiceResponse<IdCardBean> response);
}

