package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TeamCarryRankAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityTeamBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/7/28.
 */
public class MMTeamCarryRankActivity extends BaseMVVMActivity<ActivityTeamBinding>
    implements ScrollableHelper.ScrollableContainer {
  private TeamCarryRankAdapter mTeamCarryRankAdapter;

  @Override protected void initView() {
    b.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    b.recyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecorationForFooter.VERTICAL_LIST));
    mTeamCarryRankAdapter = new TeamCarryRankAdapter(this);
    b.recyclerview.setAdapter(mTeamCarryRankAdapter);
    b.scrollableLayout.getHelper().setCurrentScrollableContainer(b.recyclerview);
  }

  @Override protected void initListener() {

  }

  @Override protected void initData() {
    MamaInfoModel.getInstance()
        .getTeamCarryRankBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<Response<List<PersonalCarryRankBean>>>() {
          @Override
          public void onNext(Response<List<PersonalCarryRankBean>> personalCarryRankBeanResponse) {
            if (null != personalCarryRankBeanResponse) {
              if (personalCarryRankBeanResponse.isSuccessful()) {
                List<PersonalCarryRankBean> personalCarryRankBeanList =
                    personalCarryRankBeanResponse.body();
                mTeamCarryRankAdapter.addAll(personalCarryRankBeanList);
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }
        });

    MamaInfoModel.getInstance()
        .getTeamSelfRank()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<Response<PersonalCarryRankBean>>() {
          @Override
          public void onNext(Response<PersonalCarryRankBean> personalCarryRankBeanResponse) {
            if (null != personalCarryRankBeanResponse) {
              if (personalCarryRankBeanResponse.isSuccessful()) {
                PersonalCarryRankBean personalCarryRankBean = personalCarryRankBeanResponse.body();
                b.setPersonalInfo(personalCarryRankBean);
                if (personalCarryRankBean.getRank() == 0) {
                  b.tvRank.setText("");
                } else {
                  b.tvRank.setText("第" + personalCarryRankBean.getRank() + "名");
                }
                b.tvCarry.setText("团队收益" + personalCarryRankBean.getTotal() / 100.00 + "元");
                try {
                  if (!TextUtils.isEmpty(personalCarryRankBean.getThumbnail())) {

                    ViewUtils.loadImgToImgViewWithTransformCircle(MMTeamCarryRankActivity.this,
                        b.imgUser, personalCarryRankBean.getThumbnail());
                  } else {
                    b.imgUser.setImageResource(R.drawable.img_diamond);
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_team;
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public View getScrollableView() {
    return b.recyclerview;
  }
}
