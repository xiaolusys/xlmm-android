package com.jimei.xiaolumeimei.ui.activity.product;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MyPagerAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.databinding.ActivityGroupPurchaseDetailBinding;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.AttrView;
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

public class GroupPurchaseDetailActivity extends BaseMVVMActivity<ActivityGroupPurchaseDetailBinding> implements View.OnClickListener {
    private int model_id;
    private ShareModelBean shareModel;
    private long left;

    @Override
    protected void initView() {
        WebSettings settings = b.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        b.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 60) {
                    hideIndeterminateProgressDialog();
                }
            }
        });
        b.webView.loadUrl(XlmmApi.getAppUrl() + "/mall/product/details/app/" + 19049);

    }

    @Override
    protected void initListener() {
        b.finish.setOnClickListener(this);
        b.share.setOnClickListener(this);
        b.tvOne.setOnClickListener(this);
        b.tvThree.setOnClickListener(this);
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
        showIndeterminateProgressDialog(false);
        addSubscription(ProductModel.getInstance()
                .getProductDetail(model_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductDetailBean>() {

                    @Override
                    public void onNext(ProductDetailBean productDetailBean) {
                        fillDataToView(productDetailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("商品详情加载失败!");
                        hideIndeterminateProgressDialog();
                    }
                }));
        addSubscription(ProductModel.getInstance()
                .getShareModel(model_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ShareModelBean>() {

                    @Override
                    public void onNext(ShareModelBean shareModelBean) {
                        shareModel = shareModelBean;
                    }
                }));
    }

    private void fillDataToView(ProductDetailBean productDetailBean) {
        ProductDetailBean.DetailContentBean detail_content = productDetailBean.getDetail_content();
        b.name.setText(detail_content.getName());
        b.agentPrice.setText("¥" + detail_content.getLowest_agent_price());
        b.salePrice.setText("/¥" + detail_content.getLowest_std_sale_price());
        String offshelf_time = detail_content.getOffshelf_time().replace("T", " ");
        initAttr(productDetailBean.getComparison().getAttributes());
        initLeftTime(offshelf_time);
        initHeadImg(detail_content);
        hideIndeterminateProgressDialog();
    }

    private void initHeadImg(ProductDetailBean.DetailContentBean detail_content) {
        ArrayList<ImageView> list = new ArrayList<>();
        List<String> head_imgs = detail_content.getHead_imgs();
        if (head_imgs.size() > 0) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(head_imgs.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().placeholder(R.drawable.parceholder).into(imageView);
            list.add(imageView);
        }
        PagerAdapter viewPagerAdapter = new MyPagerAdapter(list);
        b.viewPager.setAdapter(viewPagerAdapter);
    }

    private void initAttr(List<ProductDetailBean.ComparisonBean.AttributesBean> attributes) {
        for (int i = 0; i < attributes.size(); i++) {
            AttrView attrView = new AttrView(this);
            attrView.setAttrName(attributes.get(i).getName());
            attrView.setAttrValue(attributes.get(i).getValue());
            b.llAttr.addView(attrView);
        }
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
                SystemClock.sleep(1000);
                runOnUiThread(() -> b.countView.updateShow(left));
            }
        }).start();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        model_id = extras.getInt("model_id");
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
        super.onDestroy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_group_purchase_detail;
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
            case R.id.tv_one:
                break;
            case R.id.tv_three:
                break;
            default:
                break;
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
}
