package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.BR;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseRevHolder;
import com.jimei.xiaolumeimei.databinding.ItemCarryPersonalBinding;
import com.jimei.xiaolumeimei.databinding.ItemCarryPersonalFirstBinding;
import com.jimei.xiaolumeimei.databinding.ItemCarryPersonalSecondBinding;
import com.jimei.xiaolumeimei.databinding.ItemCarryPersonalThridBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.glidemoudle.CropCircleTransformation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye on 2016/7/27.
 */
public class PersonalCarryRankAdapter extends RecyclerView.Adapter<BaseRevHolder> {

  private static final int ITEM_VIEW_TYPE_FIRST = 1;
  private static final int ITEM_VIEW_TYPE_SECOND = 2;
  private static final int ITEM_VIEW_TYPE_THIRD = 3;
  private static final int ITEM_VIEW_TYPE_FOUR = 4;

  private final LayoutInflater mLayoutInflater;

  private List<PersonalCarryRankBean> mPersonalCarryRankBeanList;
  private Context mContext;

  public PersonalCarryRankAdapter(Context context) {
    this.mContext = context;
    mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mPersonalCarryRankBeanList = new ArrayList<>();
  }

  @Override public int getItemViewType(int position) {
    PersonalCarryRankBean personalCarryRankBean = mPersonalCarryRankBeanList.get(position);
    if (personalCarryRankBean.getRank() == 1) {
      return ITEM_VIEW_TYPE_FIRST;
    } else if (personalCarryRankBean.getRank() == 2) {
      return ITEM_VIEW_TYPE_SECOND;
    } else if (personalCarryRankBean.getRank() == 3) {
      return ITEM_VIEW_TYPE_THIRD;
    } else {
      return ITEM_VIEW_TYPE_FOUR;
    }
  }

  @Override public BaseRevHolder<ItemCarryPersonalBinding> onCreateViewHolder(ViewGroup parent,
      int viewType) {
    ViewDataBinding binding;
    if (viewType == ITEM_VIEW_TYPE_FIRST) {
      binding = getViewDataBinding(parent, R.layout.item_carry_personal_first);
    } else if (viewType == ITEM_VIEW_TYPE_SECOND) {
      binding = getViewDataBinding(parent, R.layout.item_carry_personal_second);
    } else if (viewType == ITEM_VIEW_TYPE_THIRD) {
      binding = getViewDataBinding(parent, R.layout.item_carry_personal_thrid);
    } else {
      binding = getViewDataBinding(parent, R.layout.item_carry_personal);
    }
    return new BaseRevHolder(binding);
  }

  private ViewDataBinding getViewDataBinding(ViewGroup parent, int id) {
    ViewDataBinding binding;
    binding = DataBindingUtil.inflate(mLayoutInflater, id, parent, false);
    return binding;
  }

  @Override public void onBindViewHolder(BaseRevHolder holder, int position) {
    final PersonalCarryRankBean personalCarryRankBean = mPersonalCarryRankBeanList.get(position);
    holder.getBinding().setVariable(BR.item, personalCarryRankBean);
    ViewDataBinding binding = holder.getBinding();
    if (binding instanceof ItemCarryPersonalFirstBinding) {
      loadImageAndText(personalCarryRankBean.getThumbnail(),
          ((ItemCarryPersonalFirstBinding) binding).userImage, mContext,
          ((ItemCarryPersonalFirstBinding) binding).userImage,
          ((ItemCarryPersonalFirstBinding) binding).totalCarry,
          personalCarryRankBean.getTotal() / 100.00 + "");
    }

    if (binding instanceof ItemCarryPersonalSecondBinding) {
      loadImageAndText(personalCarryRankBean.getThumbnail(),
          ((ItemCarryPersonalSecondBinding) binding).userImage, mContext,
          ((ItemCarryPersonalSecondBinding) binding).userImage,
          ((ItemCarryPersonalSecondBinding) binding).totalCarry,
          personalCarryRankBean.getTotal() / 100.00 + "");
    }
    if (binding instanceof ItemCarryPersonalThridBinding) {
      loadImageAndText(personalCarryRankBean.getThumbnail(),
          ((ItemCarryPersonalThridBinding) binding).userImage, mContext,
          ((ItemCarryPersonalThridBinding) binding).userImage,
          ((ItemCarryPersonalThridBinding) binding).totalCarry,
          personalCarryRankBean.getTotal() / 100.00 + "");
    }

    if (binding instanceof ItemCarryPersonalBinding) {
      loadImageAndText(personalCarryRankBean.getThumbnail(),
          ((ItemCarryPersonalBinding) binding).userImage, mContext,
          ((ItemCarryPersonalBinding) binding).userImage,
          ((ItemCarryPersonalBinding) binding).totalCarry,
          personalCarryRankBean.getTotal() / 100.00 + "");
    }

    holder.getBinding().executePendingBindings();
  }

  private void loadImageAndText(String thumbnail, ImageView userImage, Context mContext,
      ImageView userImage2, TextView textView, String text) {
    textView.setText(text);
    if (TextUtils.isEmpty(thumbnail)) {
      Glide.with(mContext)
          .load(R.mipmap.ic_launcher)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .bitmapTransform(new CropCircleTransformation(mContext))
          .into(userImage);
    } else {
      com.jimei.xiaolumeimei.utils.ViewUtils.loadImgToImgViewWithTransformCircle(mContext,
          userImage2, thumbnail);
    }
  }

  public void addAll(List<PersonalCarryRankBean> personalCarryRankBeanList) {
    mPersonalCarryRankBeanList.addAll(personalCarryRankBeanList);
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return mPersonalCarryRankBeanList.size();
  }
}
