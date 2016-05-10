package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.utils.AppUtils;
import com.jimei.xiaolumeimei.utils.DataClearManager;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class SettingActivity extends BaseSwipeBackCompatActivity {

    private SettingFragment settingFragment;


    @Override
    protected void setListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        settingFragment.updatePref();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        settingFragment = new SettingFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container_setting, settingFragment)
                .commit();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    public static class SettingFragment extends PreferenceFragment
            implements Preference.OnPreferenceClickListener {

        private View view;
        private Preference clearCache;
        private Preference updateVersion;
        private Preference about_company;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        @Override
        public boolean onPreferenceClick(Preference preference) {
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
                                Toast.makeText(XlmmApp.getInstance(), "当前已是最新版本", Toast.LENGTH_SHORT)
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
            about_company = findPreference(getResources().getString(R.string.about_company));
            about_company.setSummary(XlmmConst.VERSION);
        }


        @Override
        public void onStop() {
            super.onStop();
            UmengUpdateAgent.setUpdateListener(null);
        }
    }
}
