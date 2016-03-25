package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.fragment.AllOrdersFragment;
import com.jimei.xiaolumeimei.ui.fragment.WaitPayOrdersFragment;
import com.jimei.xiaolumeimei.ui.fragment.WaitSendOrdersFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.ChildFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.LadyFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.PreviousFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.TodayFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class AllOrdersActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
  String TAG = "AllOrdersActivity";
  //@Bind(R.id.btn_jump) Button btn_jump;
  //@Bind(R.id.all_orders_listview) ListView all_orders_listview;
  @Bind(R.id.tab_layout)  TabLayout mTabLayout;
  @Bind(R.id.view_pager)  ViewPager mViewPager;
  @Bind(R.id.toolbar) Toolbar toolbar;
  //@Bind(R.id.rlayout_order_empty) RelativeLayout rl_empty;

  //private AllOrdersListAdapter mAllOrderAdapter;
  List<Fragment> fragments;
  List<String> titles;

  @Override protected void setListener() {
    //btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_allorders;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    //ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

    //mAllOrderAdapter = new AllOrdersListAdapter(this);
    //all_orders_listview.setAdapter(mAllOrderAdapter);

    //TextView tx_info = (TextView) findViewById(R.id.tx_info);
    //tx_info.setText("亲，您暂时还没有订单哦~快去看看吧！");
  }

  @Override protected void initData() {
    initFragment();

    initTitles();

    initTabLayout();

    swith_fragment();

  }

  private void initTabLayout() {
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

    MainTabAdapter mAdapter =
            new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(3);
    mTabLayout.setupWithViewPager(mViewPager);
    //mTabLayout.setTabsFromPagerAdapter(mAdapter);
    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
  }

  private void initTitles() {
    titles = new ArrayList<>();
    titles.add("所有订单");
    titles.add("待付款");
    titles.add("待收货");
  }

  private void initFragment() {
    fragments = new ArrayList<>();
    fragments.add(AllOrdersFragment.newInstance("所有订单"));
    fragments.add(WaitPayOrdersFragment.newInstance("待付款"));
    fragments.add(WaitSendOrdersFragment.newInstance("待收货"));

  }

  //从server端获得所有订单数据，可能要查询几次
  /*private void initOrderData() {
    Subscription subscription = TradeModel.getInstance()
        .getAlloderBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<AllOrdersBean>() {
          @Override public void onNext(AllOrdersBean allOrdersBean) {
            List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();

            if (0 == results.size()) {
              JUtils.Log(TAG, "results.size()=0");
              rl_empty.setVisibility(View.VISIBLE);
            } else {
              mAllOrderAdapter.update(results);
            }

            Log.i(TAG, allOrdersBean.toString());
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, " error:, " + e.toString());
            super.onError(e);
          }
        });
    addSubscription(subscription);
  }*/

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {

  }

  /*@Override protected void onResume() {
    JUtils.Log(TAG, "onResume init orderdata");
    super.onResume();
    initOrderData();
  }*/

  public void swith_fragment() {
    int tabid = 0;
    if (getIntent().getExtras() != null) {
      tabid = getIntent().getExtras().getInt("fragment");
      JUtils.Log(TAG, "jump to fragment:" + tabid);
      if ((tabid >= 1) && (tabid <= 3)) {
        try {
          mTabLayout.setScrollPosition(tabid - 1, 0, true);
          mViewPager.setCurrentItem(tabid - 1);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  class MainTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragment;
    private List<String> listTitle;

    public MainTabAdapter(FragmentManager fm, List<Fragment> listFragment,
                          List<String> listTitle) {
      super(fm);
      this.listFragment = listFragment;
      this.listTitle = listTitle;
    }

    @Override public Fragment getItem(int position) {
      return listFragment.get(position);
    }

    @Override public int getCount() {
      return listFragment.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return listTitle.get(position);
    }
  }
}
