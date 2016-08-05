package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by itxuye on 2016/8/5.
 */
public class MamaSelfListBean {

  /**
   * id : 1
   * title : 5adfadf
   * content_link : hpadsfal
   * content :
   * dest : null
   * status : 1
   * read : false
   * created : 2016-08-02T21:11:38
   * creator : 686011
   */

  private List<ResultsBean> results;

  public List<ResultsBean> getResults() {
    return results;
  }

  public void setResults(List<ResultsBean> results) {
    this.results = results;
  }

  public static class ResultsBean {
    private String title;
    private boolean read;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public boolean isRead() {
      return read;
    }

    public void setRead(boolean read) {
      this.read = read;
    }
  }
}
