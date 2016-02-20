package com.jimei.xiaolumeimei.ui.activity.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.FilePara;
import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ShareProductBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.okhttp.callback.FileParaCallback;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.DisplayUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.FlowLayout;
import com.jimei.xiaolumeimei.widget.TagAdapter;
import com.jimei.xiaolumeimei.widget.TagFlowLayout;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import okhttp3.Call;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailActvity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, TagFlowLayout.OnSelectListener,
    TagFlowLayout.OnTagClickListener {
  public  static String TAG = "ProductDetailActvity";

  ProductModel model = new ProductModel();
  @Bind(R.id.shopping_button) Button button_shop;
  @Bind(R.id.dot_cart) FrameLayout frameLayout;
  @Bind(R.id.rv_cart) RelativeLayout rv_cart;
  @Bind(R.id.img_share) ImageView img_share;

  int num = 0;
  List<ProductDetailBean.NormalSkusEntity> normalSkus = new ArrayList<>();
  CartsModel cartsModel = new CartsModel();
  boolean isSelect;
  private PointF startP, endP, baseP;
  private ImageView titleImage;
  private LinearLayout longimageview_content;
  private PullToZoomScrollViewEx scrollView;
  private TagFlowLayout tagFlowLayout;
  private String productId;
  private LayoutInflater mInflater;
  private String item_id;
  private String sku_id;
  private TextView bianhao, caizhi, color, beizhu, look_chima, name, price1, price2, xidi;
  private CountdownView countdownView;
  private BadgeView badge;
  private List<String> list = new ArrayList<>();
  ShareProductBean shareProductBean = new ShareProductBean();

  @Override protected void setListener() {
    rv_cart.setOnClickListener(this);
    img_share.setOnClickListener(this);
  }

  @Override protected void initData() {

    model.getProductDetails(productId)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductDetailBean>() {

          @Override public void onNext(ProductDetailBean productDetailBean) {
            //String headImg = productDetailBean.getPicPath();

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

            bianhao.setText(productDetailBean.getName());

            caizhi.setText(productDetailBean.getDetails().getMaterial());

            color.setText(productDetailBean.getDetails().getColor());

            beizhu.setText(productDetailBean.getDetails().getNote());

            name.setText(productDetailBean.getName());
            price1.setText("¥" + productDetailBean.getAgentPrice());
            price2.setText("/¥" + productDetailBean.getStdSalePrice());

            if ((null == productDetailBean.getOffshelfTime())
                || (productDetailBean.getOffshelfTime().equals(""))) {

              long time = calcLeftTime1(productDetailBean.getSaleTime());

              countdownView.start(time);
            } else {
              long time = calcLeftTime(productDetailBean.getOffshelfTime());
              countdownView.start(time);
            }

            List<String> contentImgs =
                productDetailBean.getProductModel().getContentImgs();

            //List<SubsamplingScaleImageView> viewList = new ArrayList<>();
            List<ImageView> viewList = new ArrayList<>();

            for (String s : contentImgs) {
              //SubsamplingScaleImageView scaleImageView =
              //    new SubsamplingScaleImageView(ProductDetailActvity.this);
              //scaleImageView.setMinimumScaleType(
              //    SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);

              ImageView scaleImageView = new ImageView(ProductDetailActvity.this);
              viewList.add(scaleImageView);
            }

            boolean isOldTencentPic = false;
            for (int i = 0; i < contentImgs.size(); i++) {
              final int finalI = i;
              final int finalI1 = i;
              String head_img = "";

              if (contentImgs.get(i).startsWith("https://mmbiz.qlogo.cn")) {
                head_img = contentImgs.get(i);
                isOldTencentPic = true;
              } else {
                String[] temp = contentImgs.get(i).split("http://image.xiaolu.so/");

                if (temp.length > 1) {
                  try {
                    head_img = "http://image.xiaolu.so/"
                            + URLEncoder.encode(temp[1], "utf-8")
                            + "?imageMogr2/format/jpg/thumbnail/640/quality/90/crop"
                        + "/x2048";
                  } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                  }
                }
              }

              JUtils.Log("ProductDetail", "head_img "+head_img);

              final String finalHead_img = head_img;
              OkHttpUtils.get()
                  .url(head_img)
                  .build()
                  .execute(new FileParaCallback() {
                    @Override public void onError(Call call, Exception e) {

                    }

                    @Override public void onResponse(FilePara response) {
                      if (response != null) {
                        try {
                          JUtils.Log("ProductDetail",  " height= " + response.getHeight()
                              + " width= "+response.getWidth());
                          JUtils.Log("ProductDetail", "head_img "+finalHead_img);
                          int width = DisplayUtils.getScreenW(ProductDetailActvity.this);

                          Bitmap scaled = null;

                          if(response.getHeight() > 2048) {
                            Log.e("ProductDetail", finalHead_img + ",bad picture,height "+response.getHeight());

                          }
                          else {
                            float scale_size = DisplayUtils.getScreenW
                                (ProductDetailActvity.this) / (float)(response.getWidth());
                            JUtils.Log("ProductDetail", "use glide scale_size="+ scale_size + " screen width="+DisplayUtils.getScreenW
                                    (ProductDetailActvity.this));
                            if(response.getHeight() * scale_size > 2048){
                              JUtils.Log("ProductDetail", "use glide width chg ="+ 640*(2048 / response.getHeight()));
                              Glide.with(ProductDetailActvity.this)
                                  .load(finalHead_img)
                                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                                  .override((int)(response.getWidth()* scale_size),
                                          (int)(response.getHeight() * scale_size))
                                  .centerCrop()
                                      .placeholder(R.drawable.img_emptypic)
                                  .into(viewList.get(finalI1));
                            }
                            else {
                              JUtils.Log("ProductDetail", "use glide height chg="+ response.getHeight() * scale_size);
                              Glide.with(ProductDetailActvity.this)
                                  .load(finalHead_img)
                                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                                  .override((int)(response.getWidth()* scale_size),
                                          (int)(response.getHeight() * scale_size))
                                  .centerCrop()
                                      .placeholder(R.drawable.img_emptypic)
                                  .into(viewList.get(finalI1));
                            }
                          }

                          //Bitmap scaled = Bitmap.createScaledBitmap(response, 480, nh, true);


                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      }
                    }
                  });

              longimageview_content.addView(viewList.get(finalI1));
            }

            String headImg2 = productDetailBean.getPicPath();
            JUtils.Log("ProductDetail", "head_img2: "+ headImg2);

            if (headImg2.startsWith("https://mmbiz.qlogo.cn")) {
              //titleImage.post(new Runnable() {
              //  @Override public void run() {
                  Glide.with(mContext)
                      .load(headImg2)
                      .diskCacheStrategy(DiskCacheStrategy.ALL)
                      .centerCrop()
                      .into(titleImage);
              //  }
              //});
            } else {
              String[] temp = headImg2.split("http://image.xiaolu.so/");
              String head_img = "";
              if (temp.length > 1) {
                try {
                  head_img = "http://image.xiaolu.so/"
                      + URLEncoder.encode(temp[1], "utf-8")
                      + "?imageMogr2/format/jpg/thumbnail/640/quality/90";
                  JUtils.Log("ProductDetail", "head_img2 encode: "+ head_img);

                  final String finalHead_img = head_img;
                  //titleImage.post(new Runnable() {
                  //  @Override public void run() {
                      Glide.with(mContext)
                          .load(finalHead_img)
                          .diskCacheStrategy(DiskCacheStrategy.ALL)
                          .centerCrop()
                              .placeholder(R.drawable.parceholder)
                          .into(titleImage);
                  //  }
                  //});
                } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
                }
              }
            }

            item_id = productDetailBean.getId();

            normalSkus.addAll(productDetailBean.getNormalSkus());

            tagFlowLayout.setAdapter(
                new TagAdapter<ProductDetailBean.NormalSkusEntity>(normalSkus) {

                  @Override public View getView(FlowLayout parent, int position,
                      ProductDetailBean.NormalSkusEntity normalSkusEntity) {
                    TextView tv =
                        (TextView) mInflater.inflate(R.layout.tv, tagFlowLayout, false);
                    tv.setText(normalSkus.get(position).getName());
                    return tv;
                  }
                });

            tagFlowLayout.setOnTagClickListener(ProductDetailActvity.this);
            tagFlowLayout.setOnSelectListener(ProductDetailActvity.this);
          }
        });

    model.getProductShareInfo(productId)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ShareProductBean>() {

          @Override public void onNext(ShareProductBean productBean) {
            shareProductBean = productBean;
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    productId = extras.getString("product_id");
    //Log.i("ProductDetailActivity", productId);
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.productdetail_activity;
  }

  @Override protected void initViews() {
    //manager.createFigureBadge(R.id.text_1, FigureStyle.class).setFigure(num).show();

    View target = findViewById(R.id.rv_cart);
    badge = new BadgeView(this);
    badge.setTargetView(target);

    loadViewForCode();
    mInflater = LayoutInflater.from(this);
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

    longimageview_content = (LinearLayout) scrollView.getPullRootView()
        .findViewById(R.id.longimageview_content);

    tagFlowLayout =
        (TagFlowLayout) scrollView.getPullRootView().findViewById(R.id.id_flowlayout);

    bianhao = (TextView) scrollView.getPullRootView().findViewById(R.id.shangpinbianhao);
    caizhi = (TextView) scrollView.getPullRootView().findViewById(R.id.shagpincaizhi);
    color = (TextView) scrollView.getPullRootView().findViewById(R.id.kexuanyanse);
    beizhu = (TextView) scrollView.getPullRootView().findViewById(R.id.shangpinnbeizhu);
    look_chima = (TextView) scrollView.getPullRootView().findViewById(R.id.look_size);
    xidi = (TextView) scrollView.getPullRootView().findViewById(R.id.look_xidi);

    name = (TextView) scrollView.getPullRootView().findViewById(R.id.name);
    price1 = (TextView) scrollView.getPullRootView().findViewById(R.id.price1);
    price2 = (TextView) scrollView.getPullRootView().findViewById(R.id.price2);
    countdownView =
        (CountdownView) scrollView.getPullRootView().findViewById(R.id.cv_countdownView);

    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    int mScreenHeight = localDisplayMetrics.heightPixels;
    int mScreenWidth = localDisplayMetrics.widthPixels;
    LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
        (int) (19.0F * (mScreenWidth / 16.0F)));
    scrollView.setHeaderLayoutParams(localObject);

    button_shop.setOnClickListener(this);

    look_chima.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    xidi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    //look_chima.setOnClickListener(this);
    xidi.setOnClickListener(this);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void loadViewForCode() {
    scrollView = (PullToZoomScrollViewEx) findViewById(R.id.pulltoZoomScrollView);
    View headView =
        LayoutInflater.from(this).inflate(R.layout.productdetail_head_image, null, false);
    View zoomView =
        LayoutInflater.from(this).inflate(R.layout.productdetail_zoom_image, null, false);
    View contentView =
        LayoutInflater.from(this).inflate(R.layout.pull_zoom_contentview, null, false);
    scrollView.setHeaderView(headView);

    //detailImage = (ImageView) contentView.findViewById(R.id.image_details);
    titleImage = (ImageView) zoomView.findViewById(R.id.iv_zoom);
    scrollView.setZoomView(zoomView);
    scrollView.setScrollContentView(contentView);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.shopping_button:

        if (!LoginUtils.checkLoginState(getApplicationContext())) {

          Intent intent = new Intent(ProductDetailActvity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "product");
          intent.putExtras(bundle);
          startActivity(intent);
          //startActivity(new Intent(ProductDetailActvity.this, LoginActivity.class));
        } else if (sku_id == null) {
          JUtils.Toast("请选择尺码");
        } else {
          JUtils.Log(sku_id + "这尺寸可以");

          cartsModel.addCarts(item_id, sku_id)
              .subscribeOn(Schedulers.newThread())
              .subscribe(new ServiceResponse<AddCartsBean>() {

                @Override public void onError(Throwable e) {
                  super.onError(e);
                  JUtils.Toast("商品库存不足,选择其它看看吧");
                }

                @Override public void onNext(AddCartsBean addCartsBean) {
                  super.onNext(addCartsBean);

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
                  num++;
                  badge.setBadgeCount(num);
                }
              });
        }

        break;

      //case R.id.look_size:
      //
      //  startActivity(new Intent(ProductDetailActvity.this, SizeActivity.class));
      //
      //  break;

      case R.id.look_xidi:

        startActivity(
            new Intent(ProductDetailActvity.this, WashingIntrductionActivity.class));

        break;

      case R.id.rv_cart:
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          startActivity(new Intent(ProductDetailActvity.this, CartActivity.class));
        } else {
          Intent intent = new Intent(ProductDetailActvity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "cart");
          intent.putExtras(bundle);
          startActivity(intent);
        }
        break;

      case R.id.img_share:
        JUtils.Log(TAG,"share productdetail");
        share_productdetail();
        break;
    }
  }

  @Override public void onSelected(Set<Integer> selectPosSet) {
    if (selectPosSet.isEmpty()) {
      isSelect = false;
    } else {
      isSelect = true;
    }
  }

  @Override public boolean onTagClick(View view, int position, FlowLayout parent) {
    sku_id = normalSkus.get(position).getId();
    //JUtils.Toast(sku_id + "   ,...." + item_id);
    return true;
  }

  @Override protected void onResume() {
    super.onResume();

    //显示购物车数量
    cartsModel.show_carts_num()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsNumResultBean>() {
          @Override public void onNext(CartsNumResultBean cartsNumResultBean) {
            super.onNext(cartsNumResultBean);
            if (cartsNumResultBean != null) {
              num = cartsNumResultBean.getResult();
              badge.setBadgeCount(num);
            }
          }
        });
  }

  private long calcLeftTime(String crtTime) {
    JUtils.Log("ProductDetailActvity", "calcLeftTime");
    long left = 0;
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      crtTime = crtTime.replace("T", " ");
      Date crtdate = format.parse(crtTime);
      if (crtdate.getTime() - now.getTime() > 0) {
        left = crtdate.getTime() - now.getTime();
        JUtils.Log("ProductDetailActvity",
            crtdate.getTime() + "　　　" + now.getTime() + "　　　" + left);
        return left;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  private long calcLeftTime1(String crtTime) {
    JUtils.Log("ProductDetailActvity", crtTime + " calcLeftTime1");
    long left;
    Date now = new Date();

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {

      Date nextDay14PM = format.parse(crtTime);
      JUtils.Log("ProductDetailActvity", nextDay14PM.getTime() + " calcLeftTime1");
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(nextDay14PM);
      calendar.add(Calendar.DATE, 1);
      calendar.set(Calendar.HOUR_OF_DAY, 14);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      nextDay14PM = calendar.getTime();

      JUtils.Log("ProductDetailActvity", nextDay14PM.getTime() + " calcLeftTime1");

      if (nextDay14PM.getTime() - now.getTime() > 0) {
        left = nextDay14PM.getTime() - now.getTime();
        JUtils.Log("ProductDetailActvity",
            nextDay14PM.getTime() + "　　　" + now.getTime() + "　　　" + left);
        return left;
      }
    } catch (Exception e) {
      JUtils.Log("ProductDetailActvity", e.getMessage());

      e.printStackTrace();
    }

    return 0;
  }

  private void share_productdetail(){
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(shareProductBean.getTitle());
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    //oks.setTitleUrl("http://sharesdk.cn");
    // text是分享文本，所有平台都需要这个字段
    oks.setText(shareProductBean.getDesc());
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
    oks.setUrl(shareProductBean.getShareLink());

    // url仅在微信（包括好友和朋友圈）中使用
    //oks.setUrl(myurl);
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    //oks.setComment("我是测试评论文本");
    // site是分享此内容的网站名称，仅在QQ空间使用
    //oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    //oks.setSiteUrl("http://sharesdk.cn");

    // 启动分享GUI
    oks.show(this);
  }
}
