package so.xiaolu.xiaolu.asynctask;

/**
 * Created by yann on 15-11-17.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.adapter.ProductListAdapter;
import so.xiaolu.xiaolu.customwidget.ScrollGirdView;
import so.xiaolu.xiaolu.jsonbean.IndexBean;

/**
 * 生成该类的对象，并调用execute方法之后
 * 首先执行的是onProExecute方法
 * 其次执行doInBackgroup方法
 */
public class IndexAsyncTask extends AsyncTask<Integer, Integer, String> {
    final OkHttpClient client = new OkHttpClient();
    private static final String TAG = "huangyan";
    private String url;
    private View view;
    private Context context;


    public IndexAsyncTask(View view, Context context, String url) {
        super();
        this.url = url;
        this.view = view;
        this.context = context;
    }


    /**
     * 这里的Integer参数对应AsyncTask中的第一个参数
     * 这里的String返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
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


    /**
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, result);
        parseIndexFromJson(result);

    }


    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
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
            java.lang.reflect.Type type = new TypeToken<IndexBean>() {
            }.getType();
            IndexBean indexBean = gson.fromJson(jsonData, type);

            final List<IndexBean.product> female_list = indexBean.female_list;
            final List<IndexBean.product> child_list = indexBean.child_list;

            ScrollGirdView nvzhuang_gridview = (ScrollGirdView) view.findViewById(R.id.nvzhuang_gridview);
            ScrollGirdView child_gridview = (ScrollGirdView) view.findViewById(R.id.child_gridview);


            ProductListAdapter nvadapter = new ProductListAdapter(context, nvzhuang_gridview, female_list);
            nvzhuang_gridview.setAdapter(nvadapter);   //女装列表

            ProductListAdapter childadapter = new ProductListAdapter(context, child_gridview, child_list);
            child_gridview.setAdapter(childadapter);    //童装列表
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}