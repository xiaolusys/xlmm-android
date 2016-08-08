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
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MaMaRenwuListBean;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.BoutiqueWebviewActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.widget.MaMaRenwuListView;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/30.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class NewMMFragment extends DialogFragment {

  private Activity mActivity;
  private MaMaRenwuListBean maMaRenwuListBean;
  private String actlink;

  public static NewMMFragment newInstance(MaMaRenwuListBean title, String actlink) {
    NewMMFragment todayFragment = new NewMMFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable("keyword", title);
    bundle.putString("keyword1", actlink);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = activity;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    maMaRenwuListBean = getArguments().getParcelable("keyword");
    actlink = getArguments().getString("keyword1");
    int style = DialogFragment.STYLE_NO_TITLE;
    setStyle(style, 0);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_newmm, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    List<MaMaRenwuListBean.DataBean> data = maMaRenwuListBean.getData();
    List<String> stringList = new ArrayList<>();
    List<MaMaRenwuListView> mamarenwuviews = new ArrayList<>();
    LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.renwu_list);
    ImageView quit = (ImageView) view.findViewById(R.id.quit);
    ImageView confirm = (ImageView) view.findViewById(R.id.confirm);
    quit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });

    confirm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        MobclickAgent.onEvent(mActivity, "XLMMUniID");
        JumpUtils.jumpToWebViewWithCookies(mActivity, actlink, -1, BoutiqueWebviewActivity.class,
            "妈妈活动");
        dismiss();
      }
    });
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).isShow()) {
        stringList.add(data.get(i).getDesc());
      }

      MaMaRenwuListView views = new MaMaRenwuListView(mActivity);
      mamarenwuviews.add(views);
      linearLayout.addView(views);
    }
    for (int i = 0; i < stringList.size(); i++) {
      mamarenwuviews.get(i).setBrandDesText(stringList.get(i));
      mamarenwuviews.get(i).setImageVisiBle(data.get(i).isComplete());
      mamarenwuviews.get(i).setImageCheckVisible(data.get(i).isComplete());
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
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
}
