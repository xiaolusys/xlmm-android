package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BrandActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 16/5/3.
 */
public class BrandListActivity extends BaseSwipeBackCompatActivity {

  private BrandActivityAdapter mBrandActivityAdapter;
  private String id;
  private XRecyclerView xRecyclerView;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    Subscription subscribe = ActivityModel.getInstance()
        .getBrandList(id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<BrandListBean>() {
          @Override public void onNext(BrandListBean productListBean) {

            try {
              if (productListBean != null) {
                mBrandActivityAdapter.update(productListBean.getProducts());
              }
            } catch (Exception ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }
        });
    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {
    id = extras.getString("id");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activty_brand;
  }

  @Override protected void initViews() {
    initRecyclerView();
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) findViewById(R.id.childlist_recyclerView);
    xRecyclerView.setLayoutManager(new GridLayoutManager(BrandListActivity.this, 2));
    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
    xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    xRecyclerView.setLoadingMoreEnabled(false);
    xRecyclerView.setPullRefreshEnabled(false);
    mBrandActivityAdapter = new BrandActivityAdapter(this);
    xRecyclerView.setAdapter(mBrandActivityAdapter);
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }
}
