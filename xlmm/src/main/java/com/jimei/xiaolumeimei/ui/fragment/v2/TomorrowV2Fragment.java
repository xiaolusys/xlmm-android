package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TodayAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/16.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class TomorrowV2Fragment extends BaseFragment {

    @Bind(R.id.xrcy_tomorrorv2)
    XRecyclerView xRecyclerView;
    int page_size = 10;
    private List<ProductListBean.ResultsEntity> list = new ArrayList<>();
    private MaterialDialog materialDialog;
    private int page = 2;
    private int totalPages;//总的分页数
    private TodayAdapter mTodayAdapter;
    private Subscription subscribe1;

    private Subscription subscribe3;
    private View head;
    private View view;
    private Subscription subscribe2;
    private CountdownView countTime;

    public static TomorrowV2Fragment newInstance(String title) {
        TomorrowV2Fragment tomorrowV2Fragment = new TomorrowV2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", title);
        tomorrowV2Fragment.setArguments(bundle);
        return tomorrowV2Fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tomorrorv2, container, false);
        ButterKnife.bind(this, view);
        initViews();

        return view;
    }
    private long calcLeftTime() {
        Date now = new Date();
        Date nextDay14PM = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextDay14PM);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        nextDay14PM = calendar.getTime();
        long left;
        if (nextDay14PM.getTime() - now.getTime() > 0) {
            left = nextDay14PM.getTime() - now.getTime();
            return left;
        } else {
            return 0;
        }
    }
    @Override
    protected void initData() {
        load();
        subscribe2 = Observable.timer(1, 1, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .map(aLong -> calcLeftTime())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override public void call(Long aLong) {
                        if (aLong > 0) {
                            countTime.updateShow(aLong);
                        } else {
                            countTime.setVisibility(View.INVISIBLE);
                        }
                    }
                }, Throwable::printStackTrace);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    private void load() {
        showIndeterminateProgressDialog(false);
        subscribe1 = ProductModel.getInstance()
                .getAdvanceList(1, 10)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ProductListBean productListBean) {
                        try {
                            if (productListBean != null) {
                                List<ProductListBean.ResultsEntity> results =
                                        productListBean.getResults();
                                totalPages = productListBean.getCount() / page_size;
                                list.addAll(results);
                                mTodayAdapter.update(list);
                            }
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        head = LayoutInflater.from(getActivity())
                .inflate(R.layout.today_poster_header,
                        (ViewGroup) view.findViewById(R.id.head_today), false);

        countTime = (CountdownView) head.findViewById(R.id.countTime);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        //manager.setOrientation(GridLayoutManager.VERTICAL);
        //manager.setSmoothScrollbarEnabled(true);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setPullRefreshEnabled(false);
        mTodayAdapter = new TodayAdapter(this, getActivity());
        xRecyclerView.addHeaderView(head);

        xRecyclerView.setAdapter(mTodayAdapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //subscribe2 = ProductModel.getInstance()
                //    .getChildList(1, page * page_size)
                //    .subscribeOn(Schedulers.io())
                //    .subscribe(new ServiceResponse<ChildListBean>() {
                //      @Override public void onNext(ChildListBean childListBean) {
                //        List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                //        mChildListAdapter.updateWithClear(results);
                //      }
                //
                //      @Override public void onCompleted() {
                //        super.onCompleted();
                //        try {
                //          xRecyclerView.post(xRecyclerView::refreshComplete);
                //        } catch (Exception e) {
                //          e.printStackTrace();
                //        }
                //      }
                //    });
            }

            @Override
            public void onLoadMore() {
                if (page <= totalPages) {
                    loadMoreData(page, 10);
                    page++;
                } else {
                    Toast.makeText(getActivity(), "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                }
            }
        });
    }

    private void loadMoreData(int page, int page_size) {

        subscribe3 = ProductModel.getInstance()
                .getAdvanceList(page, page_size)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        List<ProductListBean.ResultsEntity> results = productListBean.getResults();
                        mTodayAdapter.update(results);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        try {
                            xRecyclerView.post(xRecyclerView::loadMoreComplete);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDetach() {
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

    @Override
    public View getScrollableView() {
        return xRecyclerView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscribe1 != null && subscribe1.isUnsubscribed()) {
            subscribe1.unsubscribe();
        }
        if (subscribe2 != null && subscribe2.isUnsubscribed()) {
            subscribe2.unsubscribe();
        }
        if (subscribe3 != null && subscribe3.isUnsubscribed()) {
            subscribe3.unsubscribe();
        }
    }


}
