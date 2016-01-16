package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/14.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartActivity extends BaseSwipeBackCompatActivity {
  CartsModel model = new CartsModel();
  List<String> ids = new ArrayList<>();

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    model.getCartsList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
          @Override public void onNext(List<CartsinfoBean> cartsinfoBeans) {
            if (cartsinfoBeans != null) {
              for (int i = 0; i < cartsinfoBeans.size(); i++) {

                ids.add(cartsinfoBeans.get(i).getId());
                JUtils.Log("ITXUYE", cartsinfoBeans.toString());
              }
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.carts_actvivity;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  public void confirmCarts(View view) {
    Intent intent = new Intent(CartActivity.this, CartsPayInfoActivity.class);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < ids.size(); i++) {
      sb.append(ids.get(i)).append(",");
    }
    String str = new String(sb);

    String s = str.substring(0, str.length() - 1);

    intent.putExtra("ids", s);
    JUtils.Toast(s);

    startActivity(intent);
  }
}
