package com.jimei.xiaolumeimei.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.widget.doubleview.CustWebView;

public class VerticalFragmentWeb extends Fragment {

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
