package com.jimei.xiaolumeimei.ui.fragment.v1.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.util.List;
import okhttp3.Call;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/30.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MastFragment extends DialogFragment {

  @Bind(R.id.mask_image) ImageView maskImage;
  @Bind(R.id.ll) LinearLayout ll;

  public static MastFragment newInstance(String title) {
    MastFragment todayFragment = new MastFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.masklayout, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ActivityModel.getInstance()
        .getPostActivity()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<PostActivityBean>>() {
          @Override public void onNext(List<PostActivityBean> postActivityBean) {

            if (postActivityBean != null) {
              if (!TextUtils.isEmpty(postActivityBean.get(0).getMaskLink())) {
                try {
                  OkHttpUtils.get()
                      .url(postActivityBean.get(0).getMaskLink())
                      .build()
                      .execute(new BitmapCallback() {
                        @Override public void onError(Call call, Exception e) {
                          e.printStackTrace();
                        }

                        @Override public void onResponse(Bitmap response) {
                          if (null != response) {
                            LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(response.getWidth(),
                                    response.getHeight());
                            maskImage.setLayoutParams(layoutParams);
                            maskImage.setImageBitmap(response);
                          }
                        }
                      });
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          }
        });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
