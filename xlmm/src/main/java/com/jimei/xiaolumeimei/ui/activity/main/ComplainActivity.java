package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ComplainActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, TextWatcher {
    @Bind(R.id.commit)
    Button confirm;
    @Bind(R.id.complain_text)
    EditText complainText;
    @Bind(R.id.count_text)
    TextView countText;
    @Bind(R.id.rg)
    RadioGroup radioGroup;
    String com_type = "1";

    @Override
    protected void setListener() {
        confirm.setOnClickListener(this);
        complainText.addTextChangedListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.complain_activity;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commit) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_1:
                    com_type = "1";
                    break;
                case R.id.rb_2:
                    com_type = "2";
                    break;
                case R.id.rb_3:
                    com_type = "3";
                    break;
                case R.id.rb_4:
                    com_type = "4";
                    break;
            }
            String text = complainText.getText().toString().trim();
            if (!text.equals("")) {
                Subscription subscribe = UserModel.getInstance()
                        .complain(com_type, text)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<AddressResultBean>() {
                            @Override
                            public void onNext(AddressResultBean responseBody) {
                                JUtils.Toast("您的建议已提交，我们会尽快处理,谢谢");
                            }
                        });
                addSubscription(subscribe);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                JUtils.Toast("请输入有效的建议内容");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String countStr = s.length() + "/200";
        countText.setText(countStr);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complain:
                Intent intent = new Intent(this, ComplainWebActivity.class);
                Bundle bundle = new Bundle();
                SharedPreferences sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                bundle.putString("cookies", sharedPreferences.getString("cookiesString", ""));
                bundle.putString("domain", sharedPreferences.getString("cookiesDomain", ""));
                bundle.putString("Cookie", sharedPreferences.getString("Cookie", ""));
                bundle.putString("actlink", XlmmApi.getAppUrl() + "/mall/complaint/history");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complain, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
