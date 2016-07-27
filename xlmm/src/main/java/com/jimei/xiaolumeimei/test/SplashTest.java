package com.jimei.xiaolumeimei.test;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.widget.ImageView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.main.SplashActivity;
import com.jude.utils.JUtils;

/**
 * Created by wisdom on 16/7/26.
 */
public class SplashTest extends InstrumentationTestCase {
    private SplashActivity activity = null;
    private ImageView imageView = null;

    @Override
    protected void setUp() throws Exception {
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClassName("com.jimei.xiaolumeimei.ui.activity.main", SplashActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity = (SplashActivity) getInstrumentation().startActivitySync(intent);
        imageView = ((ImageView) activity.findViewById(R.id.img));
    }

    @Override
    protected void tearDown() {
        activity.finish();
        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testActivity() throws Exception {
        JUtils.Log("testActivity", "test the Activity");
        SystemClock.sleep(1000);
        imageView.setImageResource(R.drawable.bg_img_pastcoupon);
    }
}
