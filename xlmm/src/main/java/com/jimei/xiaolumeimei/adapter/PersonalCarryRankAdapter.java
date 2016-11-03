package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.glidemoudle.CropCircleTransformation;
import com.jimei.xiaolumeimei.BR;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseRevHolder;
import com.jimei.xiaolumeimei.databinding.ItemCarryPersonalBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;

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

    public PersonalCarryRankAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPersonalCarryRankBeanList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
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

    @Override
    public BaseRevHolder<ItemCarryPersonalBinding> onCreateViewHolder(ViewGroup parent,
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

    @Override
    public void onBindViewHolder(BaseRevHolder holder, int position) {
        final PersonalCarryRankBean personalCarryRankBean = mPersonalCarryRankBeanList.get(position);
        holder.getBinding().setVariable(BR.item, personalCarryRankBean);
        holder.getBinding().executePendingBindings();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(view.getContext()))
                    .into(view);
        } else {
            ViewUtils.loadImgToImgViewWithTransformCircle(view.getContext(), view, url);
        }
    }

    public void addAll(List<PersonalCarryRankBean> personalCarryRankBeanList) {
        mPersonalCarryRankBeanList.addAll(personalCarryRankBeanList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPersonalCarryRankBeanList.size();
    }
}
