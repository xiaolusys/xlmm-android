package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jimei.library.rx.RxCountDown;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;

public class AdvertisementActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text;
    private boolean isDestroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ViewUtils.setWindowStatus(this);
        String link = getIntent().getExtras().getString("link");
        isDestroy = false;
        ImageView img = (ImageView) findViewById(R.id.img);
        text = (TextView) findViewById(R.id.text);
        assert text != null;
        text.setOnClickListener(this);
        assert img != null;
        Glide.with(this).load(link).into(img);
        readyToJump();
    }

    private void readyToJump() {
        RxCountDown.countdown(3).subscribe(
                integer -> {
                    if (!isDestroy) {
                        text.setText("跳过  " + integer);
                        if (integer == 0) {
                            jumpAndFinish();
                        }
                    }
                }, e -> {
                    if (!isDestroy) {
                        jumpAndFinish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void jumpAndFinish() {
        isDestroy = true;
        startActivity(new Intent(AdvertisementActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text:
                jumpAndFinish();
                break;
        }
    }
}
