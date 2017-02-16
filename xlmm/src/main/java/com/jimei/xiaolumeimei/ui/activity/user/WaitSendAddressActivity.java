package com.jimei.xiaolumeimei.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.IdCardChecker;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.wheelcitypicker.CityPickerDialog;
import com.jimei.library.widget.wheelcitypicker.Util;
import com.jimei.library.widget.wheelcitypicker.address.City;
import com.jimei.library.widget.wheelcitypicker.address.County;
import com.jimei.library.widget.wheelcitypicker.address.Province;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;

public class WaitSendAddressActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    private static final String TAG = WaitSendAddressActivity.class.getSimpleName();
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.clear_address)
    EditText clearAddress;
    @Bind(R.id.save)
    Button save;
    @Bind(R.id.rl_default)
    RelativeLayout relativeLayout;
    @Bind(R.id.id_layout)
    LinearLayout idLayout;
    @Bind(R.id.id_num)
    EditText idNum;
    @Bind(R.id.layout)
    LinearLayout layout;
    private String id;

    private ArrayList<Province> provinces = new ArrayList<>();
    private String city_string;
    private String clearaddressa;
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_name;
    private String receiver_mobile;
    private String referal_trade_id;
    private String idNo;
    private County county;
    private City city;
    private Province province;
    private ArrayList<City> cities;
    private ArrayList<County> counties;
    private boolean is_bonded_goods;

    @Override
    protected void setListener() {
        save.setOnClickListener(this);
        address.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (receiver_name != null) {
            name.setText(receiver_name);
            if (receiver_name.length() < 20) {
                name.setSelection(receiver_name.length());
            } else {
                name.setSelection(20);
            }
        }
        mobile.setText(receiver_mobile);
        address.setText(city_string);
        clearAddress.setText(clearaddressa);
        idNum.setText(idNo);
    }

    @Override
    public View getLoadingView() {
        return layout;
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
        id = extras.getString("address_id");
        is_bonded_goods = extras.getBoolean("is_bonded_goods", false);
        referal_trade_id = extras.getString("referal_trade_id");
        idNo = extras.getString("idNo");
        JUtils.Log(TAG, receiver_name + receiver_mobile + clearaddressa + receiver_state);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_changeaddress;
    }

    @Override
    protected void initViews() {
        relativeLayout.setVisibility(View.GONE);
        if (is_bonded_goods) {
            idLayout.setVisibility(View.VISIBLE);
        }
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
                idNo = idNum.getText().toString().trim();
                if (IdCardChecker.isValidatedAllIdcard(idNo) || !is_bonded_goods) {
                    if (checkInput(receiver_name, receiver_mobile, city_string, clearaddressa)) {
                        Subscription subscribe = AddressModel.getInstance()
                                .update_address(id, receiver_state, receiver_city, receiver_district,
                                        clearaddressa, receiver_name, receiver_mobile, null, referal_trade_id, idNo)
                                .subscribe(new ServiceResponse<AddressResultBean>() {
                                    @Override
                                    public void onNext(AddressResultBean addressResultBean) {
                                        if (addressResultBean != null) {
                                            if (addressResultBean.getCode() == 0) {
                                                JUtils.Toast("修改成功");
                                                finish();
                                            } else {
                                                JUtils.Toast("修改失败");
                                            }
                                        }
                                    }
                                });
                        addSubscription(subscribe);
                    }
                } else {
                    JUtils.Toast("请填写合法的身份证号码!");
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
