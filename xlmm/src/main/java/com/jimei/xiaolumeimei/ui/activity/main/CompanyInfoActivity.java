package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

import butterknife.Bind;


/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CompanyInfoActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.tv_version)
    TextView tv_version;
    @Bind(R.id.imageView)
    ImageView imageView;
    private int num;

    @Override
    protected void setListener() {
        imageView.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.companyinfo_activity;
    }

    @Override
    protected void initViews() {
        tv_version.setText(JUtils.getAppVersionName());
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView) {
            num++;
            if (num == 8) {
                Intent intent = new Intent(this, DebugActivity.class);
                startActivity(intent);
            } else if (num == 9) {
                num = 0;
            }
        }
    }
}
