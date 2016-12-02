package com.jimei.xiaolumeimei.ui.fragment.main;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentBoutiqueBinding;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;

import java.util.List;

/**
 * Created by wisdom on 16/12/2.
 */

public class BoutiqueFragment extends BaseBindingFragment<FragmentBoutiqueBinding> implements View.OnClickListener {

    private ProductListAdapter mProductListAdapter;
    private String next;
    private String order_by;
    private int page;

    public static BoutiqueFragment newInstance() {
        return new BoutiqueFragment();
    }


    @Override
    public void initData() {
        order_by = "";
        refreshData(true);
    }

    @Override
    protected void initViews() {
        GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setPullRefreshEnabled(false);
        mProductListAdapter = new ProductListAdapter(mActivity);
        b.xrv.setAdapter(mProductListAdapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
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
        if (clear) {
            showIndeterminateProgressDialog(false);
            mProductListAdapter.clear();
            page = 1;
        }
        addSubscription(ProductModel.getInstance()
                .getBoutiqueList(page, order_by)
                .subscribe(bean -> {
                            List<ProductListBean.ResultsBean> results = bean.getResults();
                            if (results != null && results.size() > 0) {
                                mProductListAdapter.update(results);
                            }
                            next = bean.getNext();
                            if (next != null && !"".equals(next)) {
                                page++;
                            }
                            hideIndeterminateProgressDialog();
                            b.xrv.loadMoreComplete();
                        }, e -> {
                            hideIndeterminateProgressDialog();
                            b.xrv.loadMoreComplete();
                            JUtils.Toast("数据加载有误!");
                        }
                ));
    }

    @Override
    public void setListener() {
        b.sortDefault.setOnClickListener(this);
        b.sortPrice.setOnClickListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_boutique;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sort_default:
                b.sortDefault.setClickable(false);
                b.sortPrice.setClickable(true);
                b.sortDefault.setTextColor(getResources().getColor(R.color.colorAccent));
                b.sortPrice.setTextColor(getResources().getColor(R.color.text_color_4A));
                order_by = "";
                refreshData(true);
                break;
            case R.id.sort_price:
                b.sortPrice.setClickable(false);
                b.sortDefault.setClickable(true);
                b.sortPrice.setTextColor(getResources().getColor(R.color.colorAccent));
                b.sortDefault.setTextColor(getResources().getColor(R.color.text_color_4A));
                order_by = "price";
                refreshData(true);
                break;
        }
    }
}
