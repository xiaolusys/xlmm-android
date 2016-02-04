package com.jimei.xiaolumeimei.data;

/**
 * Created by wulei on 2016/2/4.
 */
public class FilePara {
  int width;
  int height;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override public String toString() {
    return "FilePara{" +
        "width=" + width +
        ", height=" + height +
        '}';
  }
}
