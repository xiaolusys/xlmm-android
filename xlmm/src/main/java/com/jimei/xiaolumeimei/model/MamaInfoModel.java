package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import com.squareup.okhttp.ResponseBody;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MamaInfoModel {

  private volatile static MamaInfoModel single_model;
  private MamaInfoModel(){}

  public static MamaInfoModel getInstance(){
    if(single_model == null){
      synchronized (MamaInfoModel.class){
        if(single_model == null){
          single_model = new MamaInfoModel();
        }
      }
    }
    return single_model;
  }

  //得到全部订单数据列表
  public Observable<AgentInfoBean> getAgentInfoBean() {
    return XlmmRetrofitClient.getService()
        .getAgentInfoBean()
        .compose(new DefaultTransform<>());
  }


}
