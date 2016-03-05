package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wulei on 2016/2/21.
 */
public class XiaoMiPushContent {

  /**
   * target_url : target_url
   */

  @SerializedName("target_url") private String targetUrl;

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  @Override public String toString() {
    return "XiaoMiPushContent{" +
        "targetUrl='" + targetUrl + '\'' +
        '}';
  }
}
