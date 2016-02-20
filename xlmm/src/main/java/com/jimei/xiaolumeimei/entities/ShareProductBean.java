package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wulei on 2016/2/20.
 */
public class ShareProductBean {

  /**
   * url : http://api.xiaolumeimei.com/rest/v1/share/2
   * id : 2
   * title : 花朵印花裙套装/粉色
   * desc : 圆领卫衣+裙子，柔软舒适。
   商品入口：　http://m.xiaolumeimei.com/pages/shangpinxq.html?id=32849&mm_linkid=44
   * share_img : http://image.xiaolu.so/MG_1455787789379%E8%8A%B1%E6%9C%B5%E5%8D%B0%E8%8A%B1%E8%A3%99%E5%A5%97%E8%A3%8501.png?imageMogr2/thumbnail/108/quality/80/format/jpg/crop/108x108/
   * active_at : 2015-12-19
   * created : 2015-12-19T13:30:19
   * status : true
   * share_link : http://m.xiaolumeimei.com/pages/shangpinxq.html?id=32849&mm_linkid=44
   */

  @SerializedName("url") private String url;
  @SerializedName("id") private int id;
  @SerializedName("title") private String title;
  @SerializedName("desc") private String desc;
  @SerializedName("share_img") private String shareImg;
  @SerializedName("active_at") private String activeAt;
  @SerializedName("created") private String created;
  @SerializedName("status") private boolean status;
  @SerializedName("share_link") private String shareLink;

  public void setUrl(String url) {
    this.url = url;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setShareImg(String shareImg) {
    this.shareImg = shareImg;
  }

  public void setActiveAt(String activeAt) {
    this.activeAt = activeAt;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void setShareLink(String shareLink) {
    this.shareLink = shareLink;
  }

  public String getUrl() {
    return url;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDesc() {
    return desc;
  }

  public String getShareImg() {
    return shareImg;
  }

  public String getActiveAt() {
    return activeAt;
  }

  public String getCreated() {
    return created;
  }

  public boolean isStatus() {
    return status;
  }

  public String getShareLink() {
    return shareLink;
  }
}
