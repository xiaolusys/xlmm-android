package so.xiaolu.xiaolu.adapter;

/**
 * Created by yann on 15-11-17.
 * 商品列表数据适配
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
import so.xiaolu.xiaolu.jsonbean.IndexBean;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.Images;

public class ProductListAdapter extends BaseAdapter {
    private static final String TAG = "huangyan";
    private Context context;
    List<HashMap<String, String>> data;
    private GridView mGridView;
    private List<IndexBean.product> dataSource;

    public ProductListAdapter(Context context, GridView mGridView, List<IndexBean.product> productList) {
        this.context = context;
        this.mGridView = mGridView;
        this.dataSource = productList;
        String productName = null;
        String agentPrice = null;
        String std_sale_price = null;
        String head_img = null;
        this.data = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < dataSource.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            productName = dataSource.get(i).name;
            head_img = dataSource.get(i).head_img;
            String[] temp = head_img.split("http://image.xiaolu.so/");
            if (temp.length > 1) {
                try {
                    head_img = "http://image.xiaolu.so/" + URLEncoder.encode(temp[1], "utf-8") + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/600x800";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            agentPrice = "￥ " + dataSource.get(i).agent_price;
            std_sale_price = "￥ " + dataSource.get(i).std_sale_price;
            map.put("Name", productName.split("/")[0]);
            map.put("agentPrice", agentPrice);
            map.put("stdSalePrice", std_sale_price);
            map.put("headImg", head_img);
            data.add(map);
        }


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).get("headImg");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgView = null;
        TextView product_name = null;
        TextView agent_price = null;
        TextView stdSalePrice = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.today_main_fragment_gridview, null);
        }
        imgView = (ImageView) convertView.findViewById(R.id.product_gridview_ItemImage);
        product_name = (TextView) convertView.findViewById(R.id.product_gridview_ItemText);
        agent_price = (TextView) convertView.findViewById(R.id.product_gridview_price);
        stdSalePrice = (TextView) convertView.findViewById(R.id.product_gridview_std_price);
        product_name.setText(data.get(position).get("Name"));
        agent_price.setText(data.get(position).get("agentPrice"));
        stdSalePrice.setText(data.get(position).get("stdSalePrice"));

        stdSalePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        imgView.setImageResource(R.drawable.default_product);
        imgView.setTag(data.get(position).get("headImg"));
        ImageUtils.loadImage(data.get(position).get("headImg"), mGridView);
        return convertView;
    }
}

