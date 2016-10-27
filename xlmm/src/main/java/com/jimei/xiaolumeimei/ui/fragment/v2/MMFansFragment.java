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

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaFansAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMFansFragment extends BaseFragment {
    @Bind(R.id.xrv_mama_fans)
    XRecyclerView xrvMamaFans;
    private int pageNext = 2;
    private MamaFansAdapter mAdapter;

    List<MamaFansBean.ResultsEntity> list = new ArrayList<>();

    private Subscription subscription1;
    private Subscription subscription2;
    private Activity mActivity;
    private XlmmLoadingDialog loadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    public static MMFansFragment newInstance(String title) {
        MMFansFragment mmFansFragment = new MMFansFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", title);
        mmFansFragment.setArguments(bundle);
        return mmFansFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && list.size() == 0) {
            load();
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        subscription1 = MamaInfoModel.getInstance()
                .getMamaFans("1")
                .subscribe(new ServiceResponse<MamaFansBean>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MamaFansBean fansBeen) {
                        if (fansBeen != null) {
                            if (0 != fansBeen.getCount()) {
                                list.addAll(fansBeen.getResults());
                                mAdapter.update(list);
                            }
                            if (null == fansBeen.getNext()) {
                                xrvMamaFans.setLoadingMoreEnabled(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        xrvMamaFans.setLayoutManager(new LinearLayoutManager(mActivity));
        xrvMamaFans.addItemDecoration(
                new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        xrvMamaFans.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xrvMamaFans.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xrvMamaFans.setArrowImageView(R.drawable.iconfont_downgrey);
        xrvMamaFans.setPullRefreshEnabled(false);
        xrvMamaFans.setLoadingMoreEnabled(true);
        mAdapter = new MamaFansAdapter(mActivity);
        xrvMamaFans.setAdapter(mAdapter);

        xrvMamaFans.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(pageNext + "");
                pageNext++;
                JUtils.Log("fans", pageNext + "");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mmfans, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadMoreData(String page) {
        subscription2 = MamaInfoModel.getInstance()
                .getMamaFans(page)
                .subscribe(new ServiceResponse<MamaFansBean>() {
                    @Override
                    public void onNext(MamaFansBean fansBeen) {
                        super.onNext(fansBeen);
                        if (fansBeen != null) {
                            mAdapter.update(fansBeen.getResults());
                            if (null == fansBeen.getNext()) {
                                Toast.makeText(mActivity, "没有更多了", Toast.LENGTH_SHORT).show();
                                xrvMamaFans.loadMoreComplete();
                                xrvMamaFans.setLoadingMoreEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        xrvMamaFans.loadMoreComplete();
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscription1 != null && subscription1.isUnsubscribed()) {
            subscription1.unsubscribe();
        }
        if (subscription2 != null && subscription2.isUnsubscribed()) {
            subscription2.unsubscribe();
        }
    }

    public void showIndeterminateProgressDialog(boolean horizontal) {
        loadingDialog = XlmmLoadingDialog.create(activity)
                .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
                .setCancellable(!horizontal)
                .show();
    }

    public void hideIndeterminateProgressDialog() {
        try {
            loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
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

    @Override
    public View getScrollableView() {
        return xrvMamaFans;
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
