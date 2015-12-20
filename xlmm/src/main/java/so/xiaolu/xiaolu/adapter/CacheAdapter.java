package so.xiaolu.xiaolu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.VolleyApplication;

/**
 * Created by root on 15-11-18.
 */
public class CacheAdapter extends BaseAdapter {
    private static final String TAG = "CacheAdapter";
    public class Item {
        public String itemImageURL;
        public String itemTitle;

        public Item(String itemImageURL, String itemTitle) {
            this.itemImageURL = itemImageURL;
            this.itemTitle = itemTitle;
        }
    }

    private Context mContext;
    private ArrayList<Item> mItems = new ArrayList<Item>();
    List<HashMap<String, String>> data;
    LayoutInflater inflater;

    public CacheAdapter(Context c) {
        mContext = c;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void addItem(String itemImageURL, String itemTitle) {
        mItems.add(new Item(itemImageURL, itemTitle));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    List<Integer> LstPosition = new ArrayList<Integer>();
    List<View> LstView = new ArrayList<View>();
    List<Integer> LstTimes = new ArrayList<Integer>();
    long startTime = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        startTime = System.nanoTime();
        if(LstPosition.contains(position) == false){
            if(LstPosition.size() > 75){ //这儿设置缓存的Item数量
                LstPosition.remove(0);//删除第一项
                LstView.remove(0);
            }
            convertView = inflater.inflate(R.layout.today_main_fragment_gridview,null);
            TextView name = (TextView)convertView.findViewById(R.id.product_gridview_ItemText);
            ImageView head_img = (ImageView)convertView.findViewById(R.id.product_gridview_ItemImage);
            TextView agent_price = (TextView)convertView.findViewById(R.id.product_gridview_price);

            // 设置数据
            name.setText(mItems.get(position).itemTitle);
            ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(head_img, R.drawable.default_product, R.drawable.default_product);
            imageLoader.get(mItems.get(position).itemImageURL, listener,600, 700);
            LstPosition.add(position);
            LstView.add(convertView);
        }else{
            convertView = LstView.get(LstPosition.indexOf(position));
        }
        int endtime = (int)(System.nanoTime()-startTime);
        LstTimes.add(endtime);
        if(LstTimes.size() == 10){
            int total = 0;
            for(int i =0;i<LstTimes.size();i++)
                total = total + LstTimes.get(i);
            Log.d(TAG, "10个共花了：" + total / 1000 + "所用内存：" + Runtime.getRuntime().totalMemory() / 1024 + "KB");
            LstTimes.clear();
        }
        return convertView;
    }
}
