package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;

public class MamaFansAdapter extends RecyclerView.Adapter<MamaFansAdapter.ViewHolder>  {
  private static final String TAG = "MamaFansAdapter";
  private Activity context;
  private List<MamaFansBean.FansEntity> mList;

  public MamaFansAdapter(Activity context) {
    mList = new ArrayList<MamaFansBean.FansEntity>();
    this.context = context;
  }


  public void updateWithClear(List<MamaFansBean.FansEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MamaFansBean.FansEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }


  @Override public long getItemId(int position) {
    return position;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_mamafans, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    JUtils.Log(TAG, "getView ");


    MamaFansBean.FansEntity record = mList.get(position);
    if (null != record) {
      JUtils.Log(TAG, "record.getNick() = "+record.getNick() );
      if(!record.getThumbnail().isEmpty()) {
        ViewUtils.loadImgToImgView(context, holder.img_fans, record.getThumbnail());
      }
      if(!record.getNick().isEmpty()) {
        holder.tx_nickname.setText((record.getNick()));
      }
      //holder.tv_id.setText(record.getId());
    }

  }

  @Override public int getItemCount() {
    return mList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder{
    int id = R.layout.item_mamafans;
    @Bind(R.id.img_fans) ImageView img_fans;
    @Bind(R.id.tx_nickname) TextView tx_nickname;
    //@Bind(R.id.tv_id) TextView tv_id;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

  }
}

