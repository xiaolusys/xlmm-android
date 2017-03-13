package com.jimei.xiaolumeimei.ui.fragment.product;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.OnScrollCallback;
import com.jimei.library.widget.banner.SliderLayout;
import com.jimei.library.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.library.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.library.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.MainProductAdapter;
import com.jimei.xiaolumeimei.adapter.MainTabAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentTodayNewBinding;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.event.BoutiqueEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wisdom on 17/2/13.
 */

public class TodayNewFragment extends BaseBindingFragment<FragmentTodayNewBinding>
    implements ScrollableHelper.ScrollableContainer, SwipeRefreshLayout.OnRefreshListener,
    ScrollableLayout.OnScrollListener, OnScrollCallback {

    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    private Map<String, String> map = new HashMap<>();
    private MainTabAdapter mainTabAdapter;
    private List<MainTodayBean> data = new ArrayList<>();
    private MainProductAdapter mainProductAdapter;
    private int width;
    private int scrollCount;
    private int state;
    private int moveX;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mainTabAdapter.setCurrentPosition(msg.what);
        }
    };

    public static TodayNewFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        TodayNewFragment fragment = new TodayNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getLoadingView() {
        return b.scrollLayout;
    }

    @Override
    public void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getPortalBean("activitys,categorys", new ServiceResponse<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    initSliderLayout(portalBean);
                }
            }));
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getMainTodayList(new ServiceResponse<List<MainTodayBean>>() {
                @Override
                public void onNext(List<MainTodayBean> list) {
                    initTodayList(list);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    private void initTodayList(List<MainTodayBean> list) {
        data.clear();
        data.addAll(list);
        mainTabAdapter.updateWithClear(list);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        b.recyclerTab.scrollBy(-scrollCount * width, 0);
        scrollCount = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getHour() <= hour) {
                scrollCount = i;
            }
        }
        mainTabAdapter.setCurrentPosition(scrollCount + 2);
        b.recyclerTab.scrollBy(scrollCount * width, 0);
        mainProductAdapter.updateWithClear(list.get(scrollCount).getItems());
        hideIndeterminateProgressDialog();
    }

    @Override
    public void setListener() {
        b.swipeLayout.setOnRefreshListener(this);
        b.scrollLayout.setOnScrollListener(this);
        b.recyclerTab.setOnScrollCallback(this);
    }

    @Override
    protected void initViews() {
        width = JUtils.getScreenWidth() / 5;
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);
        b.recyclerProduct.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mainProductAdapter = new MainProductAdapter(mActivity);
        b.recyclerProduct.setAdapter(mainProductAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        b.recyclerTab.setLayoutManager(manager);
        mainTabAdapter = new MainTabAdapter(mActivity) {
            @Override
            public void itemClick(int count, int position) {
                scrollCount += count;
                b.recyclerTab.scrollBy(count * width, 0);
                mainProductAdapter.updateWithClear(data.get(position).getItems());
                b.recyclerProduct.scrollToPosition(0);
            }
        };
        b.recyclerTab.setAdapter(mainTabAdapter);
        b.scrollLayout.getHelper().setCurrentScrollableContainer(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_today_new;
    }

    public void initSliderLayout(PortalBean postBean) throws RuntimeException {
        map.clear();
        List<PortalBean.PostersBean> posters = postBean.getPosters();
        for (int i = 0; i < posters.size(); i++) {
            map.put(posters.get(i).getPic_link(), posters.get(i).getApp_link());
        }
        if (b.slider != null) {
            b.slider.stopAutoCycle();
            b.slider.removeAllSliders();
        }
        for (String name : map.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(mActivity);
            textSliderView.image(name + POST_URL).setScaleType(BaseSliderView.ScaleType.CenterInside);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", map.get(name));
            b.slider.addSlider(textSliderView);
            textSliderView.setOnSliderClickListener(slider -> {
                if (slider.getBundle() != null) {
                    MobclickAgent.onEvent(mActivity, "BannerID");
                    String link = slider.getBundle().getString("extra");
                    JumpUtils.push_jump_proc(mActivity, link);
                }
            });
        }
        b.slider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
        b.slider.setDuration(3000);
        b.slider.startAutoCycle();
    }

    @Override
    public View getScrollableView() {
        return b.recyclerProduct;
    }

    @Override
    public void onRefresh() {
        b.swipeLayout.setRefreshing(false);
        EventBus.getDefault().post(new BoutiqueEvent());
        initData();
    }

    @Override
    public void onScroll(int currentY, int maxY) {
        if (mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).setTabLayoutMarginTop((double) currentY / b.slider.getHeight());
        } else if (mActivity instanceof TabActivity) {
            double percent = (double) currentY / b.slider.getHeight();
            Message msg = new Message();
            msg.what = (int) (percent * 100);
            ((TabActivity) mActivity).mHandler.sendMessage(msg);
        }
        if (currentY > 0) {
            b.swipeLayout.setEnabled(false);
        } else {
            b.swipeLayout.setEnabled(true);
        }
    }

    @Override
    public void onStateChanged(int state) {
        this.state = state;
        if (state == 1) {
            moveX = 0;
        }
        if (state == 0) {
            int lastMoveX;
            if (moveX > 0 && moveX >= width / 2) {
                scrollCount += 1;
                lastMoveX = width - moveX;
            } else if (moveX < 0 && moveX <= -width / 2) {
                scrollCount -= 1;
                lastMoveX = -width - moveX;
            } else {
                lastMoveX = -moveX;
            }
            b.recyclerTab.scrollBy(lastMoveX, 0);
            if (scrollCount < 0) {
                scrollCount = 0;
            } else if (scrollCount > data.size() - 1) {
                scrollCount = data.size() - 1;
            }
            mainTabAdapter.setCurrentPosition(scrollCount + 2);
            mainProductAdapter.updateWithClear(data.get(scrollCount).getItems());
            b.recyclerProduct.scrollToPosition(0);
        }
        JUtils.Log("State:::" + state);
    }

    @Override
    public void onScroll(int dx) {
        JUtils.Log("Left :<<" + dx);
        if (state > 0) {
            moveX += dx;
            if (moveX / width != 0) {
                scrollCount += moveX / width;
                moveX = moveX % width;
            }
            if (moveX > 0 && moveX >= width / 2) {
                Message msg = new Message();
                msg.what = scrollCount + 3;
                handler.sendMessage(msg);
            } else if (moveX < 0 && moveX <= -width / 2) {
                Message msg = new Message();
                msg.what = scrollCount + 1;
                handler.sendMessage(msg);
            }
        }
    }
}
