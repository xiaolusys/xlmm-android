package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MaMaStoreAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.widget.CircleImageView;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.util.List;
import okhttp3.Call;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/15.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MaMaMyStoreActivity extends BaseSwipeBackCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.store_rcyView) RecyclerView storeRcyView;
  @Bind(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
  @Bind(R.id.loading) RotateLoading loading;
  @Bind(R.id.titleiamge) CircleImageView titleiamge;
  private MaMaStoreAdapter maMaStoreAdapter;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

    Subscription subscribe = MMProductModel.getInstance()
        .getMMStoreList()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            try {
              if (mmChooselistBeans != null) {
                maMaStoreAdapter.update(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }
        });

    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_store;
  }

  @Override protected void initViews() {
    toolbar.setTitle("小鹿妈妈de精选集");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    collapsingToolbarLayout.setTitle("小鹿妈妈de精选集");

    UserNewModel.getInstance()
        .getProfile()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean userInfoBean) {
            if (userInfoBean != null) {
              if (!TextUtils.isEmpty(userInfoBean.getThumbnail())
                  && !userInfoBean.getThumbnail().equals("")) {
                OkHttpUtils.get()
                    .url(userInfoBean.getThumbnail())
                    .build()
                    .execute(new BitmapCallback() {
                      @Override public void onError(Call call, Exception e) {

                      }

                      @Override public void onResponse(Bitmap response) {
                        if (response != null) {
                          titleiamge.setImageBitmap(response);
                        }
                      }
                    });
              }
            }
          }
        });

    initRecyclerView();
  }

  private void initRecyclerView() {
    storeRcyView.setLayoutManager(new LinearLayoutManager(this));
    storeRcyView.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    maMaStoreAdapter = new MaMaStoreAdapter(this);
    storeRcyView.setAdapter(maMaStoreAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onStop() {
    super.onStop();
    ShareSDK.stopSDK();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_store, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId() == R.id.action_eye) {

    }

    if (item.getItemId() == R.id.action_share) {

      MMProductModel.getInstance()
          .getShareShopping()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<MMShoppingBean>() {
            @Override public void onNext(MMShoppingBean mmShoppingBean) {
              if (null != mmShoppingBean) {
                mmShoppingBean.getShopInfo().getShopLink();
                share_shopping();
              }
            }
          });
    }

    return super.onOptionsItemSelected(item);
  }

  private void share_shopping() {
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    //oks.setTitle(shareProductBean.getTitle());
    //// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    //oks.setTitleUrl(shareProductBean.getShareLink());
    //// text是分享文本，所有平台都需要这个字段
    //oks.setText(shareProductBean.getDesc());
    //// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    ////oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
    ////oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
    //oks.setImageUrl(shareProductBean.getShareImg());
    //oks.setUrl(shareProductBean.getShareLink());

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
