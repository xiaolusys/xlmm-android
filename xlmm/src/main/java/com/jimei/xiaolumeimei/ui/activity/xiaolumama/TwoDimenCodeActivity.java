package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.FilePara;
import com.jimei.xiaolumeimei.okhttp3.FileParaCallback;
import com.jude.utils.JUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;

/**
 * Created by wulei on 2016/2/4.
 */
public class TwoDimenCodeActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    String TAG = "TwoDimenCodeActivity";

    @Bind(R.id.img_2dimen)
    ImageView img_2dimen;
    @Bind(R.id.btn_share)
    Button btn_share;

    String myurl = "";
    FilePara filePara = new FilePara();

    @Override
    protected void setListener() {
        btn_share.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_2dimen_code;
    }

    @Override
    protected void initViews() {
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initData() {

        if (null != getIntent() && null != getIntent().getExtras()) {
            myurl = getIntent().getExtras().getString("myurl");
        }
        if (!"".equals(myurl)) {
            ViewUtils.loadImgToImgView(this, img_2dimen, myurl);
            JUtils.Log(TAG, "myurl " + myurl);
        }

        RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        OkHttpUtils.get().url(myurl).build().execute(new FileParaCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(FilePara response, int id) {
                                if (response != null) {
                                    filePara = response;
                                    try {
                                        String newName = Environment.getExternalStorageDirectory() +
                                                CameraUtils.XLMM_IMG_PATH + "我的推荐二维码.jpg";
                                        new File(response.getFilePath()).renameTo(new File(newName));

                                        Uri uri = Uri.fromFile(new File(newName));
                                        // 通知图库更新
                                        Intent scannerIntent =
                                                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                                        scannerIntent.setData(uri);

                                        TwoDimenCodeActivity.this.sendBroadcast(scannerIntent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        // Oups permission denied
                        JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
                    }
                }, e -> JUtils.Log(e.getMessage()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                JUtils.Log(TAG, "share 2 dimen code");
                share_2dimencode();
                break;
        }
    }

    private void share_2dimencode() {
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
    /*if ((filePara != null) && (filePara.getFilePath() != null) && (!filePara.getFilePath()
        .isEmpty())) {
      oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
      JUtils.Log(TAG, "local pic " + filePara.getFilePath());
    } else {*/
        JUtils.Log(TAG, "url pic");
        oks.setImageUrl(myurl);
        //}
        // url仅在微信（包括好友和朋友圈）中使用
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

    @Override
    protected void onStop() {
        super.onStop();
        ShareSDK.stopSDK(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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