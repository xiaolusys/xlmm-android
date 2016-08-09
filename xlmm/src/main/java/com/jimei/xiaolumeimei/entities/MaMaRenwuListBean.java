package com.jimei.xiaolumeimei.entities;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye on 2016/8/3.
 */
public class MaMaRenwuListBean implements Parcelable {

  /**
   * page_pop : true
   */

  private ConfigBean config;
  /**
   * show : true
   * complete : true
   * desc : 获得第一笔收益
   */

  private List<DataBean> data;

  public ConfigBean getConfig() {
    return config;
  }

  public void setConfig(ConfigBean config) {
    this.config = config;
  }

  public List<DataBean> getData() {
    return data;
  }

  public void setData(List<DataBean> data) {
    this.data = data;
  }

  public static class ConfigBean implements Parcelable {

    private boolean page_pop;

    public boolean isPage_pop() {
      return page_pop;
    }

    public void setPage_pop(boolean page_pop) {
      this.page_pop = page_pop;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeByte(this.page_pop ? (byte) 1 : (byte) 0);
    }

    public ConfigBean() {
    }

    protected ConfigBean(Parcel in) {
      this.page_pop = in.readByte() != 0;
    }

    public static final Creator<ConfigBean> CREATOR = new Creator<ConfigBean>() {
      @Override public ConfigBean createFromParcel(Parcel source) {
        return new ConfigBean(source);
      }

      @Override public ConfigBean[] newArray(int size) {
        return new ConfigBean[size];
      }
    };
  }

  public static class DataBean implements Parcelable {

    private boolean show;
    private boolean complete;
    private String desc;

    public boolean isShow() {
      return show;
    }

    public void setShow(boolean show) {
      this.show = show;
    }

    public boolean isComplete() {
      return complete;
    }

    public void setComplete(boolean complete) {
      this.complete = complete;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeByte(this.show ? (byte) 1 : (byte) 0);
      dest.writeByte(this.complete ? (byte) 1 : (byte) 0);
      dest.writeString(this.desc);
    }

    public DataBean() {
    }

    protected DataBean(Parcel in) {
      this.show = in.readByte() != 0;
      this.complete = in.readByte() != 0;
      this.desc = in.readString();
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
      @Override public DataBean createFromParcel(Parcel source) {
        return new DataBean(source);
      }

      @Override public DataBean[] newArray(int size) {
        return new DataBean[size];
      }
    };
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.config, flags);
    dest.writeList(this.data);
  }

  public MaMaRenwuListBean() {
  }

  protected MaMaRenwuListBean(Parcel in) {
    this.config = in.readParcelable(ConfigBean.class.getClassLoader());
    this.data = new ArrayList<DataBean>();
    in.readList(this.data, DataBean.class.getClassLoader());
  }

  public static final Parcelable.Creator<MaMaRenwuListBean> CREATOR =
      new Parcelable.Creator<MaMaRenwuListBean>() {
        @Override public MaMaRenwuListBean createFromParcel(Parcel source) {
          return new MaMaRenwuListBean(source);
        }

        @Override public MaMaRenwuListBean[] newArray(int size) {
          return new MaMaRenwuListBean[size];
        }
      };
}
