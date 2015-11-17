package so.xiaolu.xiaolu.adapter;

/**
 * Created by yann on 15-11-17.
 */

import android.content.Context;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.jsonbean.IndexBean;
import so.xiaolu.xiaolu.utils.ImageUtils;

public class ChilddAdapter extends BaseAdapter implements OnScrollListener {
    private static final String TAG = "ImageAdapter";
    private Context context;
//    private String[] items = Images.imageThumbUrls;
    List<HashMap<String, String>> data;
    private GridView mGridView;

    private boolean isFirstEnter;

    private int firstSeeItem;


    private int orifirstItem;

    private int totalSeeItem;

    private List<IndexBean.product> dataSource;
    public ChilddAdapter(Context context, GridView mGridView, List<IndexBean.product> productList) {
        this.context = context;
        this.mGridView = mGridView;
        this.dataSource = productList;
        String productName = null;
        String agentPrice = null;
        String head_img = null;
        this.data = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < dataSource.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            productName = dataSource.get(i).name;
            head_img = dataSource.get(i).head_img;
            String[] temp = head_img.split("http://image.xiaolu.so/");
            if (temp.length > 1){
                try {
                    head_img = "http://image.xiaolu.so/" + URLEncoder.encode(temp[1],"utf-8") + "?imageMogr2/auto-orient/strip/size-limit/10k/q/85/thumbnail/600x700";
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            agentPrice = "价格:￥" + dataSource.get(i).agent_price;
            map.put("Name", productName);
            map.put("agentPrice", agentPrice);
            map.put("headImg", head_img);
            data.add(map);
        }

        mGridView.setOnScrollListener(this);
        isFirstEnter = true;

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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.today_main_fragment_gridview, null);
        }
        imgView = (ImageView) convertView.findViewById(R.id.product_gridview_ItemImage);
        product_name = (TextView) convertView.findViewById(R.id.product_gridview_ItemText);
        agent_price = (TextView) convertView.findViewById(R.id.product_gridview_price);
        product_name.setText(data.get(position).get("Name").toString());
        agent_price.setText(data.get(position).get("agentPrice").toString());
        imgView.setImageResource(R.drawable.default_product);
        imgView.setTag(data.get(position).get("headImg"));

        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.v(TAG, "imagedown--->onScroll");
        firstSeeItem = firstVisibleItem;
        totalSeeItem = visibleItemCount;

        if (isFirstEnter && visibleItemCount > 0) {
            orifirstItem = firstVisibleItem;
            startLoadImages(firstSeeItem, totalSeeItem);
            isFirstEnter = false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.v(TAG, "imagedown--->onScrollStateChanged");
        if (orifirstItem != firstSeeItem) {
            if (scrollState == SCROLL_STATE_IDLE) {
                startLoadImages(firstSeeItem, totalSeeItem);
                orifirstItem = firstSeeItem;
            } else {
                ImageUtils.cancelAllImageRequests();
            }
        }


    }

    private void startLoadImages(int first, int total) {
        Log.v(TAG, "imagedown--->startLoadImages,first-->" + first + ",total-->" + total);
        for (int i = first; i < first + total; i++) {
            ImageUtils.loadImage(data.get(i).get("headImg"), mGridView);
        }
    }


    private String fixUrl(String url) {
        String[] temp = url.split("http://image.xiaolu.so/");
        if (temp.length > 1) {
            try {
                url = "http://image.xiaolu.so/" + URLEncoder.encode(temp[1], "utf-8") + "?imageMogr2/auto-orient/strip/size-limit/10k/q/85/thumbnail/600x700";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "http://www.sinaimg.cn/qc/photo_auto/photo/84/35/39698435/39698435_140.jpg";
    }
}

