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
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CartsHisBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.MyAnimator;
import com.jimei.xiaolumeimei.widget.ScrollLinearLayoutManager;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/14.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  private static final String TAG = "CartActivity";
  List<String> ids = new ArrayList<>();
  @Bind(R.id.carts_recyclerview) RecyclerView cartsRecyclerview;
  @Bind(R.id.cartshis_recyclerview) RecyclerView cartshisRecyclerview;
  @Bind(R.id.confirm) Button confirmTrade;
  @Bind(R.id.total_price) TextView totalPrice;
  @Bind(R.id.tv_show) TextView tvShow;
  @Bind(R.id.go_main) Button goMain;
  @Bind(R.id.go_main1) Button goMain1;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.empty_content) RelativeLayout emptyContent;
  @Bind(R.id.empty_content1) RelativeLayout emptyContent1;
  @Bind(R.id.showline) View showLine;
  private TextView totalPrice_all_1;
  private CartsAdapetr mCartsAdapetr;
  private CartsHisAdapetr mCartsHisAdapetr;
  private double total_price;
  private List<CartsinfoBean> mList;
  private List<CartsinfoBean> mListhis;
  private View view;

  @Override protected void setListener() {
    confirmTrade.setOnClickListener(this);
  }

  @Override protected void initData() {

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
    cartsRecyclerview.setNestedScrollingEnabled(false);
    cartsRecyclerview.setHasFixedSize(false);
    cartsRecyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    ScrollLinearLayoutManager scrollLinearLayoutManager =
        new ScrollLinearLayoutManager(this);
    scrollLinearLayoutManager.setAutoMeasureEnabled(true);
    cartsRecyclerview.setLayoutManager(scrollLinearLayoutManager);
    cartsRecyclerview.setItemAnimator(new MyAnimator());
    mCartsAdapetr = new CartsAdapetr();

    cartsRecyclerview.setAdapter(mCartsAdapetr);

    cartshisRecyclerview.setNestedScrollingEnabled(false);
    cartshisRecyclerview.setHasFixedSize(false);

    cartshisRecyclerview.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    ScrollLinearLayoutManager scrollLinearLayoutManager1 =
        new ScrollLinearLayoutManager(this);
    scrollLinearLayoutManager1.setAutoMeasureEnabled(true);
    cartshisRecyclerview.setLayoutManager(scrollLinearLayoutManager1);
    cartshisRecyclerview.setItemAnimator(new MyAnimator());
    mCartsHisAdapetr = new CartsHisAdapetr();

    cartshisRecyclerview.setAdapter(mCartsHisAdapetr);
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
          finish();
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

              Subscription subscribe = CartsModel.getInstance()
                  .getCartsHisList()
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
                    @Override public void onNext(List<CartsinfoBean> cartsinfoBeen) {
                      if (null != cartsinfoBeen) {
                        tvShow.setVisibility(View.VISIBLE);
                        showLine.setVisibility(View.VISIBLE);
                        mCartsHisAdapetr.updateWithClear(cartsinfoBeen);
                      } else {

                         /* emptyContent.setVisibility(View.VISIBLE);
                          goMain.setOnClickListener(new View.OnClickListener() {
                            @Override public void onClick(View v) {
                              startActivity(new Intent(CartActivity.this, MainActivity.class));
                              finish();
                            }
                          });*/

                        tvShow.setVisibility(View.INVISIBLE);
                        showLine.setVisibility(View.INVISIBLE);
                      }
                    }
                  });
              addSubscription(subscribe);

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
                      }
                    }
                  });
              addSubscription(subscription1);
            } else {

              emptyContent1.setVisibility(View.VISIBLE);
              goMain1.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                  startActivity(new Intent(CartActivity.this, MainActivity.class));
                  finish();
                }
              });

              Subscription subscribe = CartsModel.getInstance()
                  .getCartsHisList()
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
                    @Override public void onNext(List<CartsinfoBean> cartsinfoBeen) {
                      if (null != cartsinfoBeen) {
                        tvShow.setVisibility(View.VISIBLE);
                        showLine.setVisibility(View.VISIBLE);
                        mCartsHisAdapetr.updateWithClear(cartsinfoBeen);
                      } else {

                        emptyContent.setVisibility(View.VISIBLE);
                        goMain.setOnClickListener(new View.OnClickListener() {
                          @Override public void onClick(View v) {
                            startActivity(
                                new Intent(CartActivity.this, MainActivity.class));
                            finish();
                          }
                        });

                        tvShow.setVisibility(View.INVISIBLE);
                        showLine.setVisibility(View.INVISIBLE);
                      }
                    }
                  });
              addSubscription(subscribe);
            }

            //else {
            //  emptyContent.setVisibility(View.VISIBLE);
            //  goMain.setOnClickListener(new View.OnClickListener() {
            //    @Override public void onClick(View v) {
            //      startActivity(new Intent(CartActivity.this, MainActivity.class));
            //      finish();
            //    }
            //  });
            //}

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

      CartsinfoBean cartsinfoBean = mList.get(holder.getAdapterPosition());
      holder.title.setText(cartsinfoBean.getTitle());
      holder.skuName.setText("尺码:" + cartsinfoBean.getSkuName());
      //holder.color.setText(cartsinfoBean.get);
      holder.price1.setText(
          "¥" + (float) (Math.round(cartsinfoBean.getPrice() * 100)) / 100);
      holder.price2.setText(
          "/¥" + (float) (Math.round(cartsinfoBean.getStdSalePrice() * 100)) / 100);
      holder.count.setText(cartsinfoBean.getNum() + "");

      String headImg = cartsinfoBean.getPicPath();

      //if (!headImg.equals(holder.cartImage.getTag(holder.cartImage.getId()))) {
      ViewUtils.loadImgToImgView(mContext, holder.cartImage, headImg);
      //}

      //holder.cartImage.setTag(holder.cartImage.getId(), headImg);

      holder.add.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

          Subscription subscription = CartsModel.getInstance()
              .plus_product_carts(cartsinfoBean.getId())
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<Response<CodeBean>>() {
                @Override public void onNext(Response<CodeBean> responseBody) {

                  if (null != responseBody) {

                    JUtils.Log(TAG, "json= "
                        + responseBody.message()
                        + "  "
                        + responseBody.toString()
                        + "  "
                        + responseBody.code()
                        + "  "
                        + responseBody.isSuccessful());

                    String code = String.valueOf(responseBody.code());
                    if (code.startsWith("2")) {
                      if (null != responseBody.body()) {
                        CodeBean codeBean = responseBody.body();
                        if (codeBean.getCode() == 0) {
                          getCartsInfo(holder.getAdapterPosition());
                        }
                      }
                    } else {
                      if (null != responseBody.body()) {
                        CodeBean codeBean = responseBody.body();
                        JUtils.Toast(codeBean.getInfo());
                      }
                    }
                  }
                }

                @Override public void onError(Throwable e) {
                  super.onError(e);
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
                  .subscribe(new ServiceResponse<Response<CodeBean>>() {
                    @Override public void onNext(Response<CodeBean> responseBody) {
                      if (null != responseBody) {
                        try {
                          JUtils.Log(TAG, "json="
                              + responseBody.message()
                              + responseBody.toString()
                              + responseBody.errorBody().string()
                              + responseBody.isSuccessful());
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                        String code = String.valueOf(responseBody.code());
                        if (code.startsWith("2")) {
                          if (null != responseBody.body()) {
                            CodeBean codeBean = responseBody.body();
                            if (codeBean.getCode() == 0) {
                              Subscription subscribe = CartsModel.getInstance()
                                  .getCartsHisList()
                                  .subscribeOn(Schedulers.io())
                                  .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
                                    @Override public void onNext(
                                        List<CartsinfoBean> cartsinfoBeen) {
                                      if (null != cartsinfoBeen) {
                                        mListhis = cartsinfoBeen;
                                        mCartsHisAdapetr.notifyDataSetChanged();
                                      } else {
                                        tvShow.setVisibility(View.INVISIBLE);
                                        showLine.setVisibility(View.INVISIBLE);
                                      }
                                    }
                                  });
                              addSubscription(subscribe);

                              getCartsInfo(holder.getAdapterPosition());
                              //notifyItemChanged(holder.getAdapterPosition());
                            }
                          }
                        } else {
                          if (null != responseBody.body()) {
                            CodeBean codeBean = responseBody.body();
                            JUtils.Toast(codeBean.getInfo());
                          }
                        }
                      }
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
                          .subscribe(new ServiceResponse<Response<CodeBean>>() {
                            @Override
                            public void onNext(Response<CodeBean> responseBody) {
                              if (null != responseBody) {
                                try {
                                  JUtils.Log(TAG, "json="
                                      + responseBody.message()
                                      + responseBody.toString()
                                      + responseBody.errorBody().string()
                                      + responseBody.isSuccessful());
                                } catch (IOException e) {
                                  e.printStackTrace();
                                }
                                String code = String.valueOf(responseBody.code());
                                if (code.startsWith("2")) {
                                  if (null != responseBody.body()) {
                                    CodeBean codeBean = responseBody.body();
                                    if (codeBean.getCode() == 0) {
                                      Subscription subscribe = CartsModel.getInstance()
                                          .getCartsHisList()
                                          .subscribeOn(Schedulers.io())
                                          .subscribe(
                                              new ServiceResponse<List<CartsinfoBean>>() {
                                                @Override public void onNext(
                                                    List<CartsinfoBean> cartsinfoBeen) {
                                                  if (null != cartsinfoBeen) {
                                                    mListhis = cartsinfoBeen;
                                                    mCartsHisAdapetr.notifyDataSetChanged();
                                                  } else {
                                                    tvShow.setVisibility(View.INVISIBLE);
                                                    showLine.setVisibility(
                                                        View.INVISIBLE);
                                                  }
                                                }
                                              });
                                      addSubscription(subscribe);
                                      removeAt(holder.getAdapterPosition());
                                      getCartsInfo();

                                      dialog.dismiss();

                                      startActivity(new Intent(CartActivity.this,
                                          CartActivity.class));
                                      finish();
                                    }
                                  }
                                } else {
                                  if (null != responseBody.body()) {
                                    CodeBean codeBean = responseBody.body();
                                    JUtils.Toast(codeBean.getInfo());
                                  }
                                }
                              }
                            }
                          });
                      addSubscription(subscription);
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
                  .subscribe(new ServiceResponse<Response<CodeBean>>() {
                    @Override public void onNext(Response<CodeBean> responseBody) {
                      if (null != responseBody) {
                        JUtils.Log(TAG, "json= "
                            + responseBody.message()
                            + "  "
                            + responseBody.toString()
                            + "  "
                            + responseBody.code()
                            + "  "
                            + responseBody.isSuccessful());
                        String code = String.valueOf(responseBody.code());
                        if (code.startsWith("2")) {
                          if (null != responseBody.body()) {
                            CodeBean codeBean = responseBody.body();
                            if (codeBean.getCode() == 0) {
                              getCartsInfo(holder.getAdapterPosition());
                              //notifyItemChanged(holder.getAdapterPosition());
                            }
                          }
                        } else {
                          if (null != responseBody.body()) {
                            CodeBean codeBean = responseBody.body();
                            JUtils.Toast(codeBean.getInfo());
                          }
                        }
                      }
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
                        .subscribe(new ServiceResponse<Response<CodeBean>>() {
                          @Override public void onNext(Response<CodeBean> responseBody) {
                            if (null != responseBody) {

                              JUtils.Log(TAG, "json= "
                                  + responseBody.message()
                                  + "  "
                                  + responseBody.toString()
                                  + "  "
                                  + responseBody.code()
                                  + "  "
                                  + responseBody.isSuccessful());

                              String code = String.valueOf(responseBody.code());
                              if (code.startsWith("2")) {
                                if (null != responseBody.body()) {
                                  CodeBean codeBean = responseBody.body();
                                  if (codeBean.getCode() == 0) {

                                    removeAt(holder.getAdapterPosition());
                                    Subscription subscribe = CartsModel.getInstance()
                                        .getCartsHisList()
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(
                                            new ServiceResponse<List<CartsinfoBean>>() {
                                              @Override public void onNext(
                                                  List<CartsinfoBean> cartsinfoBeen) {
                                                if (null != cartsinfoBeen) {
                                                  mListhis = cartsinfoBeen;
                                                  mCartsHisAdapetr.notifyDataSetChanged();
                                                } else {
                                                  tvShow.setVisibility(View.INVISIBLE);
                                                  showLine.setVisibility(View.INVISIBLE);
                                                }
                                              }
                                            });
                                    addSubscription(subscribe);
                                    getCartsInfo();

                                    dialog.dismiss();
                                  }
                                }
                              } else {
                                if (null != responseBody.body()) {
                                  CodeBean codeBean = responseBody.body();
                                  JUtils.Toast(codeBean.getInfo());
                                }
                              }
                            }
                          }
                        });
                    addSubscription(subscription);
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
              if (list == null) {
                return;
              }
              mList.clear();
              mList.addAll(list);

              if (ids != null) {
                ids.clear();
              }
              for (int i = 0; i < list.size(); i++) {
                ids.add(list.get(i).getId());
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
                      if (cartsPayinfoBean != null) {
                        total_price = cartsPayinfoBean.getTotalFee();
                        totalPrice.setText("¥" + total_price);
                        totalPrice_all_1.setText("总金额¥" + total_price);
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
              if (list == null) {
                return;
              }
              mList.clear();
              mList.addAll(list);

              if (ids != null) {
                ids.clear();
              }
              for (int i = 0; i < list.size(); i++) {
                ids.add(list.get(i).getId());
              }

              StringBuilder sb = new StringBuilder();
              String s = null;
              if (ids.size() > 0) {
                s = apendString(sb);
              }

              Subscription subscribe = CartsModel.getInstance()
                  .getCartsInfoList(s)
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                      super.onNext(cartsPayinfoBean);
                      if (cartsPayinfoBean != null) {

                        total_price = cartsPayinfoBean.getTotalFee();

                        totalPrice.setText("¥" + total_price);
                        totalPrice_all_1.setText("总金额¥" + total_price);
                      }
                    }
                  });
              addSubscription(subscribe);
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
      //notifyItemRangeChanged(position, mList.size());
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
        AutoUtils.auto(itemView);
        ButterKnife.bind(this, itemView);
      }
    }
  }

  class CartsHisAdapetr extends RecyclerView.Adapter<CartsHisAdapetr.CartsHisVH> {

    public CartsHisAdapetr() {
      mListhis = new ArrayList<>();
    }

    @Override public CartsHisVH onCreateViewHolder(ViewGroup parent, int viewType) {

      View view;

      view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_hiscarts, parent, false);
      return new CartsHisVH(view);
    }

    @Override public void onBindViewHolder(CartsHisVH holder, int position) {
      if (mListhis == null || mListhis.size() == 0) {
        return;
      }

      CartsinfoBean cartsinfoBean = mListhis.get(holder.getAdapterPosition());
      holder.title.setText(cartsinfoBean.getTitle());
      holder.skuName.setText("尺码:" + cartsinfoBean.getSkuName());
      //holder.color.setText(cartsinfoBean.get);
      holder.price1.setText(
          "¥" + (float) (Math.round(cartsinfoBean.getPrice() * 100)) / 100);
      holder.price2.setText(
          "/¥" + (float) (Math.round(cartsinfoBean.getStdSalePrice() * 100)) / 100);

      ViewUtils.loadImgToImgView(mContext, holder.cartImage, cartsinfoBean.getPicPath());

      holder.rebuy.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Subscription subscribe = CartsModel.getInstance()
              .rebuy(cartsinfoBean.getItemId(), cartsinfoBean.getSkuId(),
                  cartsinfoBean.getId())
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<CartsHisBean>() {
                @Override public void onNext(CartsHisBean cartsHisBean) {
                  if (null != cartsHisBean) {
                    if (cartsHisBean.getCode() == 0) {
                      emptyContent1.setVisibility(View.INVISIBLE);
                      removeAt(holder.getAdapterPosition());
                      Subscription subscription = CartsModel.getInstance()
                          .getCartsList()
                          .subscribeOn(Schedulers.io())
                          .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
                            @Override
                            public void onNext(List<CartsinfoBean> cartsinfoBeen) {
                              if (cartsinfoBeen != null) {
                                mList = cartsinfoBeen;
                                mCartsAdapetr.notifyDataSetChanged();
                                getCartsInfo();
                              }
                            }
                          });
                      addSubscription(subscription);
                    }
                  }
                }
              });
          addSubscription(subscribe);
        }
      });
    }

    @Override public int getItemCount() {
      return mListhis == null ? 0 : mListhis.size();
    }

    public void update(List<CartsinfoBean> list) {
      mListhis.addAll(list);
      notifyDataSetChanged();
    }

    public void updateWithClear(List<CartsinfoBean> list) {
      mListhis.clear();
      mListhis.addAll(list);
      notifyDataSetChanged();
    }

    private void getCartsInfo() {
      Subscription subscription = CartsModel.getInstance()
          .getCartsList()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
            @Override public void onNext(List<CartsinfoBean> list) {
              if (list == null) {
                return;
              }
              //mList.clear();
              //mList.addAll(list);

              if (ids != null) {
                ids.clear();
              }
              for (int i = 0; i < list.size(); i++) {
                ids.add(list.get(i).getId());
              }

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
                      }
                    }
                  });
            }
          });
      addSubscription(subscription);
    }

    //删除某一项
    public void removeAt(int position) {
      mListhis.remove(position);
      notifyItemRemoved(position);
    }

    class CartsHisVH extends RecyclerView.ViewHolder {
      int id = R.layout.item_hiscarts;
      @Bind(R.id.cart_image) ImageView cartImage;
      @Bind(R.id.title) TextView title;
      @Bind(R.id.sku_name) TextView skuName;
      @Bind(R.id.price1) TextView price1;
      @Bind(R.id.price2) TextView price2;
      @Bind(R.id.rebuy) Button rebuy;

      public CartsHisVH(View itemView) {
        super(itemView);
        AutoUtils.auto(itemView);
        ButterKnife.bind(this, itemView);
      }
    }
  }
}
