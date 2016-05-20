package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.LadyListActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/03.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LadyListActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  @Bind(R.id.text_1) TextView textView1;
  @Bind(R.id.text_2) TextView textView2;
  @Bind(R.id.xrv_by) XRecyclerView byXRecyclerView;

  int page_size = 10;
  private int page1 = 2;
  private int page2 = 2;
  private int totalPages;//总的分页数
  private XRecyclerView xRecyclerView;
  private LadyListActivityAdapter mLadyListAdapter;
  private LadyListActivityAdapter mLadyListAdapter2;

  @Override protected void setListener() {
    textView1.setOnClickListener(this);
    textView2.setOnClickListener(this);
  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    Subscription subscribe = ProductModel.getInstance()
        .getLadyList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<LadyListBean>() {
          @Override public void onNext(LadyListBean ladyListBean) {

            try {
              if (ladyListBean != null) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
                totalPages = ladyListBean.getCount() / page_size;
                mLadyListAdapter.update(results);
              }
            } catch (Exception ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }
        });

    Subscription subscribe2 = ProductModel.getInstance()
        .getLadyList(1, 10, "price")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<LadyListBean>() {
          @Override public void onNext(LadyListBean ladyListBean) {

            try {
              if (ladyListBean != null) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
                totalPages = ladyListBean.getCount() / page_size + 1;
                mLadyListAdapter2.update(results);
              }
            } catch (Exception ex) {
            }
          }
        });
    addSubscription(subscribe);
    addSubscription(subscribe2);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_ladylist;
  }

  @Override protected void initViews() {
    initRecyclerView();
  }

  private void initRecyclerView() {
    xRecyclerView = (XRecyclerView) findViewById(R.id.childlist_recyclerView);
    GridLayoutManager layoutManager = new GridLayoutManager(LadyListActivity.this, 2);

    xRecyclerView.setLayoutManager(layoutManager);

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    byXRecyclerView.setLayoutManager(new GridLayoutManager(LadyListActivity.this, 2));

    byXRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    byXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    byXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    byXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mLadyListAdapter = new LadyListActivityAdapter(LadyListActivity.this);
    mLadyListAdapter2 = new LadyListActivityAdapter(LadyListActivity.this);
    xRecyclerView.setAdapter(mLadyListAdapter);

    byXRecyclerView.setAdapter(mLadyListAdapter2);
    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        Subscription subscribe = ProductModel.getInstance()
            .getLadyList(1, page1 * page_size)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<LadyListBean>() {
              @Override public void onNext(LadyListBean ladyListBean) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
                mLadyListAdapter.updateWithClear(results);
              }

              @Override public void onCompleted() {
                super.onCompleted();
                xRecyclerView.post(xRecyclerView::refreshComplete);
              }
            });
        addSubscription(subscribe);
      }

      @Override public void onLoadMore() {
        if (page1 <= totalPages) {
          loadMoreData(page1, 10, null);
          page1++;
        } else {
          Toast.makeText(LadyListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
    byXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        Subscription subscribe = ProductModel.getInstance()
            .getLadyList(1, page2 * page_size, "price")
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<LadyListBean>() {
              @Override public void onNext(LadyListBean ladyListBean) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
                mLadyListAdapter2.updateWithClear(results);
              }

              @Override public void onCompleted() {
                super.onCompleted();
                byXRecyclerView.post(byXRecyclerView::refreshComplete);
              }
            });
        addSubscription(subscribe);
      }

      @Override public void onLoadMore() {
        if (page2 <= totalPages) {
          loadMoreData(page2, 10, "price");
          page2++;
        } else {
          Toast.makeText(LadyListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          byXRecyclerView.post(byXRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void loadMoreData(int page, int page_size, String orderBy) {
    if (orderBy != null) {
      Subscription subscribe = ProductModel.getInstance()
          .getLadyList(page, page_size, orderBy)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<LadyListBean>() {
            @Override public void onNext(LadyListBean productListBean) {
              List<LadyListBean.ResultsEntity> results = productListBean.getResults();
              mLadyListAdapter2.update(results);
            }

            @Override public void onCompleted() {
              super.onCompleted();
              byXRecyclerView.post(byXRecyclerView::loadMoreComplete);
            }
          });
      addSubscription(subscribe);
    } else {
      Subscription subscribe = ProductModel.getInstance()
          .getLadyList(page, page_size)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<LadyListBean>() {
            @Override public void onNext(LadyListBean productListBean) {
              List<LadyListBean.ResultsEntity> results = productListBean.getResults();
              mLadyListAdapter.update(results);
            }

            @Override public void onCompleted() {
              super.onCompleted();
              xRecyclerView.post(xRecyclerView::loadMoreComplete);
            }
          });
      addSubscription(subscribe);
    }
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override public void onClick(View v) {
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
}
