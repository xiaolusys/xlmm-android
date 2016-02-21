package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BindInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.rx.RxCountDown;
import com.jimei.xiaolumeimei.widget.CircleImageView;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import okhttp3.Call;
import rx.Subscriber;
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
  UserModel model = new UserModel();
  private String mobile, invalid_code;
  private String headimgurl;
  private String nickname;

  @Override protected void setListener() {
    next.setOnClickListener(this);
    pass.setOnClickListener(this);
    getCheckCode.setOnClickListener(this);
  }

  @Override protected void initData() {
    OkHttpUtils.get().url(headimgurl).build().execute(new BitmapCallback() {
      @Override public void onError(Call call, Exception e) {

      }

      @Override public void onResponse(Bitmap response) {
        headimage.setImageBitmap(response);
      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {

    headimgurl = extras.getString("headimgurl");
    nickname = extras.getString("nickname");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_wxlogin;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    tvNickname.setText("微信账号： " + nickname);
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
          Intent intent = new Intent(this, BindSettingPasswordActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("username", mobile);
          bundle.putString("valid_code", invalid_code);
          intent.putExtras(bundle);
          startActivity(intent);
          finish();
        }

        break;
      case R.id.pass:

        finish();

        break;
      case R.id.getCheckCode:

        mobile = registerName.getText().toString().trim();
        if (checkMobileInput(mobile)) {
          RxCountDown.countdown(60).doOnSubscribe(new Action0() {
            @Override public void call() {
              getCheckCode.setClickable(false);
              getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));

              model.bang_mobile_code(mobile)
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<BindInfoBean>() {
                    @Override public void onNext(BindInfoBean responseBody) {
                      super.onNext(responseBody);
                      int code = responseBody.getCode();
                      if (0 == code) {
                        JUtils.Toast("验证码获取成功");
                      } else if (1 == code) {
                        JUtils.Toast("手机号码已经注册");
                      }
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
}
