package so.xiaolu.xiaolu.asynctask;

/**
 * Created by yann on 15-11-17.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.jsonbean.PosterBean;
import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.UrlUtils;

public class PosterAsyncTask extends AsyncTask<Integer, Integer, String> {
    final OkHttpClient client = new OkHttpClient();
    private static final String TAG = "huangyan";
    private String url;
    private View view;
    private Context context;


    public PosterAsyncTask(View view, Context context, String url) {
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
            java.lang.reflect.Type type = new TypeToken<PosterBean>() {
            }.getType();
            PosterBean posterBean = gson.fromJson(jsonData, type);

            ImageView wemposter = (ImageView)view.findViewById(R.id.nvzhuang_poster);
            TextView wemText1 = (TextView)view.findViewById(R.id.nvzhuang_subject1);
            TextView wemText2 = (TextView)view.findViewById(R.id.nvzhuang_subject2);
            wemText1.setText(posterBean.wem_posters.get(0).subject.get(0).toString());
            wemText2.setText(posterBean.wem_posters.get(0).subject.get(1).toString());

            ImageView chd_posters = (ImageView)view.findViewById(R.id.child_poster);
            TextView chdText1 = (TextView)view.findViewById(R.id.child_subject1);
            TextView chdText2 = (TextView)view.findViewById(R.id.child_subject2);
            chdText1.setText(posterBean.chd_posters.get(0).subject.get(0).toString());
            chdText2.setText(posterBean.chd_posters.get(0).subject.get(1).toString());


            ImageUtils.loadImageView(UrlUtils.fixPosterUrl(posterBean.wem_posters.get(0).pic_link), wemposter);
            ImageUtils.loadImageView(UrlUtils.fixPosterUrl(posterBean.chd_posters.get(0).pic_link), chd_posters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}