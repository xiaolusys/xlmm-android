package com.jimei.xiaolumeimei.ui.fragment.main;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MainActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentBoutiqueListBinding;
import com.jimei.xiaolumeimei.model.MainModel;

import java.net.UnknownHostException;

/**
 * Created by wisdom on 16/12/9.
 */

public class BoutiqueTabFragment extends BaseBindingFragment<FragmentBoutiqueListBinding> {

    private MainActivityAdapter adapter;

    public static BoutiqueTabFragment newInstance() {
        return new BoutiqueTabFragment();
    }

    @Override
    public View getLoadingView() {
        return b.xrv;
    }

    @Override
    public void initData() {
        addSubscription(MainModel.getInstance()
                .getBoutiqueActivitys()
                .subscribe(portalBean -> {
                    if (portalBean != null && portalBean.getActivitys() != null) {
                        adapter.updateWithClear(portalBean.getActivitys());
                    }
                    hideIndeterminateProgressDialog();
                }, e -> {
                    hideIndeterminateProgressDialog();
                    if (e instanceof UnknownHostException) {
                        showNetworkError();
                    } else {
                        JUtils.Toast("数据加载有误,请下拉刷新!");
                    }
                }));


    }

    @Override
    protected void initViews() {
        b.xrv.setLayoutManager(new LinearLayoutManager(mActivity));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setPullRefreshEnabled(false);
        b.xrv.setLoadingMoreEnabled(false);
        b.xrv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 12));
        adapter = new MainActivityAdapter(mActivity);
        b.xrv.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_boutique_list;
    }
}
