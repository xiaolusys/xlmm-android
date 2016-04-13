package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MMHavaChooseResultBean;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.BaseAdapter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/2.
 */
public class MMHaveChooseAdapter
    extends BaseAdapter<ShopProductBean.ResultsBean, MMHaveChooseAdapter.MMHaveChooseVH> {

  private static final String TAG = MMHaveChooseAdapter.class.getSimpleName();
  private MaterialDialog materialDialog;

  public MMHaveChooseAdapter(Context context, List<ShopProductBean.ResultsBean> data) {
    super(context, data);
    JUtils.Log(TAG, "  data size  " + data.size());
  }

  @Override public MMHaveChooseVH onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MMHaveChooseVH(
        layoutInflater.inflate(R.layout.item_havechoose, parent, false));
  }

  @Override public void onBindViewHolder(MMHaveChooseVH holder, int position) {
    super.onBindViewHolder(holder, position);
    JUtils.Log(TAG, "onbindviewholder  data size " + data.size());
    ShopProductBean.ResultsBean resultsBean = data.get(position);
    holder.bindData(resultsBean);
  }

  @Override public void onItemDismiss(int position) {

    MMProductModel.getInstance()
        .removeProFromShop(data.get(position).getProduct() + "")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MMHavaChooseResultBean>() {
          @Override public void onNext(MMHavaChooseResultBean responseBody) {
            if (responseBody != null) {
              if (responseBody.getCode() == 0) {
              } else {
                JUtils.Toast("删除失败,请重新尝试");
              }
            }
          }
        });
    super.onItemDismiss(position);
  }

  @Override public void onItemMove(int fromPosition, int targetPosition) {

    JUtils.Log(TAG, fromPosition + "  " + targetPosition);
    super.onItemMove(fromPosition, targetPosition);
    MMProductModel.getInstance()
        .changeProPosition(data.get(fromPosition).getId() + "",
            data.get(targetPosition).getId() + "")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MMHavaChooseResultBean>() {
          @Override public void onCompleted() {
            super.onCompleted();
            JUtils.Log(TAG, "onCompleted()");
          }

          @Override public void onNext(MMHavaChooseResultBean responseBody) {
            JUtils.Log(TAG, "next");
            if (responseBody != null) {
              JUtils.Log(TAG, responseBody.toString());
              if (responseBody.getCode() == 0) {

                //                                notifyDataSetChanged();
                //MMProductModel.getInstance()
                //    .getShopProduct("1")
                //    .subscribeOn(Schedulers.io())
                //    .subscribe(new ServiceResponse<ShopProductBean>() {
                //      @Override public void onNext(ShopProductBean shopProductBean) {
                //        if (shopProductBean != null
                //            && shopProductBean.getResults() != null) {
                //          data = shopProductBean.getResults();
                //          notifyDataSetChanged();
                //        }
                //      }
                //    });
              } else {
                JUtils.Toast("交换失败");
              }
            }
          }
        });
  }

  public void showIndeterminateProgressDialog(boolean horizontal) {
    materialDialog = new MaterialDialog.Builder(mContext)
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

  class MMHaveChooseVH extends RecyclerView.ViewHolder {

    int id = R.layout.item_havechoose;
    @Bind(R.id.image_chooselist) ImageView imageChooselist;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.agent_price) TextView agentPrice;
    @Bind(R.id.std_sale_price) TextView stdSalePrice;
    //        @Bind(R.id.rebet_amount)
    //        TextView rebetAmount;
    //        @Bind(R.id.lock_num)
    //        TextView lockNum;

    public MMHaveChooseVH(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
      AutoUtils.auto(itemView);
    }

    public void bindData(ShopProductBean.ResultsBean data) {
      name.setText(data.getName());
      ViewUtils.loadImgToImgView(mContext, imageChooselist, data.getPic_path());
      agentPrice.setText("¥" + (float) (Math.round(data.getAgent_price() * 100)) / 100);
      stdSalePrice.setText(
          "/¥" + (float) (Math.round(data.getStd_sale_price() * 100)) / 100);
      //            rebetAmount.setText(data.getSale_num() );
      //            lockNum.setText(mmChooselistBean.getSaleNumDes());

    }
  }
}
