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
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.Images;

public class OrderGoodsListAdapter extends BaseAdapter {
    private static final String TAG = "OrderGoodsListAdapter";
    private Context context;
    List<HashMap<String, String>> data;

    private List<OrderDetailBean.Goods> dataSource;

    public OrderGoodsListAdapter(Context context, List<OrderDetailBean.Goods> goodsList, int position) {
        Log.d(TAG," create");
        this.context = context;
        this.dataSource = goodsList;
        String img_url = "";
        String name = "";
        String color = "";
        float std_sale_price = 0;
        float agent_price = 0;
        String model_id = "";
        int num = 0;

        this.data = new ArrayList<HashMap<String, String>>();

        Log.d(TAG,"dataSource.size "+ dataSource.size());
        for (int i = 0; i < dataSource.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            img_url = dataSource.get(i).img_url;
            name = dataSource.get(i).name;
            color = dataSource.get(i).color;
            std_sale_price = dataSource.get(i).std_sale_price;
            agent_price = dataSource.get(i).agent_price;
            model_id = dataSource.get(i).model_id;
            num = dataSource.get(i).num;

            map.put("img_url", img_url );
            map.put("name", name );
            map.put("color", color );
            map.put("std_sale_price", Float.toString(std_sale_price) );
            map.put("agent_price", Float.toString(agent_price) );
            map.put("model_id", model_id );
            map.put("num", Integer.toString(num) );

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

        ImageView img_goods= null;
        TextView tx_good_name = null;
        TextView tx_good_price = null;
        TextView tx_good_size = null;
        TextView tx_good_num = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.one_order_item, null);
        }

        img_goods = (ImageView) convertView.findViewById(R.id.img_good);;
        tx_good_name = (TextView) convertView.findViewById(R.id.tx_good_name);
        tx_good_price = (TextView) convertView.findViewById(R.id.tx_good_price);
        tx_good_size = (TextView) convertView.findViewById(R.id.tx_good_size);
        tx_good_num = (TextView) convertView.findViewById(R.id.tx_good_num);

        tx_good_name.setText(data.get(position).get("name") +"/"+data.get(position).get("color"));
        tx_good_price.setText(data.get(position).get("agent_price"));
        tx_good_size.setText(data.get(position).get("model_id"));
        tx_good_num.setText(data.get(position).get("num"));

        return convertView;
    }
}

