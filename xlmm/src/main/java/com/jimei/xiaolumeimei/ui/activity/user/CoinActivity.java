package com.jimei.xiaolumeimei.ui.activity.user;

import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CoinHisListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CoinHistoryListBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;

import java.util.List;

import butterknife.Bind;

public class CoinActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, ScrollableHelper.ScrollableContainer, AbsListView.OnScrollListener {
    String TAG = "CoinActivity";
    @Bind(R.id.btn_jump)
    Button btn_jump;
    @Bind(R.id.rlayout_order_empty)
    RelativeLayout rlayout_order_empty;
    @Bind(R.id.all_points_listview)
    ListView all_points_listview;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    TextView tx_point;
    private CoinHisListAdapter mPointAdapter;
    private boolean flag;
    private int page = 1;
    private String next;

    @Override
    protected void setListener() {
        btn_jump.setOnClickListener(this);
        all_points_listview.setOnScrollListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_membershippoint;
    }

    @Override
    public View getLoadingView() {
        return scrollableLayout;
    }

    @Override
    protected void initViews() {
        scrollableLayout.getHelper().setCurrentScrollableContainer(this);
        mPointAdapter = new CoinHisListAdapter(this);
        all_points_listview.setAdapter(mPointAdapter);
        tx_point = (TextView) findViewById(R.id.tv_Point);
        ((TextView) findViewById(R.id.tx_info)).setText("亲，您暂时还没有小鹿币记录哦~");
        ((TextView) findViewById(R.id.tx2)).setText("快去下单吧~");
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(R.drawable.img_emptypoint);
    }


    @Override
    protected void initData() {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getProfile(new ServiceResponse<UserInfoBean>() {
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    tx_point.setText("" + userInfoBean.getXiaoluCoin());
                }
            }));
        loadCoinList();
    }

    private void loadCoinList() {
        showIndeterminateProgressDialog(false);
        addSubscription(XlmmApp.getUserInteractor(this)
            .getCoinHisList(page, new ServiceResponse<CoinHistoryListBean>() {
                @Override
                public void onNext(CoinHistoryListBean historyListBean) {
                    List<CoinHistoryListBean.ResultsBean> result = historyListBean.getResults();
                    if (0 == result.size() && page == 1) {
                        rlayout_order_empty.setVisibility(View.VISIBLE);
                    }
                    next = historyListBean.getNext();
                    if (next == null || "".equals(next)) {
                        JUtils.Toast("全部加载完成!");
                    }
                    mPointAdapter.update(result);
                    page++;
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                readyGo(TabActivity.class);
                finish();
                break;
        }
    }

    @Override
    public View getScrollableView() {
        return all_points_listview;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && flag) {
            if (next != null && !"".equals(next)) {
                loadCoinList();
            }
            flag = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if ((firstVisibleItem + visibleItemCount) == totalItemCount && totalItemCount > 0) {
            flag = true;
        }
    }
}
