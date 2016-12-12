package com.jimei.xiaolumeimei.ui.fragment.mminfo;


import android.os.Bundle;
import android.webkit.WebView;

import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

public class MamaSecondFragment extends BaseWebViewFragment {

    public static MamaSecondFragment newInstance(String title, int id) {
        MamaSecondFragment fragment = new MamaSecondFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaUrl()
                .subscribe(mamaUrl -> {
                    loadUrl(mamaUrl.getResults().get(0).getExtra().getForum());
                    hideIndeterminateProgressDialog();
                }, throwable -> {
                    throwable.printStackTrace();
                    hideIndeterminateProgressDialog();
                }));
    }

    public WebView getWebView() {
        if (b != null) {
            return b.wbView;
        }
        return null;
    }
}
