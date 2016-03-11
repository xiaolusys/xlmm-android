package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CarryLogAllAdapter;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class CarryLogCommissionFragment
    extends Fragment {

  public static CarryLogCommissionFragment newInstance(String title) {
    CarryLogCommissionFragment carryLogAllFragment = new CarryLogCommissionFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    carryLogAllFragment.setArguments(bundle);
    return carryLogAllFragment;
  }

  @Bind(R.id.carrylogall_xry) XRecyclerView xRecyclerView;
  private CarryLogAllAdapter adapter;
  private int page = 2;
  private Subscription subscription1;
  private Subscription subscription2;



  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser) {
      load();
    }
  }

  private void load() {
    subscription1 = MMProductModel.getInstance()
        .getMamaAllCarryLogs("1")
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
              JUtils.Log("carrylog",carryLogListBean.toString());
            }
          }
        });
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews();
  }

  private void initViews() {
    xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    //xRecyclerView.addItemDecoration(
    //    new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    xRecyclerView.setPullRefreshEnabled(false);
    xRecyclerView.setLoadingMoreEnabled(true);

    adapter = new CarryLogAllAdapter(getActivity());
    xRecyclerView.setAdapter(adapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "", getActivity());
        page++;
      }
    });
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_carrylogall, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  private void loadMoreData(String page, Context context) {

    subscription2 = MMProductModel.getInstance()
        .getMamaAllCarryLogs(page)
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

  @Override public void onStop() {
    super.onStop();
    if (subscription1 != null && subscription1.isUnsubscribed()) {
      subscription1.unsubscribe();
    }
    if (subscription2 != null && subscription2.isUnsubscribed()) {
      subscription2.unsubscribe();
    }
  }
}
