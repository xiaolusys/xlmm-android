package com.jimei.xiaolumeimei.xlmmService.api;

import com.jimei.xiaolumeimei.entities.ActivityGoodListBean;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionDeleteBody;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface ProductService {


    @GET("/sale/promotion/promotion/goods/get_promotion_topic_pics")
    Observable<ActivityGoodListBean> getActivityGoodList(
            @Query("promotion_id") int promotion_id
    );

    @GET("/rest/v2/modelproducts/{id}")
    Observable<ProductDetailBean> getProductDetail(
            @Path("id") int id);

    @GET("/rest/v1/share/model")
    Observable<ShareModelBean> getShareModel(
            @Query("model_id") int model_id);

    @GET("/rest/v1/favorites")
    Observable<CollectionAllBean> getCollection(
            @Query("page") int page,
            @Query("shelf_status") String shelf_status);

    @FormUrlEncoded
    @POST("/rest/v1/favorites")
    Observable<CollectionResultBean> addCollection(
            @Field("model_id") int model_id);

    @HTTP(method = "DELETE", path = "/rest/v1/favorites", hasBody = true)
    Observable<CollectionResultBean> deleteCollection(
            @Body CollectionDeleteBody deleteBody);

    @GET("/rest/v2/modelproducts")
    Observable<ProductListBean> getCategoryProductList(
            @Query("cid") String cid,
            @Query("page") int page,
            @Query("order_by") String order_by);

    @GET("/rest/v2/modelproducts/today")
    Observable<ProductListBean> getTodayProducts(
            @Query("page") int page);

    @GET("/rest/v2/modelproducts/tomorrow")
    Observable<ProductListBean> getTomorrowProducts(
            @Query("page") int page);

    @GET("/rest/v2/modelproducts/yesterday")
    Observable<ProductListBean> getYesterdayProducts(
            @Query("page") int page);

    //品牌
    @GET("/rest/v1/activitys/{id}")
    Observable<BrandListBean> getBrandList(
            @Path("id") String id);


    @GET("/rest/v2/modelproducts/boutique")
    Observable<ProductListBean> getBoutiqueList(
            @Query("page") int page,
            @Query("order_by") String order_by
    );
}
