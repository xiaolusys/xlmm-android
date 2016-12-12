package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TaskRewardAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentTaskRewardBinding;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.net.UnknownHostException;

/**
 * Created by wisdom on 16/8/22.
 */
public class TaskRewardFragment extends BaseBindingFragment<FragmentTaskRewardBinding> {
    private static final String TYPE = "type";
    private static final String TITLE = "title";

    private int type;

    public static TaskRewardFragment newInstance(int type, String title) {
        TaskRewardFragment fragment = new TaskRewardFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    public View getLoadingView() {
        return b.xrv;
    }

    @Override
    public void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getTaskReward()
                .subscribe(new ServiceResponse<WeekTaskRewardBean>() {
                    @Override
                    public void onNext(WeekTaskRewardBean weekTaskRewardBean) {
                        TaskRewardAdapter adapter = new TaskRewardAdapter(getActivity(), weekTaskRewardBean, type);
                        b.xrv.setAdapter(adapter);
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                        if (e instanceof UnknownHostException) {
                            showNetworkError();
                        } else {
                            JUtils.Toast("数据加载有误!");
                        }
                    }
                }));
    }

    @Override
    protected void initViews() {
        b.xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        b.xrv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        b.xrv.setPullRefreshEnabled(false);
        b.xrv.setLoadingMoreEnabled(false);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_task_reward;
    }
}
