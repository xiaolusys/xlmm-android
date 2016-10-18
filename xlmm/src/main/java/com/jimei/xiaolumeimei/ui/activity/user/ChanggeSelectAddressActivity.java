package com.jimei.xiaolumeimei.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.widget.wheelcitypicker.CityPickerDialog;
import com.jimei.xiaolumeimei.widget.wheelcitypicker.Util;
import com.jimei.xiaolumeimei.widget.wheelcitypicker.address.City;
import com.jimei.xiaolumeimei.widget.wheelcitypicker.address.County;
import com.jimei.xiaolumeimei.widget.wheelcitypicker.address.Province;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChanggeSelectAddressActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = ChanggeSelectAddressActivity.class.getSimpleName();
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.clear_address)
    EditText clearAddress;
    @Bind(R.id.switch_button)
    SwitchCompat switchButton;
    @Bind(R.id.save)
    Button save;
    @Bind(R.id.main)
    LinearLayout main;
    private String id;
    private ArrayList<Province> provinces = new ArrayList<>();

    private String city_string;
    private String clearaddressa;
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_name;
    private String receiver_mobile;
    private String defalut;
    private County county;
    private City city;
    private Province province;
    private ArrayList<City> cities;
    private ArrayList<County> counties;

    @Override
    protected void setListener() {
        save.setOnClickListener(this);
        address.setOnClickListener(this);
        switchButton.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        name.setText(receiver_name);
        mobile.setText(receiver_mobile);
        address.setText(city_string);
        clearAddress.setText(clearaddressa);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

        receiver_name = extras.getString("receiver_name");
        receiver_mobile = extras.getString("mobile");
        city_string = extras.getString("address1");
        clearaddressa = extras.getString("address2");
        receiver_state = extras.getString("receiver_state");
        receiver_city = extras.getString("receiver_city");
        receiver_district = extras.getString("receiver_district");
        id = extras.getString("id");

        JUtils.Log(TAG, receiver_name + receiver_mobile + clearaddressa + receiver_state);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_changgeaddress;
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

                JUtils.Log(TAG,
                        receiver_mobile + "====" + receiver_state + "====" + receiver_city + "====" +
                                receiver_district + "====" +
                                clearaddressa + "====" + receiver_name);

                if (checkInput(receiver_name, receiver_mobile, city_string, clearaddressa)) {
                    Subscription subscribe = AddressModel.getInstance()
                            .update_address(id, receiver_state, receiver_city, receiver_district, clearaddressa,
                                    receiver_name, receiver_mobile, defalut)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new ServiceResponse<AddressResultBean>() {
                                @Override
                                public void onNext(AddressResultBean addressResultBean) {
                                    if (addressResultBean != null) {
                                        if (addressResultBean.getCode() == 0) {
                                            JUtils.Toast("修改成功");
                                            finish();
                                        }
                                    }
                                }
                            });
                    addSubscription(subscribe);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_delete:
                Subscription subscribe = AddressModel.getInstance()
                        .delete_address(id)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<AddressResultBean>() {
                            @Override
                            public void onNext(AddressResultBean addressResultBean) {
                                if (addressResultBean != null && addressResultBean.isRet()) {
                                    finish();
                                }
                            }
                        });
                addSubscription(subscribe);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkInput(String receivername, String mobile, String address1, String address2) {

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

    private void showAddressDialog() {
        new CityPickerDialog(this, provinces, province, city, county,
                (selectProvince, selectCity, selectCounty) -> {
                    receiver_state = selectProvince != null ? selectProvince.getName() : "";
                    receiver_district = selectCounty != null ? selectCounty.getName() : "";
                    receiver_city = selectCity != null ? selectCity.getName() : "";
                    city_string = receiver_state + receiver_city + receiver_district;
                    address.setText(city_string);
                }).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

        @Override
        protected void onPreExecute() {

            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (provinces.size() > 0) {
                showAddressDialog();
            } else {
                Toast.makeText(mContext, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            String address;
            InputStream in = null;
            try {
                if (FileUtils.isFileExist(XlmmConst.XLMM_DIR + "areas.json")) {
                    File file = new File(XlmmConst.XLMM_DIR + "areas.json");
                    in = new FileInputStream(file);
                } else {
                    in = mContext.getResources().getAssets().open("areas.json");
                }
                byte[] arrayOfByte = new byte[in.available()];
                in.read(arrayOfByte);
                address = new String(arrayOfByte, "UTF-8");

                Gson gson = new Gson();
                provinces = gson.fromJson(address, new TypeToken<List<Province>>() {
                }.getType());

                for (int i = 0; i < provinces.size(); i++) {
                    if (provinces.get(i).getName().equals(receiver_state)) {
                        province = provinces.get(i);
                        cities = provinces.get(i).getCities();
                    }
                }

                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).getName().equals(receiver_city)) {
                        city = cities.get(i);
                        counties = cities.get(i).getCounties();
                    }
                }

                for (int i = 0; i < counties.size(); i++) {
                    if (counties.get(i).getName().equals(receiver_district)) {
                        county = counties.get(i);
                    }
                }
                return true;
            } catch (Exception e) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
            return false;
        }
    }
}
