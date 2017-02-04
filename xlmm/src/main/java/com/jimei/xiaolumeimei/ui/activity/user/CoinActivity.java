package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
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
import com.jimei.xiaolumeimei.adapter.CoinHisListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CoinHistoryListBean;
import com.jimei.xiaolumeimei.model.MainModel;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

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
    boolean flag;
    int page = 2;

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
        showIndeterminateProgressDialog(false);
        addSubscription(MainModel.getInstance()
                .getProfile()
                .subscribe(userInfoBean -> {
                    tx_point.setText("" + userInfoBean.getXiaoluCoin());
                }, throwable -> {
                    hideIndeterminateProgressDialog();
                }));
        addSubscription(UserModel.getInstance()
                .getCoinHisList("1")
                .subscribe(historyListBean -> {
                    List<CoinHistoryListBean.ResultsBean> result = historyListBean.getResults();
                    if (0 == result.size()) {
                        rlayout_order_empty.setVisibility(View.VISIBLE);
                    } else {
                        mPointAdapter.update(result);
                    }
                    hideIndeterminateProgressDialog();
                }, throwable -> {
                    hideIndeterminateProgressDialog();
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Bundle bundle = new Bundle();
                bundle.putString("flag", "main");
                readyGo(TabActivity.class, bundle);
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
            addSubscription(UserModel.getInstance()
                    .getCoinHisList(page + "")
                    .subscribe(new ServiceResponse<CoinHistoryListBean>() {
                        @Override
                        public void onNext(CoinHistoryListBean historyListBean) {
                            List<CoinHistoryListBean.ResultsBean> results = historyListBean.getResults();
                            page++;
                            mPointAdapter.update(results);
                            if ("".equals(historyListBean.getNext()) || historyListBean.getNext() == null) {
                                JUtils.Toast("没有更多的数据了");
                            }
                        }
                    }));
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
