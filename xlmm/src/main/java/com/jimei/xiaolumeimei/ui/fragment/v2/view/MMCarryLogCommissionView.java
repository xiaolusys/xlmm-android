package com.jimei.xiaolumeimei.ui.fragment.v2.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CarryLogAllAdapter;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.ui.fragment.view.ViewImpl;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMCarryLogCommissionView extends ViewImpl {
  @Bind(R.id.carrylogall_xry) XRecyclerView xRecyclerView;
  private CarryLogAllAdapter adapter;
  private int page = 2;
  private Subscription subscription1;
  private Subscription subscription2;

  @Override public int getLayoutId() {
    return R.layout.fragment_carrylogall;
  }

  public void initViews(Fragment fragment, Context context) {

    xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    xRecyclerView.addItemDecoration(
        new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    xRecyclerView.setPullRefreshEnabled(false);
    xRecyclerView.setLoadingMoreEnabled(true);

    adapter = new CarryLogAllAdapter(context);
    xRecyclerView.setAdapter(adapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "", context);
        page++;
      }
    });

    subscription1 = MMProductModel.getInstance()
        .getCarryLogList("1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CarryLogListBean>() {

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(CarryLogListBean carryLogListBean) {
            if (carryLogListBean != null) {

              adapter.update(carryLogListBean.getResults());
            }
          }
        });
  }

  private void loadMoreData(String page, Context context) {

    subscription2 = MMProductModel.getInstance()
        .getCarryLogList(page)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CarryLogListBean>() {
          @Override public void onNext(CarryLogListBean carryLogListBean) {
            if (carryLogListBean != null) {
              if (null != carryLogListBean.getNext()) {
                adapter.update(carryLogListBean.getResults());
              } else {
                Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                xRecyclerView.post(xRecyclerView::loadMoreComplete);
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xRecyclerView.post(xRecyclerView::loadMoreComplete);
          }
        });
  }

  @Override public void destroy() {
    ButterKnife.unbind(this);
    if (subscription1 != null && subscription1.isUnsubscribed()) {
      subscription1.unsubscribe();
    }
    if (subscription2 != null && subscription2.isUnsubscribed()) {
      subscription2.unsubscribe();
    }
  }
}
