package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMChooseAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMStoreBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMChooseListActivity extends BaseSwipeBackCompatActivity
    implements AdapterView.OnItemSelectedListener, View.OnClickListener {

  private static final String COMMISSION = "rebet_amount";//佣金
  private static final String SALES = "sale_num";//销量
  private static final String CHILD = "1";//童装
  private static final String LADY = "2";//女装
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.spinner_choose) AppCompatSpinner spinnerChoose;
  @Bind(R.id.tv_commission) TextView tvCommission;
  @Bind(R.id.tv_sales) TextView tvSales;
  @Bind(R.id.chooselist_xey) RecyclerView chooselistXey;
  @Bind(R.id.loading) RotateLoading loading;
  //@Bind(R.id.choosenum) TextView choosenum;
  private MMChooseAdapter mmChooseAdapter;
  private boolean isAll, isLady, isChild;
  private int chooseNum;

  @Override protected void setListener() {
    spinnerChoose.setOnItemSelectedListener(this);
    tvCommission.setOnClickListener(this);
    tvSales.setOnClickListener(this);
  }

  @Override protected void initData() {
    List<Map<String, String>> textList = new ArrayList<>();
    HashMap<String, String> mapAll = new HashMap<>();
    mapAll.put("chooselist", "全部");
    textList.add(mapAll);

    HashMap<String, String> mapLady = new HashMap<>();
    mapLady.put("chooselist", "女装");
    textList.add(mapLady);

    HashMap<String, String> mapChild = new HashMap<>();
    mapChild.put("chooselist", "童装");
    textList.add(mapChild);

    SimpleAdapter adapter = new SimpleAdapter(this, textList, R.layout.item_choosespinner,
        new String[] { "chooselist" }, new int[] { R.id.choose_tv });
    spinnerChoose.setAdapter(adapter);

    chooseNum = getChooseNum();

    Subscription subscribe = MMProductModel.getInstance()
        .getMMChooseList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            super.onNext(mmChooselistBeans);
            try {
              if (mmChooselistBeans != null) {
                mmChooseAdapter.update(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }
        });
    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.chooselist_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    //choose_num = (TextView) toolbar.findViewById(R.id.choose_num);

    initRecyclerView();
  }

  private void initRecyclerView() {
    chooselistXey.setLayoutManager(new LinearLayoutManager(this));
    chooselistXey.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    mmChooseAdapter = new MMChooseAdapter(this);
    chooselistXey.setAdapter(mmChooseAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    tvCommission.setTextColor(Color.parseColor("#4A4A4A"));
    tvSales.setTextColor(Color.parseColor("#4A4A4A"));

    if (position == 0) {
      isAll = true;
      isChild = false;
      isLady = false;

      getchooseList();
    } else if (position == 1) {
      isAll = false;
      isChild = false;
      isLady = true;
      getChooseListLadyorChild(LADY);
    } else if (position == 2) {
      isAll = false;
      isChild = true;
      isLady = false;
      getChooseListLadyorChild(CHILD);
    }
  }

  private void getchooseList() {
    showIndeterminateProgressDialog(false);

    Subscription subscribe = MMProductModel.getInstance()
        .getMMChooseList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            super.onNext(mmChooselistBeans);
            try {
              if (mmChooselistBeans != null) {
                mmChooseAdapter.update(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
            //hideIndeterminateProgressDialog();
          }
        });
    addSubscription(subscribe);
  }

  private void getchooseSortList(String sortfeild) {
    showIndeterminateProgressDialog(false);

    Subscription subscribe = MMProductModel.getInstance()
        .getMMChooseSortList(sortfeild)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            super.onNext(mmChooselistBeans);
            try {
              if (mmChooselistBeans != null) {
                mmChooseAdapter.updateWithClear(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
            //hideIndeterminateProgressDialog();
          }
        });
    addSubscription(subscribe);
  }

  private void getChooseListLadyorChild(String choice) {
    showIndeterminateProgressDialog(false);

    Subscription subscribe = MMProductModel.getInstance()
        .getMMChooseLadyOrChildList(choice)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            super.onNext(mmChooselistBeans);
            try {
              if (mmChooselistBeans != null) {
                mmChooseAdapter.updateWithClear(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
            //hideIndeterminateProgressDialog();
          }
        });

    addSubscription(subscribe);
  }

  private void getChooseListLadyorChildSort(String sortfeild, String category) {

    showIndeterminateProgressDialog(false);
    Subscription subscribe = MMProductModel.getInstance()
        .getMMChooseLadyOrChildSortListSort(sortfeild, category)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            super.onNext(mmChooselistBeans);
            try {
              if (mmChooselistBeans != null) {
                mmChooseAdapter.updateWithClear(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }
        });
    addSubscription(subscribe);
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_commission:

        tvCommission.setTextColor(Color.parseColor("#F5B123"));
        tvSales.setTextColor(Color.parseColor("#4A4A4A"));

        if (isLady) {
          getChooseListLadyorChildSort(COMMISSION, LADY);
        } else if (isAll) {
          getchooseSortList(COMMISSION);
        } else if (isChild) {
          getChooseListLadyorChildSort(COMMISSION, CHILD);
        }

        break;
      case R.id.tv_sales:
        tvCommission.setTextColor(Color.parseColor("#4A4A4A"));
        tvSales.setTextColor(Color.parseColor("#F5B123"));
        if (isLady) {
          getChooseListLadyorChildSort(SALES, LADY);
        } else if (isAll) {

          getchooseSortList(SALES);
        } else if (isChild) {
          getChooseListLadyorChildSort(SALES, CHILD);
        }

        break;
    }
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onResume() {
    super.onResume();
    //choosenum.setText(getChooseNum());
  }

  public int getChooseNum() {

    final int[] num = new int[1];

    MMProductModel.getInstance()
        .getMMStoreList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<MMStoreBean>>() {
          @Override public void onNext(List<MMStoreBean> mmStoreBeen) {
            if (mmStoreBeen != null) {
              num[0] = mmStoreBeen.size();
            } else {
              num[0] = 0;
            }
          }
        });

    return num[0];
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }
}
