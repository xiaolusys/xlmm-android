package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PushBoutiqueListAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentPushBoutiqueBinding;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

/**
 * Created by wisdom on 16/11/24.
 */

public class PushBoutiqueFragment extends BaseBindingFragment<FragmentPushBoutiqueBinding> {
    private int page = 1;
    private String next;
    private PushBoutiqueListAdapter adapter;

    public static PushBoutiqueFragment newInstance(String codeLink) {
        Bundle args = new Bundle();
        args.putString("codeLink", codeLink);
        PushBoutiqueFragment fragment = new PushBoutiqueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        loadMore();
    }

    private void loadMore() {
        addSubscription(MamaInfoModel.getInstance()
                .getBoutiqueList(page)
                .subscribe(productListBean -> {
                    if (productListBean != null) {
                        next = productListBean.getNext();
                        page++;
                        adapter.update(productListBean.getResults());
                    }
                    b.recyclerView.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }, e -> {
                    JUtils.Toast("数据加载失败,请刷新重试!");
                    b.recyclerView.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }));
    }


    @Override
    protected void initViews() {
        b.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        b.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        b.recyclerView.setPullRefreshEnabled(false);
        b.recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        adapter = new PushBoutiqueListAdapter(mActivity);
        b.recyclerView.setAdapter(adapter);
        if (getArguments() != null) {
            String codeLink = getArguments().getString("codeLink");
            adapter.setCodeLink(codeLink);
        }
        b.recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    loadMore();
                } else {
                    JUtils.Toast("已经到底啦!");
                    b.recyclerView.loadMoreComplete();
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_push_boutique;
    }

    @Override
    public String getTitle() {
        return "按精品展示";
    }

    @Override
    public View getLoadingView() {
        return b.recyclerView;
    }
}
