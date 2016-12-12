package com.jimei.xiaolumeimei.ui.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CollectionAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentCollectionBinding;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionBean;
import com.jimei.xiaolumeimei.entities.event.CollectChangeEvent;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.UnknownHostException;
import java.util.ArrayList;


public class CollectionFragment extends BaseBindingFragment<FragmentCollectionBinding> implements View.OnClickListener {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "param2";
    private String type;
    private ArrayList<CollectionBean> collectionList;
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
    public View getLoadingView() {
        return b.xrv;
    }

    @Override
    public void setListener() {
        b.jumpView.setOnClickListener(this);
    }


    public void showEmpty() {
        b.emptyLayout.setVisibility(View.VISIBLE);
        b.xrv.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        addSubscription(ProductModel.getInstance()
                .getCollection(page, type)
                .subscribe(new ServiceResponse<CollectionAllBean>() {
                    @Override
                    public void onNext(CollectionAllBean collectionAllBean) {
                        collectionList.addAll(collectionAllBean.getResults());
                        if (collectionList.size() > 0) {
                            b.emptyLayout.setVisibility(View.GONE);
                            b.xrv.setVisibility(View.VISIBLE);
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
                        hideIndeterminateProgressDialog();
                        b.xrv.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        b.xrv.loadMoreComplete();
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
        EventBus.getDefault().register(this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setPullRefreshEnabled(false);
        adapter = new CollectionAdapter(this, getActivity(), collectionList);
        b.xrv.setAdapter(adapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (next == null || "".equals(next)) {
                    JUtils.Toast("已经到底啦");
                    b.xrv.loadMoreComplete();
                } else {
                    initData();
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jump_view:
                Bundle bundle = new Bundle();
                bundle.putString("flag", "main");
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
