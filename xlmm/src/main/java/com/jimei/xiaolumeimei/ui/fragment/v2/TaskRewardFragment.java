package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.widget.MyXRecyclerView;

/**
 * Created by wisdom on 16/8/22.
 */
public class TaskRewardFragment extends Fragment {
    private static final String TYPE = "type";
    private static final String TITLE = "title";

    private int type;
    private MyXRecyclerView xrv;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_reward, container, false);
        xrv = ((MyXRecyclerView) view.findViewById(R.id.xrv));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecorationForFooter.VERTICAL_LIST));
        xrv.setPullRefreshEnabled(false);
        xrv.setLoadingMoreEnabled(false);
        initData();
    }

    private void initData() {

    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }
}
