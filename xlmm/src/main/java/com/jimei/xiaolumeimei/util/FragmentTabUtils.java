package com.jimei.xiaolumeimei.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
    private boolean boutiqueLoad = false;
    private boolean myLoad = false;
    private int currentId;
    private boolean isVip;

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
        if ((checkedId == R.id.rb_car || checkedId == R.id.rb_boutique || checkedId == R.id.rb_my)
            && !LoginUtils.checkLoginState(context)) {
            if (currentId == R.id.rb_main || currentId == R.id.rb_category) {
                group.check(currentId);
            } else {
                group.check(R.id.rb_main);
            }
            Intent intent = new Intent(context, LoginActivity.class);
            Bundle bundle = new Bundle();
            switch (checkedId) {
                case R.id.rb_car:
                    bundle.putString("login", "car");
                    break;
                case R.id.rb_boutique:
                    bundle.putString("login", "boutique");
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
        } else if (!isVip && checkedId == R.id.rb_boutique) {
            group.check(currentId);
            new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("您暂时不是小鹿妈妈,请关注\"小鹿美美\"公众号,获取更多信息哦!")
                .setCancelable(false)
                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                .show();
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
                    synchronized (this) {
                        if (!carLoad) {
                            getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                            carLoad = true;
                        }
                    }
                    break;
                case R.id.rb_boutique:
                    synchronized (this) {
                        if (!boutiqueLoad) {
                            getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                            boutiqueLoad = true;
                        }
                    }
                    break;
                case R.id.rb_my:
                    synchronized (this) {
                        if (!myLoad) {
                            getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                            myLoad = true;
                        }
                    }
                    break;
                default:
                    getFragmentTransaction().add(containerId, fragment).commitAllowingStateLoss();
                    break;
            }
            fragment.setUserVisibleHint(true);
        }
        getFragmentTransaction().show(fragment).commitAllowingStateLoss();
        if (checkedId == R.id.rb_main || checkedId == R.id.rb_category) {
            currentId = checkedId;
        } else if (LoginUtils.checkLoginState(context) && (checkedId == R.id.rb_car || checkedId == R.id.rb_my)) {
            currentId = checkedId;
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return manager.beginTransaction();
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }
}