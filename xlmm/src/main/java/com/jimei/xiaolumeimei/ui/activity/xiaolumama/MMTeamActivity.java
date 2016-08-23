package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMTeamAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityMmteamBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/7/27.
 */
public class MMTeamActivity extends BaseMVVMActivity<ActivityMmteamBinding>
    implements View.OnClickListener, ScrollableHelper.ScrollableContainer {
  private String id;
  private MMTeamAdapter mmTeamAdapter;
  private String url;

  @Override protected void initView() {
    b.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    b.recyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecorationForFooter.VERTICAL_LIST));
    mmTeamAdapter = new MMTeamAdapter(this);
    b.recyclerview.setAdapter(mmTeamAdapter);
    b.scrollableLayout.getHelper().setCurrentScrollableContainer(b.recyclerview);
  }

  @Override protected void initListener() {
    b.btnTeam.setOnClickListener(this);
  }

  @Override protected void initData() {

    MamaInfoModel.getInstance()
        .getTeamSelfRank()
        .subscribeOn(Schedulers.io())
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
        });

    MamaInfoModel.getInstance()
        .getTeamMembers(id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<Response<List<PersonalCarryRankBean>>>() {
          @Override
          public void onNext(Response<List<PersonalCarryRankBean>> personalCarryRankBeanResponse) {
            if (personalCarryRankBeanResponse != null) {
              if (personalCarryRankBeanResponse.isSuccessful()) {
                mmTeamAdapter.addAll(personalCarryRankBeanResponse.body());
              }
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    id = extras.getString("id");
    url = extras.getString("url");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mmteam;
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_personal:
          JumpUtils.jumpToWebViewWithCookies(this,url, -1,
                  TeamExplainActivity.class);
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (url!=null&& !"".equals(url)) {
      getMenuInflater().inflate(R.menu.menu_team, menu);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_team:
        MobclickAgent.onEvent(this, "TeamRankID");
        readyGo(MMTeamCarryRankActivity.class);
        break;
    }
  }

  @Override public View getScrollableView() {
    return b.recyclerview;
  }
  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }
}
