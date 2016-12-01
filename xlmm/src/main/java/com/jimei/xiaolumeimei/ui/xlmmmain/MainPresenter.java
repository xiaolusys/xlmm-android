package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.support.v4.widget.SwipeRefreshLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainPresenter extends MainContract.Presenter {
    public UserInfoBean getUserInfoNewBean() {
        return userInfoNewBean;
    }

    protected UserInfoBean userInfoNewBean;

    @Override
    public void getUserInfoBean() {
        mRxManager.add(mModel.getProfile()
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        if (userInfoBean != null) {
                            userInfoNewBean = userInfoBean;
                            mView.initDrawer(userInfoBean);
                            mView.initUserView(userInfoBean);
                        } else {
                            LoginUtils.delLoginInfo(XlmmApp.getmContext());
                            mView.initDrawer(null);
                            mView.initUserView(null);
                        }
                    }
                }));
    }

    @Override
    public void getUserInfoBeanChange() {
        mRxManager.add(mModel.getProfile()
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        if (userInfoBean != null) {
                            userInfoNewBean = userInfoBean;
                            mView.initUserViewChange(userInfoBean);
                        } else {
                            LoginUtils.delLoginInfo(XlmmApp.getmContext());
                            mView.initDrawer(null);
                            mView.initUserView(null);
                        }
                    }
                }));
    }

    @Override
    public void isCouPon() {
        mRxManager.add(mModel.isCouPon()
                .subscribe(isGetcoupon -> {
                    if (null != isGetcoupon) {
                        mView.initShowCoiuponWindow(isGetcoupon);
                    }
                }, Throwable::printStackTrace));
    }

    @Override
    public void getCartsNum() {
        mRxManager.add(mModel.getCartsNum()
                .subscribe(cartsNumResultBean -> {
                    mView.iniCartsNum(cartsNumResultBean);
                }, Throwable::printStackTrace));
    }

    @Override
    public void getPortalBean(SwipeRefreshLayout swipeRefreshLayout) {
        mRxManager.add(mModel.getPortalBean()
                .subscribe(new ServiceResponse<PortalBean>() {
                    @Override
                    public void onNext(PortalBean portalBean) {
                        if (null != portalBean) {
                            JUtils.Log("MainPresenter", portalBean.toString());
                            mView.refreshView();
                            mView.initSliderLayout(portalBean);

                            mView.initCategory(portalBean);

                            mView.initBrand(portalBean);

                            mView.initActivity(portalBean);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        ((MainActivity) mView).hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        JUtils.Toast("数据加载有误,请下拉刷新!");
                        JUtils.Log("MainPresenter", "    " + e.getClass().getName());
                        ((MainActivity) mView).hideIndeterminateProgressDialog();
                    }
                }));
    }

    @Override
    public void getAddressVersionAndUrl() {
        mRxManager.add(mModel.getAddressVersionAndUrl()
                .subscribe(addressDownloadResultBean -> {
                    if (null != addressDownloadResultBean) {
                        mView.downLoaAddressFile(addressDownloadResultBean);
                    }
                }, Throwable::printStackTrace));
    }

    @Override
    public void getCategoryDown() {
        mRxManager.add(mModel.getCategoryDown()
                .subscribe(categoryDownBean -> {
                    if (categoryDownBean != null) {
                        mView.downCategoryFile(categoryDownBean);
                    }
                }, Throwable::printStackTrace));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getVersion() {
        mRxManager.add(mModel.getVersion().subscribe(versionBean -> {
            if (versionBean != null) {
                mView.checkVersion(versionBean.getVersion_code(),
                        ("最新版本:" + versionBean.getVersion() + "\n\n更新内容:\n" + versionBean.getMemo()),
                        versionBean.getDownload_link(), versionBean.isAuto_update());
            }
        }, Throwable::printStackTrace));
    }

    @Override
    public void getTopic() {
        mRxManager.add(mModel.getTopic()
                .subscribe(userTopic -> {
                    mView.setTopic(userTopic);
                }, Throwable::printStackTrace));
    }
}
