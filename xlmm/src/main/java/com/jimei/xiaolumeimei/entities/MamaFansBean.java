package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by wulei on 2016/2/5.
 */
public class MamaFansBean {

  /**
   * count : 2071
   * next : http://m.xiaolumeimei.com/rest/v1/pmt/xlmm/get_fans_list?page=2
   * previous : null
   * results : [{"nick":"","id":842057,"thumbnail":""},{"nick":"","id":842061,"thumbnail":""},{"nick":"Hixb","id":842086,"thumbnail":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM770QstXrQ96q9NWztxnufEHg5vdyYOyZ3ibpC9vHsQ87Cl680ibFAbE8vBdzzRk8JebDv1RHUko0gxxKDUToT8wQkYG2FThm8RQ/0"},{"nick":"","id":842101,"thumbnail":""},{"nick":"朦胧的眼睛","id":842107,"thumbnail":"http://wx.qlogo.cn/mmopen/F8wmNnQLXwl0Z8icWzHkfnJpxThDxtibT9DWBYWGNTSKTpc6wf5zDQBxOicickTib9ETmPPbvkWdwu5VnEjtDZ4pPm2xiafiaWfWzqG/0"},{"nick":"","id":842120,"thumbnail":""},{"nick":"YANYAN","id":842176,"thumbnail":"http://wx.qlogo.cn/mmopen/pf11h2zpf4BzV9VvDvY35zULcMgJibShbB8MO3oibwSprO994yhNk6KmialSO7A0niayRwsicUYaGxdewN1ReR8EK7bicItxAOoUWic/0"},{"nick":"芳儿","id":842231,"thumbnail":"http://wx.qlogo.cn/mmopen/pf11h2zpf4Dl3UEZCK9MDdW06uoRMwY5TuI7BqcicJNfuTjmvXu2Bcbn8fZRrgJtTicubbQATib4ylStFeDll0c9vSQaVfyg0vz/0"},{"nick":"","id":842256,"thumbnail":"http://wx.qlogo.cn/mmopen/jfpC8E6yr8eAicOVqlBHXZqiakQaUsjRQKukRv359vSbc4AKxlGyic9OH4cJIWW1iaL0fiaYybrbUPMBy1vjPBpMHSPepibScoRFCK/0"},{"nick":"","id":842312,"thumbnail":""}]
   */

  @SerializedName("count") private int count;
  @SerializedName("next") private String next;
  @SerializedName("previous") private Object previous;
  /**
   * nick :
   * id : 842057
   * thumbnail :
   */

  @SerializedName("results") private List<FansEntity> fans;

  public void setCount(int count) {
    this.count = count;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public void setPrevious(Object previous) {
    this.previous = previous;
  }

  public void setFans(List<FansEntity> fans) {
    this.fans = fans;
  }

  public int getCount() {
    return count;
  }

  public String getNext() {
    return next;
  }

  public Object getPrevious() {
    return previous;
  }

  public List<FansEntity> getFans() {
    return fans;
  }

  public static class FansEntity {
    @SerializedName("nick") private String nick;
    @SerializedName("id") private int id;
    @SerializedName("thumbnail") private String thumbnail;

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
}
