package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 2016/1/24.
 */
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.LogisticsCompanyInfo;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements SectionIndexer{

  private List<LogisticsCompanyInfo> list = null;
  private Context mContext;
  private SectionIndexer mIndexer;

  public MyAdapter(Context mContext, List<LogisticsCompanyInfo> list) {
    this.mContext = mContext;
    this.list = list;

  }

  public int getCount() {
    return this.list.size();
  }

  public Object getItem(int position) {
    return null;
  }

  public long getItemId(int position) {
    return position;
  }

  public View getView(final int position, View view, ViewGroup arg2) {
    ViewHolder viewHolder = null;
    if (view == null) {
      viewHolder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_company, null);
      viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
      viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_catalog);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    final LogisticsCompanyInfo mContent = list.get(position);
    if (position == 0) {
      viewHolder.tvLetter.setVisibility(View.VISIBLE);
      viewHolder.tvLetter.setText(mContent.getLetter());
    } else {
      String lastCatalog = list.get(position - 1).getLetter();
      if (mContent.getLetter().equals(lastCatalog)) {
        viewHolder.tvLetter.setVisibility(View.GONE);
      } else {
        viewHolder.tvLetter.setVisibility(View.VISIBLE);
        viewHolder.tvLetter.setText(mContent.getLetter());
      }
    }

    viewHolder.tvTitle.setText(this.list.get(position).getName());

    return view;

  }



  final static class ViewHolder {
    TextView tvTitle;
    TextView tvLetter;
  }


  public Object[] getSections() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getSectionForPosition(int position) {

    return 0;
  }

  public int getPositionForSection(int section) {
    LogisticsCompanyInfo mContent;
    String l;
    if (section == '!') {
      return 0;
    } else {
      for (int i = 0; i < getCount(); i++) {
        mContent = (LogisticsCompanyInfo) list.get(i);
        l = mContent.getLetter();
        char firstChar = l.toUpperCase().charAt(0);
        if (firstChar == section) {
          return i + 1;
        }

      }
    }
    mContent = null;
    l = null;
    return -1;
  }
}
