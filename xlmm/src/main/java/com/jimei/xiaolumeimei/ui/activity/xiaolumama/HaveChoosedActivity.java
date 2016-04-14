package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMHaveChooseAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.LinearRecyclerView;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.OnPageListener;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.SuperRecyclerView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import rx.schedulers.Schedulers;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/2.
 */
public class HaveChoosedActivity extends BaseSwipeBackCompatActivity{
    @Bind(R.id.choose_recyclerview)
    SuperRecyclerView chooseRecyclerview;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private List<ShopProductBean.ResultsBean> data = new ArrayList<>();
    private MMHaveChooseAdapter adapter;
    private int page = 2;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        //        showIndeterminateProgressDialog(false);
        chooseRecyclerview.onLoadStart();
        MMProductModel.getInstance()
                .getShopProduct("200")
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ShopProductBean>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        //                        hideIndeterminateProgressDialog();
                        chooseRecyclerview.onLoadFinish();
                    }

                    @Override
                    public void onNext(ShopProductBean shopProductBean) {
                        if (shopProductBean != null) {
                            if (shopProductBean.getResults() != null) {
                                JUtils.Toast("可以进行手势滑动操作");
                                data.addAll(shopProductBean.getResults());
                                adapter.notifyDataSetChanged();

                                if (shopProductBean.getNext() == null) {
                                    chooseRecyclerview.onLoadFinish();
                                }
                            }
                        }else {
                            JUtils.Toast("还没有选品数据,返回添加");
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        chooseRecyclerview.onLoadFinish();
                    }
                });

        chooseRecyclerview.setOnScrollListener(new LinearRecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_havachoosed;
    }

    @Override
    protected void initViews() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        finishBack(toolbar);

        adapter = new MMHaveChooseAdapter(this, data);
        chooseRecyclerview.setAdapter(adapter);
//        chooseRecyclerview.setOnPageListener(this);

        chooseRecyclerview.addItemDecoration(new DividerItemDecoration(this));
        //chooseRecyclerview.addItemDecoration(new SpaceItemDecoration(8));
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

//    @Override
//    public void onPage() {
//        chooseRecyclerview.onLoadStart();
//        JUtils.Log("pageTest", page + "");
//        MMProductModel.getInstance()
//                .getShopProduct(page + "")
//                .subscribeOn(Schedulers.io())
//                .subscribe(new ServiceResponse<ShopProductBean>() {
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        //                        hideIndeterminateProgressDialog();
////                        chooseRecyclerview.onLoadFinish();
//                    }
//
//                    @Override
//                    public void onNext(ShopProductBean shopProductBean) {
//                        if (shopProductBean != null) {
//                            if (shopProductBean.getResults() != null
//                                    && shopProductBean.getNext() != null) {
//                                data.addAll(shopProductBean.getResults());
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        chooseRecyclerview.notifyDataSetChanged();
//                                        chooseRecyclerview.onLoadFinish();
//                                    }
//                                }, 1000);
//
//                            }
//                            if (shopProductBean.getResults() != null
//                                    && shopProductBean.getNext() == null) {
//                                JUtils.Toast("没有数据了");
//                            } else {
//                                page++;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        chooseRecyclerview.onLoadFinish();
//                    }
//                });
//    }
}
