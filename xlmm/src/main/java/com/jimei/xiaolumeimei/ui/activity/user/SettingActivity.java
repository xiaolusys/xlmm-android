package com.jimei.xiaolumeimei.ui.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CompoundButton;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.mipush.sdk.MiPushClient;
import rx.schedulers.Schedulers;

public class SettingActivity extends BaseMVVMActivity<ActivitySettingBinding>
    implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

  @Override protected void initView() {
    b.clearCache.setTitleText("清理缓存");
    b.update.setTitleText("检查更新");
    b.aboutXlmm.setTitleText("关于小鹿美美");
    b.tvTitle.setText("推送通知");
    updateCache();
  }

  @Override protected void initListener() {
    b.clearCache.setOnClickListener(this);
    b.update.setOnClickListener(this);
    b.aboutXlmm.setOnClickListener(this);
    b.openNoticeA.setOnCheckedChangeListener(this);
    b.openNoticeA.setOnClickListener(this);
  }

  @Override protected void initData() {
    if (LoginUtils.isMipushOk(getApplicationContext())) {
      b.openNoticeA.setChecked(true);
    } else {
      b.openNoticeA.setChecked(false);
    }
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_setting;
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  public void updatePref() {
    b.aboutXlmm.setSummary(XlmmConst.getVersionName(SettingActivity.this));
  }

  @Override protected void onResume() {
    super.onResume();
    updatePref();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

  @Override public void onClick(View v) {
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
        if (b.openNoticeA.isChecked()) {
          new MaterialDialog.Builder(this).title("关闭推送")
              .content("您确定要关闭推送吗？")
              .positiveText("确定")
              .positiveColorRes(R.color.colorAccent)
              .negativeText("取消")
              .negativeColorRes(R.color.colorAccent)
              .callback(new MaterialDialog.ButtonCallback() {
                @Override public void onPositive(MaterialDialog dialog) {
                  final String finalAccount = LoginUtils.getUserAccount(getApplicationContext());
                  if ((finalAccount != null) && ((!finalAccount.isEmpty()))) {
                    MiPushClient.unsetUserAccount(getApplicationContext(), finalAccount, null);
                  }
                  b.openNoticeA.setChecked(false);
                  dialog.dismiss();
                }

                @Override public void onNegative(MaterialDialog dialog) {
                  dialog.dismiss();
                }
              })
              .show();
        } else {
          setPushUserAccount(SettingActivity.this, MiPushClient.getRegId(getApplicationContext()));
        }
        break;
    }
  }

  private void update() {
    ActivityModel.getInstance()
        .getVersion()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<VersionBean>() {
          @Override public void onNext(VersionBean versionBean) {
            VersionManager versionManager = new VersionManager() {
              @Override public int getServerVersion() {
                return versionBean.getVersion_code();
              }

              @Override public String getUpdateContent() {
                return "最新版本:" + versionBean.getVersion() + "\n\n更新内容:\n" + versionBean.getMemo();
              }

              @Override public boolean showMsg() {
                return true;
              }
            };
            versionManager.setPositiveListener(new View.OnClickListener() {
              @Override public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UpdateService.class);
                intent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, versionBean.getDownload_link());
                SettingActivity.this.startService(intent);
                versionManager.getDialog().dismiss();
                JUtils.Toast("应用正在后台下载!");
              }
            });
            versionManager.checkVersion(SettingActivity.this);
          }
        });
  }

  void updateCache() {
    b.clearCache.setSummary(DataClearManager.getApplicationDataSize(XlmmApp.getInstance()));
  }

  @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

  }

  public void setPushUserAccount(Context context, String mRegId) {
    try {
      //register xiaomi push
      RxPermissions.getInstance(context)
          .request(Manifest.permission.READ_PHONE_STATE)
          .subscribe(aBoolean -> {
            if (aBoolean) {
              UserModel.getInstance()
                  .getUserAccount("android", mRegId, ((TelephonyManager) context.getSystemService(
                      Context.TELEPHONY_SERVICE)).getDeviceId())
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<UserAccountBean>() {
                    @Override public void onNext(UserAccountBean user) {

                      if ((LoginUtils.getUserAccount(context) != null)
                          && ((!LoginUtils.getUserAccount(context).isEmpty()))
                          && (!LoginUtils.getUserAccount(context).equals(user.getUserAccount()))) {
                        LoginUtils.saveMipushOk(context, true);
                        MiPushClient.unsetUserAccount(context.getApplicationContext(),
                            LoginUtils.getUserAccount(context), null);
                      }
                      MiPushClient.setUserAccount(context.getApplicationContext(),
                          user.getUserAccount(), null);
                      b.openNoticeA.setChecked(true);
                    }

                    @Override public void onCompleted() {
                      super.onCompleted();
                    }

                    @Override public void onError(Throwable e) {
                      e.printStackTrace();
                      super.onError(e);
                      LoginUtils.deleteIsMipushOk(context);
                      b.openNoticeA.setChecked(false);
                    }
                  });
            } else {
              b.openNoticeA.setChecked(false);
              LoginUtils.deleteIsMipushOk(context);
              JUtils.Toast("读取设备权限被关闭,可以前往应用设置打开权限");
            }
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
