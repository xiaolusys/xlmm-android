package com.jimei.xiaolumeimei.ui.fragment.v2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.MyXRecyclerView;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PersonalCarryRankAdapter;
import com.jimei.xiaolumeimei.adapter.TeamCarryRankAdapter;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.List;

public class RankFragment extends Fragment implements ScrollableHelper.ScrollableContainer {
    private static final String TYPE = "type";
    private static final String TITLE = "title";

    private int type;
    private MyXRecyclerView xrv;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        xrv = ((MyXRecyclerView) view.findViewById(R.id.xrv));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        xrv.setPullRefreshEnabled(false);
        xrv.setLoadingMoreEnabled(false);
        switch (type) {
            case XlmmConst.TYPE_PERSON_RANK:
            case XlmmConst.TYPE_PERSON_WEEK_RANK:
                personalCarryRankAdapter = new PersonalCarryRankAdapter(getActivity());
                xrv.setAdapter(personalCarryRankAdapter);
                break;
            case XlmmConst.TYPE_TEAM_RANK:
            case XlmmConst.TYPE_TEAM_WEEK_RANK:
                teamCarryRankAdapter = new TeamCarryRankAdapter(getActivity());
                xrv.setAdapter(teamCarryRankAdapter);
                break;
        }
        initData();
    }

    private void initData() {
        switch (type) {
            case XlmmConst.TYPE_PERSON_RANK:
                MamaInfoModel.getInstance()
                        .getPersonalCarryRankBean()
                        .subscribe(new ServiceResponse<List<PersonalCarryRankBean>>() {
                            @Override
                            public void onNext(List<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                                if (null != personalCarryRankBeanResponse) {
                                    personalCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                                }
                            }
                        });
                break;
            case XlmmConst.TYPE_PERSON_WEEK_RANK:
                MamaInfoModel.getInstance()
                        .getWeekPersonalCarryRankBean()
                        .subscribe(new ServiceResponse<List<PersonalCarryRankBean>>() {
                            @Override
                            public void onNext(List<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                                if (null != personalCarryRankBeanResponse) {
                                    personalCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                                }
                            }
                        });
                break;
            case XlmmConst.TYPE_TEAM_RANK:
                MamaInfoModel.getInstance()
                        .getTeamCarryRankBean()
                        .subscribe(new ServiceResponse<List<PersonalCarryRankBean>>() {
                            @Override
                            public void onNext(List<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                                if (null != personalCarryRankBeanResponse) {
                                    teamCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                                }
                            }
                        });
                break;
            case XlmmConst.TYPE_TEAM_WEEK_RANK:
                MamaInfoModel.getInstance()
                        .getWeekTeamCarryRankBean()
                        .subscribe(new ServiceResponse<List<PersonalCarryRankBean>>() {
                            @Override
                            public void onNext(List<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                                if (null != personalCarryRankBeanResponse) {
                                    teamCarryRankAdapter.addAll(personalCarryRankBeanResponse);
                                }
                            }
                        });
                break;
        }
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString("title");
        }
        return "";
    }

    @Override
    public View getScrollableView() {
        return xrv;
    }
}
