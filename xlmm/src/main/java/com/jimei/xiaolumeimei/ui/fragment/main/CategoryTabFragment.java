package com.jimei.xiaolumeimei.ui.fragment.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;

import com.jimei.library.utils.FileUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CategoryItemAdapter;
import com.jimei.xiaolumeimei.adapter.CategoryNameListAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.FragmentCategoryTabBinding;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.product.SearchActivity;
import com.jimei.xiaolumeimei.widget.CategoryListTask;
import com.jimei.xiaolumeimei.widget.CategoryTask;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by wisdom on 17/3/6.
 */

public class CategoryTabFragment extends BaseBindingFragment<FragmentCategoryTabBinding> implements AdapterView.OnItemClickListener, View.OnClickListener {
    private CategoryItemAdapter adapter;
    private CategoryNameListAdapter mCategoryNameListAdapter;

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    public static CategoryTabFragment newInstance() {
        return new CategoryTabFragment();
    }

    @Override
    public void initData() {
        hideIndeterminateProgressDialog();
        mActivity.showIndeterminateProgressDialog(false);
        downloadJson("");
    }

    private void downloadJson(String cid) {
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getCategoryDown(new ServiceResponse<CategoryDownBean>() {
                @Override
                public void onNext(CategoryDownBean categoryDownBean) {
                    if (categoryDownBean != null) {
                        String downloadUrl = categoryDownBean.getDownload_url();
                        String sha1 = categoryDownBean.getSha1();
                        if (!FileUtils.isCategorySame(mActivity, sha1) ||
                            !FileUtils.isFileExist(XlmmConst.CATEGORY_JSON)) {
                            if (FileUtils.isFolderExist(XlmmConst.CATEGORY_JSON)) {
                                FileUtils.deleteFile(XlmmConst.CATEGORY_JSON);
                            }
                            OkHttpUtils.get().url(downloadUrl).build()
                                .execute(new FileCallBack(XlmmConst.XLMM_DIR, "category.json") {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        mActivity.hideIndeterminateProgressDialog();
                                    }

                                    @Override
                                    public void onResponse(File response, int id) {
                                        FileUtils.saveCategoryFile(mActivity, sha1);
                                        new CategoryListTask(mCategoryNameListAdapter, cid).execute();
                                        new CategoryTask(adapter, b.emptyLayout, mActivity).execute(cid);
                                    }
                                });
                        } else {
                            new CategoryListTask(mCategoryNameListAdapter, "").execute();
                            new CategoryTask(adapter, b.emptyLayout, mActivity).execute("");
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    mActivity.hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    public void setListener() {
        b.lv.setOnItemClickListener(this);
        b.searchLayout.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        mCategoryNameListAdapter = new CategoryNameListAdapter(mActivity);
        b.lv.setAdapter(mCategoryNameListAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setPullRefreshEnabled(false);
        b.xrv.setLoadingMoreEnabled(false);

        adapter = new CategoryItemAdapter(mActivity);
        b.xrv.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_category_tab;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        mActivity.showIndeterminateProgressDialog(false);
        b.emptyLayout.setVisibility(View.GONE);
        String cid = ((CategoryBean) mCategoryNameListAdapter.getItem(position)).getCid();
        mCategoryNameListAdapter.setCid(cid);
        if (!FileUtils.isFileExist(XlmmConst.CATEGORY_JSON)) {
            downloadJson(cid);
        } else {
            mCategoryNameListAdapter.setCid(cid);
            new CategoryTask(adapter, b.emptyLayout, mActivity).execute(cid);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_layout:
                Pair<View, String> searchPair = new Pair<>(b.searchLayout, "search");//nameTv是名字控件
                Intent intent = new Intent(mActivity, SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity,
                        searchPair).toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
