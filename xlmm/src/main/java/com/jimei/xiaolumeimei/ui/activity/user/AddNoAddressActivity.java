package com.jimei.xiaolumeimei.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.wheelcitypicker.CityPickerDialog;
import com.jimei.library.widget.wheelcitypicker.Util;
import com.jimei.library.widget.wheelcitypicker.address.Province;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddNoAddressActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.clear_address)
    EditText clearAddress;
    @Bind(R.id.switch_button)
    SwitchCompat switchButton;
    @Bind(R.id.save)
    Button save;
    @Bind(R.id.address)
    TextView address;
    private ArrayList<Province> provinces = new ArrayList<>();
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_name;
    private String receiver_mobile;
    private String city_string;
    private String clearaddressa;
    private String defaulta;

    @Override
    protected void setListener() {
        save.setOnClickListener(this);
        address.setOnClickListener(this);
        switchButton.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.addaddress_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address:

                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mobile.getWindowToken(), 0);

                if (provinces.size() > 0) {
                    showAddressDialog();
                } else {
                    new InitAreaTask(this).execute(0);
                }

                break;
            case R.id.save:

                receiver_name = name.getText().toString().trim();
                receiver_mobile = mobile.getText().toString().trim();
                clearaddressa = clearAddress.getText().toString().trim();

                if (checkInput(receiver_name, receiver_mobile, city_string, clearaddressa)) {
                    Subscription subscribe = AddressModel.getInstance()
                            .create_address(receiver_state, receiver_city, receiver_district,
                                    clearaddressa, receiver_name, receiver_mobile, defaulta)
                            .subscribe(new ServiceResponse<AddressResultBean>() {
                                @Override
                                public void onNext(AddressResultBean addressResultBean) {
                                    if (addressResultBean != null) {
                                        if (addressResultBean.isRet()) {

                                            AddNoAddressActivity.this.finish();
                                        }
                                    }
                                }
                            });
                    addSubscription(subscribe);
                }

                break;
        }
    }

    private void showAddressDialog() {
        new CityPickerDialog(this, provinces, null, null, null,
                (selectProvince, selectCity, selectCounty) -> {

                    receiver_state = selectProvince != null ? selectProvince.getName() : "";
                    receiver_district = selectCounty != null ? selectCounty.getName() : "";
                    receiver_city = selectCity != null ? selectCity.getName() : "";
                    city_string = receiver_state + receiver_city + receiver_district;
                    address.setText(city_string);
                }).show();
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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_button:
                if (isChecked) {
                    defaulta = "true";
                } else {
                    defaulta = "false";
                }

                break;
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

    private class InitAreaTask extends AsyncTask<Integer, Integer, Boolean> {

        Context mContext;

        Dialog progressDialog;

        public InitAreaTask(Context context) {
            mContext = context;
            progressDialog = Util.createLoadingDialog(mContext, "请稍等...", true, 0);
        }

        @Override protected void onPreExecute() {

            progressDialog.show();
        }

        @Override protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (provinces.size() > 0) {
                showAddressDialog();
            } else {
                Toast.makeText(mContext, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override protected Boolean doInBackground(Integer... params) {
            String address;
            InputStream in = null;
            try {
                in = mContext.getResources().getAssets().open("areas.json");
                byte[] arrayOfByte = new byte[in.available()];
                in.read(arrayOfByte);
                address = new String(arrayOfByte, "UTF-8");
                Gson gson = new Gson();
                provinces = gson.fromJson(address, new TypeToken<List<Province>>() {
                }.getType());
                return true;
            } catch (Exception e) {
            } finally {
                if (in != null) {
                    try {
                        in.close();                    } catch (IOException ignored) {
                    }
                }
            }
            return false;
        }
    }
}