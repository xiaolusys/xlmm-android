package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CompoundButton;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivitySettingBinding;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.CompanyInfoActivity;
import com.jimei.xiaolumeimei.utils.DataClearManager;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jimei.xiaolumeimei.xlmmService.UpdateService;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.mipush.sdk.MiPushClient;

import rx.schedulers.Schedulers;

public class SettingActivity extends BaseMVVMActivity<ActivitySettingBinding>
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void initView() {
        b.clearCache.setTitleText("清理缓存");
        b.update.setTitleText("检查更新");
        b.aboutXlmm.setTitleText("关于小鹿美美");
        b.tvTitle.setText("推送通知");
        updateCache();
    }

    @Override
    protected void initListener() {
        b.clearCache.setOnClickListener(this);
        b.update.setOnClickListener(this);
        b.aboutXlmm.setOnClickListener(this);
        b.openNoticeA.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        boolean mipushOk = LoginUtils.isMipushOk(getApplicationContext());
        JUtils.Log("regid", mipushOk + "");
        if (mipushOk) {
            b.openNoticeA.setChecked(true);
        } else {
            b.openNoticeA.setChecked(false);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    public void updatePref() {
        b.aboutXlmm.setSummary(XlmmConst.getVersionName(SettingActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePref();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_cache:
                DataClearManager.cleanApplicationData(XlmmApp.getInstance());
                updateCache();
                showToast("缓存已经清理");
                break;
            case R.id.update:
                update();
                break;
            case R.id.about_xlmm:
                readyGo(CompanyInfoActivity.class);
                break;

            case R.id.open_notice_a:

                break;
        }
    }

    private void update() {
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
                        versionManager.setPositiveListener(v -> {
                            Intent intent = new Intent(SettingActivity.this, UpdateService.class);
                            intent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, versionBean.getDownload_link());
                            SettingActivity.this.startService(intent);
                            versionManager.getDialog().dismiss();
                            JUtils.Toast("应用正在后台下载!");
                        });
                        versionManager.checkVersion(SettingActivity.this);
                    }
                });
    }

    void updateCache() {
        b.clearCache.setSummary(DataClearManager.getApplicationDataSize(XlmmApp.getInstance()));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!b.openNoticeA.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("关闭推送")
                    .setMessage("关闭推送可能错过重要通知哦\n" + "\n您确定要关闭推送吗?")
                    .setPositiveButton("确定", (dialog, which) -> {
                        MiPushClient.unregisterPush(getApplicationContext());
                        LoginUtils.deleteIsMipushOk(SettingActivity.this);
                        dialog.dismiss();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        b.openNoticeA.setChecked(true);
                        dialog.dismiss();
                    })
                    .show();
        } else {
            try {
                setPushUserAccount(getApplicationContext(), MiPushClient.getRegId(getApplicationContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setPushUserAccount(Context context, String mRegId) {
        MiPushClient.registerPush(getApplicationContext(), XlmmConst.XIAOMI_APP_ID,
                XlmmConst.XIAOMI_APP_KEY);
        try {
            //register xiaomi push
            UserModel.getInstance()
                    .getUserAccount("android", mRegId,
                            Settings.Secure.getString(XlmmApp.getmContext().getContentResolver(),
                                    Settings.Secure.ANDROID_ID))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<UserAccountBean>() {
                        @Override
                        public void onNext(UserAccountBean user) {
                            LoginUtils.saveMipushOk(context, true);
                            MiPushClient.setUserAccount(context, user.getUserAccount(), null);
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            super.onError(e);
                            LoginUtils.deleteIsMipushOk(context);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
