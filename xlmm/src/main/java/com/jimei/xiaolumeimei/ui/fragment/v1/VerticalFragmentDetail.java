package com.jimei.xiaolumeimei.ui.fragment.v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ShareProductBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.FlowLayout;
import com.jimei.xiaolumeimei.widget.TagAdapter;
import com.jimei.xiaolumeimei.widget.TagFlowLayout;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class VerticalFragmentDetail extends Fragment implements View.OnClickListener {

    @Bind(R.id.iv_zoom)
    ImageView ivZoom;
    @Bind(R.id.img_share)
    ImageView img_share;
    @Bind(R.id.img_back)
    ImageView backImg;

    List<ProductDetailBean.NormalSkusEntity> normalSkus = new ArrayList<>();
    ShareProductBean shareProductBean = new ShareProductBean();
    setSkuidListener listener;
    private TextView bianhao, caizhi, color, beizhu, name, price1, price2;
    private CountdownView countdownView;
    private TagFlowLayout tagFlowLayout;
    private LayoutInflater mInflater;
    private Subscription Subscription, subscription2;
    private MaterialDialog materialDialog;

    public static VerticalFragmentDetail newInstance(String title) {
        VerticalFragmentDetail todayFragment = new VerticalFragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", title);
        todayFragment.setArguments(bundle);
        return todayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getActivity());
        setRetainInstance(true);
    }

    public void setListener(setSkuidListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (setSkuidListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement setSkuidListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);

        bianhao = (TextView) view.findViewById(R.id.shangpinbianhao);
        caizhi = (TextView) view.findViewById(R.id.shagpincaizhi);
        color = (TextView) view.findViewById(R.id.kexuanyanse);
        beizhu = (TextView) view.findViewById(R.id.shangpinnbeizhu);
        //look_chima = (TextView) view.findViewById(R.id.look_size);
        //xidi = (TextView) view.findViewById(R.id.look_xidi);

        name = (TextView) view.findViewById(R.id.name);
        price1 = (TextView) view.findViewById(R.id.price1);
        price2 = (TextView) view.findViewById(R.id.price2);
        countdownView = (CountdownView) view.findViewById(R.id.cv_countdownView);

        //look_chima.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //xidi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        img_share.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, null);
        ButterKnife.bind(this, view);

        mInflater = LayoutInflater.from(getActivity());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void initView(String pid) {

        //showIndeterminateProgressDialog(true);

        Subscription = ProductModel.getInstance()
                .getProductDetails(pid)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ProductDetailBean>() {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        //hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ProductDetailBean productDetailBean) {

                        if (null != productDetailBean) {

                            bianhao.setText(productDetailBean.getName());

                            caizhi.setText(productDetailBean.getDetails().getMaterial());

                            color.setText(productDetailBean.getDetails().getColor());

                            beizhu.setText(productDetailBean.getDetails().getNote());

                            name.setText(productDetailBean.getName());
                            price1.setText("¥"
                                    + (float) (Math.round(productDetailBean.getAgentPrice() * 100)) / 100);
                            price2.setText("/¥"
                                    + (float) (Math.round(productDetailBean.getStdSalePrice() * 100))
                                    / 100);

                            if ((null == productDetailBean.getOffshelfTime())
                                    || (productDetailBean.getOffshelfTime().equals(""))) {

                                long time = calcLeftTime1(productDetailBean.getSaleTime());

                                countdownView.start(time);
                            } else {
                                long time = calcLeftTime(productDetailBean.getOffshelfTime());
                                countdownView.start(time);
                            }

                            String headImg2 = productDetailBean.getPicPath();
                            JUtils.Log("ProductDetail", "head_img2: " + headImg2);

                            if (headImg2.startsWith("https://mmbiz.qlogo.cn")) {
                                //titleImage.post(new Runnable() {
                                //  @Override public void run() {
                                Glide.with(getActivity())
                                        .load(headImg2)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop()
                                        .into(ivZoom);
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
                                        JUtils.Log("ProductDetail", "head_img2 encode: " + head_img);

                                        final String finalHead_img = head_img;
                                        Glide.with(VerticalFragmentDetail.this)
                                                .load(finalHead_img)
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .centerCrop()
                                                .placeholder(R.drawable.parceholder)
                                                .into(ivZoom);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            normalSkus.addAll(productDetailBean.getNormalSkus());
                        }

                        TagAdapter<ProductDetailBean.NormalSkusEntity> tagAdapter =
                                new TagAdapter<ProductDetailBean.NormalSkusEntity>(normalSkus) {

                                    @Override
                                    public View getView(FlowLayout parent, int position,
                                                        ProductDetailBean.NormalSkusEntity normalSkusEntity) {
                                        TextView tv;
                                        if (!normalSkus.get(position).isIsSaleout()) {
                                            tv =
                                                    (TextView) mInflater.inflate(R.layout.tv, tagFlowLayout, false);
                                        } else {
                                            tv = (TextView) mInflater.inflate(R.layout.tv_issalout,
                                                    tagFlowLayout, false);
                                        }

                                        tv.setText(normalSkus.get(position).getName());

                                        return tv;
                                    }
                                };

                        tagFlowLayout.setAdapter(tagAdapter);

                        if (normalSkus.size() == 1) {
                            tagAdapter.setSelectedList(0);
                            listener.setSkuid(normalSkus.get(0).getId(), true);
                        }

                        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet, int position) {

                                if (!selectPosSet.isEmpty() && listener != null) {
                                    listener.setSkuid(normalSkus.get(position).getId(), true);
                                } else {
                                    if (null != listener) {
                                        listener.setSkuid("", false);
                                    }
                                }
                            }
                        });
                    }
                });

        subscription2 = ProductModel.getInstance()
                .getProductShareInfo(pid)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ShareProductBean>() {

                    @Override
                    public void onNext(ShareProductBean productBean) {
                        shareProductBean = productBean;
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

    private void share_productdetail() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        oks.setTitle(shareProductBean.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareProductBean.getShareLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareProductBean.getDesc());
        oks.setImageUrl(shareProductBean.getShareImg());
        oks.setUrl(shareProductBean.getShareLink());

        // 启动分享GUI

        //Bitmap enableLogo = BitmapFactory.decodeResource(getResources(),
        //    R.drawable
        //    .search_button);
        //String label = "快照链接";
        //Bitmap enableLogo2 = BitmapFactory.decodeResource(getResources(),
        //    R.drawable.search_button);
        //View.OnClickListener listener = new View.OnClickListener() {
        //  public void onClick(View v) {
        //    //if (shareProductBean.getShareLink()) {
        //    //}
        //  }
        //};
        //oks.setCustomerLogo(enableLogo, enableLogo2,label, listener);
        //
        //Bitmap enableLogo3 = BitmapFactory.decodeResource(getResources(),
        //    R.drawable
        //    .search_button);
        //String label1 = "测试";
        //Bitmap enableLogo4 = BitmapFactory.decodeResource(getResources(),
        //    R.drawable.search_button);
        //View.OnClickListener listener1 = new View.OnClickListener() {
        //  public void onClick(View v) {
        //    //if (shareProductBean.getShareLink()) {
        //    //}
        //  }
        //};
        //oks.setCustomerLogo(enableLogo3, enableLogo4,label1, listener);
        oks.show(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_share:
                if (LoginUtils.checkLoginState(getActivity())) {
                    share_productdetail();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("login", "productdetail");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.img_back:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Subscription != null && Subscription.isUnsubscribed()) {
            Subscription.unsubscribe();
        }
        if (subscription2 != null && subscription2.isUnsubscribed()) {
            subscription2.unsubscribe();
        }
        ShareSDK.stopSDK(getActivity());
    }

    public void showIndeterminateProgressDialog(boolean horizontal) {
        materialDialog = new MaterialDialog.Builder(getActivity())
                //.title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .widgetColorRes(R.color.colorAccent)
                .progressIndeterminateStyle(horizontal)
                .show();
    }

    public void hideIndeterminateProgressDialog() {
        try {
            materialDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
