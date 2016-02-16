package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MembershipPointListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.entities.PointLogBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.schedulers.Schedulers;


public class MembershipPointActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
    String TAG = "MembershipPointActivity";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_jump)    Button btn_jump;
    @Bind(R.id.rlayout_order_empty) RelativeLayout rlayout_order_empty;

    UserModel model = new UserModel();
    private MembershipPointListAdapter mPointAdapter;
    TextView  tx_point;

    @Override protected void setListener() {
        btn_jump.setOnClickListener(this);
        toolbar.setOnClickListener(this);
    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_membershippoint;
    }

    @Override protected void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        finishBack(toolbar);
        ListView all_orders_listview = (ListView) findViewById(R.id.all_points_listview);

        mPointAdapter = new MembershipPointListAdapter(this);
        all_orders_listview.setAdapter(mPointAdapter);

        tx_point = (TextView) findViewById(R.id.tv_Point);

        TextView tx_info = (TextView) findViewById(R.id.tx_info);
        tx_info.setText("亲，您暂时还没有积分记录哦~");
        TextView tx_info2 = (TextView) findViewById(R.id.tx2);
        tx_info2.setText("快去下单赚取积分吧~");

        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.img_emptypoint);
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getMembershipPointBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<MembershipPointBean>() {
                    @Override public void onNext(MembershipPointBean pointBean) {
                        List<MembershipPointBean.ResultsEntity> results = pointBean.getResults();
                        if (0 != results.size())
                        {
                            tx_point.setText(results.get(0).getIntegral_value());
                            Log.d(TAG, "point " + results.get(0).getIntegral_value());
                        }
                        else{
                            Log.d(TAG, "point record not exist. ");
                        }
                    }
                });

        model.getPointLogBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<PointLogBean>() {
                    @Override public void onNext(PointLogBean pointLogBean) {
                        List<PointLogBean.ResultsEntity> results = pointLogBean.getResults();
                        if (0 == results.size()){
                            Log.d(TAG, "pointlog 0 " );
                            rlayout_order_empty.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Log.d(TAG, "pointlog " + results.get(0).toString());
                            mPointAdapter.update(results);
                        }

                    }
                });
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(MembershipPointActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
