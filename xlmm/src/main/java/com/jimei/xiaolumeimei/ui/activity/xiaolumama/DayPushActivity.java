package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaTabAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.ui.fragment.v1.NinePicFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.PushCategoryFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 16/10/9.
 */

public class DayPushActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.pager)
    ViewPager mPager;

    @Override
    protected void initData() {
        addSubscription(MMProductModel.getInstance()
                .getWxCode()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<WxQrcode>() {
                    @Override
                    public void onNext(WxQrcode wxQrcode) {
                        List<BaseLazyFragment> fragments = new ArrayList<>();
                        fragments.add(NinePicFragment.newInstance(wxQrcode.getQrcode_link()));
                        fragments.add(PushCategoryFragment.newInstance(wxQrcode.getQrcode_link()));
                        MamaTabAdapter mAdapter = new MamaTabAdapter(getSupportFragmentManager(), fragments);
                        mPager.setAdapter(mAdapter);
                        mPager.setOffscreenPageLimit(2);
                        mTabLayout.setupWithViewPager(mPager);
                    }
                }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_day_push;
    }

}
