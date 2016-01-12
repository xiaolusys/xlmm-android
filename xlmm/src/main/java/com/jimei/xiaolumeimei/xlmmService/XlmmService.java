package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import java.util.List;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public interface XlmmService {

  //@formatter:off

  @FormUrlEncoded @POST("register/customer_login")
  Observable<UserBean> login(
      @Field("username") String username,
      @Field("password") String password);

  //童装列表
  @GET(XlmmApi.CHILD_URL)
  Observable<ChildListBean> getChildList();

  //女装列表
  @GET(XlmmApi.WOMAN_URL)
  Observable<LadyListBean> getLadyList();

  //今日列表
  @GET(XlmmApi.TODAY_URL)
  Observable<ProductListBean> getTodayList(
      @Query("page") int page,
      @Query("page_size") int page_size);

  //今日海报列表
  @GET(XlmmApi.TODAY_POSTER_URL)
  Observable<PostBean> getTodayPost();

  //昨日海报列表
  @GET(XlmmApi.YESTERDAY_POSTER_URL)
  Observable<PostBean> getYestDayPost();

  //昨日列表
  @GET(XlmmApi.YESTERDAY_URL)
  Observable<ProductListBean> getYestDayList();

  //同款列表
  @GET(XlmmApi.TONGKUAN_URL+"{model_id}")
  Observable<List<ProductBean>> getTongKuanList(
      @Path("model_id")int model_id);

  //获取待支付订单
  @GET(XlmmApi.)

}
