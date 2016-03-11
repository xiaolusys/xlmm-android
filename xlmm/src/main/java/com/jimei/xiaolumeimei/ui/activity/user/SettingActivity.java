package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.utils.AppUtils;
import com.jimei.xiaolumeimei.utils.DataClearManager;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SettingActivity extends BaseSwipeBackCompatActivity {
  static String nickName;
  static String mobile;
  String TAG = "SettingActivity";
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.container_setting) FrameLayout containerSetting;
  UserInfoBean userinfo;
  private SettingFragment settingFragment;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    Subscription subscribe = UserModel.getInstance()
        .getUserInfo()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean user) {
            userinfo = user;
            Log.d(TAG, "getUserInfo:, " + userinfo.toString());
            nickName = userinfo.getNick();
            mobile = userinfo.getMobile();
            Log.d(TAG, "getUserInfo nick "
                + userinfo.getNick()
                + " phone "
                + userinfo.getMobile());
            settingFragment.updatePref();
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, "error:, " + e.toString());
            super.onError(e);
          }
        });
    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    settingFragment = new SettingFragment();
    getFragmentManager().beginTransaction()
        .replace(R.id.container_setting, settingFragment)
        .commit();
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  public static class SettingFragment extends PreferenceFragment
      implements Preference.OnPreferenceClickListener {

    private View view;
    private Preference clearCache;
    private Preference updateVersion;
    private Preference setNickname;
    private Preference bindPhone;
    private Preference about_company;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      view = super.onCreateView(inflater, container, savedInstanceState);
      addPreferencesFromResource(R.xml.setting);

      clearCache = findPreference(getResources().getString(R.string.clear_cache));
      updateVersion = findPreference(getResources().getString(R.string.update));
      clearCache.setOnPreferenceClickListener(this);
      updateVersion.setOnPreferenceClickListener(this);
      updateCache();

      return view;
    }

    void updateCache() {
      clearCache.setSummary(
          DataClearManager.getApplicationDataSize(XlmmApp.getInstance()));
    }

    @Override public boolean onPreferenceClick(Preference preference) {
      if (preference.equals(clearCache)) {
        DataClearManager.cleanApplicationData(XlmmApp.getInstance());
        updateCache();
        AppUtils.showSnackBar(view, R.string.update_cache);
      }

      if (preference.equals(updateVersion)) {

        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
          @Override
          public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
            switch (updateStatus) {
              case UpdateStatus.Yes: // has update
                UmengUpdateAgent.showUpdateDialog(XlmmApp.getInstance(), updateInfo);
                break;
              case UpdateStatus.No: // has no update
                Toast.makeText(XlmmApp.getInstance(), "当前已是最新版本", Toast.LENGTH_LONG)
                    .show();
                break;
              case UpdateStatus.NoneWifi: // none wifi
                Toast.makeText(XlmmApp.getInstance(), "温馨提示，当前无wifi连接， 只在wifi下更新",
                    Toast.LENGTH_LONG).show();
                break;
              case UpdateStatus.Timeout: // time out
                Toast.makeText(XlmmApp.getInstance(), "网络不给力", Toast.LENGTH_LONG).show();
                break;
            }
          }
        });
        UmengUpdateAgent.update(XlmmApp.getInstance());
      }
      return false;
    }

    public void updatePref() {
      setNickname = findPreference(getResources().getString(R.string.set_nick));
      bindPhone = findPreference(getResources().getString(R.string.bind_phone));
      setNickname.setSummary(nickName);
      bindPhone.setSummary(mobile.substring(0, 3) + "****" + mobile.substring(7));

      about_company = findPreference(getResources().getString(R.string.about_company));
      about_company.setSummary(XlmmConst.VERSION);
    }
  }
}
