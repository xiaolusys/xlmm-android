package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.jimei.library.utils.StatusBarUtil;
import com.jimei.library.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wisdom on 16/10/14.
 */

public abstract class BaseActivity extends AutoLayoutActivity {
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * context
     */
    protected Context mContext = null;
    protected CompositeSubscription mCompositeSubscription;
    protected XlmmLoadingDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Uri uri = getIntent().getData();
        if (extras != null) {
            getBundleExtras(extras);
        } else if (uri != null) {
            getIntentUrl(uri);
        }
        mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
    }

    public void getIntentUrl(Uri uri) {

    }

    protected void setListener() {

    }

    protected void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * get bundle data
     */
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     */
    protected void initViews() {

    }

    /**
     * toggle overridePendingTransition
     */
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    /**
     * get the overridePendingTransition mode
     */
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    /**
     * startActivity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showIndeterminateProgressDialog(boolean horizontal) {
        if (loadingdialog == null) {
            loadingdialog = XlmmLoadingDialog.create(this)
                    .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
//                    .setCancellable(!horizontal)
                    .setCancellable(false)
                    .show();
        }
    }

    public void hideIndeterminateProgressDialog() {
        try {
            if (loadingdialog != null) {
                loadingdialog.dismiss();
                loadingdialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideIndeterminateProgressDialog();
        try {
            if (this.mCompositeSubscription != null) {
                this.mCompositeSubscription.unsubscribe();
                this.mCompositeSubscription = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMsg(String title, String msg1, String msg2) {
        showMsgAndFinish(title, msg1, msg2, false);
    }

    public void showMsgAndFinish(String title, String msg1, String msg2, boolean isfinish) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        if (title.equals("fail")) {
            str = "支付失败，请重试！";
        } else if (title.equals("invalid")) {
            str = "支付失败，支付软件未安装完整！";
        }
        new AlertDialog.Builder(this)
                .setMessage(str)
                .setTitle("提示")
                .setPositiveButton("OK", (dialog1, which) -> {
                    dialog1.dismiss();
                    if (isfinish) {
                        finish();
                    }
                })
                .create()
                .show();
    }
}
