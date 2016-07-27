package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMTeamAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityMmteamBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/7/27.
 */
public class MMTeamActivity extends BaseMVVMActivity<ActivityMmteamBinding> {
  private String id;
  private MMTeamAdapter mmTeamAdapter;

  @Override protected void initView() {
    b.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    b.recyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecorationForFooter.VERTICAL_LIST));
    mmTeamAdapter = new MMTeamAdapter(this);
    b.recyclerview.setAdapter(mmTeamAdapter);
  }

  @Override protected void initListener() {

  }

  @Override protected void initData() {
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
}
