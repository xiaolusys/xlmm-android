package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.Bind;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllowanceAdapter;
import com.jimei.xiaolumeimei.adapter.WithdrawCashHisAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class ShareAllowanceActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "ShareAllowanceActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lv_allowance) XRecyclerView lv_allowance;

  private int page = 2;
  private AllowanceAdapter mAdapter;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_shareallowance;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    initRecyclerView();
  }

  private void initRecyclerView() {
    lv_allowance.setLayoutManager(new LinearLayoutManager(this));
    lv_allowance.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    lv_allowance.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    lv_allowance.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    lv_allowance.setArrowImageView(R.drawable.iconfont_downgrey);
    lv_allowance.setPullRefreshEnabled(false);
    lv_allowance.setPullRefreshEnabled(false);

    mAdapter = new AllowanceAdapter(this);
    lv_allowance.setAdapter(mAdapter);

    lv_allowance.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }

      private void loadMoreData(String page) {
        MamaInfoModel.getInstance()
            .getAllowance(page)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<AllowanceBean>() {
              @Override public void onNext(AllowanceBean shoppingListBean) {
                super.onNext(shoppingListBean);
                if (shoppingListBean != null) {
                  if (null != shoppingListBean.getNext()) {
                    mAdapter.update(shoppingListBean.getAllowances());
                  } else {
                    Toast.makeText(ShareAllowanceActivity.this, "没有更多了",
                        Toast.LENGTH_SHORT).show();
                    lv_allowance.post(lv_allowance::loadMoreComplete);
                  }
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
                lv_allowance.post(lv_allowance::loadMoreComplete);
              }
            });
      }
    });
  }
  @Override protected void initData() {
    MamaInfoModel.getInstance().getAllowance("1")
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AllowanceBean>() {
          @Override public void onNext(AllowanceBean pointBean) {
            JUtils.Log(TAG,"AllowanceBean="+ pointBean.toString());
            List<AllowanceBean.AllowanceEntity> results = pointBean.getAllowances();

            if (0 == results.size()) {
              JUtils.Log(TAG, "results.size()=0");

            } else {
              mAdapter.update(results);
            }
          }
        });


  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {


    }
  }


}