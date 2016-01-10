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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class OrderDetailActivity extends AppCompatActivity {
    String TAG = "OrderDetailActivity";
    OrderDetailBean order_detail = new OrderDetailBean();
    int good_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        this.setTitle("订单详情");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        order_detail = getIntent().getExtras().getParcelable("orderinfo");
        good_index = getIntent().getExtras().getInt("goodnum");
        initView();
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

    private void initView(){
        TextView tx_order_id = (TextView) findViewById(R.id.tx_order_id);
        tx_order_id.setText("订单编号" + order_detail.getOrder_info().getTid());

        TextView tx_order_state = (TextView) findViewById(R.id.tx_order_state);
        tx_order_state.setText("状态" + order_detail.getOrder_info().getStatus());

        TextView tx_custom_name = (TextView) findViewById(R.id.tx_custom_name);
        tx_custom_name.setText("姓名：" + order_detail.getOrder_info().getReceiver_name());

        TextView tx_custom_address = (TextView) findViewById(R.id.tx_custom_address);
        tx_custom_address.setText("地址：" + order_detail.getOrder_info().getReceiver_address());

        //没有订单创建时间?
        TextView tx_order_crttime = (TextView) findViewById(R.id.tx_order_crttime);
        tx_order_crttime.setText("时间：" + order_detail.getOrder_info().getPay_time());
        TextView tx_order_crtstate = (TextView) findViewById(R.id.tx_order_crtstate);
        tx_order_crtstate.setText("订单创建成功" );

        ImageView img_good = (ImageView) findViewById(R.id.img_good);

        TextView tx_good_name = (TextView) findViewById(R.id.tx_good_name);
        tx_good_name.setText(order_detail.getGoods_list().get(good_index).getName()
                        +"/"+order_detail.getGoods_list().get(good_index).getColor());
        TextView tx_good_price = (TextView) findViewById(R.id.tx_good_price);
        tx_good_price.setText("￥"+order_detail.getGoods_list().get(good_index).getAgent_price());

        TextView tx_good_size = (TextView) findViewById(R.id.tx_good_size);
        tx_good_size.setText(order_detail.getGoods_list().get(good_index).getModel_id());

        TextView tx_good_num = (TextView) findViewById(R.id.tx_good_num);
        tx_good_num.setText("×"+order_detail.getGoods_list().get(good_index).getNum());

        TextView tx_order_totalfee = (TextView) findViewById(R.id.tx_order_totalfee);
        tx_order_totalfee.setText("￥" + order_detail.getOrder_info().getTotal_fee());

        TextView tx_order_discountfee = (TextView) findViewById(R.id.tx_order_discountfee);
        tx_order_discountfee.setText("￥" + order_detail.getOrder_info().getDiscount_fee());

        TextView tx_order_postfee = (TextView) findViewById(R.id.tx_order_postfee);
        tx_order_postfee.setText("￥" + order_detail.getOrder_info().getPost_fee());

        TextView tx_order_payment = (TextView) findViewById(R.id.tx_order_payment);
        tx_order_payment.setText("总金额 ￥" + order_detail.getOrder_info().getPayment());
    }
}
