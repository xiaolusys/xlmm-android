package cn.udesk.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import cn.udesk.StatusBarUtil;

/**
 * Created by wisdom on 16/9/8.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, Color.parseColor("#FAAA14"), 0);
    }
}
