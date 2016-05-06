package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.widget.citypicker.CityPicker;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddAddressActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

  private static final String TAG = AddAddressActivity.class.getSimpleName();

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
  private String city_string;
  private String clearaddressa;
  private String defalut;

  @Override protected void setListener() {
    save.setOnClickListener(this);
    address.setOnClickListener(this);
    switchButton.setOnCheckedChangeListener(this);
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

        InputMethodManager imm =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mobile.getWindowToken(), 0);

        //为popWindow添加动画效果
        popupWindow.setAnimationStyle(R.style.popWindow_animation);
        // 点击弹出泡泡窗口
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        cityPicker.setOnSelectingListener(new CityPicker.OnSelectingListener() {
          @Override public void selected(boolean selected) {
            if (selected) {
              city_string = cityPicker.getCity_string();
              address.setText(cityPicker.getCity_string());

              receiver_state = cityPicker.getCity_receiver_state();
              receiver_district = cityPicker.getCity_receiver_district();
              receiver_city = cityPicker.getCity_receiver_city();
              JUtils.Log(TAG, receiver_state + receiver_district + receiver_city);
            }
          }
        });
        break;
      case R.id.save:

        receiver_name = name.getText().toString().trim();
        receiver_address = address.getText().toString().trim();
        receiver_mobile = mobile.getText().toString().trim();
        clearaddressa = clearAddress.getText().toString().trim();

        if (checkInput(receiver_name, receiver_mobile, city_string, clearaddressa)) {
          Subscription subscribe = AddressModel.getInstance()
              .create_address(receiver_state, receiver_city, receiver_district,
                  clearaddressa, receiver_name, receiver_mobile,defalut)
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<AddressResultBean>() {
                @Override public void onNext(AddressResultBean addressResultBean) {
                  if (addressResultBean != null) {
                    if (addressResultBean.getCode()==0) {
                        startActivity(
                            new Intent(AddAddressActivity.this, AddressActivity.class));
                        AddAddressActivity.this.finish();

                    }
                  }
                }
              });
          addSubscription(subscribe);
        }

        break;
    }
  }

  public boolean checkInput(String receivername, String mobile, String address1,
      String address2) {

    if (receivername == null || receivername.trim().equals("")) {
      JUtils.Toast("请输入收货人姓名");
    } else {
      if (mobile == null || mobile.trim().equals("")) {
        JUtils.Toast("请输入手机号");
      } else {
        if (mobile.length() != 11) {
          JUtils.Toast("请输入正确的手机号");
        } else if (address1 == null || address1.trim().equals("")) {
          JUtils.Toast("请选择所在地区");
        } else if (address2 == null || address2.trim().equals("")) {
          JUtils.Toast("请输入详细地址");
        } else {
          return true;
        }
      }
    }

    return false;
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

  @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    switch (buttonView.getId()) {
      case R.id.switch_button:
        if (isChecked) {
          defalut = "true";

        } else {
          defalut = "false";
        }

        break;
    }
  }
}