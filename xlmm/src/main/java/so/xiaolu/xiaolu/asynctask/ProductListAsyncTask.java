package so.xiaolu.xiaolu.asynctask;

/**
 * Created by yann on 15-11-18.
 * 商品(童装，女装)列表
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
import so.xiaolu.xiaolu.utils.GirdUtils;

public class ProductListAsyncTask extends AsyncTask<Integer, Integer, String> {
    final OkHttpClient client = new OkHttpClient();
    private static final String TAG = "huangyan";
    private String url;
    private View view;
    private Context context;


    public ProductListAsyncTask(View view, Context context, String url) {
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
            java.lang.reflect.Type type = new TypeToken<ProductListBean>() {
            }.getType();
            ProductListBean productListBean = gson.fromJson(jsonData, type);

            final List<IndexBean.product> allProducts = productListBean.results;

            ScrollGirdView productGridview = (ScrollGirdView) view.findViewById(R.id.products_gridview);


            productGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                Bundle bundle = null;
                String product_id;
                String model_id;


                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View view, int position, long id) {


                    try {
                        product_id = allProducts.get(position).id;
                        model_id = allProducts.get(position).model_id;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    bundle = new Bundle();
                    bundle.putString("product_id", product_id);
                    bundle.putString("model_id", model_id);
                    if (allProducts.get(position).product_model.is_single_spec) {
                        Intent intent = new Intent(context, tongkuanActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, tongkuanActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                }
            });
            ProductListAdapter productadapter = new ProductListAdapter(context, productGridview, allProducts);
            productGridview.setAdapter(productadapter);
            GirdUtils.setGrideViewHeightBasedOnChildren(productGridview, productadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}