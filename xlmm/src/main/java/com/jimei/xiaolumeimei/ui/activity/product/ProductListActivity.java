package com.jimei.xiaolumeimei.ui.activity.product;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityProductListBinding;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

public class ProductListActivity extends BaseMVVMActivity<ActivityProductListBinding> implements View.OnClickListener {

    private String type;
    private String title;
    private int page;

    private ProductListAdapter mProductListAdapter;
    private String cid;
    private String next;
    private String order_by;

    @Override
    protected void setListener() {
        b.sortDefault.setOnClickListener(this);
        b.sortPrice.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        page = 1;
        order_by = "";
        refreshData(type, true);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        type = extras.getString("type");
        title = extras.getString("title");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_product_list;
    }

    @Override
    public void getIntentUrl(Uri uri) {
        if (uri.getPath().contains("childlist")) {
            type = XlmmConst.TYPE_CHILD;
            title = "萌娃专区";
        } else if (uri.getPath().contains("ladylist")) {
            type = XlmmConst.TYPE_LADY;
            title = "女装专区";
        } else if (uri.getPath().contains("category")) {
            type = uri.getQueryParameter("cid");
            title = uri.getQueryParameter("title");
        }
    }

    @Override
    protected void initViews() {
        b.title.setName(title);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        mProductListAdapter = new ProductListAdapter(this);
        b.xrv.setAdapter(mProductListAdapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                refreshData(type, true);
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    refreshData(cid, false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    b.xrv.loadMoreComplete();
                }
            }
        });
    }

    public void refreshData(String cid, boolean clear) {
        this.cid = cid;
        b.emptyLayout.setVisibility(View.GONE);
        if (clear) {
            showIndeterminateProgressDialog(false);
            mProductListAdapter.clear();
            page = 1;
        }
        addSubscription(XlmmApp.getProductInteractor(this)
            .getCategoryProductList(cid, page, order_by, new ServiceResponse<ProductListBean>() {
                @Override
                public void onNext(ProductListBean bean) {
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
                }

                @Override
                public void onError(Throwable e) {
                    hideIndeterminateProgressDialog();
                    b.xrv.loadMoreComplete();
                    b.xrv.refreshComplete();
                    JUtils.Toast("数据加载有误!");
                }
            }));
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
                page = 1;
                refreshData(cid, true);
                break;
            case R.id.sort_price:
                b.sortPrice.setClickable(false);
                b.sortDefault.setClickable(true);
                b.sortPrice.setTextColor(getResources().getColor(R.color.colorAccent));
                b.sortDefault.setTextColor(getResources().getColor(R.color.text_color_4A));
                order_by = "price";
                page = 1;
                refreshData(cid, true);
                break;
            default:
                break;
        }

    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }
}
