package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jimei.library.rx.RxCountDown;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.CircleImageView;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import butterknife.Bind;
import okhttp3.Call;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/05.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WxLoginBindPhoneActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  private static final String TAG = WxLoginBindPhoneActivity.class.getSimpleName();
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.headimage) CircleImageView headimage;
  @Bind(R.id.tv_nickname) TextView tvNickname;
  @Bind(R.id.register_name) ClearEditText registerName;
  @Bind(R.id.checkcode) EditText checkcode;
  @Bind(R.id.getCheckCode) Button getCheckCode;
  @Bind(R.id.next) Button next;
  @Bind(R.id.pass) Button pass;
  private String mobile, invalid_code;
  private String headimgurl;
  private String nickname;
  private Subscription subscribe;

  @Override protected void setListener() {
    next.setOnClickListener(this);
    pass.setOnClickListener(this);
    getCheckCode.setOnClickListener(this);
  }

  @Override protected void initData() {
    if ((headimgurl != null) && (!headimgurl.isEmpty())) {
      OkHttpUtils.get().url(headimgurl).build().execute(new BitmapCallback() {
        @Override public void onError(Call call, Exception e,int id) {

        }

        @Override public void onResponse(Bitmap response,int id) {
          if((headimage != null) && (response != null)) {
            headimage.setImageBitmap(response);
          }
        }
      });
    }
  }

  @Override protected void getBundleExtras(Bundle extras) {
    if (extras != null) {
      headimgurl = extras.getString("headimgurl");
      nickname = extras.getString("nickname");
    } else {
      subscribe = UserModel.getInstance()
          .getUserInfo()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<UserInfoBean>() {
            @Override public void onNext(UserInfoBean user) {
              Log.d(TAG, "getUserInfo:, " + user.toString());
              if ((user != null) ) {
                headimgurl = user.getThumbnail();
                nickname = user.getNick();
              }
            }

            @Override public void onCompleted() {
              super.onCompleted();
            }

            @Override public void onError(Throwable e) {
              LoginUtils.delLoginInfo(mContext);
              Log.e(TAG, "error:, " + e.toString());
              super.onError(e);
            }
          });
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_wxlogin;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    if (nickname != null) {
      tvNickname.setText("微信账号： " + nickname);
    }
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {
      case R.id.next:

        mobile = registerName.getText().toString().trim();
        invalid_code = checkcode.getText().toString().trim();
        if (checkInput(mobile, invalid_code)) {
          bindMobilePhone(mobile, invalid_code);
          /*Intent intent = new Intent(this, BindSettingPasswordActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("username", mobile);
          bundle.putString("valid_code", invalid_code);
          intent.putExtras(bundle);
          startActivity(intent);
          finish();*/
        }

        break;
      case R.id.pass:
        startActivity(new Intent(WxLoginBindPhoneActivity.this, MainActivity.class));
        finish();
        break;
      case R.id.getCheckCode:

        mobile = registerName.getText().toString().trim();
        if (checkMobileInput(mobile)) {
          RxCountDown.countdown(60).doOnSubscribe(new Action0() {
            @Override public void call() {
              getCheckCode.setClickable(false);
              getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));

              subscribe = UserModel.getInstance()
                  .getCodeBean(mobile,"bind")
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CodeBean>() {
                    @Override public void onNext(CodeBean codeBean) {
                      JUtils.Toast(codeBean.getMsg());
                    }
                  });
            }
          }).subscribe(new Subscriber<Integer>() {
            @Override public void onCompleted() {
              getCheckCode.setText("获取验证码");
              getCheckCode.setClickable(true);
              getCheckCode.setBackgroundResource(R.drawable.shape_getcheckcode);
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(Integer integer) {
              getCheckCode.setText(integer + "s后重新获取");
            }
          });
        }

        break;
    }
  }

  public boolean checkMobileInput(String mobile) {

    if (mobile == null || mobile.trim().trim().equals("")) {
      JUtils.Toast("请输入手机号");
    } else {
      if (mobile.length() != 11) {
        JUtils.Toast("请输入正确的手机号");
      } else {
        return true;
      }
    }

    return false;
  }

  public boolean checkInput(String mobile, String checkcode) {

    if (mobile == null || mobile.trim().trim().equals("")) {
      JUtils.Toast("请输入手机号");
    } else {
      if (mobile.length() != 11) {
        JUtils.Toast("请输入正确的手机号");
      } else if (checkcode == null || checkcode.trim().trim().equals("")) {
        JUtils.Toast("验证码不能为空");
      } else {
        return true;
      }
    }

    return false;
  }

  private void bindMobilePhone(String username, String valid_code) {

    JUtils.Log(TAG, "username=" + username + " valid_code=" + valid_code);
    subscribe = UserModel.getInstance()
        .verify_code(username, "bind",valid_code)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CodeBean>() {
          @Override public void onNext(CodeBean codeBean) {
            JUtils.Log(TAG, codeBean.toString());
            int code = codeBean.getRcode();
            if (0 == code) {
              startActivity(new Intent(WxLoginBindPhoneActivity.this, MainActivity.class));
              finish();
            } else{
              JUtils.Toast(codeBean.getMsg());
            }
          }
        });
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
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
}
