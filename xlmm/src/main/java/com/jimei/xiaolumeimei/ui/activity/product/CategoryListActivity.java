package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.adapter.CategoryListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.widget.CategoryListTask;
import com.jimei.xiaolumeimei.widget.CategoryTask;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;

import butterknife.Bind;

/**
 * Created by wisdom on 16/9/23.
 */

public class CategoryListActivity extends BaseSwipeBackCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv)
    ListView mListView;
    @Bind(R.id.xrv)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private CategoryAdapter adapter;
    private CategoryListAdapter mCategoryListAdapter;

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_category_list;
    }

    @Override
    protected void initViews() {
        mCategoryListAdapter = new CategoryListAdapter(this);
        mListView.setAdapter(mCategoryListAdapter);
        new CategoryListTask(mCategoryListAdapter).execute();

        GridLayoutManager manager2 = new GridLayoutManager(this, 3);
        mXRecyclerView.setLayoutManager(manager2);
        mXRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mXRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        adapter = new CategoryAdapter(this);
        mXRecyclerView.setAdapter(adapter);
        new CategoryTask(adapter).execute("");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        emptyLayout.setVisibility(View.GONE);
        String cid = ((CategoryBean) mCategoryListAdapter.getItem(position)).getCid();
        mCategoryListAdapter.setCid(cid);
        new CategoryTask(adapter, emptyLayout).execute(cid);
    }

}
