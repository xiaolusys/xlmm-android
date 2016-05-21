package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMHaveChooseAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.LinearRecyclerView;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.SuperRecyclerView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/2.
 */
public class HaveChoosedActivity extends BaseSwipeBackCompatActivity {
  @Bind(R.id.choose_recyclerview) SuperRecyclerView chooseRecyclerview;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.home) ImageView homeView;

  private List<ShopProductBean.ResultsBean> data = new ArrayList<>();
  private MMHaveChooseAdapter adapter;
  private int page = 2;
  private String sharelink;
  private String cookies;
  private String domain;

  @Override protected void setListener() {
    homeView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intentrl_shop =
            new Intent(HaveChoosedActivity.this, MMStoreWebViewActivity.class);
        SharedPreferences sharedPreferences =
            getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");
        Bundle bundlerl_shop = new Bundle();
        bundlerl_shop.putString("cookies", cookies);
        bundlerl_shop.putString("domain", domain);
        bundlerl_shop.putString("Cookie", sharedPreferences.getString("Cookie", ""));
        bundlerl_shop.putString("actlink", sharelink);
        intentrl_shop.putExtras(bundlerl_shop);
        startActivity(intentrl_shop);
      }
    });
  }

  @Override protected void initData() {
    MMProductModel.getInstance()
        .getShareShopping()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MMShoppingBean>() {

          @Override public void onNext(MMShoppingBean mmShoppingBean) {

            if (null != mmShoppingBean) {
              sharelink = mmShoppingBean.getShopInfo().getPreviewShopLink();
            }
          }
        });
    chooseRecyclerview.onLoadStart();
    MMProductModel.getInstance()
        .getShopProduct("200")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ShopProductBean>() {
          @Override public void onCompleted() {
            super.onCompleted();
            //                        hideIndeterminateProgressDialog();
            chooseRecyclerview.onLoadFinish();
          }

          @Override public void onNext(ShopProductBean shopProductBean) {
            if (shopProductBean != null) {
              if (shopProductBean.getResults() != null) {
                JUtils.Toast("可以进行手势滑动操作");
                data.addAll(shopProductBean.getResults());
                adapter.notifyDataSetChanged();

                if (shopProductBean.getNext() == null) {
                  chooseRecyclerview.onLoadFinish();
                }
              }
            } else {
              JUtils.Toast("还没有选品数据,返回添加");
              finish();
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            chooseRecyclerview.onLoadFinish();
          }
        });

    chooseRecyclerview.setOnScrollListener(new LinearRecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_havachoosed;
  }

  @Override protected void initViews() {

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    adapter = new MMHaveChooseAdapter(this, data);
    chooseRecyclerview.setAdapter(adapter);
    //        chooseRecyclerview.setOnPageListener(this);

    chooseRecyclerview.addItemDecoration(new DividerItemDecoration(this));
    //chooseRecyclerview.addItemDecoration(new SpaceItemDecoration(8));
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
