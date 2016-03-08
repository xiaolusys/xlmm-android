package com.jimei.xiaolumeimei.ui.activity.product;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.fragment.VerticalFragmentDetail;
import com.jimei.xiaolumeimei.ui.fragment.VerticalFragmentWeb;
import com.jimei.xiaolumeimei.ui.fragment.setSkuidListener;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.widget.doubleview.DragLayout;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailActvityWeb extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, setSkuidListener {
  private static final String TAG = ProductDetailActvityWeb.class.getSimpleName();
  @Bind(R.id.first) FrameLayout first;
  @Bind(R.id.second) FrameLayout second;
  @Bind(R.id.draglayout) DragLayout dragLayout;
  @Bind(R.id.image_1) ImageView imageView1;
  @Bind(R.id.image_2) ImageView imageView2;
  @Bind(R.id.rv_cart) RelativeLayout rvCart;
  @Bind(R.id.shopping_button) Button button_shop;
  CartsModel cartsModel = new CartsModel();
  int num = 0;
  @Bind(R.id.cv_lefttime) CountdownView cvLefttime;
  private String item_id;
  private String sku_id;
  private BadgeView badge;
  private String productId;
  private VerticalFragmentDetail fragment1;
  private VerticalFragmentWeb fragment3;
  private boolean isSelectzz;

  @Override protected void setListener() {
    button_shop.setOnClickListener(this);
    rvCart.setOnClickListener(this);
  }

  @Override protected void initData() {
    fragment1.initView(productId);

    Subscription subscribeSubscription = ProductModel.getInstance()
        .getProductDetails(productId)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductDetailBean>() {
          @Override public void onNext(ProductDetailBean productDetailBean) {
            if (productDetailBean != null) {
              boolean isSaleopen = productDetailBean.isIsSaleopen();
              boolean isSaleout = productDetailBean.isIsSaleout();
              if (isSaleopen) {
                if (isSaleout) {
                  button_shop.setClickable(false);
                  button_shop.setBackgroundColor(Color.parseColor("#f3f3f4"));
                } else {
                  button_shop.setClickable(true);
                }
              } else {
                button_shop.setClickable(false);
                button_shop.setBackgroundColor(Color.parseColor("#f3f3f4"));
              }

              item_id = productDetailBean.getId();
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    productId = extras.getString("product_id");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_productdetail_web;
  }

  @Override protected void initViews() {

    Window window = getWindow();
    //4.4版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    //5.0版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      //window.setNavigationBarColor(Color.TRANSPARENT);
    }

    View target = findViewById(R.id.rv_cart);
    badge = new BadgeView(this);
    badge.setTargetView(target);

    fragment1 = new VerticalFragmentDetail();
    fragment3 = new VerticalFragmentWeb();

    getSupportFragmentManager().beginTransaction()
        .add(R.id.first, fragment1)
        .add(R.id.second, fragment3)
        .commit();

    DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
      @Override public void onDragNext() {
        fragment3.initView(productId);
      }
    };
    dragLayout.setNextPageListener(nextIntf);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.shopping_button:

        if (!LoginUtils.checkLoginState(getApplicationContext())) {

          Intent intent = new Intent(ProductDetailActvityWeb.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "product");
          intent.putExtras(bundle);
          startActivity(intent);
          //startActivity(new Intent(ProductDetailActvity.this, LoginActivity.class));
        } else {

          if (isSelectzz) {
            JUtils.Log(sku_id + "这尺寸可以");

            Subscription subscribe = cartsModel.addCarts(item_id, sku_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<AddCartsBean>() {

                  @Override public void onError(Throwable e) {
                    super.onError(e);
                    JUtils.Toast("商品库存不足,选择其它看看吧");
                  }

                  @Override public void onNext(AddCartsBean addCartsBean) {

                    //int[] location = new int[2];
                    //if (endP == null) {
                    //  frameLayout.getLocationOnScreen(location);
                    //  baseP = new PointF(location[0], location[1]);
                    //  endP = new PointF();
                    //  endP.x = 0;
                    //  endP.y = frameLayout.getMeasuredHeight();
                    //}
                    //
                    ////final int viewW, viewH;
                    ////viewW = v.getMeasuredWidth();
                    ////viewH = v.getMeasuredHeight();
                    //v.getLocationOnScreen(location);
                    //startP = new PointF(location[0] - baseP.x, location[1] - baseP.y);
                    //
                    //final View animView =
                    //    getLayoutInflater().inflate(R.layout.item_cart, frameLayout, false);
                    //ValueAnimator valueAnimator =
                    //    ValueAnimator.ofObject(new BezierEvaluator(), startP, endP);
                    //valueAnimator.addUpdateListener(
                    //    new ValueAnimator.AnimatorUpdateListener() {
                    //      @Override public void onAnimationUpdate(ValueAnimator animation) {
                    //        PointF pointF = (PointF) animation.getAnimatedValue();
                    //        animView.setX(pointF.x);
                    //        animView.setY(pointF.y);
                    //      }
                    //    });
                    //valueAnimator.addListener(new Animator.AnimatorListener() {
                    //  public void onAnimationStart(Animator animation) {
                    //    frameLayout.addView(animView);
                    //  }
                    //
                    //  public void onAnimationEnd(Animator animation) {
                    //    frameLayout.removeView(animView);
                    //    animView.destroyDrawingCache();
                    //  }
                    //
                    //  public void onAnimationCancel(Animator animation) {
                    //  }
                    //
                    //  public void onAnimationRepeat(Animator animation) {
                    //  }
                    //});
                    //
                    //AnimatorSet animatorSet = new AnimatorSet();
                    //animatorSet.playTogether(
                    //    ObjectAnimator.ofFloat(animView, "scaleX", 0.3f, 1f),
                    //    ObjectAnimator.ofFloat(animView, "scaleY", 0.3f, 1f),
                    //    valueAnimator);
                    //animatorSet.setDuration(1000);
                    //animatorSet.start();

                    if (addCartsBean != null) {
                      //num++;
                      //badge.setBadgeCount(num);

                      cartsModel.show_carts_num()
                          .subscribeOn(Schedulers.io())
                          .subscribe(new ServiceResponse<CartsNumResultBean>() {
                            @Override
                            public void onNext(CartsNumResultBean cartsNumResultBean) {
                              if (cartsNumResultBean != null
                                  && cartsNumResultBean.getResult() != 0) {
                                num = cartsNumResultBean.getResult();
                                badge.setBadgeCount(num);
                                imageView1.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.VISIBLE);
                                cvLefttime.setVisibility(View.VISIBLE);

                                JUtils.Log(TAG,
                                    calcLefttowTime(cartsNumResultBean.getLastCreated())
                                        + "");
                                cvLefttime.start(
                                    calcLefttowTime(cartsNumResultBean.getLastCreated()));
                                cvLefttime.setOnCountdownEndListener(
                                    new CountdownView.OnCountdownEndListener() {
                                      @Override public void onEnd(CountdownView cv) {
                                        imageView1.setVisibility(View.VISIBLE);
                                        imageView2.setVisibility(View.INVISIBLE);
                                        cvLefttime.setVisibility(View.INVISIBLE);
                                        badge.setBadgeCount(0);
                                      }
                                    });
                              } else {
                                imageView1.setVisibility(View.VISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                                cvLefttime.setVisibility(View.INVISIBLE);
                              }
                            }
                          });
                    }
                  }
                });

            addSubscription(subscribe);
          } else {
            Toast.makeText(mContext, "请选择尺码", Toast.LENGTH_SHORT).show();
          }
        }

        break;

      case R.id.rv_cart:
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          startActivity(new Intent(ProductDetailActvityWeb.this, CartActivity.class));
        } else {
          Intent intent = new Intent(ProductDetailActvityWeb.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "cart");
          intent.putExtras(bundle);
          startActivity(intent);
        }
        break;
    }
  }

  @Override protected void onResume() {
    super.onResume();
    //显示购物车数量
    cartsModel.show_carts_num()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsNumResultBean>() {
          @Override public void onNext(CartsNumResultBean cartsNumResultBean) {
            if (cartsNumResultBean != null && cartsNumResultBean.getResult() != 0) {
              num = cartsNumResultBean.getResult();
              badge.setBadgeCount(num);
              imageView1.setVisibility(View.INVISIBLE);
              imageView2.setVisibility(View.VISIBLE);
              cvLefttime.setVisibility(View.VISIBLE);

              cvLefttime.start(calcLefttowTime(cartsNumResultBean.getLastCreated()));
              cvLefttime.setOnCountdownEndListener(
                  new CountdownView.OnCountdownEndListener() {
                    @Override public void onEnd(CountdownView cv) {
                      imageView1.setVisibility(View.VISIBLE);
                      imageView2.setVisibility(View.INVISIBLE);
                      cvLefttime.setVisibility(View.INVISIBLE);
                      badge.setBadgeCount(0);
                    }
                  });

            } else {
              imageView1.setVisibility(View.VISIBLE);
              imageView2.setVisibility(View.INVISIBLE);
              cvLefttime.setVisibility(View.INVISIBLE);
            }
          }
        });
  }

  @Override public void setSkuid(String skuid, boolean isSelect) {
    sku_id = skuid;
    isSelectzz = isSelect;
  }

  private long calcLeftTime(String crtTime) {
    long left = 0;
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      crtTime = crtTime.replace("T", " ");
      Date crtdate = format.parse(crtTime);
      if (crtdate.getTime() + 20 * 60 * 1000 - now.getTime() > 0) {
        left = crtdate.getTime() + 20 * 60 * 1000 - now.getTime();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return left;
  }

  private long calcLefttowTime(long crtTime) {
    long left = 0;
    Date now = new Date();
    try {
      if (crtTime * 1000 - now.getTime() > 0) {
        left = crtTime * 1000 - now.getTime();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return left;
  }
}

