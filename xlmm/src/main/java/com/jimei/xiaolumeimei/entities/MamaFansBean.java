package com.jimei.xiaolumeimei.entities;

/**
 * Created by wulei on 2016/2/5.
 */
public class MamaFansBean {

  /**
   * nick : yann
   * id : 2
   * thumbnail : http://wx.qlogo.cn/mmopen/0v06jdfpoVJicdicpGWelGJ20rSRhicwc1J3L0SFLCvVTkAwxVpkdGJyCUAN1uiatZ6lolZiapvPsyib9RjEUy95lYzia18mn5WxQrg/0
   */

  private String nick;
  private int id;
  private String thumbnail;

  public void setNick(String nick) {
    this.nick = nick;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getNick() {
    return nick;
  }

  public int getId() {
    return id;
  }

  public String getThumbnail() {
    return thumbnail;
  }
}
