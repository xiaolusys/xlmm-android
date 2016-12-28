package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

import butterknife.Bind;

public class DrawCashResultActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.finish_btn)
    Button finishBtn;
    private Bundle extras;

    @Override
    protected void setListener() {
        finishBtn.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        this.extras = extras;
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_draw_cash_result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_btn:
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detail:
                Intent intent = new Intent(this, MamaDrawCashDetailActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_draw_cash_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
