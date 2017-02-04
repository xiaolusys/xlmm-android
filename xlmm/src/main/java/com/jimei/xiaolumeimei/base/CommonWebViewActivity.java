package com.jimei.xiaolumeimei.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.event.CartEvent;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.utils.pay.PayUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.mob.tools.utils.UIHandler;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.Subscription;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/04.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CommonWebViewActivity extends BaseSwipeBackCompatActivity
        implements PlatformActionListener, Handler.Callback {

    @Bind(R.id.layout)
    LinearLayout layout;

    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    private static final int MSG_ACTION_CCALLBACK = 2;
    public WebView mWebView;
    LinearLayout ll_actwebview;
    private Bitmap bitmap;
    private ProgressBar mProgressBar;
    private String cookies;
    private String actlink;
    private ActivityBean partyShareInfo;
    private String domain;
    private String sessionid;
    private int id;
    public XlmmTitleView titleView;
    private AndroidJsBridge mAndroidJsBridge;

    @Override
    protected void initData() {
        try {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Cookie", sessionid);
            mWebView.loadUrl(actlink, extraHeaders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        get_party_share_content(id + "");
    }

    @Override
    public void getBundleExtras(Bundle extras) {
        cookies = extras.getString("cookies");
        domain = extras.getString("domain");
        sessionid = extras.getString("Cookie");
        actlink = extras.getString("actlink");
        id = extras.getInt("id");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_actwebview;
    }

    @Override
    public void getIntentUrl(Uri uri) {
        SharedPreferences sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");
        sessionid = sharedPreferences.getString("Cookie", "");
        actlink = uri.getQueryParameter("url");
        String id = uri.getQueryParameter("activity_id");
        if (id != null) {
            this.id = Integer.valueOf(id);
        } else {
            this.id = -1;
        }
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initViews() {
        ShareSDK.initSDK(this);
        titleView = (XlmmTitleView) findViewById(R.id.title_view);
        ll_actwebview = (LinearLayout) findViewById(R.id.ll_actwebview);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_view);
        mWebView = (WebView) findViewById(R.id.wb_view);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("title") != null) {
                titleView.setName(getIntent().getExtras().getString("title"));
            }
            if (getIntent().getExtras().getBoolean("hide")) {
                titleView.setVisibility(View.GONE);
            }
        }
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            } else {
                mWebView.getSettings().setLoadsImagesAutomatically(false);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            String userAgentString = mWebView.getSettings().getUserAgentString();
            mWebView.getSettings().setUserAgentString(userAgentString +
                    "; xlmm/" + BuildConfig.VERSION_NAME + ";");
            mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//            mWebView.getSettings().setBlockNetworkImage(false);
//            mWebView.getSettings().setBlockNetworkLoads(false);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mAndroidJsBridge = new AndroidJsBridge(this);
            mWebView.addJavascriptInterface(mAndroidJsBridge, "AndroidBridge");
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setDatabaseEnabled(true);
            mWebView.getSettings().setUseWideViewPort(true);

            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mWebView.setInitialScale(100);
            mWebView.getSettings().setSupportZoom(true);

            mWebView.setDrawingCacheEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.GONE);
//                        mWebView.getSettings().setBlockNetworkImage(true);
//                        mWebView.getSettings().setBlockNetworkLoads(true);
                    } else {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }

                public boolean onJsAlert(WebView view, String url, String message,
                                         JsResult result) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", null);
                    builder.setOnKeyListener((dialog, keyCode, event) -> {
                        Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
                        return true;
                    });
                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    result.confirm();
                    return true;
                }

                public boolean onJsConfirm(WebView view, String url, String message,
                                           final JsResult result) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", (dialog, which) -> result.confirm())
                            .setNeutralButton("取消", (dialog, which) -> result.cancel());
                    builder.setOnCancelListener(dialog -> result.cancel());
                    builder.setOnKeyListener((dialog, keyCode, event) -> {
                        Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                        return true;
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }

                public boolean onJsPrompt(WebView view, String url, String message,
                                          String defaultValue, final JsPromptResult result) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("提示").setMessage(message);
                    final EditText et = new EditText(view.getContext());
                    et.setSingleLine();
                    et.setText(defaultValue);
                    builder.setView(et)
                            .setPositiveButton("确定", (dialog, which) -> result.confirm(et.getText().toString()))
                            .setNeutralButton("取消", (dialog, which) -> result.cancel());
                    builder.setOnKeyListener((dialog, keyCode, event) -> {
                        Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
                        return true;
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }

                //扩展浏览器上传文件
                //3.0++版本
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                    openFileChooserImpl(uploadMsg);
                }

                //3.0--版本
                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    openFileChooserImpl(uploadMsg);
                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    openFileChooserImpl(uploadMsg);
                }

                // For Android > 5.0
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg,
                                                 WebChromeClient.FileChooserParams fileChooserParams) {
                    openFileChooserImplForAndroid5(uploadMsg);
                    return true;
                }
            });
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    CookieSyncManager.getInstance().sync();
                    if (mWebView != null && !mWebView.getSettings().getLoadsImagesAutomatically()) {
                        mWebView.getSettings().setLoadsImagesAutomatically(true);
                    }
                }

                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,
                                                      String realm) {
                    view.reload();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        syncCookie(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        } else if (requestCode == PayUtils.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String result = intent.getExtras().getString("pay_result");
                String errorMsg = intent.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = intent.getExtras().getString("extra_msg"); // 错误信息
                if (result != null) {
                    switch (result) {
                        case "cancel":
                            JUtils.Toast("已取消支付!");
                            break;
                        case "success":
                            JUtils.Toast("支付成功！");
//                            successJump(mAndroidJsBridge.getTid());
                            finish();
                            break;
                        default:
                            showMsg(result, errorMsg, extraMsg);
                            break;
                    }
                }
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        }
    }

//    private void successJump(String tid) {
//        Intent intent = new Intent(this, RedBagActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("tid", tid);
//        intent.putExtras(bundle);
//        startActivity(intent);
//        finish();
//    }

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                sharePartyInfo();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("share", true)) {
            getMenuInflater().inflate(R.menu.menu_shareproduct, menu);
        } else if (getIntent().getData() != null) {
            getMenuInflater().inflate(R.menu.menu_shareproduct, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    public void setActlink(String actlink) {
        this.actlink = actlink;
    }


    public String getActlink() {
        return actlink;
    }

    @Override
    protected void onPause() {
        super.onPause();
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().stopSync();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        mWebView.onResume();
        ShareSDK.initSDK(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
        EventBus.getDefault().post(new CartEvent());
        if (ll_actwebview != null) {
            ll_actwebview.removeView(mWebView);
        }
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }

        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    public void syncCookie(Context context) {

        try {
            CookieSyncManager.createInstance(context);

            CookieManager cookieManager = CookieManager.getInstance();

            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();

            cookieManager.setAcceptCookie(true);

            cookieManager.setCookie(domain, cookies);

            CookieSyncManager.getInstance().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel(Platform platform, int action) {
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        // 失敗
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 分享成功回调
                MobclickAgent.onEvent(mContext, "share_success");
                JUtils.Toast("分享成功");
            }
            break;
            case 2: {
                // 分享失败回调
                HashMap<String, String> map = new HashMap<>();
                map.put("error", msg.obj.toString());
                MobclickAgent.onEvent(mContext, "share_failed", map);
                MobclickAgent.onEvent(mContext, "share_failed");
                JUtils.Toast("分享失败");
            }
            break;
            case 3: {
                // 分享取消回调
                MobclickAgent.onEvent(mContext, "share_cancel");
                JUtils.Toast("分享已取消");
            }
            break;
        }
        return false;
    }

    public void get_party_share_content(String id) {
        Subscription subscribe = ActivityModel.getInstance()
                .get_party_share_content(id)
                .subscribe(new ServiceResponse<ActivityBean>() {
                    @Override
                    public void onNext(ActivityBean activityBean) {

                        if (null != activityBean) {
                            partyShareInfo = activityBean;
                            partyShareInfo.setQrcodeLink(activityBean.getQrcodeLink());
                        }
                    }
                });
        addSubscription(subscribe);
    }

    protected void share_shopping(String title, String sharelink, String desc, String shareimg) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setTitleUrl(sharelink);
        oks.setText(desc);
        oks.setImageUrl(shareimg);
        oks.setUrl(sharelink);
        oks.setShareContentCustomizeCallback(new ShareContentCustom(desc));
        oks.show(this);
    }

    public void sharePartyInfo() {
        if (partyShareInfo == null) {
            JUtils.Toast("页面加载未完成!");
            return;
        }
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(partyShareInfo.getTitle());
        oks.setTitleUrl(partyShareInfo.getShareLink());
        oks.setText(partyShareInfo.getActiveDec());
        oks.setImageUrl(partyShareInfo.getShareIcon());
        oks.setUrl(partyShareInfo.getShareLink());
        Bitmap enableLogo =
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ssdk_oks_logo_copy);
        String label = "二维码";
        View.OnClickListener listener = v -> saveTwoDimenCode();
        oks.setCustomerLogo(enableLogo, label, listener);
        oks.setShareContentCustomizeCallback(
                new ShareContentCustom(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink()));
        oks.setCallback(this);
        oks.show(this);
    }

    public void saveTwoDimenCode() {
        if (TextUtils.isEmpty(partyShareInfo.getShareLink())) {
            return;
        }
        try {
            bitmap = mWebView.getDrawingCache();
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            String fileName = Environment.getExternalStorageDirectory() +
                                    CameraUtils.XLMM_IMG_PATH + "/webview_capture" + ".jpg";

                            if (FileUtils.isFileExist(fileName)) {
                                FileUtils.deleteFile(fileName);
                            }

                            try {
                                FileOutputStream fos = new FileOutputStream(fileName);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Uri uri = Uri.fromFile(new File(fileName));
                            // 通知图库更新
                            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                            scannerIntent.setData(uri);

                            sendBroadcast(scannerIntent);
                            Toast.makeText(this, "截取快照成功至/xlmm/xiaolumeimei", Toast.LENGTH_LONG).show();
                        } else {
                            JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ShareContentCustom implements ShareContentCustomizeCallback {

        private String text;

        ShareContentCustom(String text) {
            this.text = text;
        }

        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            Map<String, String> map = new HashMap<>();
            map.put("id", "name");
            map.put(platform.getId() + "", platform.getName());
            JUtils.Log("ShareID", platform.getId() + "    " + platform.getName());
            MobclickAgent.onEvent(mContext, "ShareID", map);
            if (WechatMoments.NAME.equals(platform.getName())) {
                paramsToShare.setTitle(text);
            }
        }
    }
}
