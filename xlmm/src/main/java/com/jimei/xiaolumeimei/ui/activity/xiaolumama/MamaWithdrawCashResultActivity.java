package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaWithdrawCashResultActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    String TAG = "MamaWithdrawCashResultActivity";

    @Bind(R.id.btn_jump)
    Button btn_jump;
    @Bind(R.id.img_red_packet1)
    ImageView img_red_packet1;
    @Bind(R.id.tv_msg)
    TextView msgTv;
    @Bind(R.id.tv_nickname)
    TextView nickNameTv;

    double cash;
    private int code;
    String msg = "";
    String nickName;

    @Override
    protected void setListener() {
        btn_jump.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            cash = extras.getDouble("cash");
            code = extras.getInt("code");
            msg = extras.getString("msg");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_withdrawcash_result;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
    }

    @Override
    protected void initData() {
        if (Double.compare(cash, 100) == 0 && code == 0) {
            img_red_packet1.setImageResource(R.drawable.img_redpacket100_2);
        } else if (Double.compare(cash, 200) == 0 & code == 0) {
            img_red_packet1.setImageResource(R.drawable.img_redpacket200_2);
        } else if (Double.compare(cash, 100) == 0) {
            img_red_packet1.setImageResource(R.drawable.img_graypacket100);
        } else if (Double.compare(cash, 200) == 0) {
            img_red_packet1.setImageResource(R.drawable.img_graypacket200);
        }
        JUtils.Toast(msg);
        msgTv.setText(msg);
        Subscription subscribe = UserNewModel.getInstance()
                .getProfile()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<UserInfoBean>(){
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        nickName = userInfoBean.getNick();
                        nickNameTv.setText("微信账号:"+nickName);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                JUtils.Log(TAG, "publish now");
                startActivity(new Intent(this, MMChooseListActivity.class));
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                JUtils.Log(TAG, "withdraw cash history entry");
                startActivity(new Intent(this, MamaWithdrawCashHistoryActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_withdrawcash, menu);
        return super.onCreateOptionsMenu(menu);
    }
}