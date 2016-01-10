package so.xiaolu.xiaolu.orders;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.adapter.AllOrdersListAdapter;
import so.xiaolu.xiaolu.data.XlmmApi;
import so.xiaolu.xiaolu.jsonbean.AllOrdersBean;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean.Goods;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean;
import so.xiaolu.xiaolu.mainsetting.MainUrl;
import so.xiaolu.xiaolu.okhttp.callback.OkHttpCallback;
import so.xiaolu.xiaolu.okhttp.request.OkHttpRequest;

public class AllOrdersActivity extends AppCompatActivity {
    String TAG = "AllOrdersActivity";
    AllOrdersBean all_orders_info = new AllOrdersBean();
    List<OrderDetailBean> all_orders_detail = new ArrayList<OrderDetailBean>();
    OrderDetailBean one_order = new OrderDetailBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        this.setTitle("全部订单");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get all orders info from server
        if(null != all_orders_info.orders_list) {
            all_orders_info.orders_list.clear();
        }
        if(null != all_orders_detail) {
            all_orders_detail.clear();
        }
        get_all_orders_info();

        //config allorders list adaptor
        ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

        AllOrdersListAdapter all_orders_adapter = new AllOrdersListAdapter(this, all_orders_info, all_orders_detail);
        all_orders_listview.setAdapter(all_orders_adapter);
        all_orders_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onItemClick "+arg2 + " " + arg3);

            }

        });
    }



    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG,"nav back");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //从server端获得所有订单数据，可能要查询几次
    private void get_all_orders_info(){

        new OkHttpRequest.Builder().url(XlmmApi.ALL_ORDERS_URL)
                .get(new OkHttpCallback<AllOrdersBean>() {

                    @Override public void onBefore(Request request) {
                        super.onBefore(request);

                    }

                    @Override public void onProgress(float progress) {
                        super.onProgress(progress);
                    }

                    @Override public void onError(Request request, Exception e) {

                    }

                    @Override public void onResponse(Response response, AllOrdersBean data) {
                        Log.i(TAG, "OkHttpCallback onResponse");
                        all_orders_info = data;
                    }
                });

        if((null != all_orders_info) && (null != all_orders_info.orders_list)){
            Log.d(TAG,"orders num "+ all_orders_info.orders_list.size());
            for(int i = 0; i < all_orders_info.orders_list.size(); i++) {


                Log.d(TAG,"get orders tid "+ all_orders_info.orders_list.get(i).getTid());
                new OkHttpRequest.Builder().url(XlmmApi.ALL_ORDERS_URL
                        +all_orders_info.orders_list.get(i).getTid()+"/"+"details.json")
                        .get(new OkHttpCallback<OrderDetailBean>() {

                            @Override
                            public void onBefore(Request request) {
                                super.onBefore(request);

                            }

                            @Override
                            public void onProgress(float progress) {
                                super.onProgress(progress);
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(Response response, OrderDetailBean data) {
                                Log.i(TAG, "OkHttpCallback get orderdetail onResponse");
                                one_order =  data;
                            }
                        });

                all_orders_detail.add(i, one_order);
            }
        }

        //test data
        fill_test_orders_info();
    }

    private void fill_test_orders_info(){
        AllOrdersBean.OrderBaseInfo one_order_base_info = new AllOrdersBean.OrderBaseInfo() ;
        one_order_base_info.tid = 1;
        one_order_base_info.buyer_nick = "david";
        one_order_base_info.buyer_id = 12;
        one_order_base_info.ringchannel = 1;
        one_order_base_info.payment = (float)79.99;
        one_order_base_info.post_fee = 0;
        one_order_base_info.total_fee = (float)79.99;
        one_order_base_info.discount_fee = 0;
        one_order_base_info.status = 1;
        one_order_base_info.buyer_message = "fast";
        one_order_base_info.trade_type = 1;
        one_order_base_info.pay_time = "20151231";
        one_order_base_info.consign_time = "20151231";
        one_order_base_info.out_sid = 2;
        one_order_base_info.receiver_name = "dave";
        one_order_base_info.receiver_state = 2;
        one_order_base_info.receiver_city = "shenzhen";
        one_order_base_info.receiver_district = "nanshan";
        one_order_base_info.receiver_address = "street";
        one_order_base_info.receiver_mobile = "18924134433";
        one_order_base_info.receiver_phone = "";

        OrderDetailBean.Goods one_good = new OrderDetailBean.Goods();
        OrderDetailBean.Goods two_good = new OrderDetailBean.Goods();
        one_good.id = "98";
        one_good.img_url = "http://xiaolu.so";
        one_good.name = "shirt";
        one_good.color = "black";
        one_good.std_sale_price = (float)139.99;
        one_good.agent_price = (float)39.99;
        one_good.model_id = "xl";
        one_good.num = 1;

        two_good.id = "99";
        two_good.img_url = "http://xiaolu.so";
        two_good.name = "shirt";
        two_good.color = "black";
        two_good.std_sale_price = (float)149.99;
        two_good.agent_price = (float)49.99;
        two_good.model_id = "xl";
        two_good.num = 3;

        AllOrdersBean.OrderBaseInfo orders = new AllOrdersBean.OrderBaseInfo();

        all_orders_info.orders_list = new ArrayList<AllOrdersBean.OrderBaseInfo>();
        all_orders_info.orders_list.add(one_order_base_info);

        OrderDetailBean.OrderBaseInfo one_order_detail_info = new OrderDetailBean.OrderBaseInfo() ;
        one_order_detail_info.tid = 1;
        one_order_detail_info.buyer_nick = "david";
        one_order_detail_info.buyer_id = 12;
        one_order_detail_info.ringchannel = 1;
        one_order_detail_info.payment = (float)79.99;
        one_order_detail_info.post_fee = 0;
        one_order_detail_info.total_fee = (float)79.99;
        one_order_detail_info.discount_fee = 0;
        one_order_detail_info.status = 1;
        one_order_detail_info.buyer_message = "fast";
        one_order_detail_info.trade_type = 1;
        one_order_detail_info.pay_time = "20151231";
        one_order_detail_info.consign_time = "20151231";
        one_order_detail_info.out_sid = 2;
        one_order_detail_info.receiver_name = "dave";
        one_order_detail_info.receiver_state = 2;
        one_order_detail_info.receiver_city = "shenzhen";
        one_order_detail_info.receiver_district = "nanshan";
        one_order_detail_info.receiver_address = "street";
        one_order_detail_info.receiver_mobile = "18924134433";
        one_order_detail_info.receiver_phone = "";
        one_order.order_base_info = one_order_detail_info;
        one_order.goods_list = new ArrayList<Goods>();
        one_order.goods_list.add(one_good);
        one_order.goods_list.add(two_good);

        all_orders_detail.add(one_order);

    }
}
