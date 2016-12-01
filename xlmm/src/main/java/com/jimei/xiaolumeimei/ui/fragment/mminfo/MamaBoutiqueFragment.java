package com.jimei.xiaolumeimei.ui.fragment.mminfo;

import android.os.Bundle;
import android.webkit.WebView;

import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

/**
 * Created by wisdom on 16/11/14.
 */

public class MamaBoutiqueFragment extends BaseWebViewFragment {

    public static MamaBoutiqueFragment newInstance(String title, int mamaid) {
        MamaBoutiqueFragment fragment = new MamaBoutiqueFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("mamaid", mamaid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public WebView getWebView() {
        if (b != null) {
            return b.wbView;
        }
        return null;
    }

    @Override
    public void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaUrl()
                .subscribe(mamaUrl -> {
                    loadUrl(mamaUrl.getResults().get(0).getExtra().getBoutique());
                    hideIndeterminateProgressDialog();
                }, throwable -> {
                    throwable.printStackTrace();
                    hideIndeterminateProgressDialog();
                }));
    }

//    private static final String TAG = MamaBoutiqueFragment.class.getSimpleName();
//    private String cookies;
//    private String domain;
//    private String sessionid;
//
//    public static MamaBoutiqueFragment newInstance(String title, int mamaid) {
//        MamaBoutiqueFragment fragment = new MamaBoutiqueFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("title", title);
//        bundle.putInt("mamaid", mamaid);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.fragment_boutique;
//    }
//
//    @Override
//    protected void initData() {
//        addSubscription(MamaInfoModel.getInstance()
//                .getMamaUrl()
//                .subscribe(this::fillDataToView, throwable -> {
//                    throwable.printStackTrace();
//                    hideIndeterminateProgressDialog();
//                }));
//    }
//
//    private void fillDataToView(MamaUrl mamaUrl) {
//        try {
//            Map<String, String> extraHeaders = new HashMap<>();
//            extraHeaders.put("Cookie", sessionid);
//            b.webView.loadUrl(mamaUrl.getResults().get(0).getExtra().getBoutique(), extraHeaders);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        hideIndeterminateProgressDialog();
//    }
//
//    @SuppressLint("JavascriptInterface")
//    @Override
//    protected void initViews() {
//        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
//        cookies = sharedPreferences.getString("cookiesString", "");
//        domain = sharedPreferences.getString("cookiesDomain", "");
//        sessionid = sharedPreferences.getString("Cookie", "");
//        try {
//            if (Build.VERSION.SDK_INT >= 19) {
//                b.webView.getSettings().setLoadsImagesAutomatically(true);
//            } else {
//                b.webView.getSettings().setLoadsImagesAutomatically(false);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                b.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//            }
//            String userAgentString = b.webView.getSettings().getUserAgentString();
//            b.webView.getSettings().setUserAgentString(userAgentString +
//                    "; xlmm/" + BuildConfig.VERSION_NAME + ";");
//            b.webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
////            mWebView.getSettings().setBlockNetworkImage(false);
////            mWebView.getSettings().setBlockNetworkLoads(false);
//            b.webView.getSettings().setJavaScriptEnabled(true);
//            b.webView.addJavascriptInterface(new AndroidJsBridge((BaseActivity) mActivity), "AndroidBridge");
//            b.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            b.webView.getSettings().setAllowFileAccess(true);
//            b.webView.getSettings().setAllowFileAccess(true);
//            b.webView.getSettings().setAppCacheEnabled(true);
//            b.webView.getSettings().setDomStorageEnabled(true);
//            b.webView.getSettings().setDatabaseEnabled(true);
//            b.webView.getSettings().setUseWideViewPort(true);
//            b.webView.setDrawingCacheEnabled(true);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                b.webView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
//            }
//            b.webView.setWebChromeClient(new WebChromeClient() {
//                @Override
//                public void onProgressChanged(WebView view, int newProgress) {
//                    JUtils.Log(TAG, "process:" + newProgress);
//                    b.progressBar.setProgress(newProgress);
//                    if (newProgress == 100) {
//                        b.progressBar.setVisibility(View.GONE);
//                    } else {
//                        b.progressBar.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                public boolean onJsAlert(WebView view, String url, String message,
//                                         JsResult result) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setTitle("提示")
//                            .setMessage(message)
//                            .setPositiveButton("确定", null);
//                    builder.setOnKeyListener((dialog, keyCode, event) -> {
//                        Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
//                        return true;
//                    });
//                    builder.setCancelable(false);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    result.confirm();
//                    return true;
//                }
//
//                public boolean onJsConfirm(WebView view, String url, String message,
//                                           final JsResult result) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setTitle("提示")
//                            .setMessage(message)
//                            .setPositiveButton("确定", (dialog, which) -> result.confirm())
//                            .setNeutralButton("取消", (dialog, which) -> result.cancel());
//                    builder.setOnCancelListener(dialog -> result.cancel());
//                    builder.setOnKeyListener((dialog, keyCode, event) -> {
//                        Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
//                        return true;
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    return true;
//                }
//
//                public boolean onJsPrompt(WebView view, String url, String message,
//                                          String defaultValue, final JsPromptResult result) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setTitle("提示").setMessage(message);
//                    final EditText et = new EditText(view.getContext());
//                    et.setSingleLine();
//                    et.setText(defaultValue);
//                    builder.setView(et)
//                            .setPositiveButton("确定", (dialog, which) -> result.confirm(et.getText().toString()))
//                            .setNeutralButton("取消", (dialog, which) -> result.cancel());
//                    builder.setOnKeyListener((dialog, keyCode, event) -> {
//                        Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
//                        return true;
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    return true;
//                }
//            });
//            b.webView.setWebViewClient(new WebViewClient() {
//
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    JUtils.Log(TAG, "onPageFinished:" + url);
//                    CookieSyncManager.getInstance().sync();
//                    if (b.webView != null && !b.webView.getSettings().getLoadsImagesAutomatically()) {
//                        b.webView.getSettings().setLoadsImagesAutomatically(true);
//                    }
//                }
//
//                @Override
//                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,
//                                                      String realm) {
//                    JUtils.Log(TAG, "onReceivedHttpAuthRequest");
//                    view.reload();
//                }
//
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    JUtils.Log(TAG, "shouldOverrideUrlLoading:" + url);
//                    view.loadUrl(url);
//                    return super.shouldOverrideUrlLoading(view, url);
//                }
//
//                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                    JUtils.Log(TAG, "onReceivedSslError:");
//                    handler.proceed();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            JUtils.Log(TAG, "set webview err");
//        }
//        syncCookie(mActivity);
//    }
//
//    public void syncCookie(Context context) {
//        try {
//            CookieSyncManager.createInstance(context);
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.removeSessionCookie();// 移除
//            cookieManager.removeAllCookie();
//            cookieManager.setAcceptCookie(true);
//            cookieManager.setCookie(domain, cookies);
//            CookieSyncManager.getInstance().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public WebView getWebView() {
//        if (b != null) {
//            return b.webView;
//        }
//        return null;
//    }
//
//    @Override
//    public View getLoadingView() {
//        return b.layout;
//    }
}
