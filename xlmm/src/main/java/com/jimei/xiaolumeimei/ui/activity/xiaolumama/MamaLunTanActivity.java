package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;

/**
 * Created by wisdom on 16/8/12.
 */
public class MamaLunTanActivity extends CommonWebViewActivity {
    @Override
    protected void initViews() {
        if (getActlink()==null||"".equals(getActlink())) {
            setActlink("http://forum.xiaolumeimei.com/accounts/xlmm/login/");
        }
        super.initViews();
        titleView.setName("小鹿论坛");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                JUtils.Toast("加载失败");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
