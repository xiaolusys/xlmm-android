package com.jimei.xiaolumeimei.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;

import java.util.List;

/**
 * Created by wisdom on 16/11/28.
 */
public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private FragmentManager manager;
    private List<BaseFragment> fragments;
    private int containerId;
    private OnTabCheckListener listener;
    private Context context;

    public FragmentTabUtils(FragmentManager manager, RadioGroup rgs, List<BaseFragment> fragments,
                            int containerId, OnTabCheckListener listener, Context context) {
        this.manager = manager;
        this.containerId = containerId;
        this.fragments = fragments;
        this.listener = listener;
        this.context = context;
        rgs.setOnCheckedChangeListener(this);
        RadioButton rBtn = (RadioButton) rgs.getChildAt(0);
        rBtn.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if ((checkedId == group.getChildAt(2).getId() || checkedId == group.getChildAt(3).getId())
                && !LoginUtils.checkLoginState(context)) {
            group.check(group.getChildAt(0).getId());
            Intent intent = new Intent(context, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("login", "main");
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            for (int i = 0; i < group.getChildCount(); i++) {
                BaseFragment fragment = fragments.get(i);
                if (group.getChildAt(i).getId() == checkedId) {
                    addAndShow(group, checkedId, i, fragment);
                } else {
                    getFragmentTransaction().hide(fragment).commit();
                }
            }
        }
    }

    private void addAndShow(RadioGroup group, int checkedId, int i, BaseFragment fragment) {
        if (!fragment.isAdded()) {
            getFragmentTransaction().add(containerId, fragment).commit();
            fragment.setUserVisibleHint(true);
        }
        getFragmentTransaction().show(fragment).commit();
        if (listener != null) {
            listener.onTabCheckListener(group, checkedId, i);
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return manager.beginTransaction();
    }

    public interface OnTabCheckListener {
        void onTabCheckListener(RadioGroup group, int checkedId, int index);
    }

    public void setListener(OnTabCheckListener listener) {
        this.listener = listener;
    }
}