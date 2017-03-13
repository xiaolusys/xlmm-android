package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.os.Bundle;

import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by wisdom on 16/11/14.
 */

public class MMFansWebFragment extends BaseWebViewFragment {
    private String link = "";

    public static MMFansWebFragment newInstance(String title) {
        MMFansWebFragment fragment = new MMFansWebFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        super.initViews();
    }

    @Override
    public void initData() {
        if (link != null && !link.equals("")) {
            loadUrl(link);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WebViewEvent stickyEvent = EventBus.getDefault().getStickyEvent(WebViewEvent.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void getWebViewInfo(WebViewEvent event) {
        link = event.actlink;
        initData();
    }
}
