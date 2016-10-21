package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.RedBagBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;

public class RedBagActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.tv_num)
    TextView numTv;
    @Bind(R.id.iv_share)
    ImageView shareIv;
    RedBagBean redBagEntity;
    @Bind(R.id.finish_rl)
    RelativeLayout finishLayout;
    private String tid;

    @Override
    protected void setListener() {
        shareIv.setOnClickListener(this);
        finishLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        TradeModel.getInstance()
                .getRedBag(tid)
                .subscribe(new ServiceResponse<RedBagBean>() {
                    @Override
                    public void onNext(RedBagBean redBagBean) {
                        if (redBagBean.getCode() == 0) {
                            numTv.setText(redBagBean.getShare_times_limit() + "");
                            redBagEntity = redBagBean;
                        } else {
                            JUtils.Toast(redBagBean.getMsg());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        JUtils.Toast("加载失败!");
                        hideIndeterminateProgressDialog();
                        RedBagActivity.this.finish();
                    }
                });
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        tid = extras.getString("tid", "");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_red_bag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
                OnekeyShare oks = new OnekeyShare();
                oks.disableSSOWhenAuthorize();
                oks.setTitle(redBagEntity.getTitle());
                oks.setTitleUrl(redBagEntity.getShare_link());
                oks.setText(redBagEntity.getDescription());
                oks.setImageUrl(redBagEntity.getPost_img());
                oks.setUrl(redBagEntity.getShare_link());
                oks.setShareContentCustomizeCallback(new ShareContentCustom(redBagEntity.getDescription()));
                oks.show(this);
                break;
            case R.id.finish_rl:
                Intent intent = new Intent(this, AllOrdersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fragment", 3);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
    }

    class ShareContentCustom implements ShareContentCustomizeCallback {

        private String text;

        public ShareContentCustom(String text) {
            this.text = text;
        }

        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            if (WechatMoments.NAME.equals(platform.getName())) {
                paramsToShare.setTitle(text);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
