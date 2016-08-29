package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.iwgang.countdownview.CountdownView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ProductListBeanAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.databinding.FragmentProductListBinding;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import java.text.SimpleDateFormat;
import java.util.Date;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(http://www.itxuye.com)
 */
public class ProductListLazyFragment extends BaseLazyFragment<FragmentProductListBinding>
    implements ScrollableHelper.ScrollableContainer {

  private CountdownView countTime;
  private int type;
  private Thread thread;
  private long left;
  private int page = 1;
  private String next;
  private ProductListBeanAdapter adapter;

  public static ProductListLazyFragment newInstance(int type, String title) {
    Bundle args = new Bundle();
    args.putInt("type", type);
    args.putString("title", title);
    ProductListLazyFragment fragment = new ProductListLazyFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      type = getArguments().getInt("type");
    }
  }

  @Override protected void initData() {
    loadMore(page, false);
  }

  @Override protected void initViews() {
    View head = LayoutInflater.from(getActivity())
        .inflate(R.layout.today_poster_header,
            (ViewGroup) b.getRoot().findViewById(R.id.head_today), false);
    countTime = (CountdownView) head.findViewById(R.id.countTime);
    GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
    b.xrv.setLayoutManager(manager);
    b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
    b.xrv.addItemDecoration(new SpaceItemDecoration(10));
    b.xrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    b.xrv.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    b.xrv.setArrowImageView(R.drawable.iconfont_downgrey);
    b.xrv.setPullRefreshEnabled(false);
    b.xrv.addHeaderView(head);
    adapter = new ProductListBeanAdapter(mActivity, this);
    b.xrv.setAdapter(adapter);
    b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        if (next != null && !"".equals(next)) {
          loadMore(page, false);
        } else {
          JUtils.Toast("已经到底啦!");
          b.xrv.post(b.xrv::loadMoreComplete);
          b.xrv.setLoadingMoreEnabled(false);
        }
      }
    });
  }

  @Override protected int getContentViewId() {
    return R.layout.fragment_product_list;
  }


  private void initLeftTime() {
    if (thread == null) {
      thread = new Thread(() -> {
        while (left > 0) {
          left = left - 1000;
          SystemClock.sleep(1000);
          if (getActivity() != null) {
            getActivity().runOnUiThread(() -> countTime.updateShow(left));
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
          @Override public void onNext(ProductListBean productListBean) {
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
            b.xrv.post(b.xrv::loadMoreComplete);
          }

          @Override public void onError(Throwable e) {
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
          @Override public void onNext(ProductListBean productListBean) {
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

          @Override public void onError(Throwable e) {
            JUtils.Toast("数据加载失败,请刷新重试!");
          }
        });
  }

  @Override public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
  }

  @Override public View getScrollableView() {
    return b.xrv;
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
      b.xrv.scrollToPosition(0);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Override public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
  }
}
