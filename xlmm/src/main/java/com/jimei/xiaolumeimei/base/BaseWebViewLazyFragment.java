package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by itxuye on 2016/6/24.
 */
public class BaseWebViewLazyFragment extends BaseLazyFragment {

    private static final String TAG = "BaseWebViewLazyFragment";

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

    public static BaseWebViewLazyFragment newInstance(String title) {
        BaseWebViewLazyFragment mmFansFragment = new BaseWebViewLazyFragment();
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
        mActivity.runOnUiThread(() -> {
            try {
                Map<String, String> extraHeaders = new HashMap<>();

                extraHeaders.put("Cookie", sessionid);
                JUtils.Log(TAG, "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
                        " sessionid:" + sessionid);
                mWebView.loadUrl(actlink, extraHeaders);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        get_party_share_content(id + "");
    }

    @Override
    public void initViews() {
        ShareSDK.initSDK(mActivity);
        EventBus.getDefault().register(this);
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
            mWebView.addJavascriptInterface(new AndroidJsBridge((BaseSwipeBackCompatActivity) mActivity),
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

        syncCookie(mActivity);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_basewebview;
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
        CookieSyncManager.createInstance(mActivity);
        CookieSyncManager.getInstance().stopSync();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        CookieSyncManager.createInstance(mActivity);
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

    public void get_party_share_content(String id) {
        addSubscription(ActivityModel.getInstance()
                .get_party_share_content(id)
                .subscribe(new ServiceResponse<ActivityBean>() {
                    @Override
                    public void onNext(ActivityBean activityBean) {

                        if (null != activityBean) {
                            partyShareInfo = activityBean;
                            partyShareInfo.setQrcodeLink(activityBean.getQrcodeLink());
                        }
                    }
                }));
    }
}
