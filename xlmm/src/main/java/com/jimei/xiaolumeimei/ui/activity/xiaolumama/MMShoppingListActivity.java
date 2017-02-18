package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ShoppingListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMShoppingListActivity extends BaseSwipeBackCompatActivity implements ScrollableHelper.ScrollableContainer {
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.shoppinglist_xry)
    XRecyclerView shoppinglistXry;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    private int page = 1;
    private ShoppingListAdapter adapter;
    private String order;

    @Override
    protected void initData() {
        tvCount.setText(order);
        showIndeterminateProgressDialog(false);
        loadMoreData();
    }

    @Override
    public View getLoadingView() {
        return scrollableLayout;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        order = extras.getInt("orderNum") + "";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mmshoppinglist;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        shoppinglistXry.setLayoutManager(new LinearLayoutManager(this));
        shoppinglistXry.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        shoppinglistXry.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        shoppinglistXry.setPullRefreshEnabled(false);
        shoppinglistXry.setLoadingMoreEnabled(true);
        adapter = new ShoppingListAdapter(this);
        shoppinglistXry.setAdapter(adapter);
        shoppinglistXry.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        scrollableLayout.getHelper().setCurrentScrollableContainer(this);
    }

    private void loadMoreData() {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaAllOderCarryLogs(page)
                .subscribe(new ServiceResponse<OderCarryBean>() {
                    @Override
                    public void onNext(OderCarryBean shoppingListBean) {
                        if (shoppingListBean != null) {
                            adapter.update(shoppingListBean.getResults());
                            if (shoppingListBean.getNext() != null) {
                                page++;
                            } else {
                                JUtils.Toast("没有更多了");
                                shoppinglistXry.setLoadingMoreEnabled(false);
                            }
                        }
                        shoppinglistXry.loadMoreComplete();
                        hideIndeterminateProgressDialog();
                    }
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public View getScrollableView() {
        return shoppinglistXry;
    }
}
