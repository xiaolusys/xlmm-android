package so.xiaolu.xiaolu.collection;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.browse.BrowseCommodityActivity;
import so.xiaolu.xiaolu.browse.BrowseButtonMyThread.broMyAdapter;
import so.xiaolu.xiaolu.MainActivity;
import so.xiaolu.xiaolu.mainsetting.SettingLoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CollectionFragment extends Fragment {

    SharedPreferences.Editor editor;
    SharedPreferences sharedata;//数据存储
    ListView listviews;
    String login_data;
    String login_name_data;
    String login_password_data;
    View view;
    Context context;

    public CollectionFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getActivity();
        sharedata = context.getSharedPreferences("login_info", 0);

        login_data = sharedata.getString("success", "");//登陆状态
        login_name_data = sharedata.getString("name", "");//登陆名
        login_password_data = sharedata.getString("password", "");

        view = inflater.inflate(R.layout.collection_main_fragment, container, false);
        view.requestLayout();
        Log.e("收藏夹用户名：", login_name_data);
        Log.e("收藏夹密码：", login_password_data);

        CollectionMyThread th = new CollectionMyThread(login_name_data, login_password_data, getActivity(), context);
        th.start();

        return view;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }


}

