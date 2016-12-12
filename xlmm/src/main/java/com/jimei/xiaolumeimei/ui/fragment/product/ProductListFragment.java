package com.jimei.xiaolumeimei.ui.fragment.product;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.DateUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.CountDownView;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ProductListAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentProductListBinding;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.net.UnknownHostException;


/**
 * Created by wisdom on 16/8/16.
 */
public class ProductListFragment extends BaseBindingFragment<FragmentProductListBinding> implements ScrollableHelper.ScrollableContainer {
    CountDownView countTime;
    private int type;
    private int page = 1;
    private String next;
    private ProductListAdapter adapter;


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
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View getLoadingView() {
        return b.xrv;
    }


    @Override
    public void initData() {
        page = 1;
        adapter.clear();
        loadMore();
    }

    @Override
    protected void initViews() {
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.today_poster_header, null);
        countTime = (CountDownView) head.findViewById(R.id.count_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setArrowImageView(R.drawable.iconfont_downgrey);
        b.xrv.setPullRefreshEnabled(false);
        b.xrv.addHeaderView(head);
        adapter = new ProductListAdapter(mActivity);
        b.xrv.setAdapter(adapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    loadMore();
                } else {
                    JUtils.Toast("已经到底啦!");
                    b.xrv.loadMoreComplete();
                }
            }
        });
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_product_list;
    }

    private void loadMore() {
        addSubscription(ProductModel.getInstance()
                .getProductListBean(page, type)
                .subscribe(new ServiceResponse<ProductListBean>() {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        if (productListBean != null) {
                            next = productListBean.getNext();
                            if (next != null && !"".equals(next)) {
                                page++;
                            }
                            countTime.start(DateUtils.calcLeftTime(productListBean.getOffshelf_deadline())
                                    , CountDownView.TYPE_ALL);
                            adapter.update(productListBean.getResults());
                        }
                        b.xrv.loadMoreComplete();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                        if (e instanceof UnknownHostException) {
                            showNetworkError();
                        } else {
                            JUtils.Toast("数据加载有误,请下拉刷新!");
                        }
                    }
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public View getScrollableView() {
        if (b != null) {
            return b.xrv;
        }
        return null;
    }

    public void goToTop() {
        try {
            b.xrv.scrollToPosition(0);
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
