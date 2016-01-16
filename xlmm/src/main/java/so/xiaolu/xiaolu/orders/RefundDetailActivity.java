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
import so.xiaolu.xiaolu.adapter.OrderGoodsListAdapter;
import so.xiaolu.xiaolu.customwidget.NestedListView;
import so.xiaolu.xiaolu.data.XlmmApi;
import so.xiaolu.xiaolu.jsonbean.AllOrdersBean;
import so.xiaolu.xiaolu.jsonbean.AllRefundsBean;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean.Goods;
import so.xiaolu.xiaolu.jsonbean.OrderDetailBean;
import so.xiaolu.xiaolu.jsonbean.RefundDetailBean;
import so.xiaolu.xiaolu.mainsetting.MainUrl;
import so.xiaolu.xiaolu.okhttp.callback.OkHttpCallback;
import so.xiaolu.xiaolu.okhttp.request.OkHttpRequest;

public class RefundDetailActivity extends AppCompatActivity {
    String TAG = "RefundDetailActivity";

    RefundDetailBean refund_detail = new RefundDetailBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_detail);
        this.setTitle("退货（款）详情");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refund_detail = getIntent().getExtras().getParcelable("refunddetail");
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


        ImageView img_good = (ImageView) findViewById(R.id.img_good);

        NestedListView goods_listview = (NestedListView) findViewById(R.id.goods_listview);
        OrderGoodsListAdapter goods_adapter = new OrderGoodsListAdapter(this, refund_detail.goods_list);
        goods_listview.setAdapter(goods_adapter);



    }
}
