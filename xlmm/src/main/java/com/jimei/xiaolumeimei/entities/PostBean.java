package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class PostBean {

  /**
   * id : 227
   * url : http://api.xiaolumeimei.com/rest/v1/posters/227
   * wem_posters : [{"item_link":"http://m.xiaolumeimei.com/nvzhuang.html","pic_link":"http://image.xiaolu.so/post1455790428375bannerhello3_01.png","app_link":"app:/","subject":["2折起","小鹿美美
   * 女装专场"]}]
   * chd_posters : [{"item_link":"http://m.xiaolumeimei.com/chaotong.html","pic_link":"http://image.xiaolu.so/post1455790444645bannerhello2_03.png","app_link":"app:/","subject":["2折起","小鹿美美
   * 童装专场"]}]
   * active_time : 2016-02-19T09:30:00
   * activity : {"id":1,"title":"免费试用活动","login_required":true,"act_desc":"免费送出1000个空气棉恒温睡袋！全国包邮免费送！","act_img":"http://image.xiaolu.so/post14541168439012%E5%8F%B7%E7%AB%A5.png?&imageMogr2/thumbnail/618x253/format/jpg/quality/90/","mask_link":null,"act_link":"http://m.xiaolumeimei.com/sale/promotion/xlsampleorder/","act_applink":"app:m.xiaolumeimei.com/chaotong","start_time":"2016-02-15T17:49:56","end_time":"2016-02-29T17:50:00"}
   */

  @SerializedName("id") private int mId;
  @SerializedName("url") private String mUrl;
  @SerializedName("active_time") private String mActiveTime;
  /**
   * id : 1
   * title : 免费试用活动
   * login_required : true
   * act_desc : 免费送出1000个空气棉恒温睡袋！全国包邮免费送！
   * act_img : http://image.xiaolu.so/post14541168439012%E5%8F%B7%E7%AB%A5.png?&imageMogr2/thumbnail/618x253/format/jpg/quality/90/
   * mask_link : null
   * act_link : http://m.xiaolumeimei.com/sale/promotion/xlsampleorder/
   * act_applink : app:m.xiaolumeimei.com/chaotong
   * start_time : 2016-02-15T17:49:56
   * end_time : 2016-02-29T17:50:00
   */

  @SerializedName("activity") private ActivityEntity mActivity;
  /**
   * item_link : http://m.xiaolumeimei.com/nvzhuang.html
   * pic_link : http://image.xiaolu.so/post1455790428375bannerhello3_01.png
   * app_link : app:/
   * subject : ["2折起","小鹿美美  女装专场"]
   */

  @SerializedName("wem_posters") private List<WemPostersEntity> mWemPosters;
  @SerializedName("chd_posters") private List<WemPostersEntity> mChdPosters;
  @SerializedName("brand_promotion") private List<BrandpromotionEntity>
      mBrandpromotionEntities;

  public List<BrandpromotionEntity> getmBrandpromotionEntities() {
    return mBrandpromotionEntities;
  }

  public void setmBrandpromotionEntities(
      List<BrandpromotionEntity> mBrandpromotionEntities) {
    this.mBrandpromotionEntities = mBrandpromotionEntities;
  }

  public List<WemPostersEntity> getmChdPosters() {
    return mChdPosters;
  }

  public void setmChdPosters(List<WemPostersEntity> mChdPosters) {
    this.mChdPosters = mChdPosters;
  }

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    this.mId = id;
  }

  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String url) {
    this.mUrl = url;
  }

  public String getActiveTime() {
    return mActiveTime;
  }

  public void setActiveTime(String activeTime) {
    this.mActiveTime = activeTime;
  }

  public ActivityEntity getActivity() {
    return mActivity;
  }

  public void setActivity(ActivityEntity activity) {
    this.mActivity = activity;
  }

  public List<WemPostersEntity> getWemPosters() {
    return mWemPosters;
  }

  public void setWemPosters(List<WemPostersEntity> wemPosters) {
    this.mWemPosters = wemPosters;
  }

  public static class ActivityEntity {
    @SerializedName("id") private int mId;
    @SerializedName("title") private String mTitle;
    @SerializedName("login_required") private boolean mLoginRequired;
    @SerializedName("act_desc") private String mActDesc;
    @SerializedName("act_img") private String mActImg;
    @SerializedName("mask_link") private Object mMaskLink;
    @SerializedName("act_link") private String mActLink;
    @SerializedName("act_applink") private String mActApplink;
    @SerializedName("start_time") private String mStartTime;
    @SerializedName("end_time") private String mEndTime;

    public int getId() {
      return mId;
    }

    public void setId(int id) {
      this.mId = id;
    }

    public String getTitle() {
      return mTitle;
    }

    public void setTitle(String title) {
      this.mTitle = title;
    }

    public boolean isLoginRequired() {
      return mLoginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
      this.mLoginRequired = loginRequired;
    }

    public String getActDesc() {
      return mActDesc;
    }

    public void setActDesc(String actDesc) {
      this.mActDesc = actDesc;
    }

    public String getActImg() {
      return mActImg;
    }

    public void setActImg(String actImg) {
      this.mActImg = actImg;
    }

    public Object getMaskLink() {
      return mMaskLink;
    }

    public void setMaskLink(Object maskLink) {
      this.mMaskLink = maskLink;
    }

    public String getActLink() {
      return mActLink;
    }

    public void setActLink(String actLink) {
      this.mActLink = actLink;
    }

    public String getActApplink() {
      return mActApplink;
    }

    public void setActApplink(String actApplink) {
      this.mActApplink = actApplink;
    }

    public String getStartTime() {
      return mStartTime;
    }

    public void setStartTime(String startTime) {
      this.mStartTime = startTime;
    }

    public String getEndTime() {
      return mEndTime;
    }

    public void setEndTime(String endTime) {
      this.mEndTime = endTime;
    }
  }

  public static class WemPostersEntity {
    @SerializedName("item_link") private String mItemLink;
    @SerializedName("pic_link") private String mPicLink;
    @SerializedName("app_link") private String mAppLink;
    @SerializedName("subject") private List<String> mSubject;

    public String getItemLink() {
      return mItemLink;
    }

    public void setItemLink(String itemLink) {
      this.mItemLink = itemLink;
    }

    public String getPicLink() {
      return mPicLink;
    }

    public void setPicLink(String picLink) {
      this.mPicLink = picLink;
    }

    public String getAppLink() {
      return mAppLink;
    }

    public void setAppLink(String appLink) {
      this.mAppLink = appLink;
    }

    public List<String> getSubject() {
      return mSubject;
    }

    public void setSubject(List<String> subject) {
      this.mSubject = subject;
    }
  }

  public static class BrandpromotionEntity {

    /**
     * id : 1
     * brand_name : top10
     * brand_desc : test
     * brand_pic :
     * brand_post :
     * brand_applink :
     * start_time : 2016-04-18T18:56:20
     * end_time : 2016-04-18T18:56:23
     * is_active : true
     */

    @SerializedName("id") private int mId;
    @SerializedName("brand_name") private String mBrandName;
    @SerializedName("brand_desc") private String mBrandDesc;
    @SerializedName("brand_pic") private String mBrandPic;
    @SerializedName("brand_post") private String mBrandPost;
    @SerializedName("brand_applink") private String mBrandApplink;
    @SerializedName("start_time") private String mStartTime;
    @SerializedName("end_time") private String mEndTime;
    @SerializedName("is_active") private boolean mIsActive;

    public int getId() {
      return mId;
    }

    public void setId(int id) {
      mId = id;
    }

    public String getBrandName() {
      return mBrandName;
    }

    public void setBrandName(String brandName) {
      mBrandName = brandName;
    }

    public String getBrandDesc() {
      return mBrandDesc;
    }

    public void setBrandDesc(String brandDesc) {
      mBrandDesc = brandDesc;
    }

    public String getBrandPic() {
      return mBrandPic;
    }

    public void setBrandPic(String brandPic) {
      mBrandPic = brandPic;
    }

    public String getBrandPost() {
      return mBrandPost;
    }

    public void setBrandPost(String brandPost) {
      mBrandPost = brandPost;
    }

    public String getBrandApplink() {
      return mBrandApplink;
    }

    public void setBrandApplink(String brandApplink) {
      mBrandApplink = brandApplink;
    }

    public String getStartTime() {
      return mStartTime;
    }

    public void setStartTime(String startTime) {
      mStartTime = startTime;
    }

    public String getEndTime() {
      return mEndTime;
    }

    public void setEndTime(String endTime) {
      mEndTime = endTime;
    }

    public boolean isIsActive() {
      return mIsActive;
    }

    public void setIsActive(boolean isActive) {
      mIsActive = isActive;
    }
  }

  private static class ChildPostersEntity {

    @SerializedName("item_link") private String mItemLink;
    @SerializedName("pic_link") private String mPicLink;
    @SerializedName("app_link") private String mAppLink;
    @SerializedName("subject") private List<String> mSubject;

    public String getItemLink() {
      return mItemLink;
    }

    public void setItemLink(String itemLink) {
      this.mItemLink = itemLink;
    }

    public String getPicLink() {
      return mPicLink;
    }

    public void setPicLink(String picLink) {
      this.mPicLink = picLink;
    }

    public String getAppLink() {
      return mAppLink;
    }

    public void setAppLink(String appLink) {
      this.mAppLink = appLink;
    }

    public List<String> getSubject() {
      return mSubject;
    }

    public void setSubject(List<String> subject) {
      this.mSubject = subject;
    }
  }
}
