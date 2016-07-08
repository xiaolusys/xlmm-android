package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.GetCouponbean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.event.UserInfoEmptyEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/30.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class GetCouponFragment extends DialogFragment {

  @Bind(R.id.confirm) ImageView confirm;
  @Bind(R.id.close) ImageView close;
  private Activity mActivity;
  private Subscription subscription1;
  private Subscription subscription;

  public static GetCouponFragment newInstance(String title) {
    GetCouponFragment todayFragment = new GetCouponFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = activity;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int style = DialogFragment.STYLE_NO_TITLE;
    setStyle(style, 0);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.first_layout, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    close.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });

    confirm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        subscription1 = UserModel.getInstance()
            .getCouPon()
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<Response<GetCouponbean>>() {
              @Override public void onNext(Response<GetCouponbean> getCouponbeanResponse) {
                JUtils.Log("getCoupon", "onnext");
                if (getCouponbeanResponse != null) {
                  if (getCouponbeanResponse.isSuccessful()) {
                    JUtils.Log("getCoupon", "onnext == " + getCouponbeanResponse.body().toString());
                    JUtils.Toast(getCouponbeanResponse.body().getInfo());
                    getUserInfo();
                  }
                }
              }

              @Override public void onError(Throwable e) {
                super.onError(e);
                if (e instanceof HttpException) {
                  JUtils.Toast("优惠券领取失败");
                }
              }
            });
      }
    });
  }

  public void getUserInfo() {
    subscription = UserNewModel.getInstance()
        .getProfile()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean userInfoBean) {
            if (null != userInfoBean) {
              EventBus.getDefault().post(new UserInfoEmptyEvent());
              dismiss();
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

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (subscription1 != null && subscription1.isUnsubscribed()) {
      subscription1.unsubscribe();
    }
    if (subscription != null && subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
    ButterKnife.unbind(this);
  }

  @Override public void onStop() {
    super.onStop();
  }

  @Override public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
  }

  @Override public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }
}
