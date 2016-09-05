package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.support.v4.widget.SwipeRefreshLayout;

import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.RxUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.Date;

import retrofit2.Response;

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
                .subscribe(new ServiceResponse<Response<UserInfoBean>>() {
                    @Override
                    public void onNext(Response<UserInfoBean> userInfoBeanResponse) {
                        if (null != userInfoBeanResponse) {
                            if (userInfoBeanResponse.isSuccessful()) {
                                UserInfoBean userInfoBean = userInfoBeanResponse.body();
                                userInfoNewBean = userInfoBean;
                                mView.initDrawer(userInfoBean);
                                mView.initUserView(userInfoBean);
                            } else if (userInfoBeanResponse.code() == 403) {
                                LoginUtils.delLoginInfo(XlmmApp.getmContext());
                                mView.initDrawer(null);
                                mView.initUserView(null);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    public void getUserInfoBeanChange() {
        mRxManager.add(mModel.getProfile()
                .subscribe(new ServiceResponse<Response<UserInfoBean>>() {
                    @Override
                    public void onNext(Response<UserInfoBean> userInfoBeanResponse) {
                        if (null != userInfoBeanResponse) {
                            if (userInfoBeanResponse.isSuccessful()) {
                                UserInfoBean userInfoBean = userInfoBeanResponse.body();
                                userInfoNewBean = userInfoBean;
                                mView.initUserViewChange(userInfoBean);
                            } else if (userInfoBeanResponse.code() == 403) {
                                LoginUtils.delLoginInfo(XlmmApp.getmContext());
                                mView.initDrawer(null);
                                mView.initUserView(null);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    public void isCouPon() {
        mRxManager.add(mModel.isCouPon()
                .subscribe(isGetcouponResponse -> {
                    if (null != isGetcouponResponse) {
                        mView.initShowCoiuponWindow(isGetcouponResponse);
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

                            mView.initPost(portalBean);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        mView.showNetworkError();
                        JUtils.Log("MainPresenter", "    " + e.getClass().getName());
                        JUtils.ToastLong("数据加载有误,检查网络设置，请重新加载");
                    }
                }));
    }

    @Override
    public void getUsercoupons(String template_id) {
        mRxManager.add(mModel.getUsercoupons(template_id)
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(responseBody -> {
                    if (null != responseBody) {
                        mView.clickGetCounpon(responseBody);
                    }
                }, Throwable::printStackTrace));
    }

    @Override
    public long calcLefttowTime(long crtTime) {
        long left = 0;
        Date now = new Date();
        try {
            if (crtTime * 10000 - now.getTime() > 0) {
                left = crtTime * 10000 - now.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return left;
    }

    @Override
    public void getAddressVersionAndUrl() {
        mRxManager.add(mModel.getAddressVersionAndUrl()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(addressDownloadResultBean -> {

                    if (null != addressDownloadResultBean) {
                        mView.downLoaAddressFile(addressDownloadResultBean);
                    }
                }, Throwable::printStackTrace));
    }

    @Override
    public void getCategoryDown() {
        mRxManager.add(mModel.getCategoryDown()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
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
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(userTopic -> {
                    mView.setTopic(userTopic);
                }, Throwable::printStackTrace));
    }
}
