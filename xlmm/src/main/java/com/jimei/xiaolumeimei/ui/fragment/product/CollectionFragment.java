package com.jimei.xiaolumeimei.ui.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CollectionAdapter;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionBean;
import com.jimei.xiaolumeimei.entities.event.CollectChangeEvent;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class CollectionFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "param2";
    private String type;
    private ArrayList<CollectionBean> collectionList;
    private View emptyLayout;
    private TextView emptyBtn;
    private XRecyclerView recyclerView;
    private CollectionAdapter adapter;
    private int page;
    private String next;

    public static CollectionFragment newInstance(String title, String type) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = 1;
            collectionList = new ArrayList<>();
            type = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        emptyLayout = view.findViewById(R.id.empty_layout);
        emptyBtn = ((TextView) view.findViewById(R.id.jump_view));
        recyclerView = ((XRecyclerView) view.findViewById(R.id.xrv));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyBtn.setOnClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setPullRefreshEnabled(false);
        adapter = new CollectionAdapter(this, getActivity(), collectionList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (next == null || "".equals(next)) {
                    JUtils.Toast("已经到底啦");
                    recyclerView.post(recyclerView::loadMoreComplete);
                } else {
                    initData();
                }
            }
        });
        initData();
    }

    public void showEmpty() {
        emptyLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void initData() {
        ProductModel.getInstance()
                .getCollection(page, type)
                .subscribe(new ServiceResponse<CollectionAllBean>() {
                    @Override
                    public void onNext(CollectionAllBean collectionAllBean) {
                        List<CollectionBean> collectionBean = collectionAllBean.getResults();
                        collectionList.addAll(collectionBean);
                        if (collectionBean.size() > 0) {
                            emptyLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showEmpty();
                        }
                        adapter.notifyDataSetChanged();
                        next = collectionAllBean.getNext();
                        if (next != null && !"".equals(next)) {
                            page++;
                        }
                    }

                    @Override
                    public void onCompleted() {
                        recyclerView.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        recyclerView.loadMoreComplete();
                        JUtils.Toast("数据加载有误!");
                    }
                });
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString("title");
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jump_view:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoadData(CollectChangeEvent event) {
        if (collectionList != null) {
            page = 1;
            collectionList.clear();
            initData();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
