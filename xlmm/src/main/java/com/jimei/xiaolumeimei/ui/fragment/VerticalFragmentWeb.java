package com.jimei.xiaolumeimei.ui.fragment;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.widget.doubleview.CustWebView;
import com.jude.utils.JUtils;

public class VerticalFragmentWeb extends Fragment {
  private static final String TAG = "VerticalFragmentWeb";
  private static final String url = "http://m.xiaolumeimei.com/mm/pdetail/";
  private View progressBar;
  private CustWebView webview;
  private boolean hasInited = false;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_product_web, null);
    webview = (CustWebView) rootView.findViewById(R.id.fragment3_webview);
    progressBar = rootView.findViewById(R.id.progressbar);

    if (Build.VERSION.SDK_INT >= 19) {
      webview.getSettings().setLoadsImagesAutomatically(true);
    } else {
      webview.getSettings().setLoadsImagesAutomatically(false);
    }

    webview.getSettings().setJavaScriptEnabled(true);
    webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

    webview.getSettings().setAllowFileAccess(true);
    webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    webview.getSettings().setAllowFileAccess(true);
    webview.getSettings().setAppCacheEnabled(true);
    webview.getSettings().setDomStorageEnabled(true);
    webview.getSettings().setDatabaseEnabled(true);
    webview.getSettings().setLoadWithOverviewMode(true);
    webview.getSettings().setUseWideViewPort(true);

    webview.setWebChromeClient(new WebChromeClient() {
      @Override public void onProgressChanged(WebView view, int newProgress) {
        JUtils.Log(TAG, "process:" + newProgress);
      }
    });

    webview.setWebViewClient(new WebViewClient() {

      @Override public void onPageFinished(WebView view, String url) {
        JUtils.Log(TAG, "onPageFinished:" + url);

        if (!webview.getSettings().getLoadsImagesAutomatically()) {
          webview.getSettings().setLoadsImagesAutomatically(true);
        }
      }

      @Override
      public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler,
          String host, String realm) {
        JUtils.Log(TAG, "onReceivedHttpAuthRequest");
        view.reload();
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        JUtils.Log(TAG, "shouldOverrideUrlLoading:" + url);
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
      }

      public void onReceivedSslError(WebView view, SslErrorHandler handler,
          SslError error) {
        JUtils.Log(TAG, "onReceivedSslError:");
        //handler.cancel(); 默认的处理方式，WebView变成空白页
        //                        //接受证书
        handler.proceed();
        //handleMessage(Message msg); 其他处理
      }
    });

    ButterKnife.bind(this, rootView);
    return rootView;
  }

  public void initView(String pid) {
    if (null != webview && !hasInited) {
      hasInited = true;
      progressBar.setVisibility(View.GONE);

      //webview.loadUrl("http://192.168.1.11:9000/mm/pdetail/10001/");
      webview.loadUrl(url + pid + "/");
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
