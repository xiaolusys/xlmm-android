package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWithdrawCashHistoryActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class WithdrawCashHisAdapter extends BaseAdapter {
  private static final String TAG = "WithdrawCashHisAdapter";
  private Activity context;
  private List<WithdrawCashHisBean.WithdrawCashRecord> mList;

  public WithdrawCashHisAdapter(Activity context) {
    mList = new ArrayList<WithdrawCashHisBean.WithdrawCashRecord>();
    this.context = context;
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

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Log.d(TAG, "getView ");

    ViewHolder holder = null;

    if (convertView == null) {
      Log.d(TAG, "convertView == null " );
      convertView = LayoutInflater.from(context).inflate(R.layout.item_withdrawcash_record, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    }
    else{
      holder = (ViewHolder) convertView.getTag();
    }


    WithdrawCashHisBean.WithdrawCashRecord record = (WithdrawCashHisBean
        .WithdrawCashRecord)getItem(position);
    if (null != record) {
      holder.tx_withdraw_fund.setText(String.valueOf(Math.round(record.getValue_money()*100)/100) + "元");
      holder.tx_withdraw_state.setText(record.getGet_status_display());
      holder.tx_time.setText(record.getCreated().replace("T"," "));

      if(record.getGet_status_display().equals("待审核")){
        holder.btn_cancel.setVisibility(View.VISIBLE);
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            Subscription subscribe = MamaInfoModel.getInstance()
                .cancel_withdraw_cash(Integer.toString(record.getId()))
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ResponseResultBean>() {
                  @Override public void onNext(ResponseResultBean resp) {
                    try {
                      JUtils.Log(TAG, "ResponseBody11=" + resp.getCode());
                      switch (resp.getCode()){
                        case 0:
                          JUtils.Toast("取消成功");
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
            ((MamaWithdrawCashHistoryActivity)context).addSubscription(subscribe);
          }
        });
      }
    }


    return convertView;
  }

  public static class ViewHolder {
    View card;

    @Bind(R.id.tx_withdraw_fund) TextView tx_withdraw_fund;
    @Bind(R.id.tx_withdraw_state) TextView tx_withdraw_state;
    @Bind(R.id.tx_time) TextView tx_time;
    @Bind(R.id.btn_cancel) Button btn_cancel;

    public ViewHolder(View itemView) {
      card = itemView;
      ButterKnife.bind(this, itemView);
    }

  }
}

