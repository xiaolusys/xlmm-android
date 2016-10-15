package com.jimei.xiaolumeimei.entities.event;

/**
 * Created by itxuye on 2016/6/25.
 */
public class WebViewEvent {
  public final String cookies;
  public final String domain;
  public final String actlink;
  public final int id;
  public final String sessionid;

  public WebViewEvent(String cookies, String domain, String actlink, int id, String sessionid) {
    this.cookies = cookies;
    this.domain = domain;
    this.actlink = actlink;
    this.id = id;
    this.sessionid = sessionid;
  }
}
