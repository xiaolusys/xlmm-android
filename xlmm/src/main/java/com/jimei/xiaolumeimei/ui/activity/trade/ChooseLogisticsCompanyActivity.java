package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
import com.jimei.xiaolumeimei.adapter.MyAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.LogisticsCompanyInfo;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.widget.SideBar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jimei.xiaolumeimei.R;


import butterknife.Bind;
import rx.schedulers.Schedulers;

public class ChooseLogisticsCompanyActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "ChooseLogisticsCompanyActivity";

  @Bind(R.id.lv_logistics_company) ListView lv_logistics_company;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.sideBar) SideBar sideBar;

  TradeModel model = new TradeModel();
  private MyAdapter mCompanyAdapter;
  /**
   * 显示字母的TextView
   */
  @Bind(R.id.dialog) TextView dialog;

  final List<LogisticsCompanyInfo> company_list = new ArrayList<LogisticsCompanyInfo>();

  @Override protected void setListener() {

    toolbar.setOnClickListener(this);

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_logistics_company;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);
    //ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

    fillCompanyInfo();
    mCompanyAdapter = new MyAdapter(this, company_list);
    lv_logistics_company.setAdapter(mCompanyAdapter);
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    sideBar.setTextView(dialog);

    //设置右侧触摸监听
    sideBar.setOnTouchingLetterChangedListener(
        new SideBar.OnTouchingLetterChangedListener() {

          @Override public void onTouchingLetterChanged(String s) {
            //该字母首次出现的位置
            int position = mCompanyAdapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
              lv_logistics_company.setSelection(position);
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

  @Override public void onClick(View v) {
    switch (v.getId()) {

      case R.id.toolbar:
        finish();
        break;
    }
  }



  private void fillCompanyInfo() {

    company_list.add(new LogisticsCompanyInfo("D", "迪邦宅急送"));
    company_list.add(new LogisticsCompanyInfo("H", "华运通物流"));
    company_list.add(new LogisticsCompanyInfo("H", "汇通快递"));
    company_list.add(new LogisticsCompanyInfo("L", "联通快递"));
    company_list.add(new LogisticsCompanyInfo("S", "申通快递"));
    company_list.add(new LogisticsCompanyInfo("S", "顺丰快递"));
    company_list.add(new LogisticsCompanyInfo("S", "顺风快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "亚风快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "壹时通物流"));
    company_list.add(new LogisticsCompanyInfo("Y", "一通快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "一统快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "邮政"));
    company_list.add(new LogisticsCompanyInfo("Y", "圆通速递"));
    company_list.add(new LogisticsCompanyInfo("Z", "宅急送快递"));
    company_list.add(new LogisticsCompanyInfo("Z", "中通速递"));
         /*大田快运：www.dtw.com.cn  18． 联邦快递：:www.fedex.com  19． 联邮速递：www.upex-cn.com  20．
        快马运输：www.fast111.com  21． 华宇物流：www.hoau.net  22． 天天快递：www.ttkdex.com  23． 阳光快递：www.sdyg.b2b.cn  24． 勤诚快递：www.qc-dds.net

        25． DHL快递：www.cn.dhl.com  26． UPS快递：www.ups.com  27． 中外运：www.sinoair.com  28． 中铁快运：www.cre.cn   29． 中诚快递：http://www.zoc.net.cn  30． 中国速递：www.dgpost.com.cn  31． 中邦速递：www.szzbsd.com  32． 中驿快递：www.zykd.com  33． 飞马快递：www.horse-ex.com  34． 飞鸿快递：www.feihong.org  35． 飞扬快递：www.feihong.org  36． 飞天达快递：www.98933.net  37． 韵达货运：www.yundaex.com  38． 闻达快递：www.wendaexpress.com  39． 驱达国际快递：www.fardar.com  40． 安信达快递：www.anxinda.com  41． 飞达急便速递：www.ping-chi.com  42． 小红马快递：www.ponyex.com.cn  43． 远顺物流：www.ysfreight.com  44． 快捷顺速递：www.szkjs.com/new  45． 奇速快递：www.shseis.net  46． 捷安快递：www.htky365.com  47． 星辉快递：www.samvay.com  48． 国鑫快递：www.uppollo.com  49． 京广快递：www.kke.com.hk

        50． 越丰物流：www.yfexpress.com.hk  51． 捷森物流：wwww.js56.b2b.cn  52． 泽龙物流：www.zelong.b2b.cn  53． 超联物流：www.sul.cn  54． 全一快运：www.apex100.comt  55． 佳吉快运：www.jiaji.com  56． 佳加物流：www.sh56.cn
    */
  }
}
