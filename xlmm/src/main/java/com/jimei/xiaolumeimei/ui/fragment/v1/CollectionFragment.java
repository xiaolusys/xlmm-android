package com.jimei.xiaolumeimei.ui.fragment.v1;

import android.content.Context;
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
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CollectionAdapter;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.product.CollectionActivity;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import rx.schedulers.Schedulers;


public class CollectionFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String title;
    private String type;
    private ArrayList<CollectionBean> collectionList;
    private View emptyLayout;
    private TextView emptyBtn;
    private XRecyclerView recyclerView;
    private CollectionAdapter adapter;
    private int page;
    private String next;
    private CollectionActivity mContext;

    public static CollectionFragment newInstance(String title, String type) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = ((CollectionActivity) context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = 1;
            collectionList = new ArrayList<>();
            title = getArguments().getString(ARG_PARAM1);
            type = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
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
                .subscribeOn(Schedulers.io())
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
                        mContext.hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onCompleted() {
                        recyclerView.post(recyclerView::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        recyclerView.post(recyclerView::loadMoreComplete);
                        mContext.hideIndeterminateProgressDialog();
                        JUtils.Toast("数据加载有误!");
                    }
                });
    }

    public String getTitle() {
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        } else {
            title = "";
        }
        return title;
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
}
