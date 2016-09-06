package com.jimei.xiaolumeimei.ui.activity.product;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MyPagerAdapter;
import com.jimei.xiaolumeimei.adapter.SkuColorAdapter;
import com.jimei.xiaolumeimei.adapter.SkuSizeAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.databinding.ActivityProductDetailBinding;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartsPayInfoActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.RxUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.AttrView;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.TagTextView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.schedulers.Schedulers;

public class ProductDetailActivity extends BaseMVVMActivity<ActivityProductDetailBinding> implements View.OnClickListener, Animation.AnimationListener {
    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    private ShareModelBean shareModel;
    private ProductDetailBean productDetail;
    private int cart_num = 0;
    private long left;
    private boolean collectFlag, isAlive;
    private Dialog dialog;
    private ImageView img, plusIv, minusIv;
    private TextView nameTv, agentTv, saleTv, numTv, commitTv;
    private RecyclerView colorRv, sizeRv;
    private int model_id, sku_id, item_id, num, current;
    private SkuSizeAdapter skuSizeAdapter;
    private LinearLayout colorLayout, sizeLayout;
    private ProductDetailBean.TeamBuyInfo teamBuyInfo;
    private List<ProductDetailBean.SkuInfoBean> skuInfo;


    @Override
    protected void initView() {
        num = 1;
        showIndeterminateProgressDialog(false);
        WebSettings settings = b.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        b.webView.setWebChromeClient(new WebChromeClient());
        b.webView.loadUrl(XlmmApi.getAppUrl() + "/mall/product/details/app/" + model_id);
        View view = getLayoutInflater().inflate(R.layout.pop_product_detail_layout, null);
        findById(view);
        dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialog_anim);
        colorRv.setLayoutManager(new GridLayoutManager(this, 4));
        colorRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        colorRv.addItemDecoration(new SpaceItemDecoration(12, 12, 8, 8));
        sizeRv.setLayoutManager(new GridLayoutManager(this, 4));
        sizeRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        sizeRv.addItemDecoration(new SpaceItemDecoration(12, 12, 8, 8));

    }

    private void findById(View view) {
        img = ((ImageView) view.findViewById(R.id.img));
        nameTv = ((TextView) view.findViewById(R.id.name));
        agentTv = ((TextView) view.findViewById(R.id.agent_price));
        saleTv = ((TextView) view.findViewById(R.id.sale_price));
        plusIv = ((ImageView) view.findViewById(R.id.plus));
        minusIv = ((ImageView) view.findViewById(R.id.minus));
        numTv = ((TextView) view.findViewById(R.id.num));
        commitTv = ((TextView) view.findViewById(R.id.commit));
        colorRv = ((RecyclerView) view.findViewById(R.id.rv_color));
        sizeRv = ((RecyclerView) view.findViewById(R.id.rv_size));
        colorLayout = ((LinearLayout) view.findViewById(R.id.ll_color));
        sizeLayout = ((LinearLayout) view.findViewById(R.id.ll_size));
    }

    @Override
    protected void initListener() {
        b.finish.setOnClickListener(this);
        b.share.setOnClickListener(this);
        b.rlCart.setOnClickListener(this);
        b.tvAdd.setOnClickListener(this);
        b.tvAddTeam.setOnClickListener(this);
        b.tvAddOne.setOnClickListener(this);
        b.collectLayout.setOnClickListener(this);
        plusIv.setOnClickListener(this);
        minusIv.setOnClickListener(this);
        commitTv.setOnClickListener(this);
        b.pullToLoad.setScrollListener(
                (scrollView, x, y, oldx, oldy) -> {
                    float v = ((float) y) / (b.viewPager.getHeight() - b.tvTitle.getHeight());
                    if (v >= 0.5) {
                        b.tvTitle.setAlpha(1);
                    } else {
                        b.tvTitle.setAlpha(2 * v);
                    }
                }
        );
    }

    @Override
    protected void initData() {
        addSubscription(ProductModel.getInstance()
                .getProductDetail(model_id)
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductDetailBean>() {

                    @Override
                    public void onNext(ProductDetailBean productDetailBean) {
                        productDetail = productDetailBean;
                        fillDataToView(productDetailBean);
                    }
                }));
        addSubscription(ProductModel.getInstance()
                .getShareModel(model_id)
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ShareModelBean>() {

                    @Override
                    public void onNext(ShareModelBean shareModelBean) {
                        shareModel = shareModelBean;
                    }
                }));
        addSubscription(CartsModel.getInstance()
                .show_carts_num()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CartsNumResultBean>() {

                    @Override
                    public void onNext(CartsNumResultBean cartsNumResultBean) {
                        cart_num = cartsNumResultBean.getResult();
                        b.tvCart.setText(cart_num + "");
                        if (cart_num > 0) {
                            b.tvCart.setVisibility(View.VISIBLE);
                        }
                    }
                }));

    }

    private void initLeftTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date offTime = dateFormat.parse(time);
            Date date = new Date();
            if ((offTime.getTime() - date.getTime()) > 0) {
                left = offTime.getTime() - date.getTime();
            } else {
                left = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (left > 0) {
                left = left - 1000;
                runOnUiThread(() -> b.countView.updateShow(left));
                SystemClock.sleep(1000);
            }
        }).start();
    }

    private void fillDataToView(ProductDetailBean productDetailBean) {
        ProductDetailBean.DetailContentBean detailContent = productDetailBean.getDetail_content();
        teamBuyInfo = productDetailBean.getTeambuy_info();
        skuInfo = productDetailBean.getSku_info();
        if ("will".equals(detailContent.getSale_state())) {
            b.tvAdd.setClickable(false);
            b.tvAdd.setText("即将开售");
        } else if ("off".equals(detailContent.getSale_state())) {
            b.tvAdd.setClickable(false);
            b.tvAdd.setText("已下架");
        } else if ("on".equals(detailContent.getSale_state()) && detailContent.isIs_sale_out()) {
            b.tvAdd.setClickable(false);
            b.tvAdd.setText("已抢光");
        } else if (teamBuyInfo != null && teamBuyInfo.isTeambuy()) {
            AnimationSet animationSet = new AnimationSet(true);
            float f = ((float) b.tvAddTeam.getWidth()) / b.tvAdd.getWidth();
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, f, 1, 1,
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
            animationSet.addAnimation(scaleAnimation);
            animationSet.setDuration(600);
            animationSet.setAnimationListener(this);
            b.tvAdd.startAnimation(animationSet);
            item_id = skuInfo.get(0).getProduct_id();
            sku_id = skuInfo.get(0).getSku_items().get(0).getSku_id();
        } else {
            if (skuInfo.size() > 0) {
                ViewUtils.loadImgToImgView(this, img, skuInfo.get(0).getProduct_img());
                nameTv.setText(detailContent.getName() + "/" + skuInfo.get(0).getName());
                agentTv.setText("¥" + skuInfo.get(0).getSku_items().get(0).getAgent_price() + "");
                saleTv.setText("/¥" + skuInfo.get(0).getSku_items().get(0).getStd_sale_price());
                SkuColorAdapter colorAdapter = new SkuColorAdapter(skuInfo, this);
                colorRv.setAdapter(colorAdapter);
                skuSizeAdapter = new SkuSizeAdapter(this);
                sizeRv.setAdapter(skuSizeAdapter);
                skuSizeAdapter.update(skuInfo.get(0).getSku_items());
                setSkuId(skuInfo);
            }
            if (skuInfo.size() == 1 && skuInfo.get(0).getSku_items().size() == 1) {
                nameTv.setText(detailContent.getName());
                colorLayout.setVisibility(View.GONE);
                sizeLayout.setVisibility(View.GONE);
            }
        }
        collectFlag = productDetailBean.getCustom_info().isIs_favorite();
        if (collectFlag) {
            b.collectImg.setImageResource(R.drawable.icon_collect_true);
            b.collectText.setText("已收藏");
        }
        b.name.setText(detailContent.getName());
        b.agentPrice.setText("¥" + detailContent.getLowest_agent_price());
        b.salePrice.setText("/¥" + detailContent.getLowest_std_sale_price());
        String offshelf_time = detailContent.getOffshelf_time().replace("T", " ");
        List<String> item_marks = detailContent.getItem_marks();
        for (int i = 0; i < item_marks.size(); i++) {
            TagTextView tagTextView = new TagTextView(this);
            tagTextView.setTagName(item_marks.get(i));
            b.llTag.addView(tagTextView);
        }
        initAttr(productDetailBean.getComparison().getAttributes());
        initLeftTime(offshelf_time);
        initHeadImg(detailContent);
        hideIndeterminateProgressDialog();
    }

    private void setSkuId(List<ProductDetailBean.SkuInfoBean> sku_info) {
        for (int i = 0; i < sku_info.size(); i++) {
            for (int j = 0; j < sku_info.get(0).getSku_items().size(); j++) {
                if (!sku_info.get(i).getSku_items().get(j).isIs_saleout()) {
                    item_id = sku_info.get(i).getProduct_id();
                    sku_id = sku_info.get(i).getSku_items().get(j).getSku_id();
                    return;
                }
            }
        }
    }

    private void initAttr(List<ProductDetailBean.ComparisonBean.AttributesBean> attributes) {
        for (int i = 0; i < attributes.size(); i++) {
            AttrView attrView = new AttrView(this);
            attrView.setAttrName(attributes.get(i).getName());
            attrView.setAttrValue(attributes.get(i).getValue());
            b.llAttr.addView(attrView);
        }
    }

    private void initHeadImg(ProductDetailBean.DetailContentBean detail_content) {
        ArrayList<ImageView> list = new ArrayList<>();
        List<String> head_imgs = detail_content.getHead_imgs();
        int headNum = head_imgs.size();
        for (int i = 0; i < headNum; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(head_imgs.get(i) + POST_URL).diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop().placeholder(R.drawable.parceholder).into(imageView);
            list.add(imageView);
        }
        PagerAdapter viewPagerAdapter = new MyPagerAdapter(list);
        b.viewPager.setAdapter(viewPagerAdapter);
        isAlive = true;
        current = 0;
        if (headNum > 1) {
            new Thread(() -> {
                while (isAlive) {
                    SystemClock.sleep(2000);
                    runOnUiThread(() -> {
                        b.viewPager.setCurrentItem(current++, false);
                        if (current >= headNum) {
                            current = 0;
                        }
                    });
                }
            }).start();
        }
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        String dataString = getIntent().getDataString();
        if (dataString != null) {
            String[] split = dataString.split("model_id=");
            if (split[1] != null) {
                model_id = Integer.getInteger(split[1]);
            }
        } else {
            model_id = extras.getInt("model_id");
        }
        JUtils.Log("商品    model_id" + model_id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onDestroy() {
        left = 0;
        isAlive = false;
        super.onDestroy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_product_detail;
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
        switch (v.getId()) {
            case R.id.finish:
                this.finish();
                break;
            case R.id.share:
                if (shareModel != null) {
                    OnekeyShare oks = new OnekeyShare();
                    oks.disableSSOWhenAuthorize();
                    oks.setTitle(shareModel.getTitle());
                    oks.setTitleUrl(shareModel.getShare_link());
                    oks.setText(shareModel.getDesc() + shareModel.getShare_link());
                    oks.setImageUrl(shareModel.getShare_img());
                    oks.setUrl(shareModel.getShare_link());
                    oks.setShareContentCustomizeCallback(new ShareContentCustom(
                            shareModel.getDesc() + shareModel.getShare_link()));
                    oks.show(this);
                }
                break;
            case R.id.rl_cart:
                startActivity(new Intent(this, CartActivity.class));
                finish();
                break;
            case R.id.tv_add:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    jumpToLogin();
                } else {
                    dialog.show();
                }
                break;
            case R.id.tv_add_team:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    jumpToLogin();
                } else {
                    addSubscription(CartsModel.getInstance()
                            .addToCart(item_id, sku_id, num, 3)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new ServiceResponse<ResultEntity>() {
                                @Override
                                public void onNext(ResultEntity resultEntity) {
                                    if (resultEntity.getCode() == 0) {
                                        CartsModel.getInstance()
                                                .getCartsList(3)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
                                                    @Override
                                                    public void onNext(List<CartsInfoBean> cartsinfoBeen) {
                                                        if (cartsinfoBeen != null && cartsinfoBeen.size() > 0) {
                                                            String ids = cartsinfoBeen.get(0).getId() + "";
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("ids", ids);
                                                            bundle.putBoolean("flag", true);
                                                            Intent intent = new Intent(ProductDetailActivity.this, CartsPayInfoActivity.class);
                                                            intent.putExtras(bundle);
                                                            startActivity(intent);
                                                        } else {
                                                            JUtils.Toast("购买失败!");
                                                        }
                                                    }
                                                });
                                    } else {
                                        JUtils.Toast(resultEntity.getInfo());
                                    }
                                }
                            }));
                }
                break;
            case R.id.tv_add_one:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    jumpToLogin();
                } else {
                    addToCart(false);
                }
                break;
            case R.id.collect_layout:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    jumpToLogin();
                } else {
                    if (collectFlag) {
                        addSubscription(ProductModel.getInstance()
                                .deleteCollection(model_id)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<CollectionResultBean>() {
                                    @Override
                                    public void onNext(CollectionResultBean collectionResultBean) {
                                        if (collectionResultBean.getCode() == 0) {
                                            collectFlag = false;
                                            b.collectImg.setImageResource(R.drawable.icon_collect_false);
                                            b.collectText.setText("收藏");
                                        }
                                        JUtils.Toast(collectionResultBean.getInfo());
                                    }
                                }));
                    } else {
                        addSubscription(ProductModel.getInstance()
                                .addCollection(model_id)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<CollectionResultBean>() {
                                    @Override
                                    public void onNext(CollectionResultBean collectionResultBean) {
                                        if (collectionResultBean.getCode() == 0) {
                                            collectFlag = true;
                                            b.collectImg.setImageResource(R.drawable.icon_collect_true);
                                            b.collectText.setText("已收藏");
                                        }
                                        JUtils.Toast(collectionResultBean.getInfo());
                                    }
                                }));
                    }
                }
                break;
            case R.id.plus:
                num++;
                numTv.setText(num + "");
                break;
            case R.id.minus:
                if (num > 1) {
                    num--;
                    numTv.setText(num + "");
                }
                break;
            case R.id.commit:
                addToCart(true);
                break;
            default:
                break;
        }

    }

    private void jumpToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("login", "productdetail");
        bundle.putInt("id", model_id);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void addToCart(boolean dismiss) {
        addSubscription(CartsModel.getInstance()
                .addToCart(item_id, sku_id, num)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ResultEntity>() {
                    @Override
                    public void onNext(ResultEntity resultEntity) {
                        JUtils.Toast(resultEntity.getInfo());
                        if (resultEntity.getCode() == 0) {
                            cart_num += num;
                            if (dismiss)
                                dialog.dismiss();
                            b.tvCart.setText(cart_num + "");
                            if (cart_num > 0) {
                                b.tvCart.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }));
    }

    public void refreshSku(int position) {
        ProductDetailBean.SkuInfoBean skuInfoBean = productDetail.getSku_info().get(position);
        item_id = skuInfoBean.getProduct_id();
        ViewUtils.loadImgToImgView(this, img, skuInfoBean.getProduct_img());
        nameTv.setText(productDetail.getDetail_content().getName() + "/" + skuInfoBean.getName());
        List<ProductDetailBean.SkuInfoBean.SkuItemsBean> sku_items = skuInfoBean.getSku_items();
        skuSizeAdapter.updateWithClear(sku_items);
    }

    public void refreshSkuId(ProductDetailBean.SkuInfoBean.SkuItemsBean skuItemsBean) {
        agentTv.setText("¥" + skuItemsBean.getAgent_price());
        saleTv.setText("/¥" + skuItemsBean.getStd_sale_price());
        sku_id = skuItemsBean.getSku_id();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        b.tvAdd.setText("");
        b.tvAdd.setClickable(false);
        b.tvAddOne.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1, 1,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(600);
        b.tvAddOne.startAnimation(scaleAnimation);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        b.tvAdd.setVisibility(View.GONE);
        b.tvAddTeam.setVisibility(View.VISIBLE);
        b.tvAddOne.setText("单人购  ¥" + skuInfo.get(0).getSku_items().get(0).getAgent_price());
        b.tvAddTeam.setText("三人团  ¥" + teamBuyInfo.getTeambuy_price());
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public class ShareContentCustom implements ShareContentCustomizeCallback {

        private String text;

        public ShareContentCustom(String text) {
            this.text = text;
        }

        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            Map<String, String> map = new HashMap<>();
            map.put("id", "name");
            map.put(platform.getId() + "", platform.getName());
            JUtils.Log("ShareID", platform.getId() + "    " + platform.getName());
            MobclickAgent.onEvent(mContext, "ShareID", map);
            if (WechatMoments.NAME.equals(platform.getName())) {
                paramsToShare.setTitle(text);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
