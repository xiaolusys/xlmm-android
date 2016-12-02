package com.jimei.xiaolumeimei.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jimei.xiaolumeimei.R;
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
    private Context context;
    private boolean isLoad = false;
    private int currentId;

    public FragmentTabUtils(FragmentManager manager, RadioGroup rgs, List<BaseFragment> fragments,
                            int containerId, Context context) {
        this.manager = manager;
        this.containerId = containerId;
        this.fragments = fragments;
        this.context = context;
        rgs.setOnCheckedChangeListener(this);
        RadioButton rBtn = (RadioButton) rgs.getChildAt(0);
        currentId = R.id.rb_main;
        rBtn.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if ((checkedId == R.id.rb_car || checkedId == R.id.rb_collect)
                && !LoginUtils.checkLoginState(context)) {
            group.check(currentId);
            Intent intent = new Intent(context, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("login", "main");
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            for (int i = 0; i < group.getChildCount(); i++) {
                BaseFragment fragment = fragments.get(i);
                if (group.getChildAt(i).getId() == checkedId) {
                    addAndShow(checkedId, fragment);
                } else {
                    getFragmentTransaction().hide(fragment).commitAllowingStateLoss();
                }
            }
        }
    }

    private void addAndShow(int checkedId, BaseFragment fragment) {
        if (!fragment.isAdded()) {
            if (checkedId != R.id.rb_car || !isLoad) {
                getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
            }
            if (checkedId == R.id.rb_car) {
                isLoad = true;
            }
            fragment.setUserVisibleHint(true);
        }
        getFragmentTransaction().show(fragment).commitAllowingStateLoss();
        if (checkedId == R.id.rb_main || checkedId == R.id.rb_boutique || checkedId == R.id.rb_my) {
            currentId = checkedId;
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return manager.beginTransaction();
    }

}