package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.StatusBarUtil;
import com.jimei.library.widget.loading.VaryViewHelperController;
import com.jimei.library.widget.loading.WisdomLoading;
import com.jimei.library.widget.swipeback.SwipeBackActivityHelper;
import com.jimei.xiaolumeimei.R;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

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
    protected WisdomLoading wisdomLoading;
    public VaryViewHelperController mVaryViewHelperController;
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (getContentViewLayoutID() != 0) {
            initContentView();
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        initViews();
        if (mVaryViewHelperController == null) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingView());
        }
        if (isNeedShow()) {
            refreshView();
            showNetworkError();
        } else {
            initData();
        }
        setListener();
    }

    public abstract void initContentView();

    public void getIntentUrl(Uri uri) {

    }

    protected void setListener() {

    }

    protected void initData() {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) return mHelper.findViewById(id);
        return v;
    }

    public void setSwipeBackEnable(boolean enable) {
        mHelper.getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
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
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
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

    public void showIndeterminateProgressDialog(boolean cancelable) {
        if (wisdomLoading == null) {
            wisdomLoading = WisdomLoading.createDialog(this)
                .setCanCancel(cancelable)
                .start();
        }
    }

    public void setDialogContent(String content) {
        if (wisdomLoading != null) {
            wisdomLoading.setContent(content);
        }
    }

    public void hideIndeterminateProgressDialog() {
        try {
            if (wisdomLoading != null) {
                wisdomLoading.dismiss();
                wisdomLoading = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void showNetworkError() {
        if (!JUtils.isNetWorkAvilable()) {
            hideIndeterminateProgressDialog();
            if (mVaryViewHelperController == null) {
                throw new IllegalStateException("no ViewHelperController");
            }
            mVaryViewHelperController.showNetworkError(view -> {
                refreshView();
                showNetworkError();
            });
        } else {
            initData();
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    public void refreshView() {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.restore();
    }

    public View getLoadingView() {
        return null;
    }

    public boolean isNeedShow() {
        return true;
    }
}
