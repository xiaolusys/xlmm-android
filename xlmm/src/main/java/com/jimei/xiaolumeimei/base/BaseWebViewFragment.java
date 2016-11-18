package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseWebViewFragment extends BaseLazyFragment {

    private static final String TAG = "BaseWebViewFragment";

    @Bind(R.id.ll_actwebview)
    LinearLayout ll_actwebview;
    @Bind(R.id.pb_view)
    ProgressBar mProgressBar;
    @Bind(R.id.wb_view)
    WebView mWebView;
    private String cookies;
    private String domain;
    private String sessionid;

    public static BaseWebViewFragment newInstance(String title, String link) {
        BaseWebViewFragment mmFansFragment = new BaseWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("link", link);
        mmFansFragment.setArguments(bundle);
        return mmFansFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void load() {
        try {
            if (getArguments() != null) {
                String link = getArguments().getString("link", "");
                if (link != null && !"".equals(link)) {
                    Map<String, String> extraHeaders = new HashMap<>();
                    extraHeaders.put("Cookie", sessionid);
                    mWebView.loadUrl(link, extraHeaders);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");
        sessionid = sharedPreferences.getString("Cookie", "");
        EventBus.getDefault().register(this);
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
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new AndroidJsBridge((BaseSwipeBackCompatActivity) mActivity)
                    , "AndroidBridge");
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setDatabaseEnabled(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.setDrawingCacheEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    JUtils.Log(TAG, "process:" + newProgress);
                    if (mProgressBar != null) {
                        mProgressBar.setProgress(newProgress);
                        if (newProgress == 100) {
//                        mWebView.getSettings().setBlockNetworkImage(true);
                            mProgressBar.setVisibility(View.GONE);
                        } else {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
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

                public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                    return super.onJsBeforeUnload(view, url, message, result);
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
            });
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    JUtils.Log(TAG, "onPageFinished:" + url);
                    CookieSyncManager.getInstance().sync();
                    if (mWebView != null && !mWebView.getSettings().getLoadsImagesAutomatically()) {
                        mWebView.getSettings().setLoadsImagesAutomatically(true);
                    }
                }

                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,
                                                      String realm) {
                    JUtils.Log(TAG, "onReceivedHttpAuthRequest");
                    view.reload();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    JUtils.Log(TAG, "shouldOverrideUrlLoading:" + url);
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    JUtils.Log(TAG, "onReceivedSslError:");
                    handler.proceed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JUtils.Log(TAG, "set webview err");
        }
        syncCookie(mActivity);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_basewebview;
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
        if (ll_actwebview != null) {
            ll_actwebview.removeView(mWebView);
        }
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        ButterKnife.unbind(this);
        WebViewEvent stickyEvent = EventBus.getDefault().getStickyEvent(WebViewEvent.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
        EventBus.getDefault().unregister(this);
    }

    public void loadUrl(String url) {
        if (url != null && !"".equals(url)) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Cookie", sessionid);
            mWebView.loadUrl(url, extraHeaders);
        }
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
}
