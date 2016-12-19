package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityGoodWeekBinding;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.TaskRewardFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;


public class GoodWeekActivity extends BaseMVVMActivity<ActivityGoodWeekBinding> {

    @Override
    protected void initViews() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(TaskRewardFragment.newInstance(XlmmConst.TYPE_REWARD_PERSONAL, "个人任务"));
        fragments.add(TaskRewardFragment.newInstance(XlmmConst.TYPE_REWARD_TEAM, "团队任务"));

        BaseTabAdapter adapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(adapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
    }


    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(MamaInfoModel.getInstance()
                .getTaskReward()
                .subscribe(new ServiceResponse<WeekTaskRewardBean>() {
                    @Override
                    public void onNext(WeekTaskRewardBean weekTaskRewardBean) {
                        fillDataToView(weekTaskRewardBean);
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                        JUtils.Toast("暂时无数据!");
                    }
                }));
    }

    private void fillDataToView(WeekTaskRewardBean bean) {
        b.money.setText("" + bean.getStaging_award_amount() / 100.00);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_good_week;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

}
