package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/7/13.
 */
public class MamaUrl {

  private int count;
  private Object next;
  private Object previous;

  private List<ResultsBean> results;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Object getNext() {
    return next;
  }

  public void setNext(Object next) {
    this.next = next;
  }

  public Object getPrevious() {
    return previous;
  }

  public void setPrevious(Object previous) {
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
    private String version;
    private boolean is_valid;
    private ExtraBean extra;
    private String created;
    private String modified;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public boolean isIs_valid() {
      return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
      this.is_valid = is_valid;
    }

    public ExtraBean getExtra() {
      return extra;
    }

    public void setExtra(ExtraBean extra) {
      this.extra = extra;
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

    public static class ExtraBean {
      private String notice;
      private String act_info;
      private String exam;
      private String forum;
      private PicturesBean pictures;
      private String renew;
      private String fans_explain;
      private String invite;
      private String team_explain;

      public String getNotice() {
        return notice;
      }

      public void setNotice(String notice) {
        this.notice = notice;
      }

      public String getAct_info() {
        return act_info;
      }

      public void setAct_info(String act_info) {
        this.act_info = act_info;
      }

      public String getExam() {
        return exam;
      }

      public void setExam(String exam) {
        this.exam = exam;
      }

      public PicturesBean getPictures() {
        return pictures;
      }

      public void setPictures(PicturesBean pictures) {
        this.pictures = pictures;
      }

      public String getRenew() {
        return renew;
      }

      public void setRenew(String renew) {
        this.renew = renew;
      }

      public String getFans_explain() {
        return fans_explain;
      }

      public void setFans_explain(String fans_explain) {
        this.fans_explain = fans_explain;
      }

      public String getInvite() {
        return invite;
      }

      public void setInvite(String invite) {
        this.invite = invite;
      }

      public String getForum() {
        return forum;
      }

      public void setForum(String forum) {
        this.forum = forum;
      }

      public String getTeam_explain() {
        return team_explain;
      }

      public void setTeam_explain(String team_explain) {
        this.team_explain = team_explain;
      }

      public static class PicturesBean {
        private String exam_pic;

        public String getExam_pic() {
          return exam_pic;
        }

        public void setExam_pic(String exam_pic) {
          this.exam_pic = exam_pic;
        }
      }
    }
  }
}
