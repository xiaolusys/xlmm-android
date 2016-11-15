package com.jimei.xiaolumeimei.ui.fragment.mminfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentBoutiqueBinding;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.model.MMInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wisdom on 16/11/14.
 */

public class BoutiqueFragment extends BaseBindingFragment<FragmentBoutiqueBinding> {

    private String cookies;
    private String domain;
    private String sessionid;

    public static BoutiqueFragment newInstance(String title, int mamaid) {
        BoutiqueFragment fragment = new BoutiqueFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("mamaid", mamaid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        ((MamaActivity) mActivity).showIndeterminateProgressDialog(false);
        addSubscription(MMInfoModel.getInstance()
                .getMamaUrl()
                .subscribe(this::fillDataToView, Throwable::printStackTrace));
    }

    private void fillDataToView(MamaUrl mamaUrl) {
        try {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Cookie", sessionid);
            b.webView.loadUrl(mamaUrl.getResults().get(0).getExtra().getBoutique(), extraHeaders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((MamaActivity) mActivity).hideIndeterminateProgressDialog();
    }

    @Override
    protected void initViews() {
        SharedPreferences preferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = preferences.getString("cookiesString", "");
        domain = preferences.getString("cookiesDomain", "");
        sessionid = preferences.getString("Cookie", "");
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                b.webView.getSettings().setLoadsImagesAutomatically(true);
            } else {
                b.webView.getSettings().setLoadsImagesAutomatically(false);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                b.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            String userAgentString = b.webView.getSettings().getUserAgentString();
            b.webView.getSettings().setUserAgentString(userAgentString
                    + "; xlmm/" + BuildConfig.VERSION_NAME + ";");
            b.webView.getSettings().setJavaScriptEnabled(true);
            b.webView.getSettings().setAllowFileAccess(true);
            b.webView.getSettings().setAllowFileAccess(true);
            b.webView.getSettings().setAppCacheEnabled(true);
            b.webView.getSettings().setDomStorageEnabled(true);
            b.webView.getSettings().setDatabaseEnabled(true);
            b.webView.getSettings().setUseWideViewPort(true);
            b.webView.setDrawingCacheEnabled(true);
            b.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }
            b.webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    CookieSyncManager.getInstance().sync();
                    if (b.webView != null && !b.webView.getSettings().getLoadsImagesAutomatically()) {
                        b.webView.getSettings().setLoadsImagesAutomatically(true);
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
            b.webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    b.progressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        b.progressBar.setVisibility(View.GONE);
                    } else {
                        b.progressBar.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        syncCookie(mActivity);
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
    protected int getContentViewId() {
        return R.layout.fragment_boutique;
    }
}
