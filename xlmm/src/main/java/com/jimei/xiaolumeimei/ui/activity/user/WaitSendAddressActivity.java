package com.jimei.xiaolumeimei.ui.activity.user;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.widget.citypicker.CityPicker;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

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
    @Bind(R.id.delete)
    Button delete;
    @Bind(R.id.main)
    LinearLayout main;
    @Bind(R.id.rl_default)
    RelativeLayout relativeLayout;
    private String id;
    private PopupWindow popupWindow;
    private View view;
    private CityPicker cityPicker;
    private View parent;

    private String city_string;
    private String clearaddressa;
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_name;
    private String receiver_mobile;
    private String referal_trade_id;

    @Override
    protected void setListener() {
        save.setOnClickListener(this);
        address.setOnClickListener(this);
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
        referal_trade_id = extras.getString("referal_trade_id");
        JUtils.Log(TAG, receiver_name + receiver_mobile + clearaddressa + receiver_state);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_changgeaddress;
    }

    @Override
    protected void initViews() {
        parent = findViewById(R.id.main);
        delete.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        initPopupWindow();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address:
                //为popWindow添加动画效果
                popupWindow.setAnimationStyle(R.style.popWindow_animation);
                // 点击弹出泡泡窗口
                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                cityPicker.setOnSelectingListener(new CityPicker.OnSelectingListener() {
                    @Override
                    public void selected(boolean selected) {
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
                receiver_mobile = mobile.getText().toString().trim();
                clearaddressa = clearAddress.getText().toString().trim();

                JUtils.Log(TAG,
                        receiver_mobile + "====" + receiver_state + "====" + receiver_city + "====" +
                                receiver_district + "====" +
                                clearaddressa + "====" + receiver_name);
                if (checkInput(receiver_name, receiver_mobile, city_string, clearaddressa)) {
                    Subscription subscribe = AddressModel.getInstance()
                            .update_address(id, receiver_state, receiver_city, receiver_district,
                                    clearaddressa, receiver_name, receiver_mobile, null, referal_trade_id)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new ServiceResponse<AddressResultBean>() {
                                @Override
                                public void onNext(AddressResultBean addressResultBean) {
                                    if (addressResultBean != null) {
                                        if (addressResultBean.getCode() == 0) {
                                            JUtils.Toast("修改成功");
                                            finish();
                                        }else {
                                            JUtils.Toast("修改失败");
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
}
