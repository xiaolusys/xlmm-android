package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BrandActivityAdapter;
import com.jimei.xiaolumeimei.adapter.BrandlistAdapter;
import com.jimei.xiaolumeimei.adapter.ChildListActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 16/5/3.
 */
public class BrandListActivity extends BaseSwipeBackCompatActivity {
    int page_size = 10;
    @Bind(R.id.tool_bartitle)
    TextView toolBartitle;
    private int page = 2;
    private int totalPages;//总的分页数
    private XRecyclerView xRecyclerView;
    private BrandActivityAdapter mBrandActivityAdapter;
    private int id;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        Subscription subscribe = ProductModel.getInstance()
                .getBrandlistProducts(id, 1, 10)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<BrandListBean>() {
                    @Override
                    public void onNext(BrandListBean productListBean) {

                        try {

                            if (productListBean != null) {
                                List<BrandListBean.ResultsBean> results = productListBean.getResults();
                                totalPages = productListBean.getCount() / page_size;
                                mBrandActivityAdapter.update(results);
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
        addSubscription(subscribe);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getInt("id");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activty_childlist;
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBartitle.setText("品牌推广");
        setSupportActionBar(toolbar);
        finishBack(toolbar);
        initRecyclerView();
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

        xRecyclerView = (XRecyclerView) findViewById(R.id.childlist_recyclerView);
        xRecyclerView.setLayoutManager(new GridLayoutManager(BrandListActivity.this, 2));

        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mBrandActivityAdapter = new BrandActivityAdapter(this);
        xRecyclerView.setAdapter(mBrandActivityAdapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Subscription subscribe = ProductModel.getInstance()
                        .getBrandlistProducts(id, 1, page * page_size)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<BrandListBean>() {
                            @Override
                            public void onNext(BrandListBean childListBean) {
                                List<BrandListBean.ResultsBean> results = childListBean.getResults();
                                mBrandActivityAdapter.updateWithClear(results);
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
                if (page <= totalPages) {
                    loadMoreData(page, 10);
                    page++;
                } else {
                    Toast.makeText(BrandListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
                            .show();
                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                }
            }
        });
    }

    private void loadMoreData(int page, int page_size) {

        Subscription subscribe = ProductModel.getInstance()
                .getBrandlistProducts(id, page, page_size)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<BrandListBean>() {
                    @Override
                    public void onNext(BrandListBean productListBean) {
                        List<BrandListBean.ResultsBean> results = productListBean.getResults();
                        mBrandActivityAdapter.update(results);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        xRecyclerView.post(xRecyclerView::loadMoreComplete);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
