package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.NinePicFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.PushBoutiqueFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.PushCategoryFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wisdom on 16/10/9.
 */

public class DayPushActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.layout)
    LinearLayout layout;

    @Override
    protected void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getWxCode()
                .subscribe(new ServiceResponse<WxQrcode>() {
                    @Override
                    public void onNext(WxQrcode wxQrcode) {
                        List<BaseFragment> fragments = new ArrayList<>();
                        fragments.add(NinePicFragment.newInstance(wxQrcode.getQrcode_link()));
                        fragments.add(PushBoutiqueFragment.newInstance(wxQrcode.getQrcode_link()));
                        fragments.add(PushCategoryFragment.newInstance(wxQrcode.getQrcode_link()));
                        BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
                        mPager.setAdapter(mAdapter);
                        mPager.setOffscreenPageLimit(3);
                        mTabLayout.setupWithViewPager(mPager);
                        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                    }
                }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_day_push;
    }

    @Override
    public View getLoadingView() {
        return layout;
    }
}
