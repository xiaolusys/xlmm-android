package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.FilePara;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.okhttp.callback.FileParaCallback;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by wulei on 2016/2/4.
 */
public class TwoDimenCodeActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "TwoDimenCodeActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.img_2dimen) ImageView img_2dimen;
  @Bind(R.id.btn_share) Button btn_share;

  String myurl ="";

  @Override protected void setListener() {
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
    finishBack(toolbar);


  }

  @Override protected void initData() {
    myurl = getIntent().getExtras().getString("myurl");
    if(false == myurl.equals("")){
      myurl = XlmmApi.TWO_DIMEN_URL_BASE + myurl;
      ViewUtils.loadImgToImgView(this, img_2dimen, myurl);
      JUtils.Log(TAG,"myurl " + myurl);
    }

    OkHttpUtils.get()
            .url(myurl)
            .build()
            .execute(new FileParaCallback() {
              @Override public void onError(Call call, Exception e) {

              }

              @Override public void onResponse(FilePara response) {
                if (response != null) {
                  try {



                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }
            });
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
      case R.id.btn_share:
        JUtils.Log(TAG,"share 2 dimen code");
        share_2dimencode();
        //finish();
        break;
    }
  }

  private void share_2dimencode(){
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    //oks.setTitle("test");
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    //oks.setTitleUrl("http://sharesdk.cn");
    // text是分享文本，所有平台都需要这个字段
    oks.setText("分享我的二维码");
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setImageUrl(myurl);
    //oks.setUrl(myurl);
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    //oks.setComment("我是测试评论文本");
    // site是分享此内容的网站名称，仅在QQ空间使用
    //oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    //oks.setSiteUrl("http://sharesdk.cn");

    // 启动分享GUI
    oks.show(this);
  }
}