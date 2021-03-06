package com.jimei.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.library.R;

/**
 * Created by wisdom on 16/4/13.
 */
public class XlmmTitleView extends AppBarLayout {
    private TextView name;
    private AppBarLayout layout;
    private Toolbar toolbar;
    private RelativeLayout finishLayout;

    public XlmmTitleView(Context context) {
        super(context);
        init(context, null);
    }

    public XlmmTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_view, this, true);
        name = ((TextView) view.findViewById(R.id.tv_title));
        layout = ((AppBarLayout) view.findViewById(R.id.app_bar_layout));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        finishLayout = (RelativeLayout) view.findViewById(R.id.finish_rl);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XlmmTitleView);
            name.setText(array.getString(R.styleable.XlmmTitleView_xlmm_title));
            if (!array.getBoolean(R.styleable.XlmmTitleView_show_finish, true)) {
                finishLayout.setVisibility(GONE);
            }
            setAlpha(array.getFloat(R.styleable.XlmmTitleView_alpha, 1));
            setBackColor(array.getColor(R.styleable.XlmmTitleView_back_color, getResources().getColor(R.color.colorAccent)));
        }
        toolbar.setTitle("");
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).setSupportActionBar(toolbar);
            finishLayout.setOnClickListener(v -> ((AppCompatActivity) context).finish());
        }
    }

    public void setName(String nameStr) {
        name.setText(nameStr);
    }

    public void setBackColor(int color) {
        layout.setBackgroundColor(color);
    }
}
