package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class PostBean {

  public String id;
  public String url;
  public List<poster> wem_posters;
  public List<poster> chd_posters;
  String active_time;

  public List<poster> getWem_posters() {
    return wem_posters;
  }

  public List<poster> getChd_posters() {
    return chd_posters;
  }

  public class poster {
    public String item_link;
    public String pic_link;
    public List<String> subject;
  }
}
