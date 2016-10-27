package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.GetCouponbean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/30.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class GetCouponFragment extends DialogFragment {

    @Bind(R.id.confirm)
    ImageView confirm;
    @Bind(R.id.close)
    ImageView close;
    private Subscription subscription1;

    public static GetCouponFragment newInstance(String title) {
        GetCouponFragment todayFragment = new GetCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", title);
        todayFragment.setArguments(bundle);
        return todayFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        setStyle(style, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close.setOnClickListener(v -> dismiss());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscription1 = UserModel.getInstance()
                        .getCouPon()
                        .subscribe(new ServiceResponse<Response<GetCouponbean>>() {
                            @Override
                            public void onNext(Response<GetCouponbean> getCouponbeanResponse) {
                                JUtils.Log("getCoupon", "onnext");
                                if (getCouponbeanResponse != null) {
                                    if (getCouponbeanResponse.isSuccessful()) {
                                        JUtils.Log("getCoupon", "onnext == " + getCouponbeanResponse.body().toString());
                                        JUtils.Toast(getCouponbeanResponse.body().getInfo());
                                        dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (e instanceof HttpException) {
                                    JUtils.Toast("优惠券领取失败");
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscription1 != null && subscription1.isUnsubscribed()) {
            subscription1.unsubscribe();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
