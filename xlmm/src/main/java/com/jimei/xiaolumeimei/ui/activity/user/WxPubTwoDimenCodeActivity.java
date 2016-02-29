package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.FilePara;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.okhttp.callback.FileParaCallback;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import java.io.File;
import okhttp3.Call;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class WxPubTwoDimenCodeActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "WxPubTwoDimenCodeActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.img_2dimen) ImageView img_2dimen;
  @Bind(R.id.btn_save) Button btn_save;

  String myurl ="";
  WxPubAuthInfo wxPubAuthInfo;

  @Override protected void setListener() {
    btn_save.setOnClickListener(this);

  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_wxpub_2dimen_code;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);


  }

  @Override protected void initData() {
    Subscription subscribe1 = UserModel.getInstance()
        .getWxPubAuthInfo()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<WxPubAuthInfo>() {
          @Override public void onNext(WxPubAuthInfo wxpub) {

            if (wxpub != null) {
              wxPubAuthInfo = wxpub;
            }
          }
        });
    addSubscription(subscribe1);


  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_save:
        JUtils.Log(TAG,"save 2 dimen code");
        save_2dimencode();
        finish();
        break;
    }
  }

  private void save_2dimencode(){

  }
}