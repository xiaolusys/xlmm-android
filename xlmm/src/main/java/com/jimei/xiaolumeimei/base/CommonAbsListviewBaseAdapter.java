package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * Author  : itxuye(itxuye@gmail.com)|(http://itxuye.com)
 * 通用的adapter<T>
 * FIXME
 */
public abstract class CommonAbsListviewBaseAdapter<T> extends BaseAdapter {

  protected Context mContext;
  protected List<T> mList;
  protected LayoutInflater mInflater;

  public CommonAbsListviewBaseAdapter(Context context, List<T> list) {
    mContext = context;
    mList = list;
    this.mInflater = LayoutInflater.from(mContext);
  }

  @Override public int getCount() {
    return mList == null ? 0 : mList.size();
  }

  @Override public T getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override
  public abstract View getView(int position, View convertView, ViewGroup parent);
}
