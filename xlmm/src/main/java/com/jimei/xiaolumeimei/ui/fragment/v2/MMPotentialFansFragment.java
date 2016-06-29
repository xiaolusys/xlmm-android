package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.app.Activity;
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
import com.afollestad.materialdialogs.MaterialDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaPotentialFansAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.PotentialFans;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMPotentialFansFragment extends BaseFragment {
  @Bind(R.id.xrv_mmvisitors) XRecyclerView xrvMmvisitors;
  private int page = 2;
  private MamaPotentialFansAdapter mAdapter;

  List<PotentialFans.ResultsBean> list = new ArrayList<>();

  private Subscription subscription1;
  private Subscription subscription2;
  private MaterialDialog materialDialog;
  private Activity mActivity;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (Activity) context;
  }

  public static MMPotentialFansFragment newInstance(String title) {
    MMPotentialFansFragment mmFansFragment = new MMPotentialFansFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    mmFansFragment.setArguments(bundle);
    return mmFansFragment;
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

  @Override protected View initViews(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return null;
  }

  @Override protected void initData() {

  }

  @Override protected void setDefaultFragmentTitle(String title) {

  }

  private void load() {
    showIndeterminateProgressDialog(false);
    subscription1 = MamaInfoModel.getInstance()
        .getPotentialFans("1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PotentialFans>() {
          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(PotentialFans fansBeen) {
            if (fansBeen != null) {
              if (0 == fansBeen.getCount()) {
              } else {
                list.addAll(fansBeen.getResults());
                mAdapter.update(list);
              }
              if (null == fansBeen.getNext()) {
                xrvMmvisitors.setLoadingMoreEnabled(false);
              }
            }
          }
        });
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews(view);
  }

  private void initViews(View view) {
    xrvMmvisitors.setLayoutManager(new LinearLayoutManager(mActivity));
    xrvMmvisitors.addItemDecoration(
        new DividerItemDecorationForFooter(mActivity, DividerItemDecoration.VERTICAL_LIST));
    xrvMmvisitors.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xrvMmvisitors.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xrvMmvisitors.setArrowImageView(R.drawable.iconfont_downgrey);
    xrvMmvisitors.setPullRefreshEnabled(false);
    xrvMmvisitors.setLoadingMoreEnabled(true);
    mAdapter = new MamaPotentialFansAdapter(mActivity);
    xrvMmvisitors.setAdapter(mAdapter);

    xrvMmvisitors.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }
    });
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_mmfans, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  private void loadMoreData(String page) {
    subscription2 = MamaInfoModel.getInstance()
        .getPotentialFans(page)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PotentialFans>() {
          @Override public void onNext(PotentialFans fansBeen) {
            super.onNext(fansBeen);
            if (fansBeen != null) {
              mAdapter.update(fansBeen.getResults());
              if (null != fansBeen.getNext()) {
              } else {
                Toast.makeText(mActivity, "没有更多了", Toast.LENGTH_SHORT).show();
                xrvMmvisitors.post(xrvMmvisitors::loadMoreComplete);
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xrvMmvisitors.post(xrvMmvisitors::loadMoreComplete);
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
    materialDialog = new MaterialDialog.Builder(getActivity())

        //.title(R.string.progress_dialog)
        .content(R.string.please_wait)
        .progress(true, 0)
        .widgetColorRes(R.color.colorAccent)
        .progressIndeterminateStyle(horizontal)
        .show();
  }

  public void hideIndeterminateProgressDialog() {
    try {
      materialDialog.dismiss();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    try {
      Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
      childFragmentManager.setAccessible(true);
      childFragmentManager.set(this, null);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public View getScrollableView() {
    return xrvMmvisitors;
  }

  @Override public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
  }

  @Override public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
  }
}
