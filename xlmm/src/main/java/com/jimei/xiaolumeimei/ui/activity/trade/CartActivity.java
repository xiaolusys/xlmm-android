package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.ScrollLinearLayoutManager;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/14.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  List<String> ids = new ArrayList<>();
  @Bind(R.id.carts_recyclerview) RecyclerView cartsRecyclerview;
  @Bind(R.id.confirm) Button confirmTrade;
  @Bind(R.id.total_price) TextView totalPrice;
  @Bind(R.id.go_main) Button goMain;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.empty_content) RelativeLayout emptyContent;
  private TextView totalPrice_all_1;
  private TextView extra_price_a, haicha;
  private CartsAdapetr mCartsAdapetr;
  private double total_price;
  private List<CartsinfoBean> mList;
  private double extra_price;
  private View view;

  @Override protected void setListener() {
    confirmTrade.setOnClickListener(this);
  }

  @Override protected void initData() {
    //model.getCartsList()
    //    .subscribeOn(Schedulers.io())
    //    .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
    //      @Override public void onNext(List<CartsinfoBean> cartsinfoBeans) {
    //
    //        if ((cartsinfoBeans != null) && (cartsinfoBeans.size() > 0)) {
    //
    //          mCartsAdapetr.update(cartsinfoBeans);
    //          for (int i = 0; i < cartsinfoBeans.size(); i++) {
    //            ids.add(cartsinfoBeans.get(i).getId());
    //          }
    //
    //          StringBuilder sb = new StringBuilder();
    //          String s = null;
    //          if (ids.size() > 0) {
    //            s = apendString(sb);
    //          }
    //
    //          model.getCartsInfoList(s)
    //              .subscribeOn(Schedulers.newThread())
    //              .subscribe(new ServiceResponse<CartsPayinfoBean>() {
    //                @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
    //                  super.onNext(cartsPayinfoBean);
    //                  if (cartsPayinfoBean != null) {
    //
    //                    total_price = cartsPayinfoBean.getTotalFee();
    //                    totalPrice.setText("¥" + total_price);
    //                    totalPrice_all_1.setText("总金额¥" + total_price);
    //                    if (total_price < 150) {
    //                      extra_price = 150 - total_price;
    //                      extra_price_a.setText("还差" + extra_price + "元");
    //                    } else {
    //                      haicha.setVisibility(View.INVISIBLE);
    //                    }
    //                  }
    //                }
    //              });
    //        } else {
    //          emptyContent.setVisibility(View.VISIBLE);
    //          goMain.setOnClickListener(new View.OnClickListener() {
    //            @Override public void onClick(View v) {
    //              startActivity(new Intent(CartActivity.this, MainActivity.class));
    //              finish();
    //            }
    //          });
    //        }
    //      }
    //    });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.carts_actvivity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    view = getLayoutInflater().inflate(R.layout.footer, null);

    totalPrice_all_1 = (TextView) view.findViewById(R.id.total_price_all_1);
    extra_price_a = (TextView) view.findViewById(R.id.extra_price_a);
    haicha = (TextView) view.findViewById(R.id.haicha);

    cartsRecyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    cartsRecyclerview.setLayoutManager(new ScrollLinearLayoutManager(this));

    mCartsAdapetr = new CartsAdapetr();

    cartsRecyclerview.setAdapter(mCartsAdapetr);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.confirm:
        Intent intent = new Intent(CartActivity.this, CartsPayInfoActivity.class);
        StringBuilder sb = new StringBuilder();
        if (ids.size() > 0) {
          String s = apendString(sb);

          intent.putExtra("ids", s);
          JUtils.Log("CartActivity", s);

          startActivity(intent);
        }

        break;
    }
  }

  @NonNull private String apendString(StringBuilder sb) {
    for (int i = 0; i < ids.size(); i++) {
      sb.append(ids.get(i)).append(",");
    }
    String str = new String(sb);

    return str.substring(0, str.length() - 1);
  }

  @Override protected void onResume() {
    super.onResume();

    Subscription subscription = CartsModel.getInstance()
        .getCartsList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
          @Override public void onNext(List<CartsinfoBean> cartsinfoBeans) {

            if ((cartsinfoBeans != null) && (cartsinfoBeans.size() > 0)) {

              mCartsAdapetr.updateWithClear(cartsinfoBeans);

              if (ids != null) {
                ids.clear();
              }
              for (int i = 0; i < cartsinfoBeans.size(); i++) {
                ids.add(cartsinfoBeans.get(i).getId());
              }

              StringBuilder sb = new StringBuilder();
              String s = null;
              if (ids.size() > 0) {
                s = apendString(sb);
              }

              Subscription subscription1 = CartsModel.getInstance()
                  .getCartsInfoList(s)
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                      super.onNext(cartsPayinfoBean);
                      if (cartsPayinfoBean != null) {

                        total_price = cartsPayinfoBean.getTotalFee();
                        totalPrice.setText("¥" + total_price);
                        totalPrice_all_1.setText("总金额¥" + total_price);
                        if (total_price < 150) {
                          extra_price = 150 - total_price;
                          extra_price_a.setText("还差" + extra_price + "元");
                        } else {
                          haicha.setVisibility(View.INVISIBLE);
                        }
                      }
                    }
                  });
              addSubscription(subscription1);
            } else {
              emptyContent.setVisibility(View.VISIBLE);
              goMain.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                  startActivity(new Intent(CartActivity.this, MainActivity.class));
                  finish();
                }
              });
            }
          }
        });
    addSubscription(subscription);
  }

  //内部Recycler Adapter类
  class CartsAdapetr extends RecyclerView.Adapter<CartsAdapetr.CartsVH> {

    public CartsAdapetr() {
      mList = new ArrayList<>();
    }

    @Override public CartsVH onCreateViewHolder(ViewGroup parent, int viewType) {

      View view;

      view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_carts, parent, false);
      AutoUtils.autoSize(view);
      return new CartsVH(view);
    }

    public void update(List<CartsinfoBean> list) {

      mList.addAll(list);
      notifyDataSetChanged();
    }

    public void updateWithClear(List<CartsinfoBean> list) {
      mList.clear();
      mList.addAll(list);
      notifyDataSetChanged();
    }

    @Override public void onBindViewHolder(final CartsVH holder, int position) {

      if (mList == null || mList.size() == 0) {
        return;
      }

      CartsinfoBean cartsinfoBean = mList.get(position);
      holder.title.setText(cartsinfoBean.getTitle());
      holder.skuName.setText("尺码:" + cartsinfoBean.getSkuName());
      //holder.color.setText(cartsinfoBean.get);
      holder.price1.setText(
          "¥" + (float) (Math.round(cartsinfoBean.getPrice() * 100)) / 100);
      holder.price2.setText(
          "/¥" + (float) (Math.round(cartsinfoBean.getStdSalePrice() * 100)) / 100);
      holder.count.setText(cartsinfoBean.getNum() + "");

      String headImg = cartsinfoBean.getPicPath();

      ViewUtils.loadImgToImgView(mContext, holder.cartImage, headImg);

      holder.add.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

          Subscription subscription = CartsModel.getInstance()
              .plus_product_carts(cartsinfoBean.getId())
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<ResponseBody>() {
                @Override public void onNext(ResponseBody responseBody) {
                  super.onNext(responseBody);
                  try {
                    String s = responseBody.string();
                    JUtils.Log("CartsAdapter", s);
                    getCartsInfo(position);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }

                @Override public void onError(Throwable e) {
                  super.onError(e);
                  JUtils.Toast("该商品库存已经不足");
                }
              });
          addSubscription(subscription);
        }
      });

      if (mList.size() == 1) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            if (Integer.parseInt(cartsinfoBean.getNum()) > 1) {

              Subscription subscription = CartsModel.getInstance()
                  .minus_product_carts(cartsinfoBean.getId())
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<ResponseBody>() {
                    @Override public void onNext(ResponseBody responseBody) {
                      super.onNext(responseBody);

                      getCartsInfo(position);
                    }
                  });
              addSubscription(subscription);
            } else {
              new MaterialDialog.Builder(CartActivity.this).
                  title("删除商品").
                  content("您确定要删除吗？").
                  positiveText("确定").
                  negativeText("取消").
                  callback(new MaterialDialog.ButtonCallback() {
                    @Override public void onPositive(MaterialDialog dialog) {
                      Subscription subscription = CartsModel.getInstance()
                          .delete_carts(cartsinfoBean.getId())
                          .subscribeOn(Schedulers.io())
                          .subscribe(new ServiceResponse<ResponseBody>() {
                            @Override public void onNext(ResponseBody responseBody) {
                              super.onNext(responseBody);
                              removeAt(position);

                              getCartsInfo();
                            }
                          });
                      addSubscription(subscription);
                      dialog.dismiss();

                      startActivity(new Intent(CartActivity.this, CartActivity.class));
                      finish();
                    }

                    @Override public void onNegative(MaterialDialog dialog) {
                      dialog.dismiss();
                    }
                  }).show();
            }
          }
        });
      } else {
        holder.delete.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            if (Integer.parseInt(cartsinfoBean.getNum()) > 1) {

              Subscription subscription = CartsModel.getInstance()
                  .minus_product_carts(cartsinfoBean.getId())
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<ResponseBody>() {
                    @Override public void onNext(ResponseBody responseBody) {
                      super.onNext(responseBody);

                      getCartsInfo(position);
                    }
                  });
              addSubscription(subscription);
            } else {
              oneDelete();
            }
          }

          private void oneDelete() {
            new MaterialDialog.Builder(CartActivity.this).
                title("删除商品").
                content("您确定要删除吗？").
                positiveText("确定").
                negativeText("取消").
                callback(new MaterialDialog.ButtonCallback() {
                  @Override public void onPositive(MaterialDialog dialog) {
                    Subscription subscription = CartsModel.getInstance()
                        .delete_carts(cartsinfoBean.getId())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<ResponseBody>() {
                          @Override public void onNext(ResponseBody responseBody) {
                            super.onNext(responseBody);
                            removeAt(position);

                            getCartsInfo();
                          }
                        });
                    addSubscription(subscription);
                    dialog.dismiss();
                  }

                  @Override public void onNegative(MaterialDialog dialog) {
                    dialog.dismiss();
                  }
                }).show();
          }
        });
      }
    }

    private void getCartsInfo(int position) {
      Subscription subscription = CartsModel.getInstance()
          .getCartsList()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
            @Override public void onNext(List<CartsinfoBean> list) {
              super.onNext(list);
              mList.clear();
              mList.addAll(list);

              StringBuilder sb = new StringBuilder();
              String s = null;
              if (ids.size() > 0) {
                s = apendString(sb);
              }

              Subscription subscription1 = CartsModel.getInstance()
                  .getCartsInfoList(s)
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                      super.onNext(cartsPayinfoBean);
                      if (cartsPayinfoBean != null) {

                        total_price = cartsPayinfoBean.getTotalFee();

                        totalPrice.setText("¥" + total_price);
                        totalPrice_all_1.setText("总金额¥" + total_price);

                        if (total_price < 150) {
                          extra_price = 150 - total_price;
                          extra_price_a.setText("还差" + extra_price + "元");
                        } else {
                          haicha.setVisibility(View.INVISIBLE);
                        }
                      }
                    }
                  });

              addSubscription(subscription1);
              notifyItemChanged(position);
            }
          });
      addSubscription(subscription);
    }

    private void getCartsInfo() {
      Subscription subscription = CartsModel.getInstance()
          .getCartsList()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
            @Override public void onNext(List<CartsinfoBean> list) {
              super.onNext(list);
              mList.clear();
              mList.addAll(list);

              StringBuilder sb = new StringBuilder();
              String s = null;
              if (ids.size() > 0) {
                s = apendString(sb);
              }

              CartsModel.getInstance()
                  .getCartsInfoList(s)
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                      super.onNext(cartsPayinfoBean);
                      if (cartsPayinfoBean != null) {

                        total_price = cartsPayinfoBean.getTotalFee();

                        totalPrice.setText("¥" + total_price);
                        totalPrice_all_1.setText("总金额¥" + total_price);

                        if (total_price < 150) {
                          extra_price = 150 - total_price;
                          extra_price_a.setText("还差" + extra_price + "元");
                        } else {
                          haicha.setVisibility(View.INVISIBLE);
                        }
                      }
                    }
                  });
            }
          });
      addSubscription(subscription);
    }

    @Override public int getItemCount() {
      return mList == null ? 0 : mList.size();
    }

    //删除某一项
    public void removeAt(int position) {
      mList.remove(position);
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, mList.size());
    }

    class CartsVH extends RecyclerView.ViewHolder {
      int id = R.layout.item_carts;
      @Bind(R.id.cart_image) ImageView cartImage;
      @Bind(R.id.title) TextView title;
      @Bind(R.id.sku_name) TextView skuName;
      @Bind(R.id.price1) TextView price1;
      @Bind(R.id.price2) TextView price2;
      @Bind(R.id.delete) ImageView delete;
      @Bind(R.id.count) TextView count;
      @Bind(R.id.add) ImageView add;

      public CartsVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
      }
    }
  }
}
