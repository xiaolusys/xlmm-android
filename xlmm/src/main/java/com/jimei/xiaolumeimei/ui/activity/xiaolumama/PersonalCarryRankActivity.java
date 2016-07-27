package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PersonalCarryRankAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityPersonalcarryrankBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/7/27.
 */
public class PersonalCarryRankActivity extends BaseMVVMActivity<ActivityPersonalcarryrankBinding> {

  private PersonalCarryRankAdapter mPersonalCarryRankAdapter;

  @Override protected void initView() {
    b.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    b.recyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecorationForFooter.VERTICAL_LIST));
    mPersonalCarryRankAdapter = new PersonalCarryRankAdapter(this);
    b.recyclerview.setAdapter(mPersonalCarryRankAdapter);
  }

  @Override protected void initListener() {

  }

  @Override protected void initData() {
    MamaInfoModel.getInstance()
        .getPersonalCarryRankBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<Response<List<PersonalCarryRankBean>>>() {
          @Override
          public void onNext(Response<List<PersonalCarryRankBean>> personalCarryRankBeanResponse) {
            if (null != personalCarryRankBeanResponse) {
              if (personalCarryRankBeanResponse.isSuccessful()) {
                List<PersonalCarryRankBean> personalCarryRankBeanList =
                    personalCarryRankBeanResponse.body();
                mPersonalCarryRankAdapter.addAll(personalCarryRankBeanList);
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
        .getPersonalSelfCarryRankBean()
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
                b.tvCarry.setText("收益" + personalCarryRankBean.getTotal() / 100.00 + "元");
                try {
                  if (personalCarryRankBean.getRankAdd() > 0) {
                    b.tvRankChange.setText("比上周上升" + personalCarryRankBean.getRankAdd() + "名");
                  } else {
                    b.tvRankChange.setText(
                        "本周下降" + Math.abs(personalCarryRankBean.getRankAdd()) + "名");
                  }

                  if (!TextUtils.isEmpty(personalCarryRankBean.getThumbnail())) {

                    ViewUtils.loadImgToImgViewWithTransformCircle(PersonalCarryRankActivity.this,
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
    return R.layout.activity_personalcarryrank;
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
