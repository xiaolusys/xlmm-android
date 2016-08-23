package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye on 2016/7/26.
 */
public class PersonalCarryRankBean {

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  @SerializedName("num") private int num;
  @SerializedName("mama_nick") private String mamaNick;
  @SerializedName("thumbnail") private String thumbnail;
  @SerializedName("total") private int total;
  @SerializedName("rank") private int rank;

  public int getRankAdd() {
    return rankAdd;
  }

  public void setRankAdd(int rankAdd) {
    this.rankAdd = rankAdd;
  }

  @SerializedName("rank_add") private int rankAdd;

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

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }


  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }
}
