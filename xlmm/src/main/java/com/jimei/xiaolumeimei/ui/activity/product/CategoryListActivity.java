package com.jimei.xiaolumeimei.ui.activity.product;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CategoryItemAdapter;
import com.jimei.xiaolumeimei.adapter.CategoryNameListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.widget.CategoryListTask;
import com.jimei.xiaolumeimei.widget.CategoryTask;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by wisdom on 16/9/23.
 */

public class CategoryListActivity extends BaseSwipeBackCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.lv)
    ListView mListView;
    @Bind(R.id.xrv)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.finish_layout)
    RelativeLayout finishLayout;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    private CategoryItemAdapter adapter;
    private CategoryNameListAdapter mCategoryNameListAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_category_list;
    }

    @Override
    protected void initViews() {
        mCategoryNameListAdapter = new CategoryNameListAdapter(this);
        mListView.setAdapter(mCategoryNameListAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mXRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);


        adapter = new CategoryItemAdapter(this);
        mXRecyclerView.setAdapter(adapter);
        if (!FileUtils.isFileExist(XlmmConst.CATEGORY_JSON)) {
            showIndeterminateProgressDialog(false);
            addSubscription(XlmmApp.getMainInteractor(this)
                .getCategoryDown(new ServiceResponse<CategoryDownBean>() {
                    @Override
                    public void onNext(CategoryDownBean categoryDownBean) {
                        if (categoryDownBean != null) {
                            String downloadUrl = categoryDownBean.getDownload_url();
                            String sha1 = categoryDownBean.getSha1();
                            if (FileUtils.isFolderExist(XlmmConst.CATEGORY_JSON)) {
                                FileUtils.deleteFile(XlmmConst.CATEGORY_JSON);
                            }
                            OkHttpUtils.get().url(downloadUrl).build()
                                .execute(new FileCallBack(XlmmConst.XLMM_DIR, "category.json") {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        hideIndeterminateProgressDialog();
                                    }

                                    @Override
                                    public void onResponse(File response, int id) {
                                        FileUtils.saveCategoryFile(getApplicationContext(), sha1);
                                        new CategoryListTask(mCategoryNameListAdapter).execute();
                                        new CategoryTask(adapter, emptyLayout).execute("");
                                        hideIndeterminateProgressDialog();
                                    }
                                });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        hideIndeterminateProgressDialog();
                    }
                }));
        } else {
            new CategoryListTask(mCategoryNameListAdapter).execute();
            new CategoryTask(adapter, emptyLayout).execute("");
        }
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void setListener() {
        mListView.setOnItemClickListener(this);
        finishLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showIndeterminateProgressDialog(false);
        emptyLayout.setVisibility(View.GONE);
        String cid = ((CategoryBean) mCategoryNameListAdapter.getItem(position)).getCid();
        mCategoryNameListAdapter.setCid(cid);
        new CategoryTask(adapter, emptyLayout, this).execute(cid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_layout:
                finish();
                break;
            case R.id.search_layout:
                Pair<View, String> finishPair = new Pair<>(finishLayout, "finish");//haderIv是头像控件
                Pair<View, String> searchPair = new Pair<>(searchLayout, "search");//nameTv是名字控件
                Intent intent = new Intent(this, SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        finishPair, searchPair).toBundle());
                } else {
                    readyGo(SearchActivity.class);
                }
                break;
            default:
                break;
        }
    }
}
