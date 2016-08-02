package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.jimei.xiaolumeimei.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wisdom on 16/8/1.
 */
public class ColorTagAdapter extends SimpleAdapter {

    public ColorTagAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position == 0) {
            view.findViewById(R.id.bg).setVisibility(View.GONE);
        }
        return view;
    }

}
