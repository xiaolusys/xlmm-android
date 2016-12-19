package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaFansAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMFansFragment extends BaseLazyFragment {
    @Bind(R.id.xrv_mama_fans)
    XRecyclerView xrvMamaFans;
    private int pageNext = 2;
    private MamaFansAdapter mAdapter;

    public static MMFansFragment newInstance(String title) {
        MMFansFragment mmFansFragment = new MMFansFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        mmFansFragment.setArguments(bundle);
        return mmFansFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaFans("1")
                .subscribe(new ServiceResponse<MamaFansBean>() {
                    @Override
                    public void onCompleted() {
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MamaFansBean fansBeen) {
                        if (fansBeen != null) {
                            if (0 != fansBeen.getCount()) {
                                mAdapter.updateWithClear(fansBeen.getResults());
                            }
                            if (null == fansBeen.getNext()) {
                                xrvMamaFans.setLoadingMoreEnabled(false);
                            }
                        }
                    }
                }));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mmfans;
    }

    @Override
    protected void initViews() {
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

    private void loadMoreData(String page) {
        addSubscription(MamaInfoModel.getInstance()
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
                }));
    }

    @Override
    public View getScrollableView() {
        return xrvMamaFans;
    }

    @Override
    public View getLoadingView() {
        return xrvMamaFans;
    }
}
