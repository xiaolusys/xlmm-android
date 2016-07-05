package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by itxuye on 2016/7/2.
 */
public class MMLevelExamWebViewActivity extends CommonWebViewActivity {
  @Override protected void initViews() {
    super.initViews();
    webviewTitle.setText("妈妈考试");
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }
}
