package so.xiaolu.xiaolu.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import so.xiaolu.xiaolu.customwidget.ScrollGirdView;

import com.android.volley.toolbox.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.jsonbean.AllOrdersBean;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.Images;

public class AllOrdersListAdapter extends BaseAdapter {
    private static final String TAG = "AllOrdersListAdapter";
    private Context context;
    List<HashMap<String, String>> data;

    private AllOrdersBean dataSource;

    public AllOrdersListAdapter(Context context, AllOrdersBean allOrdersList) {
        Log.d(TAG," create");
        this.context = context;
        this.dataSource = allOrdersList;
        float payment = 0;
        int orderState = 0;

        this.data = new ArrayList<HashMap<String, String>>();

        Log.d(TAG,"dataSource.size "+ dataSource.orders_list.size());
        for (int i = 0; i < dataSource.orders_list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            payment = dataSource.orders_list.get(i).order_info.payment;
            orderState = dataSource.orders_list.get(i).order_info.status;


            map.put("payment", Float.toString(payment) );
            map.put("orderState", Integer.toString(orderState) );

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

        TextView tx_payment = null;
        TextView tx_order_sate = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orders_list_item, null);
        }

        tx_payment = (TextView) convertView.findViewById(R.id.tx_order_actual_payment);
        tx_order_sate = (TextView) convertView.findViewById(R.id.tx_order_state);

        tx_payment.setText(data.get(position).get("payment"));
        tx_order_sate.setText(data.get(position).get("orderState"));



        return convertView;
    }
}

