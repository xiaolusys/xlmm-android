package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WithdrawCashHisAdapter extends BaseAdapter {
  private static final String TAG = "WithdrawCashHisAdapter";
  private Activity context;
  private List<WithdrawCashHisBean.WithdrawCashRecord> mList;

  public WithdrawCashHisAdapter(Activity context) {
    mList = new ArrayList<WithdrawCashHisBean.WithdrawCashRecord>();
    this.context = context;
  }


  public void updateWithClear(List<WithdrawCashHisBean.WithdrawCashRecord> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<WithdrawCashHisBean.WithdrawCashRecord> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Log.d(TAG, "getView ");

    ViewHolder holder = null;

    if (convertView == null) {
      Log.d(TAG, "convertView == null " );
      convertView = LayoutInflater.from(context).inflate(R.layout.item_withdrawcash_record, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    }
    else{
      holder = (ViewHolder) convertView.getTag();
    }


    WithdrawCashHisBean.WithdrawCashRecord record = (WithdrawCashHisBean
        .WithdrawCashRecord)getItem(position);
    if (null != record) {
      holder.tx_withdraw_fund.setText(String.valueOf(Math.round(record.getValue_money()*100)/100) + "元");
      holder.tx_withdraw_state.setText(record.getGet_status_display());
      holder.tx_time.setText(record.getCreated());
    }


    return convertView;
  }

  public static class ViewHolder {
    View card;

    @Bind(R.id.tx_withdraw_fund) TextView tx_withdraw_fund;
    @Bind(R.id.tx_withdraw_state) TextView tx_withdraw_state;
    @Bind(R.id.tx_time) TextView tx_time;

    public ViewHolder(View itemView) {
      card = itemView;
      ButterKnife.bind(this, itemView);
    }

  }
}

