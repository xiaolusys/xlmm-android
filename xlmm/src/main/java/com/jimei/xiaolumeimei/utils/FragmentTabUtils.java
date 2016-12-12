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
    private boolean carLoad = false;
    private boolean collectLoad = false;
    private boolean myLoad = false;
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
        if ((checkedId == R.id.rb_car || checkedId == R.id.rb_collect || checkedId == R.id.rb_my)
                && !LoginUtils.checkLoginState(context)) {
            group.check(currentId);
            Intent intent = new Intent(context, LoginActivity.class);
            Bundle bundle = new Bundle();
            switch (checkedId) {
                case R.id.rb_car:
                    bundle.putString("login", "car");
                    break;
                case R.id.rb_collect:
                    bundle.putString("login", "collect");
                    break;
                case R.id.rb_my:
                    bundle.putString("login", "my");
                    break;
                default:
                    bundle.putString("login", "main");
                    break;
            }
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
            switch (checkedId) {
                case R.id.rb_car:
                    if (!carLoad) {
                        getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                        carLoad = true;
                    }
                    break;
                case R.id.rb_collect:
                    if (!collectLoad) {
                        getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                        collectLoad = true;
                    }
                    break;
                case R.id.rb_my:
                    if (!myLoad) {
                        getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                        myLoad = true;
                    }
                    break;
                default:
                    getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                    break;
            }
            fragment.setUserVisibleHint(true);
        }
        getFragmentTransaction().show(fragment).commitAllowingStateLoss();
        if (checkedId == R.id.rb_main || checkedId == R.id.rb_boutique) {
            currentId = checkedId;
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return manager.beginTransaction();
    }

}