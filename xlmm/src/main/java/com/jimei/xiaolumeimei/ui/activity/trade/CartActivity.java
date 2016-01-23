package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CartsAdapetr;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
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
public class CartActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  CartsModel model = new CartsModel();
  List<String> ids = new ArrayList<>();
  @Bind(R.id.carts_recyclerview) RecyclerView cartsRecyclerview;
  @Bind(R.id.confirm) Button confirmTrade;
  private CartsAdapetr mCartsAdapetr;

  @Override protected void setListener() {
    confirmTrade.setOnClickListener(this);
  }

  @Override protected void initData() {
    model.getCartsList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
          @Override public void onNext(List<CartsinfoBean> cartsinfoBeans) {

            if (cartsinfoBeans != null) {

              mCartsAdapetr.update(cartsinfoBeans);

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
    cartsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

    cartsRecyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    mCartsAdapetr = new CartsAdapetr(this);

    cartsRecyclerview.setAdapter(mCartsAdapetr);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.confirm:
        Intent intent = new Intent(CartActivity.this, CartsPayInfoActivity.class);
        StringBuilder sb = new StringBuilder();
        if (ids.size() > 0) {
          for (int i = 0; i < ids.size(); i++) {
            sb.append(ids.get(i)).append(",");
          }
          String str = new String(sb);

          String s = str.substring(0, str.length() - 1);

          intent.putExtra("ids", s);
          JUtils.Log("CartActivity", s);

          startActivity(intent);
        }

        break;
    }
  }
}
