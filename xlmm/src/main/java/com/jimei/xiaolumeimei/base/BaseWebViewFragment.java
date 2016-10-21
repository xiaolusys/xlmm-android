package com.jimei.xiaolumeimei.base;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.FileUtils;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.mob.tools.utils.UIHandler;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.Subscription;

/**
 * Created by itxuye on 2016/6/24.
 */
public class BaseWebViewFragment extends BaseFragment
        implements PlatformActionListener, Handler.Callback {

    private static final String TAG = "BaseWebViewFragment";

    private static final int MSG_ACTION_CCALLBACK = 2;
    @Bind(R.id.ll_actwebview)
    LinearLayout ll_actwebview;
    @Bind(R.id.pb_view)
    ProgressBar mProgressBar;
    @Bind(R.id.wb_view)
    WebView mWebView;
    private Bitmap bitmap;
    private String cookies;
    private String actlink;
    private ActivityBean partyShareInfo;
    private String domain;
    private String sessionid;
    private int id;

    public Activity activity;
    private View view;
    private Subscription subscribe;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    public static BaseWebViewFragment newInstance(String title) {
        BaseWebViewFragment mmFansFragment = new BaseWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", title);
        mmFansFragment.setArguments(bundle);
        return mmFansFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void load() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> extraHeaders = new HashMap<>();

                    extraHeaders.put("Cookie", sessionid);
                    JUtils.Log(TAG, "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
                            " sessionid:" + sessionid);
                    mWebView.loadUrl(actlink, extraHeaders);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        get_party_share_content(id + "");
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ShareSDK.initSDK(activity);
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_basewebview, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            } else {
                mWebView.getSettings().setLoadsImagesAutomatically(false);
            }

            String userAgentString = mWebView.getSettings().getUserAgentString();
            mWebView.getSettings()
                    .setUserAgentString(userAgentString
                            + "; xlmm/"
                            + BuildConfig.VERSION_NAME
                            + "; "
                            + "uuid/"
                            + ((TelephonyManager) XlmmApp.getInstance()
                            .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new AndroidJsBridge((BaseSwipeBackCompatActivity) activity),
                    "AndroidBridge");

            mWebView.getSettings().setAllowFileAccess(true);
            //如果访问的页面中有Javascript，则webview必须设置支持Javascript
            //mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
            //mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setDatabaseEnabled(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.setDrawingCacheEnabled(true);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                mWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                    } else {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            });

            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    CookieSyncManager.getInstance().sync();
                    if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
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

        syncCookie(activity);
    }

    @Subscribe(sticky = true)
    public void getWebViewInfo(WebViewEvent event) {
        cookies = event.cookies;
        domain = event.domain;
        actlink = event.actlink;
        id = event.id;
        sessionid = event.sessionid;

        JUtils.Log(TAG, "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
                " sessionid:" + sessionid);
    }

    @Override
    protected void initData() {
        load();
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    @Override
    public View getScrollableView() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ShareSDK.stopSDK();
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

        if (subscribe != null && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }

        ButterKnife.unbind(this);

        WebViewEvent stickyEvent = EventBus.getDefault().getStickyEvent(WebViewEvent.class);

        // Better check that an event was actually posted before
        if (stickyEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(stickyEvent);
            // Now do something with it
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        CookieSyncManager.createInstance(activity);
        CookieSyncManager.getInstance().stopSync();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        CookieSyncManager.createInstance(activity);
        CookieSyncManager.getInstance().startSync();
        mWebView.onResume();
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
        //打印错误信息,print the error msg
        t.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "name");

                map.put(((Platform) (msg.obj)).getId() + "", ((Platform) (msg.obj)).getName());
                MobclickAgent.onEvent(activity, "ShareID", map);
                JUtils.Log("UmengTest", "platfrom===" + ((Platform) (msg.obj)).getName());
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
            }
            break;
            case 2: {
                // 失败
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: {
                // 取消
                Toast.makeText(activity, "分享取消", Toast.LENGTH_SHORT).show();
            }
            break;
        }

        return false;
    }

    public void get_party_share_content(String id) {
        subscribe = ActivityModel.getInstance()
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
    }

    protected void share_shopping(String title, String sharelink, String desc, String shareimg) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(sharelink);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(desc);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageUrl(shareimg);
        oks.setUrl(sharelink);
        oks.setShareContentCustomizeCallback(new ShareContentCustom(desc));
        // 启动分享GUI
        oks.show(activity);
    }

    private void sharePartyInfo() {
        if (partyShareInfo == null) return;
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(partyShareInfo.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(partyShareInfo.getShareLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageUrl(partyShareInfo.getShareIcon());
        oks.setUrl(partyShareInfo.getShareLink());

        // url仅在微信（包括好友和朋友圈）中使用
        //oks.setUrl(myurl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");
        Bitmap enableLogo =
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.ssdk_oks_logo_copy);
        String label = "二维码";

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                //if (shareProductBean.getShareLink()) {
                //}
                saveTwoDimenCode(activity);
            }
        };
        oks.setCustomerLogo(enableLogo, label, listener);
        // 启动分享GUI
        oks.setShareContentCustomizeCallback(
                new ShareContentCustom(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink()));
        oks.show(activity);
    }

    public void saveTwoDimenCode(Context context) {

        if (partyShareInfo == null || TextUtils.isEmpty(partyShareInfo.getShareLink())) {

            return;
        }

        try {
            bitmap = mWebView.getDrawingCache();
            RxPermissions.getInstance(activity)
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
                                //压缩bitmap到输出流中
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

                            activity.sendBroadcast(scannerIntent);
                            Toast.makeText(activity, "截取快照成功至/xlmm/xiaolumeimei", Toast.LENGTH_LONG).show();
                        } else {
                            // Oups permission denied
                            JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //if (null!=bitmap) {
            //  bitmap.recycle();
            //}
        }
    }

    public void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    class ShareContentCustom implements ShareContentCustomizeCallback {

        private String text;

        public ShareContentCustom(String text) {
            this.text = text;
        }

        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            if (WechatMoments.NAME.equals(platform.getName())) {
                paramsToShare.setTitle(text);
            }
        }
    }
}
