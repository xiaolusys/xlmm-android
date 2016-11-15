package com.jimei.xiaolumeimei.ui.fragment.v2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.CountDownView;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by wisdom on 16/8/16.
 */
public class ProductListFragment extends Fragment implements ScrollableHelper.ScrollableContainer {

    XRecyclerView xRecyclerView;
    private View view;
    CountDownView countTime;
    private int type;
    private int page = 1;
    private String next;
    private Activity mActivity;
    private ProductListAdapter adapter;
    private CompositeSubscription mCompositeSubscription;
    private boolean mIsHidden = true;
    private static final String FRAGMENT_STORE = "STORE";
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

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
        if (savedInstanceState != null) {
            mIsHidden = savedInstanceState.getBoolean(FRAGMENT_STORE);
        }

        if (restoreInstanceState()) {
            processRestoreInstanceState(savedInstanceState);
        }
        setRetainInstance(true);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    protected boolean restoreInstanceState() {
        return true;
    }

    private void processRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden()) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    public boolean isSupportHidden() {
        return mIsHidden;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.xrv);
        initViews();
        isInitView = true;
        lazyLoadData();
        return view;
    }

    private void lazyLoadData() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        loadMore(page);
        isFirstLoad = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENT_STORE, isHidden());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews() {
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.today_poster_header, null);
        countTime = (CountDownView) head.findViewById(R.id.count_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.addHeaderView(head);
        adapter = new ProductListAdapter(mActivity);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    loadMore(page);
                } else {
                    JUtils.Toast("已经到底啦!");
                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                }
            }
        });
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

    private void loadMore(int num) {
        addSubscription(ProductModel.getInstance()
                .getProductListBean(num, type)
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        if (productListBean != null) {
                            next = productListBean.getNext();
                            if (next != null && !"".equals(next)) {
                                page++;
                            }
                            countTime.start(calcLeftTime(productListBean.getOffshelf_deadline())
                                    , CountDownView.TYPE_ALL);
                            adapter.update(productListBean.getResults());
                        }
                        xRecyclerView.post(xRecyclerView::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("数据加载失败,请刷新重试!");
                    }
                }));
    }

    public void load(SwipeRefreshLayout swipeRefreshLayout) {
        page = 1;
        ProductModel.getInstance()
                .getProductListBean(page, type)
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        if (productListBean != null) {
                            next = productListBean.getNext();
                            if (next != null && !"".equals(next)) {
                                page++;
                            }
                            countTime.start(calcLeftTime(productListBean.getOffshelf_deadline())
                                    , CountDownView.TYPE_ALL);
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

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroyView() {
        try {
            if (this.mCompositeSubscription != null) {
                this.mCompositeSubscription.unsubscribe();
                this.mCompositeSubscription = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString("title");
        }
        return "";
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
