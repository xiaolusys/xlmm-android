package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.annotations.SerializedName;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.widget.citypicker.CityPicker;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddAddressActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  AddressModel model = new AddressModel();
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.name) EditText name;
  @Bind(R.id.mobile) EditText mobile;
  @Bind(R.id.clear_address) EditText clearAddress;
  @Bind(R.id.switch_button) SwitchCompat switchButton;
  @Bind(R.id.save) Button save;
  @Bind(R.id.address) TextView address;
  private PopupWindow popupWindow;
  private View view;
  private CityPicker cityPicker;
  private View parent;
  private String receiver_state;
  private String receiver_city;
  private String receiver_district;
  private String receiver_address;
  private String receiver_name;
  private String receiver_mobile;
  private boolean isDefault;

  @Override protected void setListener() {
    save.setOnClickListener(this);
    address.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.addaddress_activity;
  }

  @Override protected void initViews() {
    parent = findViewById(R.id.main);

    initPopupWindow();
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.address:
        //为popWindow添加动画效果
        popupWindow.setAnimationStyle(R.style.popWindow_animation);
        // 点击弹出泡泡窗口
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        cityPicker.setOnSelectingListener(selected -> {
          if (selected) {
            address.setText(cityPicker.getCity_string());
            receiver_state = cityPicker.getCity_receiver_state();
            receiver_district = cityPicker.getCity_receiver_district();
            receiver_city = cityPicker.getCity_receiver_city();
          }
        });
        break;
      case R.id.save:

        receiver_name = name.getText().toString().trim();
        receiver_address = address.getText().toString().trim();
        receiver_mobile = mobile.getText().toString().trim();

        checkkInput();

        model.create_address(receiver_state, receiver_city, receiver_district,
            receiver_address, receiver_name, receiver_mobile)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<AddressResultBean>() {
              @Override public void onNext(AddressResultBean addressResultBean) {
                super.onNext(addressResultBean);

                if (addressResultBean.isRet()) {
                  startActivity(
                      new Intent(AddAddressActivity.this, AddressActivity.class));
                  AddAddressActivity.this.finish();
                }
              }
            });

        break;
    }
  }

  private void checkkInput() {
    if (receiver_mobile.length() != 11) {
      JUtils.Toast("请输入正确的手机号");
      return;
    }

    //if (receiver_address == null || receiver_name == null || receiver_mobile == null) {
    //  JUtils.Toast();
    //
    //}
  }

  private void initPopupWindow() {
    view = getLayoutInflater().inflate(R.layout.item_popwindow, null);

    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);

    popupWindow.setFocusable(true);
    popupWindow.setBackgroundDrawable(new BitmapDrawable());
    popupWindow.setOutsideTouchable(true);
    cityPicker = (CityPicker) view.findViewById(R.id.city_picker);
  }

  class Result {

    /**
     * ret : true
     */

    @SerializedName("ret") private boolean mRet;

    public boolean isRet() {
      return mRet;
    }

    public void setRet(boolean ret) {
      this.mRet = ret;
    }
  }
}