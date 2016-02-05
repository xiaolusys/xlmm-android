package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class TwoDimenCodeActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "TwoDimenCodeActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.img_2dimen) ImageView img_2dimen;
  @Bind(R.id.btn_save) Button btn_save;
  @Bind(R.id.btn_share) Button btn_share;

  String myurl ="";

  @Override protected void setListener() {
    btn_save.setOnClickListener(this);
    btn_share.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_2dimen_code;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });

    ViewUtils.loadImgToImgView(this, img_2dimen, myurl);
  }

  @Override protected void initData() {
    myurl = getIntent().getExtras().getString("myurl");
    if(false == myurl.equals("")){
      myurl = XlmmApi.URL_BASE + myurl;
      JUtils.Log(TAG,"myurl " + myurl);
    }
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

        finish();
        break;
      case R.id.btn_share:
        JUtils.Log(TAG,"share 2 dimen code");
        finish();
        break;
    }
  }


}