package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TaskRewardPagerAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityGoodWeekBinding;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.fragment.v2.TaskRewardFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;

import rx.schedulers.Schedulers;


public class GoodWeekActivity extends BaseMVVMActivity<ActivityGoodWeekBinding> {
    private ArrayList<TaskRewardFragment> fragments;

    @Override
    protected void initView() {
        fragments = new ArrayList<>();
        fragments.add(TaskRewardFragment.newInstance(XlmmConst.TYPE_REWARD_PERSONAL, "个人任务"));
        fragments.add(TaskRewardFragment.newInstance(XlmmConst.TYPE_REWARD_TEAM, "团队任务"));

        TaskRewardPagerAdapter adapter = new TaskRewardPagerAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(adapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(MamaInfoModel.getInstance()
                .getTaskReward()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<WeekTaskRewardBean>() {
                    @Override
                    public void onNext(WeekTaskRewardBean weekTaskRewardBean) {
                        fillDataToView(weekTaskRewardBean);
                        hideIndeterminateProgressDialog();
                    }
                }));
    }

    private void fillDataToView(WeekTaskRewardBean bean) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_good_week;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected BaseMVVMActivity.TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
