package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.util.LoginUtils;

import butterknife.Bind;

public class EditPasswordForgetActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
    String TAG = "EditPasswordForgetActivity";
    @Bind(R.id.set_password)
    EditText etPassword;
    @Bind(R.id.set_password2)
    EditText etPassword2;
    @Bind(R.id.set_commit_button)
    Button commit_button;
    String username;
    String valid_code;
    @Bind(R.id.layout)
    LinearLayout layout;

    @Override
    protected void setListener() {
        commit_button.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        username = extras.getString("username");
        valid_code = extras.getString("valid_code");
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.setting_password_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_commit_button:
                String password1 = etPassword.getText().toString().trim();
                String password2 = etPassword2.getText().toString().trim();
                Log.d(TAG, "password " + password1 + " " + password2);
                if (checkInput(password1) && checkInput(password2)) {
                    if (checkInputSame(password1, password2)) {
                        changePassword(username, valid_code, password1, password2);
                    }
                }
                break;
        }
    }

    private boolean checkInput(String name) {
        if (name.length() < 4 || name.length() > 20) {
            JUtils.Toast("请输入6-16位密码");
            return false;
        }
        return true;
    }

    private void changePassword(String username, String valid_code, String password1,
                                String password2) {
        addSubscription(XlmmApp.getUserInteractor(this)
            .resetPassword(username, password1, password2, valid_code, new ServiceResponse<CodeBean>() {
                @Override
                public void onNext(CodeBean codeBean) {
                    if (codeBean.getRcode() == 0) {
                        addSubscription(XlmmApp.getUserInteractor(EditPasswordForgetActivity.this)
                            .passwordLogin(username, password1, null, new ServiceResponse<CodeBean>() {
                                @Override
                                public void onNext(CodeBean codeBean1) {
                                    if (codeBean1.getRcode() == 0) {
                                        addSubscription(XlmmApp.getMainInteractor(EditPasswordForgetActivity.this)
                                            .getProfile(new ServiceResponse<UserInfoBean>() {
                                                @Override
                                                public void onNext(UserInfoBean userInfoBean) {
                                                    if (userInfoBean != null && userInfoBean.getXiaolumm() != null &&
                                                        userInfoBean.getXiaolumm().getId() != 0) {
                                                        LoginUtils.saveLoginInfo(true, getApplicationContext(), username,
                                                            password1);
                                                        JUtils.Toast("密码重置成功,登录成功");
                                                        readyGoThenKill(TabActivity.class);
                                                    } else {
                                                        JUtils.Toast("你不是小鹿妈妈，暂无权限登录哦!");
                                                    }
                                                }
                                            }));
                                    } else {
                                        JUtils.Toast(codeBean1.getMsg());
                                    }
                                }
                            }));
                    } else {
                        JUtils.Toast("修改失败");
                    }
                }
            }));
    }

    private boolean checkInputSame(String pass1, String pass2) {
        if (!pass1.equals(pass2)) {
            JUtils.Toast("两次密码不一致");
            return false;
        }
        return true;
    }

}
