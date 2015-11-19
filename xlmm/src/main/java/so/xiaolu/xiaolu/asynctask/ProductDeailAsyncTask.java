package so.xiaolu.xiaolu.asynctask;

/**
 * Created by yann on 15-11-18.
 * 商品详细列表
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.UI.tongkuanActivity;
import so.xiaolu.xiaolu.adapter.ProductListAdapter;
import so.xiaolu.xiaolu.customwidget.ScrollGirdView;
import so.xiaolu.xiaolu.jsonbean.IndexBean;
import so.xiaolu.xiaolu.jsonbean.ProductBean;
import so.xiaolu.xiaolu.jsonbean.ProductListBean;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.UrlUtils;

public class ProductDeailAsyncTask extends AsyncTask<Integer, Integer, String> {
    final OkHttpClient client = new OkHttpClient();
    private static final String TAG = "huangyan";
    private String url;
    private View view;
    private Context context;


    public ProductDeailAsyncTask(View view, Context context, String url) {
        super();
        this.url = url;
        this.view = view;
        this.context = context;
    }

    @Override
    protected String doInBackground(Integer... params) {
        Request request = new Request.Builder().url(url).build();
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

        return showInfoStr;
    }


    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, result);
        parseIndexFromJson(result);

    }

    @Override
    protected void onPreExecute() {

    }


    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int vlaue = values[0];

    }

    public void parseIndexFromJson(String jsonData) {
        try {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ProductBean>() {
            }.getType();
            ProductBean productBean = gson.fromJson(jsonData, type);

            ImageView headpic = (ImageView)view.findViewById(R.id.product_pic);
            ImageUtils.loadHead(UrlUtils.fixHeadPic(productBean.pic_path), headpic);
            Log.d(TAG,productBean.pic_path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}