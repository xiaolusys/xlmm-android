package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ShoppingListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMShoppingListActivity extends BaseSwipeBackCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tv_count) TextView tvCount;
  @Bind(R.id.shoppinglist_xry) XRecyclerView shoppinglistXry;
  private int page = 2;
  private ShoppingListAdapter adapter;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    Subscription subscribe = MMProductModel.getInstance()
        .getMamaAllOderCarryLogs("direct", "1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<OderCarryBean>() {

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(OderCarryBean shoppingListBean) {
            super.onNext(shoppingListBean);
            if (shoppingListBean != null) {
              int count = shoppingListBean.getCount();
              tvCount.setText("" + count);
              adapter.update(shoppingListBean.getResults());
            }
          }
        });
    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mmshoppinglist;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    initRecyclerView();
  }

  private void initRecyclerView() {

    shoppinglistXry.setLayoutManager(new LinearLayoutManager(this));
    //shoppinglistXry.addItemDecoration(
    //    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    shoppinglistXry.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    shoppinglistXry.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    shoppinglistXry.setArrowImageView(R.drawable.iconfont_downgrey);
    shoppinglistXry.setPullRefreshEnabled(false);
    shoppinglistXry.setLoadingMoreEnabled(true);

    adapter = new ShoppingListAdapter(this);
    shoppinglistXry.setAdapter(adapter);

    shoppinglistXry.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }
    });
  }

  private void loadMoreData(String page) {
    Subscription subscribe = MMProductModel.getInstance()
        .getMamaAllOderCarryLogs("direct", page)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<OderCarryBean>() {
          @Override public void onNext(OderCarryBean shoppingListBean) {
            super.onNext(shoppingListBean);
            if (shoppingListBean != null) {
              if (null != shoppingListBean.getNext()) {
                adapter.update(shoppingListBean.getResults());
              } else {
                Toast.makeText(MMShoppingListActivity.this, "没有更多了", Toast.LENGTH_SHORT)
                    .show();
                shoppinglistXry.post(shoppinglistXry::loadMoreComplete);
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            shoppinglistXry.post(shoppinglistXry::loadMoreComplete);
          }
        });
    addSubscription(subscribe);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onStop() {
    super.onStop();
  }
}
