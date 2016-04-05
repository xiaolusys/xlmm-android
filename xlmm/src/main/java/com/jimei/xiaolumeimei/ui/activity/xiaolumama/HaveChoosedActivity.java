package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMHaveChooseAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.OnPageListener;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.SuperRecyclerView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;


/**
 * Created by itxuye(http:www.itxuye.com) on 16/4/2.
 */
public class HaveChoosedActivity extends BaseSwipeBackCompatActivity implements OnPageListener {
    @Bind(R.id.choose_recyclerview)
    SuperRecyclerView chooseRecyclerview;

    private List<ShopProductBean.ResultsBean> data = new ArrayList<>();
    private MMHaveChooseAdapter adapter;


    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
//        showIndeterminateProgressDialog(false);
        MMProductModel.getInstance()
                .getShopProduct("1")
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ShopProductBean>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
//                        hideIndeterminateProgressDialog();
                        chooseRecyclerview.onLoadStart();
                    }

                    @Override
                    public void onNext(ShopProductBean shopProductBean) {
                        if (shopProductBean != null) {
                            if (shopProductBean.getResults() != null) {
                                data.addAll(shopProductBean.getResults());
                                chooseRecyclerview.notifyDataSetChanged();
                                chooseRecyclerview.onLoadFinish();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        chooseRecyclerview.onLoadFinish();
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
        adapter = new MMHaveChooseAdapter(this, data);
        chooseRecyclerview.setAdapter(adapter);
        chooseRecyclerview.setOnPageListener(this);
        chooseRecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

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
    public void onPage() {

    }
}
