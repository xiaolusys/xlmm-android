package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityProductListBinding;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.CategoryTask;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class ProductListActivity extends BaseMVVMActivity<ActivityProductListBinding> implements View.OnClickListener {

    private String type;
    private String title;
    private int page;

    private CategoryAdapter adapter;
    private Menu menu;
    private ProductListAdapter mProductListAdapter;
    private String cid;
    private String next;
    private String order_by;

    @Override
    protected void initListener() {
        b.sortDefault.setOnClickListener(this);
        b.sortPrice.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        page = 1;
        order_by = "";
        refreshData(type, true);
    }

    private void initCategory() {
        new CategoryTask(adapter, menu).execute(type);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            type = extras.getString("type");
            title = extras.getString("title");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initView() {
        b.title.setName(title);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        b.xrv.setArrowImageView(R.drawable.iconfont_downgrey);
        b.xrv.setPullRefreshEnabled(false);
        mProductListAdapter = new ProductListAdapter(new ArrayList<>(), this);
        b.xrv.setAdapter(mProductListAdapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    refreshData(cid, false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    b.xrv.post(b.xrv::loadMoreComplete);
                }
            }
        });


        GridLayoutManager manager2 = new GridLayoutManager(this, 3);
        b.xrvCategory.setLayoutManager(manager2);
        b.xrvCategory.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrvCategory.addItemDecoration(new SpaceItemDecoration(10));
        b.xrvCategory.setPullRefreshEnabled(false);
        b.xrvCategory.setLoadingMoreEnabled(false);
        adapter = new CategoryAdapter(this, new ArrayList<>());
        b.xrvCategory.setAdapter(adapter);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //隐藏分类功能
//        getMenuInflater().inflate(R.menu.menu_category, menu);
        this.menu = menu;
        initCategory();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category:
                if (b.xrvCategory.getVisibility() == View.VISIBLE) {
                    b.xrvCategory.setVisibility(View.GONE);
                } else {
                    b.xrvCategory.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshData(String cid, boolean clear) {
        this.cid = cid;
        b.emptyLayout.setVisibility(View.GONE);
        if (clear) {
            showIndeterminateProgressDialog(false);
            mProductListAdapter.clear();
            page = 1;
        }
        b.xrvCategory.setVisibility(View.GONE);
        Subscription subscribe = ProductModel.getInstance()
                .getCategoryProductList(cid, page, order_by)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CategoryProductListBean>() {
                    @Override
                    public void onNext(CategoryProductListBean categoryProductListBean) {
                        List<CategoryProductListBean.ResultsBean> results = categoryProductListBean.getResults();
                        if (results != null && results.size() > 0) {
                            mProductListAdapter.update(results);
                        } else {
                            b.emptyLayout.setVisibility(View.VISIBLE);
                        }
                        next = categoryProductListBean.getNext();
                        if (next != null && !"".equals(next)) {
                            page++;
                        }
                        hideIndeterminateProgressDialog();
                        b.xrv.post(b.xrv::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideIndeterminateProgressDialog();
                        b.xrv.post(b.xrv::loadMoreComplete);
                        JUtils.Toast("数据加载有误!");
                    }
                });
        addSubscription(subscribe);
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
                refreshData(cid,true);
                break;
            case R.id.sort_price:
                b.sortPrice.setClickable(false);
                b.sortDefault.setClickable(true);
                b.sortPrice.setTextColor(getResources().getColor(R.color.colorAccent));
                b.sortDefault.setTextColor(getResources().getColor(R.color.text_color_4A));
                order_by = "price";
                page = 1;
                refreshData(cid,true);
                break;
            default:
                break;
        }

    }
}
