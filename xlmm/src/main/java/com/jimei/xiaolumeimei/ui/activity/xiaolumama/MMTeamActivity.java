package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMTeamAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.databinding.ActivityMmteamBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import retrofit2.Response;

/**
 * Created by itxuye on 2016/7/27.
 */
public class MMTeamActivity extends BaseMVVMActivity<ActivityMmteamBinding>
        implements View.OnClickListener, ScrollableHelper.ScrollableContainer {
    private String id;
    private MMTeamAdapter mmTeamAdapter;
    private String url;

    @Override
    protected void initViews() {
        b.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        b.recyclerview.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mmTeamAdapter = new MMTeamAdapter(this);
        b.recyclerview.setAdapter(mmTeamAdapter);
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(b.recyclerview);
    }

    @Override
    protected void setListener() {
        b.btnTeam.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getTeamSelfRank()
                .subscribe(new ServiceResponse<Response<PersonalCarryRankBean>>() {
                    @Override
                    public void onNext(Response<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                        if (personalCarryRankBeanResponse != null) {
                            if (personalCarryRankBeanResponse.isSuccessful()) {
                                PersonalCarryRankBean personalCarryRankBean = personalCarryRankBeanResponse.body();
                                if (!TextUtils.isEmpty(personalCarryRankBean.getThumbnail())) {

                                    ViewUtils.loadImgToImgViewWithTransformCircle(MMTeamActivity.this, b.imgUser,
                                            personalCarryRankBean.getThumbnail());
                                } else {
                                    b.imgUser.setImageResource(R.drawable.img_diamond);
                                }

                                b.tvCarry.setText("团队收益" + personalCarryRankBean.getTotal() / 100.00 + "元");
                            }
                        }
                    }
                }));

        addSubscription(MamaInfoModel.getInstance()
                .getTeamMembers(id)
                .subscribe(new ServiceResponse<Response<List<PersonalCarryRankBean>>>() {
                    @Override
                    public void onNext(Response<List<PersonalCarryRankBean>> personalCarryRankBeanResponse) {
                        if (personalCarryRankBeanResponse != null) {
                            if (personalCarryRankBeanResponse.isSuccessful()) {
                                mmTeamAdapter.addAll(personalCarryRankBeanResponse.body());
                            }
                        }
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideIndeterminateProgressDialog();
                    }
                }));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
        url = extras.getString("url");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mmteam;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_personal:
                JumpUtils.jumpToWebViewWithCookies(this, url, -1,
                        CommonWebViewActivity.class, "团队说明", false);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (url != null && !"".equals(url)) {
            getMenuInflater().inflate(R.menu.menu_team, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_team:
                MobclickAgent.onEvent(this, "TeamRankID");
                readyGo(MMTeamCarryRankActivity.class);
                break;
        }
    }

    @Override
    public View getScrollableView() {
        return b.recyclerview;
    }

    @Override
    public View getLoadingView() {
        return b.scrollableLayout;
    }

}
