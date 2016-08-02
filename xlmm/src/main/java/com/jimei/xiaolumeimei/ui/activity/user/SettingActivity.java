package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.utils.AppUtils;
import com.jimei.xiaolumeimei.utils.DataClearManager;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jimei.xiaolumeimei.xlmmService.UpdateService;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import rx.schedulers.Schedulers;

public class SettingActivity extends BaseSwipeBackCompatActivity {

    private SettingFragment settingFragment;


    @Override
    protected void setListener() {

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
                ActivityModel.getInstance()
                        .getVersion()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<VersionBean>() {
                            @Override
                            public void onNext(VersionBean versionBean) {
                                VersionManager versionManager = new VersionManager() {

                                    @Override
                                    public int getServerVersion() {
                                        return versionBean.getVersion_code();
                                    }

                                    @Override
                                    public String getUpdateContent() {
                                        return "最新版本:" + versionBean.getVersion() + "\n\n更新内容:\n" + versionBean.getMemo();
                                    }

                                    @Override
                                    public boolean showMsg() {
                                        return true;
                                    }
                                };
                                versionManager.setPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), UpdateService.class);
                                        intent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, versionBean.getDownload_link());
                                        getActivity().startService(intent);
                                        versionManager.getDialog().dismiss();
                                        JUtils.Toast("应用正在后台下载!");
                                    }
                                });
                                versionManager.checkVersion(getActivity());
                            }
                        });
            }
            return false;
        }

        public void updatePref() {
            about_company = findPreference(getResources().getString(R.string.about_company));
            about_company.setSummary(XlmmConst.getVersionName(getActivity()));
        }


        @Override
        public void onStop() {
            super.onStop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingFragment.updatePref();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
