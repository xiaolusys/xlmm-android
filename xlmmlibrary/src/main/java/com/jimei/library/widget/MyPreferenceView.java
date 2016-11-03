package com.jimei.library.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.R;

import java.util.Calendar;


/**
 * Created by wisdom on 16/4/11.
 */
public class MyPreferenceView extends LinearLayout implements View.OnClickListener {

    private TextView titleText;
    private TextView summaryText;
    private ImageView imageView;
    private Context context;
    private Activity activity;
    private Intent intent;
    private long lastClickTime = 0;

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
        imageView = (ImageView) view.findViewById(R.id.img);
        setOnClickListener(this);
    }

    public void hideImg() {
        imageView.setVisibility(GONE);
    }

    public void setTitleText(String title) {
        titleText.setText(title);
    }

    public void setSummary(String summary) {
        summaryText.setText(summary);
    }

    public void bindActivity(Activity activity, Class clz) {
        this.activity = activity;
        if (clz != null) {
            intent = new Intent(context, clz);
        }
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > 2000L) {
            lastClickTime = currentTime;
            if (intent != null) {
                activity.startActivity(intent);
            }
        }
    }

    public void addBundle(Bundle bundle) {
        intent.putExtras(bundle);
    }
}
