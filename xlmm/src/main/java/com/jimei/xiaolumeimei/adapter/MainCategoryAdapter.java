package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wisdom on 16/9/27.
 */

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {
    private Activity mActivity;
    private List<PortalBean.CategorysBean> mList;


    public MainCategoryAdapter(Activity activity) {
        mActivity = activity;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<PortalBean.CategorysBean> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_main_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String dateStr = Calendar.getInstance().get(Calendar.MONTH) + "_" + Calendar.getInstance().get(Calendar.DATE);
        String picAddress = XlmmConst.XLMM_DIR + "main/"+ mList.get(position).getName() + dateStr + ".png";
        if (!FileUtils.isFileExist(picAddress) && position == 0) {
            FileUtils.deleteFile(XlmmConst.XLMM_DIR + "main/");
        }
        if (FileUtils.isFileExist(picAddress)) {
            holder.img.setImageBitmap(BitmapFactory.decodeFile(picAddress));
        } else {
            OkHttpUtils.get().url(mList.get(position).getCat_img())
                    .build()
                    .execute(new FileCallBack(XlmmConst.XLMM_DIR+ "main/", mList.get(position).getName() + dateStr + ".png") {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            JUtils.Toast(e.getMessage());
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            holder.img.setImageBitmap(BitmapFactory.decodeFile(picAddress));
                        }
                    });
        }
        String cat_link = mList.get(position).getCat_link();
        String name = mList.get(position).getName();
        holder.layout.setOnClickListener(v -> JumpUtils.push_jump_proc(mActivity, (cat_link + "&title=" + name)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            img = ((ImageView) itemView.findViewById(R.id.img));
        }
    }
}
