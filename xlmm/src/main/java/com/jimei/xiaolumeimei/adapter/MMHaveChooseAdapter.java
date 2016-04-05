package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.dragrecyclerview.BaseAdapter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(http:www.itxuye.com) on 16/4/2.
 */
public class MMHaveChooseAdapter extends BaseAdapter<ShopProductBean.ResultsBean, MMHaveChooseAdapter.MMHaveChooseVH> {


    private MaterialDialog materialDialog;

    public MMHaveChooseAdapter(Context context, List<ShopProductBean.ResultsBean> data) {
        super(context, data);
    }

    @Override
    public MMHaveChooseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MMHaveChooseVH(layoutInflater.inflate(R.layout.item_havechoose, parent, false));
    }

    @Override
    public void onBindViewHolder(MMHaveChooseVH holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(data.get(position));
    }

    @Override
    public void onItemMove(int fromPosition, int targetPosition) {
        super.onItemMove(fromPosition, targetPosition);

        MMProductModel.getInstance().changeProPosition(data.get(fromPosition).getId() + ""
                , data.get(targetPosition).getId() + "")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>(){
            @Override
            public void onNext(ResponseBody responseBody) {
                if (responseBody != null) {
                    try {
                        JUtils.Log("MMhavachoose",responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    class MMHaveChooseVH extends RecyclerView.ViewHolder {

        int id = R.layout.item_havechoose;
        @Bind(R.id.image_chooselist)
        ImageView imageChooselist;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.agent_price)
        TextView agentPrice;
        @Bind(R.id.std_sale_price)
        TextView stdSalePrice;
        @Bind(R.id.rebet_amount)
        TextView rebetAmount;
        @Bind(R.id.lock_num)
        TextView lockNum;

        public MMHaveChooseVH(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ShopProductBean.ResultsBean data) {
            name.setText(data.getName());
            ViewUtils.loadImgToImgView(mContext, imageChooselist,
                    data.getPic_path());
            agentPrice.setText(
                    "¥" + (float) (Math.round(data.getAgent_price() * 100)) / 100);
            stdSalePrice.setText(
                    "/¥" + (float) (Math.round(data.getStd_sale_price() * 100)) / 100);
//            rebetAmount.setText(data.getSale_num() );
//            lockNum.setText(mmChooselistBean.getSaleNumDes());


        }
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
}
