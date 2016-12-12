package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PersonalCarryRankAdapter;
import com.jimei.xiaolumeimei.adapter.TeamCarryRankAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.FragmentRankBinding;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

import java.net.UnknownHostException;

public class RankFragment extends BaseBindingFragment<FragmentRankBinding> implements ScrollableHelper.ScrollableContainer {
    private static final String TYPE = "type";
    private static final String TITLE = "title";

    private int type;
    private PersonalCarryRankAdapter personalCarryRankAdapter;
    private TeamCarryRankAdapter teamCarryRankAdapter;

    public static RankFragment newInstance(int type, String title) {
        RankFragment fragment = new RankFragment();
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
        switch (type) {
            case XlmmConst.TYPE_PERSON_RANK:
                MamaInfoModel.getInstance()
                        .getPersonalCarryRankBean()
                        .subscribe(personalCarryRankBeanResponse -> {
                            if (null != personalCarryRankBeanResponse) {
                                personalCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                            }
                            hideIndeterminateProgressDialog();
                        }, this::doOnError);
                break;
            case XlmmConst.TYPE_PERSON_WEEK_RANK:
                MamaInfoModel.getInstance()
                        .getWeekPersonalCarryRankBean()
                        .subscribe(personalCarryRankBeanResponse -> {
                            if (null != personalCarryRankBeanResponse) {
                                personalCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                            }
                            hideIndeterminateProgressDialog();
                        }, this::doOnError);
                break;
            case XlmmConst.TYPE_TEAM_RANK:
                MamaInfoModel.getInstance()
                        .getTeamCarryRankBean()
                        .subscribe(personalCarryRankBeanResponse -> {
                            if (null != personalCarryRankBeanResponse) {
                                teamCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                            }
                            hideIndeterminateProgressDialog();
                        }, this::doOnError);
                break;
            case XlmmConst.TYPE_TEAM_WEEK_RANK:
                MamaInfoModel.getInstance()
                        .getWeekTeamCarryRankBean()
                        .subscribe(personalCarryRankBeanResponse -> {
                            if (null != personalCarryRankBeanResponse) {
                                teamCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                            }
                            hideIndeterminateProgressDialog();
                        }, this::doOnError);
                break;
        }
    }

    private void doOnError(Throwable e) {
        hideIndeterminateProgressDialog();
        if (e instanceof UnknownHostException) {
            showNetworkError();
        } else {
            JUtils.Toast("数据加载有误!");
        }
    }

    @Override
    protected void initViews() {
        b.xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        b.xrv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        b.xrv.setPullRefreshEnabled(false);
        b.xrv.setLoadingMoreEnabled(false);
        switch (type) {
            case XlmmConst.TYPE_PERSON_RANK:
            case XlmmConst.TYPE_PERSON_WEEK_RANK:
                personalCarryRankAdapter = new PersonalCarryRankAdapter(getActivity());
                b.xrv.setAdapter(personalCarryRankAdapter);
                break;
            case XlmmConst.TYPE_TEAM_RANK:
            case XlmmConst.TYPE_TEAM_WEEK_RANK:
                teamCarryRankAdapter = new TeamCarryRankAdapter(getActivity());
                b.xrv.setAdapter(teamCarryRankAdapter);
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_rank;
    }

    @Override
    public View getScrollableView() {
        return b.xrv;
    }
}
