package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.utils.CommonUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.TUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by itxuye on 2016/6/24.
 */
public abstract class BasePresenterActivity<T extends BasePresenter, E extends BaseModel>
    extends AutoLayoutActivity {

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
  private SharedPreferences sharedPreferences;
  private MaterialDialog materialDialog;
  public T mPresenter;
  public E mModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
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
    // base setup
    Bundle extras = getIntent().getExtras();
    if (null != extras) {
      getBundleExtras(extras);
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
      setContentView(getContentViewLayoutID());
    } else {
      throw new IllegalArgumentException("You must return a right contentView layout resource Id");
    }
    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
    mPresenter = TUtil.getT(this, 0);
    mModel = TUtil.getT(this, 1);
    this.initViews();
    if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    setListener();
  }

  protected abstract void setListener();

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  @Override public void finish() {
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

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mPresenter != null) mPresenter.onDestroy();
    ButterKnife.unbind(this);
  }

  /**
   * get bundle data
   */
  protected abstract void getBundleExtras(Bundle extras);

  /**
   * bind layout resource file
   *
   * @return id of layout resource
   */
  protected abstract int getContentViewLayoutID();

  /**
   * init all views and add events
   */
  protected abstract void initViews();

  /**
   * toggle overridePendingTransition
   */
  protected abstract boolean toggleOverridePendingTransition();

  /**
   * get the overridePendingTransition mode
   */
  protected abstract TransitionMode getOverridePendingTransitionMode();

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

  /**
   * show toast
   */
  protected void showToast(String msg) {
    if (null != msg && !CommonUtils.isEmpty(msg)) {
      Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
    }
  }

  /**
   * set status bar translucency
   */
  protected void setTranslucentStatus(boolean on) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Window win = getWindow();
      WindowManager.LayoutParams winParams = win.getAttributes();
      final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
      if (on) {
        winParams.flags |= bits;
      } else {
        winParams.flags &= ~bits;
      }
      win.setAttributes(winParams);
    }
  }

  public boolean IsLogined() {

    sharedPreferences =
        getApplicationContext().getSharedPreferences("login_info", Context.MODE_PRIVATE);

    return sharedPreferences.getBoolean("success", false);
  }

  public void finishBack(Toolbar toolbar) {
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        finish();
      }
    });
  }

  public void showIndeterminateProgressDialog(boolean horizontal) {
    if (materialDialog == null) {
      materialDialog = new MaterialDialog.Builder(this)
          //.title(R.string.progress_dialog)
          .content(R.string.please_wait)
          .progress(true, 0)
          .widgetColorRes(R.color.colorAccent)
          .progressIndeterminateStyle(horizontal)
          .show();
    }
  }

  public void hideIndeterminateProgressDialog() {
    try {
      materialDialog.dismiss();
      materialDialog = null;
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

  @Override protected void onStop() {
    super.onStop();
  }
}
