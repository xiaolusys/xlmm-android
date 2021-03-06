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
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;

import butterknife.Bind;

public class SettingPasswordForgetActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
    String TAG = "SettingPasswordForgetActivity";
    @Bind(R.id.set_password)
    EditText etPassword;
    @Bind(R.id.set_password2)
    EditText etPassword2;
    @Bind(R.id.set_commit_button)
    Button commit_button;
    @Bind(R.id.layout)
    LinearLayout layout;
    String mobile;
    String valid_code;

    @Override
    protected void setListener() {
        commit_button.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mobile = extras.getString("mobile");
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
                        changePassword(mobile, valid_code, password1, password2);
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

    private void changePassword(String mobile, String valid_code, String password1,
                                String password2) {
        addSubscription(XlmmApp.getUserInteractor(this)
            .resetPassword(mobile, password1, password2, valid_code, new ServiceResponse<CodeBean>() {
                @Override
                public void onNext(CodeBean bean) {
                    JUtils.Toast(bean.getMsg());
                    if (bean.getRcode() == 0) {
                        readyGoThenKill(TabActivity.class);
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
