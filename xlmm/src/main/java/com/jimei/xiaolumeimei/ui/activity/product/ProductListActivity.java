package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.adapter.CategoryProductAdapter;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.entities.ProductListResultBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.CategoryTask;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class ProductListActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    @Bind(R.id.text_1)
    TextView textView1;
    @Bind(R.id.text_2)
    TextView textView2;
    @Bind(R.id.xrv_by)
    XRecyclerView byXRecyclerView;
    @Bind(R.id.xrv)
    XRecyclerView xrv;
    @Bind(R.id.xrv_category)
    XRecyclerView xrvCategory;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.list_recyclerView)
    XRecyclerView xRecyclerView;
    @Bind(R.id.title)
    XlmmTitleView titleView;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;


    int page_size = 10;
    private int page1 = 2;
    private int page2 = 2;
    private int totalPages;//总的分页数
    private ProductListAdapter mChildListAdapter;
    private ProductListAdapter mChildListAdapter2;
    private int type;
    private Menu menu;
    private CategoryAdapter adapter;
    private String cid;
    private int page;
    private CategoryProductAdapter categoryProductAdapter;
    private String next;

    @Override
    protected void setListener() {
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        page = 1;
        showIndeterminateProgressDialog(false);
        Subscription subscribe = ProductModel.getInstance()
                .getProductList(1, 10, type)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductListResultBean>() {
                    @Override
                    public void onNext(ProductListResultBean productListResultBean) {
                        try {
                            if (productListResultBean != null) {
                                List<ProductListResultBean.ResultsEntity> results = productListResultBean.getResults();
                                if (productListResultBean.getCount() % page_size == 0) {
                                    totalPages = productListResultBean.getCount() / page_size;
                                } else {
                                    totalPages = productListResultBean.getCount() / page_size + 1;
                                }
                                mChildListAdapter.update(results);
                            }
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }
                });
        Subscription subscribe2 = ProductModel.getInstance()
                .getProductList(1, 10, "price", type)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductListResultBean>() {
                    @Override
                    public void onNext(ProductListResultBean childListBean) {

                        try {

                            if (childListBean != null) {
                                List<ProductListResultBean.ResultsEntity> results = childListBean.getResults();
                                if (childListBean.getCount() % page_size == 0) {
                                    totalPages = childListBean.getCount() / page_size;
                                } else {
                                    totalPages = childListBean.getCount() / page_size + 1;
                                }
                                mChildListAdapter2.update(results);
                            }
                        } catch (Exception ex) {
                        }
                    }
                });
        addSubscription(subscribe);
        addSubscription(subscribe2);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            type = extras.getInt("type");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initViews() {
        if (type == XlmmConst.TYPE_LADY) {
            titleView.setName("时尚女装");
        } else if (type == XlmmConst.TYPE_CHILD) {
            titleView.setName("萌娃专区");
        }
        initRecyclerView();
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        xrv.setLayoutManager(manager);
        xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        xrv.addItemDecoration(new SpaceItemDecoration(10));
        xrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xrv.setArrowImageView(R.drawable.iconfont_downgrey);
        xrv.setPullRefreshEnabled(false);
        categoryProductAdapter = new CategoryProductAdapter(new ArrayList<>(), this);
        xrv.setAdapter(categoryProductAdapter);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    refreshData(cid, false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    xrv.post(xrv::loadMoreComplete);
                }
            }
        });


        GridLayoutManager manager2 = new GridLayoutManager(this, 3);
        xrvCategory.setLayoutManager(manager2);
        xrvCategory.setOverScrollMode(View.OVER_SCROLL_NEVER);
        xrvCategory.addItemDecoration(new SpaceItemDecoration(10));
        xrvCategory.setPullRefreshEnabled(false);
        xrvCategory.setLoadingMoreEnabled(false);
        adapter = new CategoryAdapter(this, new ArrayList<>());
        xrvCategory.setAdapter(adapter);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void initRecyclerView() {
        xRecyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this, 2));

        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mChildListAdapter = new ProductListAdapter(ProductListActivity.this);
        xRecyclerView.setAdapter(mChildListAdapter);
        byXRecyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this, 2));

        byXRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        byXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        byXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        byXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mChildListAdapter2 = new ProductListAdapter(ProductListActivity.this);
        byXRecyclerView.setAdapter(mChildListAdapter2);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page1 = 2;
                Subscription subscribe = ProductModel.getInstance()
                        .getProductList(1, 10, type)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<ProductListResultBean>() {
                            @Override
                            public void onNext(ProductListResultBean childListBean) {

                                try {

                                    if (childListBean != null) {
                                        List<ProductListResultBean.ResultsEntity> results =
                                                childListBean.getResults();
                                        if (childListBean.getCount() % page_size == 0) {
                                            totalPages = childListBean.getCount() / page_size;
                                        } else {
                                            totalPages = childListBean.getCount() / page_size + 1;
                                        }
                                        mChildListAdapter.updateWithClear(results);
                                    }
                                } catch (Exception ex) {
                                }
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                xRecyclerView.post(xRecyclerView::refreshComplete);
                            }
                        });
                addSubscription(subscribe);
            }

            @Override
            public void onLoadMore() {
                if (page1 <= totalPages) {
                    loadMoreData(page1, 10, null);
                    page1++;
                } else {
                    Toast.makeText(ProductListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
                            .show();
                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                }
            }
        });
        byXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page2 = 2;
                Subscription subscribe2 = ProductModel.getInstance()
                        .getProductList(1, 10, "price", type)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<ProductListResultBean>() {
                            @Override
                            public void onNext(ProductListResultBean childListBean) {

                                try {

                                    if (childListBean != null) {
                                        List<ProductListResultBean.ResultsEntity> results =
                                                childListBean.getResults();
                                        if (childListBean.getCount() % page_size == 0) {
                                            totalPages = childListBean.getCount() / page_size;
                                        } else {
                                            totalPages = childListBean.getCount() / page_size + 1;
                                        }
                                        mChildListAdapter2.updateWithClear(results);
                                    }
                                } catch (Exception ex) {
                                }
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                byXRecyclerView.post(byXRecyclerView::refreshComplete);
                            }
                        });
                addSubscription(subscribe2);
            }

            @Override
            public void onLoadMore() {
                if (page2 <= totalPages) {
                    loadMoreData(page2, 10, "price");
                    page2++;
                } else {
                    Toast.makeText(ProductListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
                            .show();
                    byXRecyclerView.post(byXRecyclerView::loadMoreComplete);
                }
            }
        });
    }

    private void loadMoreData(int page, int page_size, String orderBy) {
        if (orderBy != null) {
            Subscription subscribe = ProductModel.getInstance()
                    .getProductList(page, page_size, orderBy, type)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ProductListResultBean>() {
                        @Override
                        public void onNext(ProductListResultBean productListBean) {
                            List<ProductListResultBean.ResultsEntity> results = productListBean.getResults();
                            mChildListAdapter2.update(results);
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            byXRecyclerView.post(byXRecyclerView::loadMoreComplete);
                        }
                    });
            addSubscription(subscribe);
        } else {
            Subscription subscribe = ProductModel.getInstance()
                    .getProductList(page, page_size, type)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ProductListResultBean>() {
                        @Override
                        public void onNext(ProductListResultBean productListBean) {
                            List<ProductListResultBean.ResultsEntity> results = productListBean.getResults();
                            mChildListAdapter.update(results);
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            xRecyclerView.post(xRecyclerView::loadMoreComplete);
                        }
                    });
            addSubscription(subscribe);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_1:
                textView1.setTextColor(getResources().getColor(R.color.colorAccent));
                textView2.setTextColor(getResources().getColor(R.color.text_color_9B));
                xRecyclerView.setVisibility(View.VISIBLE);
                byXRecyclerView.setVisibility(View.GONE);
                break;
            case R.id.text_2:
                textView2.setTextColor(getResources().getColor(R.color.colorAccent));
                textView1.setTextColor(getResources().getColor(R.color.text_color_9B));
                byXRecyclerView.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        this.menu = menu;
        initCategory();
        return super.onCreateOptionsMenu(menu);
    }

    private void initCategory() {
        new CategoryTask(adapter, menu).execute(type + "","false");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category:
                if (xrvCategory.getVisibility() == View.VISIBLE) {
                    xrvCategory.setVisibility(View.GONE);
                } else {
                    xrvCategory.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void refreshData(String cid, boolean clear) {
        xrv.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
        xrvCategory.setVisibility(View.GONE);
        this.cid = cid;
        if (clear) {
            showIndeterminateProgressDialog(false);
            categoryProductAdapter.clear();
            page = 1;
        }
        Subscription subscribe = ProductModel.getInstance()
                .getCategoryProductList(cid, page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CategoryProductListBean>() {
                    @Override
                    public void onNext(CategoryProductListBean categoryProductListBean) {
                        List<CategoryProductListBean.ResultsBean> results = categoryProductListBean.getResults();
                        if (results != null && results.size() > 0) {
                            categoryProductAdapter.update(results);
                        } else {
                            emptyLayout.setVisibility(View.VISIBLE);
                        }
                        next = categoryProductListBean.getNext();
                        if (next != null && !"".equals(next)) {
                            page++;
                        }
                        hideIndeterminateProgressDialog();
                        xrv.post(xrv::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideIndeterminateProgressDialog();
                        xrv.post(xrv::loadMoreComplete);
                        JUtils.Toast("数据加载有误!");
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
