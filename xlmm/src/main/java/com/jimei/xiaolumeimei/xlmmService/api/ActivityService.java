package com.jimei.xiaolumeimei.xlmmService.api;

import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.StartBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface ActivityService {

    //活动内容分享
    @GET("/rest/v1/activitys/{id}/get_share_params")
    Observable<ActivityBean> get_party_share_content(
            @Path("id") String id);

    @GET("/rest/v1/activitys")
    Observable<List<PostActivityBean>> getPostActivity();

    @FormUrlEncoded
    @POST("/rest/v1/usercoupons")
    Observable<ResponseBody> getUsercoupons(
            @Field("template_id") String template_id);

    @GET("/rest/v1/activitys/startup_diagrams")
    Observable<StartBean> getStartAds();
}
