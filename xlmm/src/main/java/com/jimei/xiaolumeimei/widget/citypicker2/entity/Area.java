package com.jimei.xiaolumeimei.widget.citypicker2.entity;

public class Area {
  private String code;
  private String name;
  private String pcode;

  public Area() {
    super();
    // TODO Auto-generated constructor stub
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPcode() {
    return pcode;
  }

  public void setPcode(String pcode) {
    this.pcode = pcode;
  }

  @Override public String toString() {
    return "Area [code=" + code + ", name=" + name + ", pcode=" + pcode + "]";
  }
}
