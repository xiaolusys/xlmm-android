package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class SettingNicknameActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    String TAG = "SettingNicknameActivity";
    @Bind(R.id.toolbar)    Toolbar toolbar;
    @Bind(R.id.set_nick_name)    EditText nameEditText;
    @Bind(R.id.set_save_button)    Button save_button;
    UserModel model = new UserModel();
    UserInfoBean userinfo;
    NicknameBean nicknameBean = new NicknameBean();
    String nick_name_value;
    int userid;

    @Override
    protected void setListener() {
        save_button.setOnClickListener(this);
        toolbar.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getUserInfo();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.setting_nickname_activity;
    }

    @Override
    protected void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_save_button:

                nick_name_value = nameEditText.getText().toString().trim();
                nicknameBean.setNick(nick_name_value);
                Log.d(TAG, "nick_name_value " + nick_name_value);
                if (checkInput(nick_name_value)) {
                    if (null != userinfo) {
                        Log.d(TAG, "userinfo nick_name_value " + nicknameBean.getNick());
                        setNickname();
                    }
                } else {
                    Toast.makeText(mContext, "昵称长度或者字符错误,请检查", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.toolbar:
                finish();
                break;
        }
    }

    private boolean checkInput(String name) {
        if (name.length() < 4 || name.length() > 20) {
            JUtils.Toast("请输入4-20位昵称");
            return false;
        }
        for(int i = 0; i< name.length(); i++){
            if(false == Character.isLetter( name.charAt(i))
                    && false == Character.isDigit(name.charAt(i))
                    && '-' !=  name.charAt(i)){
                JUtils.Toast("字符不符合规范,只能是字母、数字、-字符.");
                return false;
            }
        }

        return true;
    }

    private void setNickname() {
        model.setNickname(userid, nicknameBean)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<UserBean>() {
                    @Override
                    public void onNext(UserBean user) {
                        Log.d(TAG, "user.getCode() " + user.getCode() + ", user.getResult() " + user.getResult());

                        if (user.getCode() == 0) {
                            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, SettingActivity.class);
                            startActivity(intent);
                            finish();
                         } else {

                          Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
                         }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });
    }

    private void getUserInfo() {
        UserModel model = new UserModel();
        model.getUserInfo()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean user) {
                        userinfo = user;
                        Log.d(TAG, "getUserInfo:, "   + userinfo.getResults().get(0).toString());

                        nameEditText.setText(userinfo.getResults().get(0).getNick());
                        userid = userinfo.getResults().get(0).getId();
                        Log.d(TAG, "getUserInfo nick "+userinfo.getResults().get(0).getNick() + " userid " + userid);

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "error:, "   + e.toString());
                        super.onError(e);
                    }
                });

    }
}
