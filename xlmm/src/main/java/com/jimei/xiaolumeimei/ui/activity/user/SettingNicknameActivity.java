package com.jimei.xiaolumeimei.ui.activity.user;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.InformationEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class SettingNicknameActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    String TAG = "SettingNicknameActivity";
    @Bind(R.id.set_nick_name)
    EditText nameEditText;
    @Bind(R.id.set_save_button)
    Button save_button;
    @Bind(R.id.layout)
    LinearLayout layout;
    UserInfoBean userinfo;
    NicknameBean nicknameBean = new NicknameBean();
    String nick_name_value;
    int userid;

    @Override
    protected void setListener() {
        save_button.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getUserInfo();
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.setting_nickname_activity;
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
                    JUtils.Toast("昵称长度或者字符错误,请检查");
                }
                break;
        }
    }

    private boolean checkInput(String name) {
        if (name.length() < 4 || name.length() > 20) {
            JUtils.Toast("请输入4-20位昵称");
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && !Character.isDigit(
                    name.charAt(i)) && '-' != name.charAt(i)) {
                JUtils.Toast("字符不符合规范,只能是字母、数字、-字符.");
                return false;
            }
        }

        return true;
    }

    private void setNickname() {
        addSubscription(UserModel.getInstance()
                .setNickname(userid, nicknameBean)
                .subscribe(new ServiceResponse<UserBean>() {
                    @Override
                    public void onNext(UserBean user) {
                        EventBus.getDefault().post(new InformationEvent());
                        if (user.getCode() == 0) {
                            JUtils.Toast("修改成功");
                            finish();
                        } else {
                            JUtils.Toast("修改失败");
                        }
                    }
                }));
    }

    private void getUserInfo() {
        addSubscription(UserModel.getInstance()
                .getUserInfo()
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean user) {
                        userinfo = user;
                        nameEditText.setText(userinfo.getNick());
                        userid = userinfo.getId();
                    }
                }));
    }

}
