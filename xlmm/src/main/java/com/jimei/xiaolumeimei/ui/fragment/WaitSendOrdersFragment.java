package com.jimei.xiaolumeimei.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.WaitSendOrdersListAdapter;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WaitSendOrdersFragment extends Fragment {

  String TAG = "WaitSendOrdersFragment";

  @Bind(R.id.all_orders_listview)  ListView all_orders_listview;
  @Bind(R.id.btn_jump)  Button btn_jump;
  @Bind(R.id.rlayout_order_empty)  RelativeLayout rl_empty;

  List<AllOrdersBean.ResultsEntity> list = new ArrayList<>();
  private WaitSendOrdersListAdapter adapter;
  private int page = 2;
  private Subscription subscription1;
  private Subscription subscription2;
  private MaterialDialog materialDialog;

  public static WaitSendOrdersFragment newInstance(String title) {
    WaitSendOrdersFragment allOrdersFragment = new WaitSendOrdersFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    allOrdersFragment.setArguments(bundle);
    return allOrdersFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews(view);
  }

  private void initViews(View view) {


    adapter = new WaitSendOrdersListAdapter(getActivity());
    all_orders_listview.setAdapter(adapter);

    all_orders_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
      //AbsListView view 这个view对象就是listview
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
          if (view.getLastVisiblePosition() == view.getCount() - 1) {
            loadMoreData(page + "", getActivity());
            page++;
          }
        }
      }
      @Override
      public void onScroll(AbsListView view, int firstVisibleItem,
                           int visibleItemCount, int totalItemCount) {
        //lastItem = firstVisibleItem + visibleItemCount - 1 ;
      }

    });

    btn_jump.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
      }
    });
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_allorders, container, false);
     ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  private void loadMoreData(String page, Context context) {

    subscription2 = TradeModel.getInstance()
            .getWaitSendOrdersBean(page)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<AllOrdersBean>() {
              @Override public void onNext(AllOrdersBean allOrdersBean) {
                if (allOrdersBean != null) {

                  adapter.update(allOrdersBean.getResults());
                  if (null == allOrdersBean.getNext()) {
                    Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();

                  }
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();

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

  @Override
  public void onResume() {
    super.onResume();
    adapter.clearAll();
    showIndeterminateProgressDialog(false);
    subscription1 = TradeModel.getInstance()
            .getWaitSendOrdersBean("1")
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<AllOrdersBean>() {
              @Override public void onNext(AllOrdersBean allOrdersBean) {
                if(allOrdersBean != null) {
                  List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
                  JUtils.Log(TAG, "results.size()=" + results.size());
                  if (0 == results.size()) {
                    rl_empty.setVisibility(View.VISIBLE);
                  } else {
                    list.addAll(results);
                    adapter.update(results);
                  }

                  Log.i(TAG, allOrdersBean.toString());
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
                hideIndeterminateProgressDialog();
              }

              @Override public void onError(Throwable e) {

                Log.e(TAG, " error:, " + e.toString());
                super.onError(e);
              }
            });
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
}
