package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jude.utils.JUtils;

import butterknife.Bind;


/**
 * Created by itxuye on 16/5/9.
 */
public class DebugActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {


    @Bind(R.id.edit_debug)
    EditText editDebug;
    @Bind(R.id.bt_debug)
    Button btDebug;

    SharedPreferences sharedPreferences ;
    String textDebug;

    @Override
    protected void setListener() {

        btDebug.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        sharedPreferences = getSharedPreferences("APICLIENT", MODE_PRIVATE);

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_debug;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_debug) {
            if (TextUtils.isEmpty(editDebug.getText())) {
                JUtils.Toast("请输入API地址");
                return;
            }
            textDebug = editDebug.getText().toString().trim();
           SharedPreferences.Editor editor =  sharedPreferences.edit();
            editor.putString("BASE_URL",textDebug);
           boolean isPut =  editor.commit();
            if (isPut) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

        }
    }
}
