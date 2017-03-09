package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.ClearEditText;
import com.jimei.library.widget.PasswordEditText;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.event.LoginEvent;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.jimei.xiaolumeimei.util.LoginUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class PhoneLoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, TextWatcher {

    private static final String TAG = PhoneLoginActivity.class.getSimpleName();
    @Bind(R.id.set_login_name)
    ClearEditText nameEditText;
    @Bind(R.id.set_login_password)
    PasswordEditText passEditText;
    @Bind(R.id.set_login_button)
    Button login_button;
    @Bind(R.id.forgetTextView)
    TextView forGetTextView;
    @Bind(R.id.cb_pwd)
    CheckBox cbPwd;
    @Bind(R.id.layout)
    LinearLayout layout;
    String login_name_value;//登录名
    String login_pass_value;//登录密码
    private String actlink;
    private String title;
    private int id;

    @Override
    protected void setListener() {
        login_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        forGetTextView.setOnClickListener(this);
        nameEditText.addTextChangedListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initData() {
        if (!LoginUtils.checkLoginState(getApplicationContext())) {
            removeWX(new Wechat(this));
        }
        String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());
        JUtils.Log(TAG, loginInfo[0] + "=====" + loginInfo[1]);
        nameEditText.setText(loginInfo[0]);
        passEditText.setText(loginInfo[1]);
    }

    public void removeWX(Platform platform) {
        if (platform != null) {
            platform.removeAccount(true);
            platform.removeAccount();
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected void initViews() {
        ShareSDK.initSDK(this);
        forGetTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_login_button:
                login_name_value = nameEditText.getText().toString().trim();
                login_pass_value = passEditText.getText().toString().trim();
                if (checkInput(login_name_value, login_pass_value)) {
                    showIndeterminateProgressDialog(false);
                    SharedPreferences sharedPreferences =
                        getSharedPreferences("password", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (cbPwd.isChecked()) {
                        editor.putString(login_name_value, login_pass_value);
                    } else {
                        editor.remove(login_name_value);
                    }
                    editor.apply();
                    addSubscription(XlmmApp.getUserInteractor(this)
                        .passwordLogin(login_name_value, login_pass_value, null, new ServiceResponse<CodeBean>() {
                            @Override
                            public void onNext(CodeBean codeBean) {
                                if (codeBean.getRcode() == 0) {
                                    EventBus.getDefault().post(new SetMiPushEvent());
                                    hideIndeterminateProgressDialog();
                                    LoginUtils.saveLoginInfo(true, getApplicationContext(),
                                        login_name_value, login_pass_value);
                                    EventBus.getDefault().post(new LoginEvent());
                                    JUtils.Toast("登录成功!");
                                    String login = null;
                                    if (getIntent() != null && getIntent().getExtras() != null) {
                                        login = getIntent().getExtras().getString("login");
                                        actlink = getIntent().getExtras().getString("actlink");
                                        title = getIntent().getExtras().getString("title");
                                        id = getIntent().getExtras().getInt("id");
                                    }

                                    if (login != null) {
                                        if (login.equals("push_jump")) {
                                            JumpUtils.push_jump_proc(PhoneLoginActivity.this, actlink);
                                            finish();
                                        } else if (login.equals("productdetail")) {
                                            Intent intent = new Intent(mContext, ProductDetailActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("model_id", id);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        } else if (login.equals("h5")) {
                                            Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                                            SharedPreferences sharedPreferences =
                                                getSharedPreferences("xlmmCookiesAxiba",
                                                    Context.MODE_PRIVATE);
                                            String cookies = sharedPreferences.getString("cookiesString", "");
                                            String domain = sharedPreferences.getString("cookiesDomain", "");
                                            Bundle bundle = new Bundle();
                                            bundle.putString("cookies", cookies);
                                            bundle.putString("domain", domain);
                                            bundle.putString("actlink", actlink);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        } else if (login.equals("car") || login.equals("my") || login.equals("boutique")) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("flag", login);
                                            readyGoThenKill(TabActivity.class, bundle);
                                        } else {
                                            finish();
                                        }
                                    }
                                } else {
                                    hideIndeterminateProgressDialog();
                                    LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");
                                    JUtils.Toast(codeBean.getMsg());
                                }
                            }

                            @Override
                            public void onCompleted() {

                            }
                        }));
                }
                break;
            case R.id.forgetTextView:
                startActivity(new Intent(this, VerifyPhoneForgetActivity.class));
                finish();
                break;
        }
    }

    public boolean checkInput(String mobile, String password) {
        if (mobile == null || mobile.trim().trim().equals("")) {
            JUtils.Toast("请输入手机号");
        } else {
            if (mobile.length() != 11) {
                JUtils.Toast("请输入正确的手机号");
            } else if (password == null || password.trim().trim().equals("")) {
                JUtils.Toast("密码不能为空");
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String phone = s.toString();
        SharedPreferences sharedPreferences =
            getSharedPreferences("password", Context.MODE_PRIVATE);
        String password = sharedPreferences.getString(phone, "");
        if (!password.equals("")) {
            passEditText.setText(password);
            cbPwd.setChecked(true);
        } else {
            passEditText.setText("");
            cbPwd.setChecked(false);
        }
    }
}
