package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.entities.event.WalletEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWithdrawCashHistoryActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class WithdrawCashHisAdapter extends XRecyclerView.Adapter<WithdrawCashHisAdapter.ViewHolder> {
    private static final String TAG = "WithdrawCashHisAdapter";
    private Activity context;
    private List<WithdrawCashHisBean.WithdrawCashRecord> mList;

    public WithdrawCashHisAdapter(Activity context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void updateWithClear(List<WithdrawCashHisBean.WithdrawCashRecord> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<WithdrawCashHisBean.WithdrawCashRecord> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_withdrawcash_record, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WithdrawCashHisBean.WithdrawCashRecord record = mList.get(position);
        holder.btn_cancel.setVisibility(View.INVISIBLE);
        holder.tx_withdraw_fund.setText(record.getValue_money() + "元");
        holder.tx_withdraw_state.setText(record.getGet_status_display());
        holder.tx_time.setText(record.getCreated().replace("T", " "));
        holder.type.setText(record.getGet_cash_out_type_display());
        if (record.getGet_status_display().equals("待审核")) {
            holder.btn_cancel.setVisibility(View.VISIBLE);
            holder.btn_cancel.setOnClickListener(v -> {
                Subscription subscribe = MamaInfoModel.getInstance()
                        .cancel_withdraw_cash(record.getId() + "")
                        .subscribe(new ServiceResponse<ResponseResultBean>() {
                            @Override
                            public void onNext(ResponseResultBean resp) {
                                EventBus.getDefault().post(new WalletEvent());
                                try {
                                    JUtils.Log(TAG, "ResponseBody11=" + resp.getCode());
                                    switch (resp.getCode()) {
                                        case 0:
                                            JUtils.Toast("取消成功");
                                            holder.btn_cancel.setVisibility(View.INVISIBLE);
                                            holder.tx_withdraw_state.setText("取消");
                                            record.setGet_status_display("取消");
                                            break;
                                        case 1:
                                            JUtils.Toast("取消失败");
                                            break;
                                        case 2:
                                            JUtils.Toast("提现记录不存在");
                                            break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                ((MamaWithdrawCashHistoryActivity) context).addSubscription(subscribe);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends XRecyclerView.ViewHolder {
        int id = R.layout.item_withdrawcash_record;
        @Bind(R.id.tx_withdraw_fund)
        TextView tx_withdraw_fund;
        @Bind(R.id.tx_withdraw_state)
        TextView tx_withdraw_state;
        @Bind(R.id.tx_time)
        TextView tx_time;
        @Bind(R.id.btn_cancel)
        Button btn_cancel;
        @Bind(R.id.type)
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

