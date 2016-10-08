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
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaFansAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
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
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMFansFragment extends BaseFragment {
    @Bind(R.id.xrv_mmvisitors)
    XRecyclerView xrvMmvisitors;
    private int pageNext = 2;
    private MamaFansAdapter mAdapter;

    List<MamaFansBean.ResultsEntity> list = new ArrayList<>();

    private Subscription subscription1;
    private Subscription subscription2;
    private Activity mActivity;
    private XlmmLoadingDialog loadingdialog;

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
                .subscribeOn(Schedulers.io())
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
                                xrvMmvisitors.setLoadingMoreEnabled(false);
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
        xrvMmvisitors.setLayoutManager(new LinearLayoutManager(mActivity));
        xrvMmvisitors.addItemDecoration(
                new DividerItemDecorationForFooter(mActivity, DividerItemDecoration.VERTICAL_LIST));
        xrvMmvisitors.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xrvMmvisitors.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xrvMmvisitors.setArrowImageView(R.drawable.iconfont_downgrey);
        xrvMmvisitors.setPullRefreshEnabled(false);
        xrvMmvisitors.setLoadingMoreEnabled(true);
        mAdapter = new MamaFansAdapter(mActivity);
        xrvMmvisitors.setAdapter(mAdapter);

        xrvMmvisitors.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(pageNext + "");
                pageNext++;
                JUtils.Log("fansXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", pageNext + "");
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
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MamaFansBean>() {
                    @Override
                    public void onNext(MamaFansBean fansBeen) {
                        super.onNext(fansBeen);
                        if (fansBeen != null) {
                            mAdapter.update(fansBeen.getResults());
                            if (null == fansBeen.getNext()) {
                                Toast.makeText(mActivity, "没有更多了", Toast.LENGTH_SHORT).show();
                                xrvMmvisitors.loadMoreComplete();
                                xrvMmvisitors.setLoadingMoreEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        xrvMmvisitors.loadMoreComplete();
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
        return xrvMmvisitors;
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
