package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.LogisticCompanyAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LogisticsCompanyInfo;
import com.jimei.xiaolumeimei.widget.SideBar;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ChooseLogisticsCompanyActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    final List<LogisticsCompanyInfo> company_list = new ArrayList<LogisticsCompanyInfo>();
    @Bind(R.id.lv_logistics_company)
    ListView lv_logistics_company;
    @Bind(R.id.sideBar)
    SideBar sideBar;
    @Bind(R.id.et)
    EditText editText;
    @Bind(R.id.btn)
    Button button;
    /**
     * 显示字母的TextView
     */
    @Bind(R.id.dialog)
    TextView dialog;
    private LogisticCompanyAdapter mCompanyAdapter;

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_logistics_company;
    }

    @Override
    protected void initViews() {
        fillCompanyInfo();
        mCompanyAdapter = new LogisticCompanyAdapter(this, company_list);
        lv_logistics_company.setAdapter(mCompanyAdapter);
        lv_logistics_company.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            Intent intent = new Intent(ChooseLogisticsCompanyActivity.this,
                    WriteLogisticsInfoActivty.class);
            intent.putExtra("company", company_list.get(arg2).getName());
            setResult(1, intent);
            finish();
        });
    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(
                s -> {
                    //该字母首次出现的位置
                    int position = mCompanyAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        lv_logistics_company.setSelection(position);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (editText.getText().toString().trim().length() >= 2) {
                    Intent intent = new Intent(ChooseLogisticsCompanyActivity.this,
                            WriteLogisticsInfoActivty.class);
                    intent.putExtra("company", editText.getText().toString().trim());
                    setResult(1, intent);
                    finish();
                } else {
                    JUtils.Toast("请填写快递名称");
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logistic, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.write:
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_write_logistic, null);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setView(view)
                        .create();
                dialog.show();
                EditText editText = (EditText) view.findViewById(R.id.et);
                View cancelBtn = view.findViewById(R.id.cancel);
                View commitBtn = view.findViewById(R.id.commit);
                cancelBtn.setOnClickListener(v -> dialog.dismiss());
                commitBtn.setOnClickListener(v -> {
                    if (editText.getText().toString().trim().length() >= 2) {
                        Intent intent = new Intent(ChooseLogisticsCompanyActivity.this,
                                WriteLogisticsInfoActivty.class);
                        intent.putExtra("company", editText.getText().toString().trim());
                        setResult(1, intent);
                        dialog.dismiss();
                        finish();
                    } else {
                        JUtils.Toast("请输入正确的快递名称");
                    }
                });
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    private void fillCompanyInfo() {
        company_list.add(new LogisticsCompanyInfo("A", "安信达快递"));
        company_list.add(new LogisticsCompanyInfo("B", "百世汇通"));
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
        company_list.add(new LogisticsCompanyInfo("J", "佳吉快运"));
        company_list.add(new LogisticsCompanyInfo("J", "佳加物流"));
        company_list.add(new LogisticsCompanyInfo("J", "捷安快递"));
        company_list.add(new LogisticsCompanyInfo("J", "捷森物流"));
        company_list.add(new LogisticsCompanyInfo("J", "京广快递"));
        company_list.add(new LogisticsCompanyInfo("K", "快捷快递"));
        company_list.add(new LogisticsCompanyInfo("K", "快马运输"));
        company_list.add(new LogisticsCompanyInfo("L", "联邦快递"));
        company_list.add(new LogisticsCompanyInfo("L", "联通快递"));
        company_list.add(new LogisticsCompanyInfo("L", "联邮速递"));
        company_list.add(new LogisticsCompanyInfo("Q", "勤诚快递"));
        company_list.add(new LogisticsCompanyInfo("Q", "奇速快递"));
        company_list.add(new LogisticsCompanyInfo("Q", "驱达国际快递"));
        company_list.add(new LogisticsCompanyInfo("Q", "全一快运"));
        company_list.add(new LogisticsCompanyInfo("Q", "全峰快递"));
        company_list.add(new LogisticsCompanyInfo("S", "申通快递"));
        company_list.add(new LogisticsCompanyInfo("S", "顺丰速运"));
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
        company_list.add(new LogisticsCompanyInfo("Y", "韵达快递"));
        company_list.add(new LogisticsCompanyInfo("Z", "宅急送"));
        company_list.add(new LogisticsCompanyInfo("Z", "泽龙物流"));
        company_list.add(new LogisticsCompanyInfo("Z", "中邦速递"));
        company_list.add(new LogisticsCompanyInfo("Z", "中诚快递"));
        company_list.add(new LogisticsCompanyInfo("Z", "中国速递"));
        company_list.add(new LogisticsCompanyInfo("Z", "中铁快运"));
        company_list.add(new LogisticsCompanyInfo("Z", "中通快递"));
        company_list.add(new LogisticsCompanyInfo("Z", "中外运"));
        company_list.add(new LogisticsCompanyInfo("Z", "中驿快递"));
    }
}
