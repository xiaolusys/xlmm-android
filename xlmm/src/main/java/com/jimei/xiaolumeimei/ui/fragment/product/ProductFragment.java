package com.jimei.xiaolumeimei.ui.fragment.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentProductBinding;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;

import java.util.List;

/**
 * Created by wisdom on 17/2/13.
 */

public class ProductFragment extends BaseBindingFragment<FragmentProductBinding> {

    private int type;
    private int page;

    private ProductListAdapter mProductListAdapter;
    private String next;


    public static ProductFragment newInstance(int type, String title) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("title", title);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View getLoadingView() {
        return b.xrv;
    }

    @Override
    public void initData() {
        page = 1;
        refreshData(true);
    }

    @Override
    protected void initViews() {
        GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        mProductListAdapter = new ProductListAdapter(mActivity);
        b.xrv.setAdapter(mProductListAdapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                refreshData(true);
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    refreshData(false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    b.xrv.loadMoreComplete();
                }
            }
        });
    }

    public void refreshData(boolean clear) {
        b.emptyLayout.setVisibility(View.GONE);
        if (clear) {
            showIndeterminateProgressDialog(false);
            mProductListAdapter.clear();
            page = 1;
        }
        addSubscription(ProductModel.getInstance()
            .getCategoryProductList(type, page)
            .subscribe(bean -> {
                    List<ProductListBean.ResultsBean> results = bean.getResults();
                    if (results != null && results.size() > 0) {
                        mProductListAdapter.update(results);
                    } else {
                        b.emptyLayout.setVisibility(View.VISIBLE);
                    }
                    next = bean.getNext();
                    if (next != null && !"".equals(next)) {
                        page++;
                    }
                    hideIndeterminateProgressDialog();
                    b.xrv.loadMoreComplete();
                    b.xrv.refreshComplete();
                }, e -> {
                    hideIndeterminateProgressDialog();
                    b.xrv.loadMoreComplete();
                    b.xrv.refreshComplete();
                    JUtils.Toast("数据加载有误!");
                }
            ));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_product;
    }
}
