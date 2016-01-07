package com.jimei.xiaolumeimei.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.jimei.xiaolumeimei.R;

public class SettingContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_info_activity);
        setTitle("个人基本信息");
        TextView a = (TextView) findViewById(R.id.setting_name1);
        TextView b = (TextView) findViewById(R.id.setting_name2);
    }

}

