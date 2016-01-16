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
import so.xiaolu.xiaolu.adapter.AllRefundsListAdapter;
import so.xiaolu.xiaolu.data.XlmmApi;
import so.xiaolu.xiaolu.jsonbean.AllOrdersBean;
import so.xiaolu.xiaolu.jsonbean.AllRefundsBean;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean.Goods;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean;
import so.xiaolu.xiaolu.jsonbean.RefundDetailBean;
import so.xiaolu.xiaolu.okhttp.callback.OkHttpCallback;
import so.xiaolu.xiaolu.okhttp.request.OkHttpRequest;

public class AllRefundsActivity extends AppCompatActivity {
    String TAG = "RefundsActivity";
    AllRefundsBean all_refunds_info = new AllRefundsBean();
    List<RefundDetailBean> refunds_detail_list = new ArrayList<RefundDetailBean>();
    RefundDetailBean one_refund = new RefundDetailBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        this.setTitle("退款退货");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get all orders info from server
        if(null != all_refunds_info.refunds_list) {
            all_refunds_info.refunds_list.clear();
        }
        if(null != refunds_detail_list) {
            refunds_detail_list.clear();
        }
        get_all_refunds_info();

        //config allorders list adaptor
        ListView all_refunds_listview = (ListView) findViewById(R.id.all_refunds_listview);

        AllRefundsListAdapter all_refunds_adapter = new AllRefundsListAdapter(this, refunds_detail_list);
        all_refunds_listview.setAdapter(all_refunds_adapter);
        all_refunds_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

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

    //从server端获得所有refund订单数据，可能要查询几次
    private void get_all_refunds_info(){

        new OkHttpRequest.Builder().url(XlmmApi.ALL_REFUNDS_URL)
                .get(new OkHttpCallback<AllRefundsBean>() {

                    @Override public void onBefore(Request request) {
                        super.onBefore(request);

                    }

                    @Override public void onProgress(float progress) {
                        super.onProgress(progress);
                    }

                    @Override public void onError(Request request, Exception e) {

                    }

                    @Override public void onResponse(Response response, AllRefundsBean data) {
                        Log.i(TAG, "OkHttpCallback onResponse");
                        all_refunds_info = data;
                    }
                });

        if((null != all_refunds_info) && (null != all_refunds_info.refunds_list)){
            Log.d(TAG,"orders num "+ all_refunds_info.refunds_list.size());
            for(int i = 0; i < all_refunds_info.refunds_list.size(); i++) {

                Log.d(TAG,"get orders tid "+ all_refunds_info.refunds_list.get(i).getOrder_id());
                new OkHttpRequest.Builder().url(XlmmApi.ALL_ORDERS_URL
                        +all_refunds_info.refunds_list.get(i).getOrder_id()+"/"+"get_by_order_id/")
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
                                Log.i(TAG, "OkHttpCallback get refunddetail onResponse");
                                //one_order =  data;
                            }
                        });

                //all_orders_detail.add(i, one_order);
            }
        }

        //test data
        fill_test_refunds_info();
    }

    private void fill_test_refunds_info(){
        AllRefundsBean.RefundBaseInfo one_refund_base_info = new AllRefundsBean.RefundBaseInfo() ;
        one_refund_base_info.refund_no = "93009";
        one_refund_base_info.trade_id = 100;
        one_refund_base_info.order_id = 12;
        one_refund_base_info.buyer_id = 1;
        one_refund_base_info.item_id = 889;
        one_refund_base_info.title = "one";
        one_refund_base_info.sku_id = 155;
        one_refund_base_info.sku_name = "blue";
        one_refund_base_info.refund_num = 22;
        one_refund_base_info.buyer_nick = "fast";
        one_refund_base_info.mobile = "1389868344";
        one_refund_base_info.phone = "28776755";
        one_refund_base_info.proof_pic = "http://so.xiaolu.xiaolu";
        one_refund_base_info.total_fee = (float)120;
        one_refund_base_info.payment = (float)120;
        one_refund_base_info.company_name = "sst";
        one_refund_base_info.sid = "7677";
        one_refund_base_info.reason = "bad";
        one_refund_base_info.desc = "bb";
        one_refund_base_info.feedback = "tt";
        one_refund_base_info.has_good_return = true;
        one_refund_base_info.has_good_change = true;
        one_refund_base_info.good_status = "back";
        one_refund_base_info.status = "back";
        one_refund_base_info.refund_fee = (float)110;


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

        //AllRefundsBean.refunds_list = new ArrayList<AllRefundsBean.RefundBaseInfo>();
        //AllRefundsBean.refunds_list.add(one_refund_base_info);

        RefundDetailBean one_refund_detail_info = new RefundDetailBean() ;

        one_refund_detail_info.refund_base_info = one_refund_base_info;
        one_refund_detail_info.goods_list = new ArrayList<Goods>();
        one_refund_detail_info.goods_list.add(one_good);
        one_refund_detail_info.goods_list.add(two_good);

        refunds_detail_list.add(one_refund_detail_info);

    }
}
