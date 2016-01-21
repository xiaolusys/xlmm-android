package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.adapter.MembershipPointListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import com.jimei.xiaolumeimei.R;
import butterknife.Bind;
import rx.schedulers.Schedulers;


public class MembershipPointActivity extends BaseSwipeBackCompatActivity {
    String TAG = "MembershipPointActivity";
    @Bind(R.id.toolbar) Toolbar toolbar;
    UserModel model = new UserModel();
    MembershipPointBean all_point_info = new MembershipPointBean();
    private MembershipPointListAdapter mPointAdapter;
    LinearLayout rlayout;
    TextView  tx_empty_info;

    @Override protected void setListener() {

    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_membershippoint;
    }

    @Override protected void initViews() {
        toolbar.setTitle("所有订单");
        setSupportActionBar(toolbar);

        rlayout = (LinearLayout) findViewById(R.id.llayout_point);
        tx_empty_info = new TextView(mContext);

        ListView all_orders_listview = (ListView) findViewById(R.id.all_points_listview);

        mPointAdapter = new MembershipPointListAdapter(this);
        all_orders_listview.setAdapter(mPointAdapter);
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getMembershipPointBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<MembershipPointBean>() {
                    @Override public void onNext(MembershipPointBean pointBean) {
                        List<MembershipPointBean.ResultsEntity> results = pointBean.getResults();
                        if (0 == results.size()){
                            fillEmptyInfo();
                        }
                        else
                        {
                            tx_empty_info.setVisibility(View.GONE);
                            mPointAdapter.update(results);
                        }

                        Log.i(TAG, pointBean.toString());
                    }
                });
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void fillEmptyInfo(){

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tx_empty_info.setLayoutParams(lp);
        tx_empty_info.setText("亲，你还没有任何订单，快去抢购吧！");

        rlayout.addView(tx_empty_info);
    }
}
