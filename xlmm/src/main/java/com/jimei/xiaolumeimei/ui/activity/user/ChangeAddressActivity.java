package com.jimei.xiaolumeimei.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.IdCardChecker;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.wheelcitypicker.CityPickerDialog;
import com.jimei.library.widget.wheelcitypicker.Util;
import com.jimei.library.widget.wheelcitypicker.address.City;
import com.jimei.library.widget.wheelcitypicker.address.County;
import com.jimei.library.widget.wheelcitypicker.address.Province;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.IdCardBean;
import com.jimei.xiaolumeimei.entities.event.AddressChangeEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChangeAddressActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

    private static final String TAG = ChangeAddressActivity.class.getSimpleName();
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
    @Bind(R.id.id_layout)
    LinearLayout idLayout;
    @Bind(R.id.id_num)
    EditText idNum;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.id_card_layout)
    LinearLayout idCardLayout;
    @Bind(R.id.image_id_before)
    ImageView imageIdBefore;
    @Bind(R.id.image_id_after)
    ImageView imageIdAfter;
    @Bind(R.id.address_layout)
    LinearLayout addressLayout;
    @Bind(R.id.et_address_1)
    EditText etAddress1;
    @Bind(R.id.et_address_2)
    EditText etAddress2;
    @Bind(R.id.et_address_3)
    EditText etAddress3;

    private String id;
    private ArrayList<Province> provinces = new ArrayList<>();

    private String city_string;
    private String clearaddressa;
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_name;
    private String receiver_mobile;
    private County county;
    private boolean isDefaultX;
    private String idNo;
    private City city;
    private Province province;
    private ArrayList<City> cities;
    private ArrayList<County> counties;
    private int needLevel = 1;
    private String side;
    private String card_facepath;
    private String card_backpath;
    private File file;
    private String defaultAddress;

    @Override
    protected void setListener() {
        save.setOnClickListener(this);
        address.setOnClickListener(this);
        imageIdBefore.setOnClickListener(this);
        imageIdAfter.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initViews() {
        if (needLevel == 2) {
            idLayout.setVisibility(View.VISIBLE);
        } else if (needLevel == 3) {
            idLayout.setVisibility(View.VISIBLE);
            idCardLayout.setVisibility(View.VISIBLE);
        }
        if (isDefaultX) {
            switchButton.setChecked(true);
        }
    }

    @Override
    protected void initData() {
        if (receiver_name != null) {
            name.setText(receiver_name);
            name.setSelection(receiver_name.length());
        }
        mobile.setText(receiver_mobile);
        address.setText(city_string);
        clearAddress.setText(clearaddressa);
        idNum.setText(idNo);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        needLevel = extras.getInt("needLevel", 1);
        receiver_name = extras.getString("receiver_name");
        receiver_mobile = extras.getString("mobile");
        city_string = extras.getString("address1");
        clearaddressa = extras.getString("address2");
        receiver_state = extras.getString("receiver_state");
        receiver_city = extras.getString("receiver_city");
        receiver_district = extras.getString("receiver_district");
        id = extras.getString("id");
        idNo = extras.getString("idNo");
        isDefaultX = extras.getBoolean("isDefaultX", false);
        JUtils.Log(TAG, receiver_name + receiver_mobile + clearaddressa + receiver_state);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_changeaddress;
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
                if (switchButton.isChecked()) {
                    defaultAddress = "true";
                } else {
                    defaultAddress = "false";
                }
                if (needLevel == 3 && (card_facepath == null || card_backpath == null || "".equals(card_facepath) ||
                    "".equals(card_backpath))) {
                    JUtils.Toast("身份证照片未完善");
                } else if (needLevel >= 2 && !IdCardChecker.isValidatedAllIdcard(idNo)) {
                    JUtils.Toast("请填写合法的身份证号码!");
                } else {
                    if (needLevel >= 2) {
                        new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("请确保身份证信息和收货人信息一致，否则无法通过海关检测!")
                            .setCancelable(false)
                            .setPositiveButton("同意", (dialog, which) -> {
                                dialog.dismiss();
                                commitInfo();
                            })
                            .show();
                    } else {
                        commitInfo();
                    }
                }
                break;
            case R.id.image_id_before:
                side = "face";
                CameraUtils.getSystemPicture(this);
                break;
            case R.id.image_id_after:
                side = "back";
                CameraUtils.getSystemPicture(this);
                break;
        }
    }

    private void commitInfo() {
        if (addressLayout.getVisibility() == View.VISIBLE) {
            receiver_state = etAddress1.getText().toString().trim();
            receiver_city = etAddress2.getText().toString().trim();
            receiver_district = etAddress3.getText().toString().trim();
            if (receiver_state == null || "".equals(receiver_state)) {
                receiver_state = receiver_city;
            }
        }
        addSubscription(XlmmApp.getAddressInteractor(this)
            .update_addressWithId(id, receiver_state, receiver_city, receiver_district,
                clearaddressa, receiver_name, receiver_mobile, defaultAddress, idNo, card_facepath
                , card_backpath, new ServiceResponse<AddressResultBean>() {
                    @Override
                    public void onNext(AddressResultBean addressResultBean) {
                        EventBus.getDefault().post(new AddressChangeEvent());
                        if (addressResultBean != null) {
                            if (addressResultBean.getCode() == 0) {
                                JUtils.Toast("修改成功");
                                finish();
                            }
                        }
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            file = null;
            if (requestCode == CameraUtils.SELECT_PICTURE) {
                file = Image_Selecting_Task(data);
            } else if (requestCode == CameraUtils.SELECT_CAMERA) {
                file = Image_Photo_Task(data);
            }
            if (file != null) {
                if ("face".equals(side)) {
                    Glide.with(ChangeAddressActivity.this).load(file).into(imageIdBefore);
                } else {
                    Glide.with(ChangeAddressActivity.this).load(file).into(imageIdAfter);
                }
            }
        } else {
            JUtils.Toast("请重新上传!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (file != null) {
            showIndeterminateProgressDialog(true);
            setDialogContent("上传中...");
            new compressTask().execute(file.getPath());
        }

    }

    public File Image_Selecting_Task(Intent data) {
        File b = null;
        try {
            CameraUtils.uri = data.getData();
            if (CameraUtils.uri != null) {
                b = read_img_from_uri();
            } else {
                JUtils.Toast("对不起，您还没有选择任何照片。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public File Image_Photo_Task(Intent data) {
        File b = null;
        try {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            CameraUtils.uri = data.getData();
            if (CameraUtils.uri == null) {
                CameraUtils.uri = Uri.parse(
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            }
            if (CameraUtils.uri != null) {
                b = read_img_from_uri();
            } else {
                JUtils.Toast("对不起，照相机返回照片失败。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public File read_img_from_uri() {
        Cursor cursor = getContentResolver().query(CameraUtils.uri,
            new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        final String imageFilePath = cursor.getString(0);
        CameraUtils.Default_DIR = new File(imageFilePath);
        CameraUtils.Create_MY_IMAGES_DIR();
        CameraUtils.copyFile(CameraUtils.Default_DIR, CameraUtils.MY_IMG_DIR);
        cursor.close();
        return CameraUtils.Paste_Target_Location;
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
                addSubscription(XlmmApp.getAddressInteractor(this)
                    .delete_address(id, new ServiceResponse<AddressResultBean>() {
                        @Override
                        public void onNext(AddressResultBean addressResultBean) {
                            EventBus.getDefault().post(new AddressChangeEvent());
                            if (addressResultBean != null && addressResultBean.isRet()) {
                                finish();
                            }
                        }
                    }));
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
                JUtils.Toast("数据初始化失败，请手动填写省市区");
                address.setVisibility(View.GONE);
                addressLayout.setVisibility(View.VISIBLE);
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
            } catch (Exception ignored) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            return false;
        }
    }

    private class compressTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(strings[0]);
                Bitmap compressImage = CameraUtils.imageZoom(bitmap, 200);
                file = null;
                return CameraUtils.getBitmapStrBase64(compressImage);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if ("".equals(s) || s == null) {
                hideIndeterminateProgressDialog();
            } else {
                addSubscription(XlmmApp.getAddressInteractor(ChangeAddressActivity.this)
                    .idCardIndentify(side, s, new ServiceResponse<IdCardBean>() {
                        @Override
                        public void onNext(IdCardBean idCardBean) {
                            if (idCardBean.getCode() == 0) {
                                JUtils.Toast("上传成功");
                                if ("face".equals(side)) {
                                    card_facepath = idCardBean.getCard_infos().getCard_imgpath();
                                    JUtils.Log("-----------------------", idCardBean.getCard_infos().getName());
                                    JUtils.Log("-----------------------", idCardBean.getCard_infos().getNum());
                                } else {
                                    card_backpath = idCardBean.getCard_infos().getCard_imgpath();
                                }
                            } else {
                                JUtils.Toast(idCardBean.getInfo());
                            }
                            hideIndeterminateProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            hideIndeterminateProgressDialog();
                        }
                    }));
            }

        }
    }
}
