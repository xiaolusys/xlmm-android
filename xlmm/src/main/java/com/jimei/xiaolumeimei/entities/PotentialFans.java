package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by itxuye on 2016/6/25.
 */
public class PotentialFans {

  private int count;
  private String next;
  private String previous;
  private List<ResultsBean> results;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public String getPrevious() {
    return previous;
  }

  public void setPrevious(String previous) {
    this.previous = previous;
  }

  public List<ResultsBean> getResults() {
    return results;
  }

  public void setResults(List<ResultsBean> results) {
    this.results = results;
  }

  public static class ResultsBean {
    private int id;
    private String created;
    private String modified;
    private int from_customer;
    private String openid;
    private String unionid;
    private String headimgurl;
    private String nick;
    private boolean status;
    private String mobile;
    private String ufrom;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getCreated() {
      return created;
    }

    public void setCreated(String created) {
      this.created = created;
    }

    public String getModified() {
      return modified;
    }

    public void setModified(String modified) {
      this.modified = modified;
    }

    public int getFrom_customer() {
      return from_customer;
    }

    public void setFrom_customer(int from_customer) {
      this.from_customer = from_customer;
    }

    public String getOpenid() {
      return openid;
    }

    public void setOpenid(String openid) {
      this.openid = openid;
    }

    public String getUnionid() {
      return unionid;
    }

    public void setUnionid(String unionid) {
      this.unionid = unionid;
    }

    public String getHeadimgurl() {
      return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
      this.headimgurl = headimgurl;
    }

    public String getNick() {
      return nick;
    }

    public void setNick(String nick) {
      this.nick = nick;
    }

    public boolean isStatus() {
      return status;
    }

    public void setStatus(boolean status) {
      this.status = status;
    }

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    public String getUfrom() {
      return ufrom;
    }

    public void setUfrom(String ufrom) {
      this.ufrom = ufrom;
    }
  }
}
