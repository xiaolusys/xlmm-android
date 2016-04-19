package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CompanyAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.LogisticsCompanyInfo;
import com.jimei.xiaolumeimei.widget.SideBar;
import java.util.ArrayList;
import java.util.List;

public class ChooseLogisticsCompanyActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  final List<LogisticsCompanyInfo> company_list = new ArrayList<LogisticsCompanyInfo>();
  String TAG = "ChooseLogisticsCompanyActivity";
  @Bind(R.id.lv_logistics_company) ListView lv_logistics_company;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.sideBar) SideBar sideBar;
  /**
   * 显示字母的TextView
   */
  @Bind(R.id.dialog) TextView dialog;
  private CompanyAdapter mCompanyAdapter;

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
    finishBack(toolbar);
    //ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

    fillCompanyInfo();
    mCompanyAdapter = new CompanyAdapter(this, company_list);
    lv_logistics_company.setAdapter(mCompanyAdapter);
    lv_logistics_company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onItemClick " + arg2 + " " + company_list.get(arg2).getName());
        Intent intent = new Intent(ChooseLogisticsCompanyActivity.this,
            WriteLogisticsInfoActivty.class);
        intent.putExtra("company", company_list.get(arg2).getName());
        setResult(1, intent);
        finish();
      }
    });
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

    }
  }

  private void fillCompanyInfo() {
    company_list.add(new LogisticsCompanyInfo("A", "安信达快递"));
    company_list.add(new LogisticsCompanyInfo("C", "超联物流"));
    company_list.add(new LogisticsCompanyInfo("D", "大田快运"));
    company_list.add(new LogisticsCompanyInfo("D", "德邦快递"));
    company_list.add(new LogisticsCompanyInfo("D", "DHL快递"));
    company_list.add(new LogisticsCompanyInfo("D", "迪邦宅急送"));
    company_list.add(new LogisticsCompanyInfo("F", "飞达急便速递"));
    company_list.add(new LogisticsCompanyInfo("F", "飞鸿快递"));
    company_list.add(new LogisticsCompanyInfo("F", "飞马快递"));
    company_list.add(new LogisticsCompanyInfo("F", "飞天达快递"));
    company_list.add(new LogisticsCompanyInfo("F", "飞扬快递"));
    company_list.add(new LogisticsCompanyInfo("G", "国鑫快递"));
    company_list.add(new LogisticsCompanyInfo("H", "华运通物流"));
    company_list.add(new LogisticsCompanyInfo("H", "华宇物流"));
    company_list.add(new LogisticsCompanyInfo("H", "汇通快递"));
    company_list.add(new LogisticsCompanyInfo("J", "佳吉快运"));
    company_list.add(new LogisticsCompanyInfo("J", "佳加物流"));
    company_list.add(new LogisticsCompanyInfo("J", "捷安快递"));
    company_list.add(new LogisticsCompanyInfo("J", "捷森物流"));
    company_list.add(new LogisticsCompanyInfo("J", "京广快递"));
    company_list.add(new LogisticsCompanyInfo("K", "快捷顺速递"));
    company_list.add(new LogisticsCompanyInfo("K", "快马运输"));
    company_list.add(new LogisticsCompanyInfo("L", "联邦快递"));
    company_list.add(new LogisticsCompanyInfo("L", "联通快递"));
    company_list.add(new LogisticsCompanyInfo("L", "联邮速递"));
    company_list.add(new LogisticsCompanyInfo("Q", "勤诚快递"));
    company_list.add(new LogisticsCompanyInfo("Q", "奇速快递"));
    company_list.add(new LogisticsCompanyInfo("Q", "驱达国际快递"));
    company_list.add(new LogisticsCompanyInfo("Q", "全一快运"));
    company_list.add(new LogisticsCompanyInfo("S", "申通快递"));
    company_list.add(new LogisticsCompanyInfo("S", "顺丰快递"));
    company_list.add(new LogisticsCompanyInfo("S", "顺风快递"));
    company_list.add(new LogisticsCompanyInfo("T", "天天快递"));
    company_list.add(new LogisticsCompanyInfo("U", "UPS快递"));
    company_list.add(new LogisticsCompanyInfo("W", "闻达快递"));
    company_list.add(new LogisticsCompanyInfo("X", "小红马快递"));
    company_list.add(new LogisticsCompanyInfo("X", "星辉快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "亚风快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "阳光快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "壹时通物流"));
    company_list.add(new LogisticsCompanyInfo("Y", "一通快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "一统快递"));
    company_list.add(new LogisticsCompanyInfo("Y", "邮政EMS"));
    company_list.add(new LogisticsCompanyInfo("Y", "远顺物流"));
    company_list.add(new LogisticsCompanyInfo("Y", "圆通速递"));
    company_list.add(new LogisticsCompanyInfo("Y", "越丰物流"));
    company_list.add(new LogisticsCompanyInfo("Y", "韵达货运"));
    company_list.add(new LogisticsCompanyInfo("Z", "宅急送快递"));
    company_list.add(new LogisticsCompanyInfo("Z", "泽龙物流"));
    company_list.add(new LogisticsCompanyInfo("Z", "中邦速递"));
    company_list.add(new LogisticsCompanyInfo("Z", "中诚快递"));
    company_list.add(new LogisticsCompanyInfo("Z", "中国速递"));
    company_list.add(new LogisticsCompanyInfo("Z", "中铁快运"));
    company_list.add(new LogisticsCompanyInfo("Z", "中通速递"));
    company_list.add(new LogisticsCompanyInfo("Z", "中外运"));
    company_list.add(new LogisticsCompanyInfo("Z", "中驿快递"));
  }
}
