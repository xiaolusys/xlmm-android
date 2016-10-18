package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ImageAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ImagePagerActivity extends BaseSwipeBackCompatActivity {

    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.guideGroup)
    LinearLayout guideGroup;
    //public static ImageSize imageSize;
    private List<View> guideViewList = new ArrayList<>();

    public static void startImagePagerActivity(Context context, List<String> imgUrls,int position) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
        intent.putExtra(INTENT_POSITION,position);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_imagepager;
    }

    @Override
    protected void initViews() {
        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        ImageAdapter mAdapter = new ImageAdapter(this, this);
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos,
                              ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
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
