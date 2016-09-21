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

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.OderCarryLogAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.schedulers.Schedulers;


/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class CarryLogCommissionFragment extends BaseFragment {
  List<OderCarryBean.ResultsEntity> list = new ArrayList<>();
  @Bind(R.id.carrylogall_xry) XRecyclerView xRecyclerView;
  private OderCarryLogAdapter adapter;
  private int page = 2;
  private Subscription subscription1;
  private Subscription subscription2;
  private XlmmLoadingDialog loadingdialog;

  public static CarryLogCommissionFragment newInstance(String title) {
    CarryLogCommissionFragment carryLogAllFragment = new CarryLogCommissionFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    carryLogAllFragment.setArguments(bundle);
    return carryLogAllFragment;
  }

  @Override public void onDetach() {
    super.onDetach();
    try {
      Field childFragmentManager =
          Fragment.class.getDeclaredField("mChildFragmentManager");
      childFragmentManager.setAccessible(true);
      childFragmentManager.set(this, null);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser && list.size() == 0) {
      load();
    }
  }

  @Override
  protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return null;
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void setDefaultFragmentTitle(String title) {

  }

  private void load() {
    showIndeterminateProgressDialog(false);
    subscription1 = MMProductModel.getInstance()
        .getMamaAllOderCarryLogs("1")
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

          @Override public void onNext(OderCarryBean carryLogListBean) {
            if (carryLogListBean != null) {
              list.addAll(carryLogListBean.getResults());
              adapter.update(list);
              if (null == carryLogListBean.getNext()) {
                xRecyclerView.setLoadingMoreEnabled(false);
              }
              JUtils.Log("carrylog", carryLogListBean.toString());
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
    xRecyclerView.addItemDecoration(
            new DividerItemDecorationForFooter(getActivity(), DividerItemDecorationForFooter.VERTICAL_LIST));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    xRecyclerView.setPullRefreshEnabled(false);
    xRecyclerView.setLoadingMoreEnabled(true);

    adapter = new OderCarryLogAdapter(getActivity());
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
        .getMamaAllOderCarryLogs(page)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<OderCarryBean>() {
          @Override public void onNext(OderCarryBean carryLogListBean) {
            if (carryLogListBean != null) {
              if (null != carryLogListBean.getResults()) {
                adapter.update(carryLogListBean.getResults());
              }
              if (null != carryLogListBean.getNext()) {
              } else {
                Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                xRecyclerView.post(xRecyclerView::loadMoreComplete);
                xRecyclerView.setLoadingMoreEnabled(false);;
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            try {
              xRecyclerView.post(xRecyclerView::loadMoreComplete);
            } catch (NullPointerException e) {
              e.printStackTrace();
            }
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

  public void showIndeterminateProgressDialog(boolean horizontal) {
    loadingdialog = XlmmLoadingDialog.create(activity)
        .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
        .setCancellable(!horizontal)
        .show();
  }

  public void hideIndeterminateProgressDialog() {
    try {
      loadingdialog.dismiss();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public View getScrollableView() {
    return xRecyclerView;
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
  }
}
