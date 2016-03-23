package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.glidemoudle.CropCircleTransformation;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/22.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMVisitorsAdapter
    extends RecyclerView.Adapter<MMVisitorsAdapter.ViewHolder> {
  private static final String TAG = "MamaFansAdapter";
  private Activity context;
  private List<MMVisitorsBean.ResultsEntity> mList;

  public MMVisitorsAdapter(Activity context) {
    mList = new ArrayList<MMVisitorsBean.ResultsEntity>();
    this.context = context;
  }

  public void updateWithClear(List<MMVisitorsBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MMVisitorsBean.ResultsEntity> list) {

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

    MMVisitorsBean.ResultsEntity resultsEntity = mList.get(position);

    holder.tvFans.setText(resultsEntity.getVisitorNick());
    holder.tvInfo.setText(resultsEntity.getVisitorDescription());
    holder.tvTime.setText(resultsEntity.getCreated().substring(6, 10));
    if (TextUtils.isEmpty(resultsEntity.getVisitorImg())) {
      Glide.with(context)
          .load(R.mipmap.ic_launcher)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .bitmapTransform(new CropCircleTransformation(context))
          .into(holder.imgFans);
    } else {
      ViewUtils.loadImgToImgViewWithTransformCircle(context, holder.imgFans,
          resultsEntity.getVisitorImg());
    }
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    int id = R.layout.item_mamafans;
    @Bind(R.id.img_fans) ImageView imgFans;
    @Bind(R.id.tv_fans) TextView tvFans;
    @Bind(R.id.tv_info) TextView tvInfo;
    @Bind(R.id.tv_time) TextView tvTime;
    @Bind(R.id.llayout_fans_item) RelativeLayout llayoutFansItem;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

