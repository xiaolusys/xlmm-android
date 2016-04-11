package com.jimei.xiaolumeimei.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;

/**
 * Created by wisdom on 16/4/11.
 */
public class MyPreferenceView extends LinearLayout implements View.OnClickListener {

    private TextView titleText;
    private TextView summaryText;
    private Context context;
    private Activity activity;
    private Class clz;

    public MyPreferenceView(Context context) {
        super(context);
        init(context);
    }

    public MyPreferenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyPreferenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyPreferenceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.preference_list_item, this, true);
        titleText = ((TextView) view.findViewById(R.id.title));
        summaryText = ((TextView) view.findViewById(R.id.summary));
       setOnClickListener(this);
    }


    public void setTitleText(String title){
        titleText.setText(title);
    }

    public void setSummary(String summary) {
        summaryText.setText(summary);
    }

    public void bindActivity(Activity activity,Class clz){
        this.activity = activity;
        this.clz = clz;
    }

    @Override
    public void onClick(View v) {
        if (clz != null) {
            activity.startActivity(new Intent(context,clz));
        }
    }
}
