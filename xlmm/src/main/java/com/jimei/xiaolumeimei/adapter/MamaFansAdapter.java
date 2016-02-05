package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import java.util.ArrayList;
import java.util.List;

public class MamaFansAdapter extends BaseAdapter {
  private static final String TAG = "MamaFansAdapter";
  private Activity context;
  private List<MamaFansBean> mList;

  public MamaFansAdapter(Activity context) {
    mList = new ArrayList<MamaFansBean>();
    this.context = context;
  }


  public void updateWithClear(List<MamaFansBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MamaFansBean> list) {

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
      convertView = LayoutInflater.from(context).inflate(R.layout.item_mamafans, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    }
    else{
      holder = (ViewHolder) convertView.getTag();
    }


    MamaFansBean record = (MamaFansBean)getItem(position);
    if (null != record) {
      holder.tx_nickname.setText((record.getNick()));
      holder.tx_url.setText(record.getThumbnail());
    }


    return convertView;
  }

  public static class ViewHolder {
    View card;

    @Bind(R.id.tx_nickname) TextView tx_nickname;
    @Bind(R.id.tx_url) TextView tx_url;

    public ViewHolder(View itemView) {
      card = itemView;
      ButterKnife.bind(this, itemView);
    }

  }
}

