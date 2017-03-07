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

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.databinding.FragmentBasewebviewBinding;
import com.jimei.xiaolumeimei.entities.event.CartEvent;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class BaseWebViewFragment extends BaseBindingFragment<FragmentBasewebviewBinding> {

    private static final String TAG = "BaseWebViewFragment";

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
                    b.wbView.loadUrl(link, extraHeaders);
                    setDialogContent("页面载入中...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                b.wbView.getSettings().setLoadsImagesAutomatically(true);
            } else {
                b.wbView.getSettings().setLoadsImagesAutomatically(false);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                b.wbView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            String userAgentString = b.wbView.getSettings().getUserAgentString();
            b.wbView.getSettings().setUserAgentString(userAgentString +
                "; xlmm/" + BuildConfig.VERSION_NAME + ";");
            b.wbView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            b.wbView.getSettings().setJavaScriptEnabled(true);
            b.wbView.addJavascriptInterface(new AndroidJsBridge((BaseActivity) mActivity)
                , "AndroidBridge");
            b.wbView.getSettings().setAllowFileAccess(true);
            b.wbView.getSettings().setAppCacheEnabled(true);
            b.wbView.getSettings().setDomStorageEnabled(true);
            b.wbView.getSettings().setDatabaseEnabled(true);
            b.wbView.getSettings().setUseWideViewPort(true);
            b.wbView.getSettings().setTextZoom(100);
            b.wbView.setDrawingCacheEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }
            b.wbView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    JUtils.Log(TAG, "process:" + newProgress);
                    if (b.pbView != null) {
                        b.pbView.setProgress(newProgress);
                        if (newProgress == 100) {
                            b.pbView.setVisibility(View.GONE);
                            hideIndeterminateProgressDialog();
                        } else {
                            b.pbView.setVisibility(View.VISIBLE);
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
            b.wbView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    JUtils.Log(TAG, "onPageFinished:" + url);
                    CookieSyncManager.getInstance().sync();
                    if (b.wbView != null && !b.wbView.getSettings().getLoadsImagesAutomatically()) {
                        b.wbView.getSettings().setLoadsImagesAutomatically(true);
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
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_basewebview;
    }

    @Override
    public void initData() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");
        sessionid = sharedPreferences.getString("Cookie", "");
        syncCookie(mActivity);
        load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new CartEvent());
        if (b.llActwebview != null) {
            b.llActwebview.removeView(b.wbView);
        }
        if (b.wbView != null) {
            b.wbView.removeAllViews();
            b.wbView.destroy();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public View getLoadingView() {
        return b.llActwebview;
    }

    public void loadUrl(String url) {
        showIndeterminateProgressDialog(true);
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");
        sessionid = sharedPreferences.getString("Cookie", "");
        syncCookie(mActivity);
        if (url != null && !"".equals(url)) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Cookie", sessionid);
            b.wbView.loadUrl(url, extraHeaders);
            setDialogContent("页面载入中...");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CookieSyncManager.createInstance(mActivity);
        CookieSyncManager.getInstance().stopSync();
        b.wbView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        CookieSyncManager.createInstance(mActivity);
        CookieSyncManager.getInstance().startSync();
        b.wbView.onResume();
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
