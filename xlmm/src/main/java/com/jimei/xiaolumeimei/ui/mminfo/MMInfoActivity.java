package com.jimei.xiaolumeimei.ui.mminfo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.github.mikephil.charting.charts.LineChart;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BasePresenterActivity;
import com.jimei.xiaolumeimei.base.RxManager;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;

/**
 * Created by itxuye on 2016/6/24.
 */
public class MMInfoActivity extends BasePresenterActivity<MMInfoPresenter, MMInfoModel>
    implements MMInfoContract.View, View.OnClickListener {

  @Bind(R.id.imgUser) ImageView imgUser;
  @Bind(R.id.btn_two_dimen) TextView btn_two_dimen;
  @Bind(R.id.tv_cashinfo) TextView tv_cashinfo;
  @Bind(R.id.rl_chart) RelativeLayout rl_chart;
  @Bind(R.id.tv_cash) TextView tv_cash;
  @Bind(R.id.btn_chooselist) TextView btn_chooselist;
  @Bind(R.id.tv_liveness) TextView tv_liveness;
  @Bind(R.id.img_liveness) com.jimei.xiaolumeimei.widget.RotateTextView img_liveness;
  @Bind(R.id.chart1) LineChart mChart;
  @Bind(R.id.img_left) ImageView img_left;
  @Bind(R.id.img_right) ImageView img_right;
  @Bind(R.id.rl_empty_chart) RelativeLayout rl_empty_chart;
  @Bind(R.id.tv_visit2) TextView tv_today_visit2;
  @Bind(R.id.tv_today_order2) TextView tv_today_order2;
  @Bind(R.id.tv_today_fund2) TextView tv_today_fund2;
  @Bind(R.id.rl_mama_info) RelativeLayout rlMamaInfo;
  @Bind(R.id.rl_order) LinearLayout rlOrder;
  @Bind(R.id.rl_chooselist) RelativeLayout rlChooselist;
  @Bind(R.id.rl_party) RelativeLayout rl_party;
  @Bind(R.id.rl_push) RelativeLayout rl_push;
  @Bind(R.id.rl_shop) RelativeLayout rl_shop;
  @Bind(R.id.rl_two_dimen) RelativeLayout rlTwoDimen;
  @Bind(R.id.tv_invite_num) TextView tv_invite_num;
  @Bind(R.id.rl_fans) RelativeLayout rl_fans;
  @Bind(R.id.tv_fansnum) TextView tv_fansnum;
  @Bind(R.id.rl_orderlist) RelativeLayout rl_orderlist;
  @Bind(R.id.tv_order) TextView tv_order;
  @Bind(R.id.rl_income) RelativeLayout rl_income;
  @Bind(R.id.tv_fund) TextView tv_fund;
  @Bind(R.id.visit_layout) LinearLayout visitLayout;
  @Bind(R.id.order_layout) LinearLayout orderLayout;
  @Bind(R.id.fund_layout) LinearLayout fundLayout;

  RxManager mRxManager = new RxManager();
  private String title, sharelink, desc, shareimg;

  @Override protected void setListener() {
    tv_cashinfo.setOnClickListener(this);
    tv_cash.setOnClickListener(this);

    tv_liveness.setOnClickListener(this);
    img_liveness.setOnClickListener(this);

    img_left.setOnClickListener(this);
    img_right.setOnClickListener(this);

    rlTwoDimen.setOnClickListener(this);
    rl_fans.setOnClickListener(this);
    rl_orderlist.setOnClickListener(this);
    rl_income.setOnClickListener(this);

    rlChooselist.setOnClickListener(this);
    rl_party.setOnClickListener(this);
    rl_push.setOnClickListener(this);
    rl_shop.setOnClickListener(this);
    visitLayout.setOnClickListener(this);
    orderLayout.setOnClickListener(this);
    fundLayout.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamainfo;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void initMMview(MamaFortune mamaFortune) {

  }

  @Override public void show_liveness(int liveness) {

  }

  @Override public void init_chart() {

  }

  @Override public void initShareInfo() {
    mRxManager.on("mmShoppingBean", o -> {
      title = ((MMShoppingBean) o).getShopInfo().getName();
      sharelink = ((MMShoppingBean) o).getShopInfo().getPreviewShopLink();
      shareimg = ((MMShoppingBean) o).getShopInfo().getThumbnail();
      desc = ((MMShoppingBean) o).getShopInfo().getDesc();
    });
  }

  @Override public void onClick(View v) {

  }
}
