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
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.IdCardChecker;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.wheelcitypicker.CityPickerDialog;
import com.jimei.library.widget.wheelcitypicker.Util;
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
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddAddressActivity extends BaseSwipeBackCompatActivity
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

    private ArrayList<Province> provinces = new ArrayList<>();
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String city_string;
    private String defaulta;
    private int needLevel;
    private String side;
    private String card_facepath;
    private String card_backpath;

    @Override
    protected void initViews() {
        if (needLevel == 2) {
            idLayout.setVisibility(View.VISIBLE);
        } else if (needLevel == 3) {
            idLayout.setVisibility(View.VISIBLE);
            idCardLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
        save.setOnClickListener(this);
        address.setOnClickListener(this);
        switchButton.setOnCheckedChangeListener(this);
        imageIdBefore.setOnClickListener(this);
        imageIdAfter.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.addaddress_activity;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        needLevel = extras.getInt("needLevel", 1);
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
                String receiver_name = name.getText().toString().trim();
                String receiver_mobile = mobile.getText().toString().trim();
                String clearaddressa = clearAddress.getText().toString().trim();
                String idNo = idNum.getText().toString().trim();
                if (IdCardChecker.isValidatedAllIdcard(idNo) || needLevel == 1) {
                    if (checkInput(receiver_name, receiver_mobile, city_string, clearaddressa)) {
                        addSubscription(XlmmApp.getAddressInteractor(this)
                            .create_addressWithId(receiver_state, receiver_city, receiver_district,
                                clearaddressa, receiver_name, receiver_mobile, defaulta, idNo,
                                new ServiceResponse<AddressResultBean>() {
                                    @Override
                                    public void onNext(AddressResultBean addressResultBean) {
                                        EventBus.getDefault().post(new AddressChangeEvent());
                                        if (addressResultBean != null) {
                                            JUtils.Toast(addressResultBean.getInfo());
                                            if (addressResultBean.getCode() == 0) {
                                                finish();
                                            }
                                        }
                                    }
                                }));
                    }
                } else {
                    JUtils.Toast("请填写合法的身份证号码!");
                }
                break;
            case R.id.image_id_before:
                side = "face";
                CameraUtils.getSystemPicture(this);
                break;
            case R.id.image_id_after:
                side = "back";
                CameraUtils.getSystemPicture(this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File file = null;
            if (requestCode == CameraUtils.SELECT_PICTURE) {
                file = Image_Selecting_Task(data);
            } else if (requestCode == CameraUtils.SELECT_CAMERA) {
                file = Image_Photo_Task(data);
            }
            if (file != null) {
                if ("face".equals(side)) {
                    Glide.with(this).load(file).into(imageIdBefore);
                } else {
                    Glide.with(this).load(file).into(imageIdAfter);
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                Bitmap compressImage = CameraUtils.compressImage(bitmap, 150);
                bitmap.recycle();
                String base64 = CameraUtils.getBitmapStrBase64(compressImage);
                compressImage.recycle();
                addSubscription(XlmmApp.getAddressInteractor(this)
                    .idCardIndentify(side, base64, new ServiceResponse<IdCardBean>() {
                        @Override
                        public void onNext(IdCardBean idCardBean) {
                            if (idCardBean.getCode() == 0) {
                                JUtils.Toast("上传成功");
                                if ("face".equals(side)) {
                                    card_facepath = idCardBean.getCard_infos().getCard_imgpath();
                                } else {
                                    card_backpath = idCardBean.getCard_infos().getCard_imgpath();
                                }
                            } else {
                                JUtils.Toast(idCardBean.getInfo());
                            }
                        }
                    }));
            }
        } else {
            JUtils.Toast("请重新上传!");
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

    private class InitAreaTask extends AsyncTask<Integer, Integer, Boolean> {

        Context mContext;

        Dialog progressDialog;

        InitAreaTask(Context context) {
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
                return true;
            } catch (Exception e) {
                e.printStackTrace();
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
}