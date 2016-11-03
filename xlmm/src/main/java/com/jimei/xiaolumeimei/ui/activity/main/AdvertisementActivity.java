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
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;

import rx.Subscription;

public class AdvertisementActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private Subscription mSubscribe;
    private TextView text;
    private boolean isDestroy;
    private String mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ViewUtils.setWindowStatus(this);
        if (getIntent().getExtras() != null) {
            mLink = getIntent().getExtras().getString("link");
        }
        isDestroy = false;
        img = (ImageView) findViewById(R.id.img);
        text = (TextView) findViewById(R.id.text);
        assert text != null;
        text.setOnClickListener(this);
        if (mLink == null) {
            mSubscribe = ActivityModel.getInstance()
                    .getStartAds()
                    .subscribe(startBean -> {
                        if (startBean != null) {
                            String picture = startBean.getPicture();
                            if (picture != null && !"".equals(picture)) {
                                img.setVisibility(View.VISIBLE);
                                Glide.with(AdvertisementActivity.this).load(picture).into(img);
                                readyToJump();
                            } else {
                                jumpAndFinish();
                            }
                        } else {
                            jumpAndFinish();
                        }
                    }, throwable -> {
                        jumpAndFinish();
                    });
        } else {
            img.setVisibility(View.VISIBLE);
            Glide.with(this).load(mLink).into(img);
            readyToJump();
        }
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

    @Override
    protected void onStop() {
        super.onStop();
        if (mSubscribe != null && mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
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
