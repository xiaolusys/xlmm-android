package so.xiaolu.xiaolu.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.jsonbean.ProductBean;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

public class tongkuanActivity extends AppCompatActivity {
    final OkHttpClient client = new OkHttpClient();
    private String model_id = null;
    tongkuan_Handle myHandler;
    View view;
    Context context;
    String TAG = "huangyan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongkuan);
        context = getApplicationContext();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        model_id = data.getString("model_id");
        Log.d(TAG, model_id);
        myHandler = new tongkuan_Handle();
        tongkuan_Thread th = new tongkuan_Thread();
        th.start();
    }

    /*
    * 同款
    * */
    public class tongkuan_Thread extends Thread {
        @Override
        public void run() {
            try {
                MainUrl url = new MainUrl();
                String tong_url = url.getTONGKUAN_URL() + model_id;
                Log.d(TAG, tong_url);
                Request request = new Request.Builder().url(tong_url).build();
                Response response;
                String showInfoStr = "";
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        showInfoStr = response.body().string();
                    } else {
                        Log.d(TAG, response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parseIndexFromJson(showInfoStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void parseIndexFromJson(String jsonData) {
        try {
            Log.v(TAG, jsonData);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<ProductBean>>() {
            }.getType();
            List<ProductBean> products = gson.fromJson(jsonData, type);
            for (int i = 0; i < products.size(); i++) {
                Log.d(TAG, products.get(i).toString());
            }
            showMsg(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMsg(List<ProductBean> products) {
        Message msg = Message.obtain();
        msg.obj = products;
        msg.setTarget(myHandler);//把message内容放入，handle
        msg.sendToTarget();
    }

    class tongkuan_Handle extends Handler {
        @Override
        public void handleMessage(Message msg) {

            final List<ProductBean> products = (List<ProductBean>) msg.obj;
            GridView tongkuan_gridview = (GridView) findViewById(R.id.tongkuan_gridview);
            TongkuanAdapter tongkuanadapter = new TongkuanAdapter(context, products);
            tongkuan_gridview.setAdapter(tongkuanadapter);
            tongkuan_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                Bundle bundle = null;
                String product_id;
                String model_id;
                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View view, int position, long id) {


                    try {
                        product_id = products.get(position).id;
                        model_id = products.get(position).model_id;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bundle = new Bundle();
                    bundle.putString("product_id", product_id);
                    bundle.putString("model_id", model_id);
//                    Intent intent = new Intent(getActivity(), tongkuanActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                }
            });


        }
    }


    /**************
     * set BaseAdapter
     ********************/

    public class TongkuanAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<ProductBean> dataSource;
        private ImageLoader mImageLoader;
        List<HashMap<String, String>> data;

        public TongkuanAdapter(Context c, List<ProductBean> productList) {
            this.inflater = LayoutInflater.from(c);
            this.dataSource = productList;
            RequestQueue mQueue = Volley.newRequestQueue(context);   //创建一个RequestQueue对象
            mImageLoader = new ImageLoader(mQueue, new BitmapCache());  //创建一个ImageLoader对象
            this.data = new ArrayList<HashMap<String, String>>();
            String productName = null;
            String agentPrice = null;
            String pic_path = null;
            for (int i = 0; i < dataSource.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                productName = dataSource.get(i).name;
                pic_path = dataSource.get(i).pic_path;
                String[] temp = pic_path.split("http://image.xiaolu.so/");
                if (temp.length > 1) {
                    try {
                        pic_path = "http://image.xiaolu.so/" + URLEncoder.encode(temp[1], "utf-8")+ "?imageMogr2/auto-orient/strip/size-limit/20k/q/85/thumbnail/600x700";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                agentPrice = "价格:￥" + dataSource.get(i).agent_price;
                map.put("Name", productName);
                map.put("agentPrice", agentPrice);
                map.put("picPath", pic_path);
                data.add(map);
            }
        }

        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.today_main_fragment_gridview, null);
                holder = new ViewHolder();
                holder.head_img = (ImageView) convertView.findViewById(R.id.product_gridview_ItemImage);
                holder.product_name = (TextView) convertView.findViewById(R.id.product_gridview_ItemText);
                holder.agent_price = (TextView) convertView.findViewById(R.id.product_gridview_price);
                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.product_name.setText(data.get(position).get("Name").toString());
            holder.agent_price.setText(data.get(position).get("agentPrice").toString());
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.head_img, R.drawable.default_product, R.drawable.default_product);//获取一个ImageListener对象
            mImageLoader.get(data.get(position).get("picPath").toString(), listener,600, 700);//调用ImageLoader的get()方法加载网络上的图片
            //mImageLoader.get(url, listener);
            return convertView;
        }

    }

    public class ViewHolder {
        public ImageView head_img;
        public TextView product_name;
        public TextView agent_price;
        public TextView std_sale_price;
    }


    /*
    * 缓存*/
    public class BitmapCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            int cacheSize = maxMemory / 8;
            //int maxSize = 2 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }
        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
