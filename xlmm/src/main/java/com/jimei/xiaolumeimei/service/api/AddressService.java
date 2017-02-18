package com.jimei.xiaolumeimei.service.api;

import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface AddressService {

    //获取地址列表
    @GET("/rest/v1/address")
    Observable<List<AddressBean>> getAddressList();

    //删除某一个地址
    @POST("/rest/v1/address/{id}/delete_address")
    Observable<AddressResultBean> delete_address(
        @Path("id") String id);

    //创建新的地址
    @FormUrlEncoded
    @POST("/rest/v1/address/create_address")
    Observable<AddressResultBean> create_addressWithId(
        @Field("receiver_state") String receiver_state,
        @Field("receiver_city") String receiver_city,
        @Field("receiver_district") String receiver_district,
        @Field("receiver_address") String receiver_address,
        @Field("receiver_name") String receiver_name,
        @Field("receiver_mobile") String receiver_mobile,
        @Field("default") String defaultStr,
        @Field("identification_no") String identification_no);


    //修改地址
    @FormUrlEncoded
    @POST("/rest/v1/address/{id}/update")
    Observable<AddressResultBean> update_address(
        @Path("id") String id,
        @Field("receiver_state") String receiver_state,
        @Field("receiver_city") String receiver_city,
        @Field("receiver_district") String receiver_district,
        @Field("receiver_address") String receiver_address,
        @Field("receiver_name") String receiver_name,
        @Field("receiver_mobile") String receiver_mobile,
        @Field("default") String defaultStr);


    //修改地址
    @FormUrlEncoded
    @POST("/rest/v1/address/{id}/update")
    Observable<AddressResultBean> update_addressWithId(
        @Path("id") String id,
        @Field("receiver_state") String receiver_state,
        @Field("receiver_city") String receiver_city,
        @Field("receiver_district") String receiver_district,
        @Field("receiver_address") String receiver_address,
        @Field("receiver_name") String receiver_name,
        @Field("receiver_mobile") String receiver_mobile,
        @Field("default") String defaultStr,
        @Field("identification_no") String identification_no);

    //修改地址
    @FormUrlEncoded
    @POST("/rest/v1/address/{id}/update")
    Observable<AddressResultBean> update_address(
        @Path("id") String id,
        @Field("receiver_state") String receiver_state,
        @Field("receiver_city") String receiver_city,
        @Field("receiver_district") String receiver_district,
        @Field("receiver_address") String receiver_address,
        @Field("receiver_name") String receiver_name,
        @Field("receiver_mobile") String receiver_mobile,
        @Field("logistic_company_code") String logistic_company_code,
        @Field("referal_trade_id") String referal_trade_id);

    //修改地址
    @FormUrlEncoded
    @POST("/rest/v1/address/{id}/update")
    Observable<AddressResultBean> update_address(
        @Path("id") String id,
        @Field("receiver_state") String receiver_state,
        @Field("receiver_city") String receiver_city,
        @Field("receiver_district") String receiver_district,
        @Field("receiver_address") String receiver_address,
        @Field("receiver_name") String receiver_name,
        @Field("receiver_mobile") String receiver_mobile,
        @Field("logistic_company_code") String logistic_company_code,
        @Field("referal_trade_id") String referal_trade_id,
        @Field("identification_no") String identification_no);
}
