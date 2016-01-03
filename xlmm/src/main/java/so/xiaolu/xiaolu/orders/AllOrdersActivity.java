package so.xiaolu.xiaolu.orders;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.adapter.AllOrdersListAdapter;
import so.xiaolu.xiaolu.coreokhttp.parser.impl.GsonParser;
import so.xiaolu.xiaolu.jsonbean.AllOrdersBean;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

public class AllOrdersActivity extends AppCompatActivity {
    String TAG = "AllOrdersActivity";
    AllOrdersBean all_orders_info;

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
        all_orders_info.orders_list.clear();
        get_all_orders_info();

        //config allorders list adaptor
        ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

        AllOrdersListAdapter all_orders_adapter = new AllOrdersListAdapter(this, all_orders_info);
        all_orders_listview.setAdapter(all_orders_adapter);

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

    private void get_all_orders_info(){
        MainUrl myurl = new MainUrl();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(myurl.getAllOrdersUrl()).build();
        GsonParser<AllOrdersBean> parser= new GsonParser<AllOrdersBean>(AllOrdersBean.class);
        okHttpClient.newCall(request).enqueue(new so.xiaolu.xiaolu.coreokhttp.callback.Callback<AllOrdersBean>(parser) {
            @Override
            public void onResponse(AllOrdersBean t) {
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Log.d(TAG,"coreokhttp onResponse");
                //此回调不是okhttp的了，是coreokhttp里面的handler收消息回调，因此是在主UI线程里面
                //可以操作UI界面了
                all_orders_info.orders_list.addAll(t.orders_list);
            }

        });
    }
}
