package com.jimei.library.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by wisdom on 16/11/28.
 */
public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private FragmentManager manager;
    private List<Fragment> fragments;
    private int containerId;
    private OnTabCheckListener listener;

    public FragmentTabUtils(FragmentManager manager, RadioGroup rgs, List<Fragment> fragments, int containerId, OnTabCheckListener listener) {
        this.manager = manager;
        this.containerId = containerId;
        this.fragments = fragments;
        this.listener = listener;
        rgs.setOnCheckedChangeListener(this);
        RadioButton rBtn = (RadioButton) rgs.getChildAt(0);
        rBtn.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            Fragment fragment = fragments.get(i);
            if (group.getChildAt(i).getId() == checkedId) {
                if (!fragment.isAdded()) {
                    getFragmentTransaction().add(containerId, fragment).commit();
                }
                getFragmentTransaction().show(fragment).commit();
                if (listener != null) {
                    listener.onTabCheckListener(group, checkedId, i);
                }
            } else {
                getFragmentTransaction().hide(fragment).commit();
            }
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return manager.beginTransaction();
    }

    interface OnTabCheckListener {
        void onTabCheckListener(RadioGroup group, int checkedId, int index);
    }

    public void setListener(OnTabCheckListener listener) {
        this.listener = listener;
    }
}