package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;

import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by wisdom on 16/11/14.
 */

public class MMFansWebFragment extends BaseWebViewFragment {

    public static MMFansWebFragment newInstance(String title) {
        MMFansWebFragment fragment = new MMFansWebFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(sticky = true)
    public void getWebViewInfo(WebViewEvent event) {
        loadUrl(event.actlink);
    }
}
