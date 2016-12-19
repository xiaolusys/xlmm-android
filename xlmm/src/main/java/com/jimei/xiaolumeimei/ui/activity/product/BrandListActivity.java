package com.jimei.xiaolumeimei.ui.activity.product;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BrandActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by itxuye on 16/5/3.
 */
public class BrandListActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.childlist_recyclerView)
    XRecyclerView xRecyclerView;

    private BrandActivityAdapter mBrandActivityAdapter;
    private String id;

    @Override
    public void getIntentUrl(Uri uri) {
        id = uri.getQueryParameter("activity_id");
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        Subscription subscribe = ProductModel.getInstance()
                .getBrandList(id)
                .subscribe(new ServiceResponse<BrandListBean>() {
                    @Override
                    public void onNext(BrandListBean productListBean) {
                        try {
                            if (productListBean != null) {
                                mBrandActivityAdapter.update(productListBean.getProducts());
                            }
                        } catch (Exception ignored) {
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
    protected int getContentViewLayoutID() {
        return R.layout.activty_brand;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    public View getLoadingView() {
        return xRecyclerView;
    }

    private void initRecyclerView() {
        xRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        mBrandActivityAdapter = new BrandActivityAdapter(this);
        xRecyclerView.setAdapter(mBrandActivityAdapter);
    }

}
