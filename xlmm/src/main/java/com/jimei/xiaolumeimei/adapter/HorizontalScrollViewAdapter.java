package com.jimei.xiaolumeimei.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.utils.ViewUtils;

public class HorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<String> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.hscrollview_index_gallery_item, parent, false);
            viewHolder = new ViewHolder(convertView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position < mDatas.size()) {
            ViewUtils.loadImgToImgView(mContext, viewHolder.mImg, mDatas.get(position));
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;

        public ViewHolder(View itemView) {
            mImg = (ImageView) itemView.findViewById(R.id.id_index_gallery_item_image);
        }
    }
}
