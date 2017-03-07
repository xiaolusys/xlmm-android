package com.jimei.xiaolumeimei.ui.fragment.main;

import android.support.v7.app.AlertDialog;

import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.LoginEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wisdom on 17/3/6.
 */

public class BoutiqueTabFragment extends BaseWebViewFragment {

    public static BoutiqueTabFragment newInstance() {
        return new BoutiqueTabFragment();
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        super.initViews();
    }

    @Override
    public void initData() {
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getProfile(new ServiceResponse<UserInfoBean>() {
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    if ((userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm().getId() != 0)) {
                        addSubscription(XlmmApp.getVipInteractor(mActivity)
                            .getMamaUrl()
                            .subscribe(new ServiceResponse<MamaUrl>() {
                                @Override
                                public void onNext(MamaUrl mamaUrl) {
                                    String boutiqueUrl = mamaUrl.getResults().get(0).getExtra().getBoutique();
                                    loadUrl(boutiqueUrl);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    hideIndeterminateProgressDialog();
                                }
                            }));
                    } else {
                        hideIndeterminateProgressDialog();
                        loadUrl("http://m.xiaolumeimei.com/mall/");
                        new AlertDialog.Builder(mActivity)
                            .setTitle("提示")
                            .setMessage("您暂时不是小鹿妈妈,请关注\"小鹿美美\"公众号,获取更多信息哦!")
                            .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                            .show();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLogin(LoginEvent event) {
        initData();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
