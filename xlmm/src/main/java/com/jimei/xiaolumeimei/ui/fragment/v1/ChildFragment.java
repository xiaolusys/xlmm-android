package com.jimei.xiaolumeimei.ui.fragment.v1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.jimei.xiaolumeimei.adapter.ChildListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ChildFragment extends BaseFragment {


    @Bind(R.id.childlist_recyclerView)
    XRecyclerView xRecyclerView;
    List<ChildListBean.ResultsEntity> list = new ArrayList<>();
    private int page = 2;
    int page_size = 10;
    private int totalPages;//总的分页数
    private ChildListAdapter mChildListAdapter;
    //private TextView mNormal, mOrder;
    private Subscription subscribe1;
    private Subscription subscribe2;
    private Subscription subscribe3;
    private MaterialDialog materialDialog;
    private XlmmLoadingDialog loadingdialog;

    public static ChildFragment newInstance(String title) {
        ChildFragment todayFragment = new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", title);
        todayFragment.setArguments(bundle);
        return todayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.childlist_fragment, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    protected void initData() {
        load();
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    private void load() {
        showIndeterminateProgressDialog(false);
        subscribe1 = ProductModel.getInstance()
                .getChildList(1, 10)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ChildListBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                        JUtils.Toast("请检查网络状况,尝试下拉刷新");
                    }

                    @Override
                    public void onNext(ChildListBean childListBean) {

                        try {

                            if (childListBean != null) {
                                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                                totalPages = childListBean.getCount() / page_size;
                                list.addAll(results);
                                mChildListAdapter.update(list);
                            }
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        //loading.post(loading::stop);
                        hideIndeterminateProgressDialog();
                    }
                });
    }


    private void initViews() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        //manager.setOrientation(GridLayoutManager.VERTICAL);
        //manager.setSmoothScrollbarEnabled(true);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setPullRefreshEnabled(false);
        mChildListAdapter = new ChildListAdapter(ChildFragment.this, getActivity());
        xRecyclerView.setAdapter(mChildListAdapter);

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void loadMoreData(int page, int page_size) {

        subscribe3 = ProductModel.getInstance()
                .getChildList(page, page_size)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ChildListBean>() {
                    @Override
                    public void onNext(ChildListBean productListBean) {
                        List<ChildListBean.ResultsEntity> results = productListBean.getResults();
                        mChildListAdapter.update(results);
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
