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
    private String headimgurl;
    private String nick;
    private String mobile;
    private String note;
    private String modified;

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

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    public String getNote() {
      return note;
    }

    public void setNote(String note) {
      this.note = note;
    }

    public String getModified() {
      return modified;
    }

    public void setModified(String modified) {
      this.modified = modified;
    }
  }
}
