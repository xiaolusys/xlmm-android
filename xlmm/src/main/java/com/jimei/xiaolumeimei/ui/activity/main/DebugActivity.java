package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.LogoutBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye on 16/5/9.
 */
public class DebugActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.edit_debug)
    EditText editDebug;
    @Bind(R.id.bt_debug)
    Button btDebug;

    SharedPreferences sharedPreferences;
    String textDebug;
    String textPass;
    @Bind(R.id.edit_pass)
    EditText editPass;
    @Bind(R.id.staging)
    RadioButton staging;
    @Bind(R.id.m)
    RadioButton m;
    @Bind(R.id.xiuqing)
    RadioButton xiuqing;
    @Bind(R.id.huang)
    RadioButton huang;
    @Bind(R.id.lin)
    RadioButton lin;
    @Bind(R.id.lei)
    RadioButton lei;
    @Bind(R.id.shawn)
    RadioButton shawn;
    @Bind(R.id.bo)
    RadioButton bo;
    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.desc)
    TextView desc;


    @Override
    protected void setListener() {
        btDebug.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        sharedPreferences = getSharedPreferences("APICLIENT", MODE_PRIVATE);
        String str = sharedPreferences.getString("BASE_URL", "m.xiaolumeimei.com");
        desc.setText("当前API地址为:" + str);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_debug;
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_debug) {
            if (TextUtils.isEmpty(editDebug.getText())) {
                JUtils.Toast("请输入API地址");
                return;
            }

            if (TextUtils.isEmpty(editPass.getText())) {
                JUtils.Toast("请输入密码");
                return;
            }

            textDebug = editDebug.getText().toString().trim();
            textPass = editPass.getText().toString().trim();

            addSubscription(XlmmApp.getUserInteractor(this)
                .openDebug(textPass, new ServiceResponse<CodeBean>() {
                    @Override
                    public void onNext(CodeBean codeBean) {
                        if (null != codeBean) {
                            if (codeBean.getRcode() == 0) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("BASE_URL", textDebug);
                                boolean isPut = editor.commit();
                                if (isPut) {
                                    final String finalAccount =
                                        LoginUtils.getUserAccount(getApplicationContext());
                                    addSubscription(XlmmApp.getUserInteractor(DebugActivity.this)
                                        .customerLogout(new ServiceResponse<LogoutBean>() {
                                            @Override
                                            public void onNext(LogoutBean responseBody) {
                                                super.onNext(responseBody);
                                                if (responseBody.getCode() == 0) {
                                                    JUtils.Toast("成功进入debug模式");
                                                    if ((finalAccount != null) && ((!finalAccount.isEmpty()))) {
                                                        MiPushClient.unsetUserAccount(getApplicationContext(),
                                                            finalAccount, null);
                                                    }

                                                    LoginUtils.delLoginInfo(getApplicationContext());
                                                    Intent intent =
                                                        new Intent(DebugActivity.this, SplashActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    Process.killProcess(Process.myPid());
                                                    System.exit(1);
                                                }
                                            }
                                        }));
                                }
                            }
                            JUtils.Toast(codeBean.getMsg());
                        }
                    }
                }));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.staging:
                editDebug.setText(staging.getText().toString().trim());
                break;
            case R.id.m:
                editDebug.setText(m.getText().toString().trim());
                break;
            case R.id.xiuqing:
                editDebug.setText(xiuqing.getText().toString().trim());
                break;
            case R.id.huang:
                editDebug.setText(huang.getText().toString().trim());
                break;
            case R.id.lin:
                editDebug.setText(lin.getText().toString().trim());
                break;
            case R.id.lei:
                editDebug.setText(lei.getText().toString().trim());
                break;
            case R.id.shawn:
                editDebug.setText(shawn.getText().toString().trim());
                break;
            case R.id.bo:
                editDebug.setText(bo.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
