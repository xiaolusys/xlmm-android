package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.jimei.xiaolumeimei.BR;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseRevHolder;
import com.jimei.xiaolumeimei.databinding.ItemMmteamBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye on 2016/7/27.
 */
public class MMTeamAdapter extends RecyclerView.Adapter<BaseRevHolder<ItemMmteamBinding>> {

  private final LayoutInflater mLayoutInflater;

  private List<PersonalCarryRankBean> mPersonalCarryRankBeanList;
  private Context mContext;

  public MMTeamAdapter(Context context) {
    this.mContext = context;
    mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mPersonalCarryRankBeanList = new ArrayList<>();
  }

  @Override
  public BaseRevHolder<ItemMmteamBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

    ViewDataBinding binding = getViewDataBinding(parent, R.layout.item_mmteam);

    return new BaseRevHolder(binding);
  }

  private ViewDataBinding getViewDataBinding(ViewGroup parent, int id) {
    ViewDataBinding binding;
    binding = DataBindingUtil.inflate(mLayoutInflater, id, parent, false);
    return binding;
  }

  @Override public void onBindViewHolder(BaseRevHolder<ItemMmteamBinding> holder, int position) {
    final PersonalCarryRankBean personalCarryRankBean = mPersonalCarryRankBeanList.get(position);
    holder.getBinding().setVariable(BR.item, personalCarryRankBean);
    holder.getBinding().tvNum.setText(personalCarryRankBean.getNum() + "个");
    holder.getBinding().tvCarry.setText(personalCarryRankBean.getTotal() / 100.00 + "");
  }

  @Override public int getItemCount() {
    return mPersonalCarryRankBeanList.size();
  }

  public void addAll(List<PersonalCarryRankBean> personalCarryRankBeanList) {
    mPersonalCarryRankBeanList.addAll(personalCarryRankBeanList);
    notifyDataSetChanged();
  }
}
