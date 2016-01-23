package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.schedulers.Schedulers;

public class OrderGoodsListAdapter extends BaseAdapter {
    private static final String TAG = "OrderGoodsListAdapter";
    private Context context;
    List<HashMap<String, String>> data;

    private List<AllOrdersBean.ResultsEntity.OrdersEntity> dataSource;

    public OrderGoodsListAdapter(Context context) {
        dataSource = new ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity>();
        this.data = new ArrayList<HashMap<String, String>>();
        this.context = context;
    }

    public OrderGoodsListAdapter(Context context, List<AllOrdersBean.ResultsEntity.OrdersEntity> goodsList) {
        Log.d(TAG," create");
        this.context = context;
        this.dataSource = goodsList;
        String img_url = "";
        String title = "";
        float std_sale_price = 0;
        float pay_price = 0;
        String model_id = "";
        int num = 0;

        this.data = new ArrayList<HashMap<String, String>>();

        Log.d(TAG,"dataSource.size "+ dataSource.size());
        for (int i = 0; i < dataSource.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            img_url = dataSource.get(i).getPicPath();
            title = dataSource.get(i).getTitle();
            std_sale_price = (float)dataSource.get(i).getTotalFee();
            pay_price = (float)dataSource.get(i).getPayment();
            model_id = dataSource.get(i).getSkuName();
            num = dataSource.get(i).getNum();

            map.put("img_url", img_url );
            map.put("title", title );
            map.put("std_sale_price", Float.toString(std_sale_price) );
            map.put("pay_price", Float.toString(pay_price) );
            map.put("model_id", model_id );
            map.put("num", Integer.toString(num) );

            data.add(map);
        }


    }

    public void updateWithClear(List<AllOrdersBean.ResultsEntity.OrdersEntity> list) {
        dataSource.clear();
        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AllOrdersBean.ResultsEntity.OrdersEntity> list) {
        int id = 0;
        String img_url = "";
        String title = "";
        float std_sale_price = 0;
        float agent_price = 0;
        String model_id = "";
        int num = 0;
        int state = 0;
        int refund_state = 0;

        Log.d(TAG,"list.size "+ list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            id = list.get(i).getId();
            img_url = list.get(i).getPicPath();
            title = list.get(i).getTitle();
            std_sale_price = (float)list.get(i).getTotalFee();
            agent_price = (float)list.get(i).getPayment();
            model_id = list.get(i).getSkuName();
            num = list.get(i).getNum();
            state = list.get(i).getStatus();
            refund_state = list.get(i).getRefundStatus();

            map.put("id", Integer.toString(id) );
            map.put("img_url", img_url );
            map.put("title", title );
            map.put("std_sale_price", Float.toString(std_sale_price) );
            map.put("pay_price", Float.toString(agent_price) );
            map.put("model_id", model_id );
            map.put("num", Integer.toString(num) );
            map.put("state", Integer.toString(state) );
            map.put("refund_state", Integer.toString(refund_state) );

            data.add(map);
        }

        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getView ");

        ImageView img_goods= null;
        TextView tx_good_name = null;
        TextView tx_good_price = null;
        TextView tx_good_size = null;
        TextView tx_good_num = null;
        int state = Integer.parseInt(data.get(position).get("state"));
        int refund_state = Integer.parseInt(data.get(position).get("refund_state"));

        if (convertView == null) {
            if((state == XlmmConst.ORDER_STATE_PAYED)
                    || (state == XlmmConst.ORDER_STATE_SENDED)
                    //|| (state == XlmmConst.ORDER_STATE_CONFIRM_RECEIVE)
                    || (state == XlmmConst.ORDER_STATE_TRADE_SUCCESS)) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail_include_proc, null);
                setBtnInfo(convertView, state, refund_state);
                setBtnListener(convertView, state, refund_state,Integer.parseInt(data.get(position).get("id")));
            }
            else{
                convertView = LayoutInflater.from(context).inflate(R.layout.one_order_item, null);
            }
        }

        img_goods = (ImageView) convertView.findViewById(R.id.img_good);
        tx_good_name = (TextView) convertView.findViewById(R.id.tx_good_name);
        tx_good_price = (TextView) convertView.findViewById(R.id.tx_good_price);
        tx_good_size = (TextView) convertView.findViewById(R.id.tx_good_size);
        tx_good_num = (TextView) convertView.findViewById(R.id.tx_good_num);

        tx_good_name.setText(data.get(position).get("title") );
        tx_good_price.setText("￥"+data.get(position).get("pay_price"));
        tx_good_size.setText(data.get(position).get("model_id"));
        tx_good_num.setText(data.get(position).get("num"));

        ViewUtils.loadImgToImgView(context, img_goods, data.get(position).get("img_url") );

        return convertView;
    }

    private void setBtnInfo(View convertView, int state, int refund_state){
        Button btn = (Button)convertView.findViewById(R.id.btn_order_proc);
        switch (state){
            case XlmmConst.ORDER_STATE_PAYED:
            {
                btn.setText("申请退款");
                break;
            }
            case XlmmConst.ORDER_STATE_SENDED: {
                btn.setText("确认收货");
                break;
            }
            //case XlmmConst.ORDER_STATE_CONFIRM_RECEIVE:
            case XlmmConst.ORDER_STATE_TRADE_SUCCESS:
            {
                switch (refund_state) {
                    case XlmmConst.REFUND_STATE_NO_REFUND: {
                        btn.setText("退货退款");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_BUYER_APPLY: {
                        btn.setText("已经申请退款");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_SELLER_AGREED: {
                        btn.setText("卖家同意退款");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_BUYER_RETURNED_GOODS: {
                        btn.setText("已经退货");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_SELLER_REJECTED: {
                        btn.setText("卖家拒绝退款");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_WAIT_RETURN_FEE: {
                        btn.setText("退款中");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_REFUND_CLOSE: {
                        btn.setText("退款关闭");
                        break;
                    }
                    case XlmmConst.REFUND_STATE_REFUND_SUCCESS: {
                        btn.setText("退款成功");
                        break;
                    }
                }
            }
        }

    }

    private void setBtnListener(View convertView, int state, int refund_state, int id){
        Button btn = (Button)convertView.findViewById(R.id.btn_order_proc);
        switch (state) {
            case XlmmConst.ORDER_STATE_PAYED: {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //enter apply refund
                    }
                });
                break;
            }
            case XlmmConst.ORDER_STATE_SENDED: {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //confirm receive goods
                        receive_goods(id);
                    }
                });
                break;
            }

            case XlmmConst.ORDER_STATE_TRADE_SUCCESS: {
                switch (refund_state) {
                    case XlmmConst.REFUND_STATE_NO_REFUND: {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //enter apply return goods
                            }
                        });
                        break;
                    }
                }
            }
        }
    }

    private void receive_goods(int id){
        TradeModel model = new TradeModel();
        model.receiveGoods(id)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<UserBean>() {
                    @Override public void onNext(UserBean userBean) {
                        Log.i(TAG, "returncode "+userBean.getCode());

                    }
                });
    }
}

