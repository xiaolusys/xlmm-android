package com.jimei.xiaolumeimei.ui.fragment.v2;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ProductListBeanAdapter;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;
import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 16/8/16.
 */
public class ProductListFragment extends Fragment implements ScrollableHelper.ScrollableContainer {

    @Bind(R.id.xrv)
    XRecyclerView xRecyclerView;
    private View view;
    private CountdownView countTime;
    private int type;
    private Thread thread;
    private long left;
    private int page = 1;
    private String next;
    private ProductListBeanAdapter adapter;


    public static ProductListFragment newInstance(int type, String title) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("title", title);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.xrv);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadMore(page, false);
    }

    private void initViews() {
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.today_poster_header,
                (ViewGroup) view.findViewById(R.id.head_today), false);
        countTime = (CountdownView) head.findViewById(R.id.countTime);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.addHeaderView(head);
        adapter = new ProductListBeanAdapter(getContext(), this);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    loadMore(page, false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                }
            }
        });
    }

    private void initLeftTime() {
        if (thread == null) {
            thread = new Thread(() -> {
                while (left > 0) {
                    left = left - 1000;
                    SystemClock.sleep(1000);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() ->
                                countTime.updateShow(left));
                    }
                }
            });
            thread.start();
        }
    }

    private long calcLeftTime(String crtTime) {
        long left = 0;
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            crtTime = crtTime.replace("T", " ");
            Date crtdate = format.parse(crtTime);
            if (crtdate.getTime() - now.getTime() > 0) {
                left = crtdate.getTime() - now.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return left;
    }

    private void loadMore(int num, boolean isClear) {
        ProductModel.getInstance()
                .getProductListBean(num, type)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        if (productListBean != null) {
                            next = productListBean.getNext();
                            if (next != null && !"".equals(next)) {
                                page++;
                            }
                            left = calcLeftTime(productListBean.getOffshelf_deadline());
                            initLeftTime();
                            if (isClear) {
                                adapter.updateWithClear(productListBean.getResults());
                            } else {
                                adapter.update(productListBean.getResults());
                            }
                        }
                        xRecyclerView.post(xRecyclerView::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("数据加载失败,请刷新重试!");
                    }
                });

    }

    public void load(SwipeRefreshLayout swipeRefreshLayout) {
        page = 1;
        ProductModel.getInstance()
                .getProductListBean(page, type)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        if (productListBean != null) {
                            next = productListBean.getNext();
                            if (next != null && !"".equals(next)) {
                                page++;
                            }
                            left = calcLeftTime(productListBean.getOffshelf_deadline());
                            initLeftTime();
                            adapter.updateWithClear(productListBean.getResults());
                            if (swipeRefreshLayout != null) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("数据加载失败,请刷新重试!");
                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public View getScrollableView() {
        return xRecyclerView;
    }

    public String getTitle() {
        String title = "";
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        return title;
    }

    public void goToTop() {
        try {
            xRecyclerView.scrollToPosition(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
