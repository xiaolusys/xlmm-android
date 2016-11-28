package com.jimei.xiaolumeimei.ui.fragment.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/30.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class FirstFragment extends DialogFragment {

  @Bind(R.id.confirm) ImageView confirm;
  @Bind(R.id.close) ImageView close;
  private Activity mActivity;

  public static FirstFragment newInstance(String title) {
    FirstFragment todayFragment = new FirstFragment();
    Bundle bundle = new Bundle();
    bundle.putString("title", title);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = activity;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int style = DialogFragment.STYLE_NO_TITLE;
    setStyle(style, 0);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.first_layout, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    close.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        LoginUtils.saveFirst(mActivity, true);
        dismiss();
      }
    });

    confirm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        LoginUtils.saveFirst(mActivity, true);
        Intent intent = new Intent(mActivity, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("login", "getCoupon");
        intent.putExtras(bundle);
        dismiss();
        startActivity(intent);
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onStop() {
    super.onStop();
  }

  @Override public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
  }

  @Override public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
  }
}
