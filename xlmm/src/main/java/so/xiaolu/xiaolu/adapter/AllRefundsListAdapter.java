package so.xiaolu.xiaolu.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import so.xiaolu.xiaolu.customwidget.NestedListView;
import so.xiaolu.xiaolu.customwidget.ScrollGirdView;

import com.android.volley.toolbox.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.jsonbean.AllOrdersBean;
import so.xiaolu.xiaolu.jsonbean.AllRefundsBean;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean;
import so.xiaolu.xiaolu.jsonbean.RefundDetailBean;
import so.xiaolu.xiaolu.orders.OrderDetailActivity;
import so.xiaolu.xiaolu.orders.RefundDetailActivity;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.Images;

public class AllRefundsListAdapter extends BaseAdapter {
    private static final String TAG = "AllRefundsListAdapter";
    private Context context;
    List<HashMap<String, String>> data;

    //private AllRefundsBean dataSource;
    private List<RefundDetailBean> data_refund_detail_list;

    public AllRefundsListAdapter(Context context, List<RefundDetailBean> orderDetailBeanList) {
        Log.d(TAG," create");
        this.context = context;
        //this.dataSource = allRefundsList;
        this.data_refund_detail_list = orderDetailBeanList;
        float refund_fee = 0;
        String refund_State = "";
        String refund_no = "";

        this.data = new ArrayList<HashMap<String, String>>();

        Log.d(TAG,"dataSource.size "+ data_refund_detail_list.size());
        for (int i = 0; i < data_refund_detail_list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            refund_no = data_refund_detail_list.get(i).refund_base_info.getRefund_no();
            refund_State = data_refund_detail_list.get(i).refund_base_info.getStatus();
            refund_fee = data_refund_detail_list.get(i).refund_base_info.getRefund_fee();

            map.put("refund_no", (refund_no) );
            map.put("refund_State", (refund_State) );
            map.put("refund_fee", Float.toString(refund_fee) );
            data.add(map);


        }


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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.refunds_list_item, null);
        }

        final RefundDetailBean one_refund_detail = data_refund_detail_list.get(position);

        TextView tx_refundno = (TextView) convertView.findViewById(R.id.tx_refund_no);
        TextView tx_refund_state = (TextView) convertView.findViewById(R.id.tx_refund_state);
        TextView tx_refundfee = (TextView) convertView.findViewById(R.id.tx_refundfee);

        tx_refundno.setText("退款编号："+data.get(position).get("refund_no"));
        tx_refund_state.setText("退款状态："+data.get(position).get("refund_State"));
        tx_refundfee.setText(data.get(position).get("refund_fee"));

        NestedListView goods_listview = (NestedListView) convertView.findViewById(R.id.goods_listview);
        OrderGoodsListAdapter goods_adapter = new OrderGoodsListAdapter(context, data_refund_detail_list.get(position).goods_list);
        goods_listview.setAdapter(goods_adapter);
        goods_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onItemClick "+arg2 + " " + arg3);
                Intent intent = new Intent(context, RefundDetailActivity.class);
                intent.putExtra("refunddetail", one_refund_detail);
                context.startActivity(intent);
            }

        });

        return convertView;
    }
}

