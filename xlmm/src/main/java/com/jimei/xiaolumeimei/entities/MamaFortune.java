package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wulei on 3/10/16.
 */
public class MamaFortune {
  @Override public String toString() {
    return "MamaFortune{" +
        "mamaFortune=" + mamaFortune +
        '}';
  }

  @SerializedName("mama_fortune") private MamaFortuneBean mamaFortune;

  public MamaFortuneBean getMamaFortune() {
    return mamaFortune;
  }

  public void setMamaFortune(MamaFortuneBean mamaFortune) {
    this.mamaFortune = mamaFortune;
  }

  public static class MamaFortuneBean {
    @Override public String toString() {
      return "MamaFortuneBean{" +
          "mamaId=" + mamaId +
          ", mamaName='" + mamaName + '\'' +
          ", mamaLevel=" + mamaLevel +
          ", mamaLevelDisplay='" + mamaLevelDisplay + '\'' +
          ", cashValue=" + cashValue +
          ", fansNum=" + fansNum +
          ", inviteNum=" + inviteNum +
          ", orderNum=" + orderNum +
          ", carryValue=" + carryValue +
          ", activeValueNum=" + activeValueNum +
          ", carryPendingDisplay=" + carryPendingDisplay +
          ", carryConfirmedDisplay=" + carryConfirmedDisplay +
          ", carryCashoutDisplay=" + carryCashoutDisplay +
          ", mamaEventLink='" + mamaEventLink + '\'' +
          ", historyLastDay=" + historyLastDay +
          ", todayVisitorNum=" + todayVisitorNum +
          ", modified='" + modified + '\'' +
          ", created='" + created + '\'' +
          ", extraInfo=" + extraInfo +
          '}';
    }

    @SerializedName("mama_id") private int mamaId;
    @SerializedName("mama_name") private String mamaName;
    @SerializedName("mama_level") private int mamaLevel;
    @SerializedName("mama_level_display") private String mamaLevelDisplay;
    @SerializedName("cash_value") private double cashValue;
    @SerializedName("fans_num") private int fansNum;
    @SerializedName("invite_num") private int inviteNum;
    @SerializedName("order_num") private int orderNum;
    @SerializedName("carry_value") private double carryValue;
    @SerializedName("active_value_num") private int activeValueNum;
    @SerializedName("carry_pending_display") private double carryPendingDisplay;
    @SerializedName("carry_confirmed_display") private double carryConfirmedDisplay;
    @SerializedName("carry_cashout_display") private double carryCashoutDisplay;
    @SerializedName("mama_event_link") private String mamaEventLink;
    @SerializedName("history_last_day") private Object historyLastDay;
    @SerializedName("today_visitor_num") private int todayVisitorNum;
    @SerializedName("modified") private String modified;
    @SerializedName("created") private String created;
    @SerializedName("extra_info") private ExtraInfoBean extraInfo;

    public int getMamaId() {
      return mamaId;
    }

    public void setMamaId(int mamaId) {
      this.mamaId = mamaId;
    }

    public String getMamaName() {
      return mamaName;
    }

    public void setMamaName(String mamaName) {
      this.mamaName = mamaName;
    }

    public int getMamaLevel() {
      return mamaLevel;
    }

    public void setMamaLevel(int mamaLevel) {
      this.mamaLevel = mamaLevel;
    }

    public String getMamaLevelDisplay() {
      return mamaLevelDisplay;
    }

    public void setMamaLevelDisplay(String mamaLevelDisplay) {
      this.mamaLevelDisplay = mamaLevelDisplay;
    }

    public double getCashValue() {
      return cashValue;
    }

    public void setCashValue(double cashValue) {
      this.cashValue = cashValue;
    }

    public int getFansNum() {
      return fansNum;
    }

    public void setFansNum(int fansNum) {
      this.fansNum = fansNum;
    }

    public int getInviteNum() {
      return inviteNum;
    }

    public void setInviteNum(int inviteNum) {
      this.inviteNum = inviteNum;
    }

    public int getOrderNum() {
      return orderNum;
    }

    public void setOrderNum(int orderNum) {
      this.orderNum = orderNum;
    }

    public double getCarryValue() {
      return carryValue;
    }

    public void setCarryValue(double carryValue) {
      this.carryValue = carryValue;
    }

    public int getActiveValueNum() {
      return activeValueNum;
    }

    public void setActiveValueNum(int activeValueNum) {
      this.activeValueNum = activeValueNum;
    }

    public double getCarryPendingDisplay() {
      return carryPendingDisplay;
    }

    public void setCarryPendingDisplay(double carryPendingDisplay) {
      this.carryPendingDisplay = carryPendingDisplay;
    }

    public double getCarryConfirmedDisplay() {
      return carryConfirmedDisplay;
    }

    public void setCarryConfirmedDisplay(double carryConfirmedDisplay) {
      this.carryConfirmedDisplay = carryConfirmedDisplay;
    }

    public double getCarryCashoutDisplay() {
      return carryCashoutDisplay;
    }

    public void setCarryCashoutDisplay(double carryCashoutDisplay) {
      this.carryCashoutDisplay = carryCashoutDisplay;
    }

    public String getMamaEventLink() {
      return mamaEventLink;
    }

    public void setMamaEventLink(String mamaEventLink) {
      this.mamaEventLink = mamaEventLink;
    }

    public Object getHistoryLastDay() {
      return historyLastDay;
    }

    public void setHistoryLastDay(Object historyLastDay) {
      this.historyLastDay = historyLastDay;
    }

    public int getTodayVisitorNum() {
      return todayVisitorNum;
    }

    public void setTodayVisitorNum(int todayVisitorNum) {
      this.todayVisitorNum = todayVisitorNum;
    }

    public String getModified() {
      return modified;
    }

    public void setModified(String modified) {
      this.modified = modified;
    }

    public String getCreated() {
      return created;
    }

    public void setCreated(String created) {
      this.created = created;
    }

    public ExtraInfoBean getExtraInfo() {
      return extraInfo;
    }

    public void setExtraInfo(ExtraInfoBean extraInfo) {
      this.extraInfo = extraInfo;
    }

    public static class ExtraInfoBean {
      @Override public String toString() {
        return "ExtraInfoBean{" +
            "agencylevelDisplay='" + agencylevelDisplay + '\'' +
            ", inviteUrl='" + inviteUrl + '\'' +
            ", agencylevel=" + agencylevel +
            ", nextAgencylevel=" + nextAgencylevel +
            ", surplusDays=" + surplusDays +
            ", couldCashOut=" + couldCashOut +
            ", nextLevelExamUrl='" + nextLevelExamUrl + '\'' +
            ", nextAgencylevelDisplay='" + nextAgencylevelDisplay + '\'' +
            ", thumbnail='" + thumbnail + '\'' +
            ", cashoutReason='" + cashoutReason + '\'' +
            '}';
      }

      @SerializedName("agencylevel_display") private String agencylevelDisplay;
      @SerializedName("invite_url") private String inviteUrl;
      @SerializedName("agencylevel") private int agencylevel;
      @SerializedName("next_agencylevel") private int nextAgencylevel;
      @SerializedName("surplus_days") private int surplusDays;
      @SerializedName("could_cash_out") private int couldCashOut;
      @SerializedName("next_level_exam_url") private String nextLevelExamUrl;
      @SerializedName("next_agencylevel_display") private String nextAgencylevelDisplay;
      @SerializedName("thumbnail") private String thumbnail;
      @SerializedName("cashout_reason") private String cashoutReason;

      public String getAgencylevelDisplay() {
        return agencylevelDisplay;
      }

      public void setAgencylevelDisplay(String agencylevelDisplay) {
        this.agencylevelDisplay = agencylevelDisplay;
      }

      public String getInviteUrl() {
        return inviteUrl;
      }

      public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
      }

      public int getAgencylevel() {
        return agencylevel;
      }

      public void setAgencylevel(int agencylevel) {
        this.agencylevel = agencylevel;
      }

      public int getNextAgencylevel() {
        return nextAgencylevel;
      }

      public void setNextAgencylevel(int nextAgencylevel) {
        this.nextAgencylevel = nextAgencylevel;
      }

      public int getSurplusDays() {
        return surplusDays;
      }

      public void setSurplusDays(int surplusDays) {
        this.surplusDays = surplusDays;
      }

      public int getCouldCashOut() {
        return couldCashOut;
      }

      public void setCouldCashOut(int couldCashOut) {
        this.couldCashOut = couldCashOut;
      }

      public String getNextLevelExamUrl() {
        return nextLevelExamUrl;
      }

      public void setNextLevelExamUrl(String nextLevelExamUrl) {
        this.nextLevelExamUrl = nextLevelExamUrl;
      }

      public String getNextAgencylevelDisplay() {
        return nextAgencylevelDisplay;
      }

      public void setNextAgencylevelDisplay(String nextAgencylevelDisplay) {
        this.nextAgencylevelDisplay = nextAgencylevelDisplay;
      }

      public String getThumbnail() {
        return thumbnail;
      }

      public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
      }

      public String getCashoutReason() {
        return cashoutReason;
      }

      public void setCashoutReason(String cashoutReason) {
        this.cashoutReason = cashoutReason;
      }
    }
  }
}
