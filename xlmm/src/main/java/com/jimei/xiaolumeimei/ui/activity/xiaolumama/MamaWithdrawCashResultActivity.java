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

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_jump)
    Button btn_jump;
    @Bind(R.id.img_red_packet1)
    ImageView img_red_packet1;
    @Bind(R.id.tv_msg)
    TextView msgTv;
    @Bind(R.id.tv_nickname)
    TextView nickNameTv;

    float cash;
    private int code;
    String msg;
    String nickName;

    @Override
    protected void setListener() {
        btn_jump.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (null != getIntent() && null != getIntent().getExtras()) {
            cash = getIntent().getExtras().getFloat("cash");
            code = getIntent().getExtras().getInt("code");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_withdrawcash_result;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        finishBack(toolbar);
    }

    @Override
    protected void initData() {
        if (Float.compare(cash, 100) == 0 && code == 0) {
            img_red_packet1.setImageResource(R.drawable.img_redpacket100_2);
        } else if (Float.compare(cash, 200) == 0 & code == 0) {
            img_red_packet1.setImageResource(R.drawable.img_redpacket200_2);
        } else if (Float.compare(cash, 100) == 0) {
            img_red_packet1.setImageResource(R.drawable.img_graypacket100);
        } else if (Float.compare(cash, 200) == 0) {
            img_red_packet1.setImageResource(R.drawable.img_graypacket200);
        }
        switch (code) {
            case 0:
                msg = "提现成功，待审核通过";
                break;
            case 1:
                msg = "参数错误";
                break;
            case 2:
                msg = "不足提现金额";
                break;
            case 3:
                msg = "有待审核记录不予再次提现";
                break;
            default:
                msg = "系统状态异常";
                break;
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