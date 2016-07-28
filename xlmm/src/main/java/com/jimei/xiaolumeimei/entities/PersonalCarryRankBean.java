package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye on 2016/7/26.
 */
public class PersonalCarryRankBean {
  @SerializedName("mama") private int mama;

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  @SerializedName("num") private int num;
  @SerializedName("mama_nick") private String mamaNick;
  @SerializedName("thumbnail") private String thumbnail;
  @SerializedName("mobile") private String mobile;
  @SerializedName("phone") private String phone;
  @SerializedName("history_total") private int historyTotal;
  @SerializedName("stat_time") private String statTime;
  @SerializedName("total") private int total;
  @SerializedName("duration_total") private int durationTotal;
  @SerializedName("rank") private int rank;

  public int getRankAdd() {
    return rankAdd;
  }

  public void setRankAdd(int rankAdd) {
    this.rankAdd = rankAdd;
  }

  @SerializedName("rank_add") private int rankAdd;

  public int getMama() {
    return mama;
  }

  public void setMama(int mama) {
    this.mama = mama;
  }

  public String getMamaNick() {
    return mamaNick;
  }

  public void setMamaNick(String mamaNick) {
    this.mamaNick = mamaNick;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getHistoryTotal() {
    return historyTotal;
  }

  public void setHistoryTotal(int historyTotal) {
    this.historyTotal = historyTotal;
  }

  public String getStatTime() {
    return statTime;
  }

  public void setStatTime(String statTime) {
    this.statTime = statTime;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getDurationTotal() {
    return durationTotal;
  }

  public void setDurationTotal(int durationTotal) {
    this.durationTotal = durationTotal;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }
}
