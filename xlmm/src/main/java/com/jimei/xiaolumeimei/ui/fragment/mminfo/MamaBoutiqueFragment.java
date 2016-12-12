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
}
