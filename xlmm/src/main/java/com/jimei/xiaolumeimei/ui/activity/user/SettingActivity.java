package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.utils.AppUtils;
import com.jimei.xiaolumeimei.utils.DataClearManager;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SettingActivity extends BaseSwipeBackCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.container_setting) FrameLayout containerSetting;
  private SettingFragment settingFragment;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_activity;
  }

  @Override protected void initViews() {
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

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      view = super.onCreateView(inflater, container, savedInstanceState);
      addPreferencesFromResource(R.xml.setting);

      clearCache = findPreference(getResources().getString(R.string.clear_cache));
      clearCache.setOnPreferenceClickListener(this);
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
        AppUtils.showSnackBar(view,R.string.update_cache);
      }

      return false;
    }
  }
}
