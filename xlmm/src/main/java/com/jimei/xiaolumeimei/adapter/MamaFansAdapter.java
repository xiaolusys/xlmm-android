package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

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
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.glidemoudle.CropCircleTransformation;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;

public class MamaFansAdapter extends RecyclerView.Adapter<MamaFansAdapter.ViewHolder> {
  private static final String TAG = "MamaFansAdapter";
  private Activity context;
  private List<MamaFansBean.ResultsEntity> mList;

  public MamaFansAdapter(Activity context) {
    mList = new ArrayList<>();
    this.context = context;
  }

  public void updateWithClear(List<MamaFansBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MamaFansBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mamafans, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    JUtils.Log(TAG, "getView ");

    MamaFansBean.ResultsEntity resultsEntity = mList.get(position);

    holder.tvFans.setText(resultsEntity.getFansNick());
    holder.tvInfo.setText(resultsEntity.getFansDescription());
    holder.tvTime.setText(resultsEntity.getCreated().replace("T", " ").substring(6, 16));
    if (TextUtils.isEmpty(resultsEntity.getFansThumbnail())) {
      Glide.with(context)
          .load(R.mipmap.ic_launcher)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .bitmapTransform(new CropCircleTransformation(context))
          .into(holder.imgFans);
    } else {

      ViewUtils.loadImgToImgViewWithTransformCircle(context, holder.imgFans,
          resultsEntity.getFansThumbnail());
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

