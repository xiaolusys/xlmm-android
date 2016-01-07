package com.jimei.xiaolumeimei.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingAccountActivity extends Activity {

  SharedPreferences.Editor editor;
  SharedPreferences sharedata;//数据存储
  Context context;
  String login_data;
  String login_name_data;
  String login_password_data;
  JSONArray jsonArray;
  MyHandle handle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_account_activity);
    setTitle("个人账户");
    Log.e("Info 发送", "成功");

    sharedata = getSharedPreferences("login_info", 0);
    sharedata = getSharedPreferences("login_info", 0);

    handle = new MyHandle();

    login_data = sharedata.getString("success", "");//登陆状态
    login_name_data = sharedata.getString("name", "");//登陆名
    login_password_data = sharedata.getString("password", "");

    Log.e("Info 发送", login_data);

    InfoMyThread th = new InfoMyThread();
    th.start();
  }

  public class InfoMyThread extends Thread {

    @Override public void run() {
      // TODO Auto-generated method stub
      try {

        new OkHttpRequest.Builder().url(XlmmApi.SETTINGMYINFO_URL)
            .addParams("name", login_name_data)
            .addParams("password", login_password_data)
            .post(new OkHttpCallback() {
              @Override public void onError(Request request, Exception e) {

              }

              @Override public void onResponse(Response response, Object data) {

              }
            });

        showMeg(jsonArray);
      } catch (Exception e) {

      }
    }

    private void showMeg(JSONArray jsonArray) {
      // TODO Auto-generated method stub
      Message msg = Message.obtain();
      msg.obj = jsonArray;
      msg.setTarget(handle);
      msg.sendToTarget();
    }
  }

  public class MyHandle extends Handler {

    @Override public void handleMessage(Message msg) {
      // TODO Auto-generated method stub
      JSONArray jsonarray = (JSONArray) msg.obj;
      JSONObject Jsonobj;

      try {
        Jsonobj = jsonarray.getJSONObject(0);

        Log.d("个人信息", Jsonobj.toString());

        TextView acc_name2 = (TextView) findViewById(R.id.acc_name);
        acc_name2.setText(Jsonobj.getString("realname"));

        Log.d("个人信息", Jsonobj.getString("realname"));
        TextView acc_grade2 = (TextView) findViewById(R.id.acc_grade);
        acc_grade2.setText(Jsonobj.getString("level"));

        TextView acc_point2 = (TextView) findViewById(R.id.acc_point);
        acc_point2.setText(Jsonobj.getString("point"));

        TextView acc_money2 = (TextView) findViewById(R.id.acc_money);
        acc_money2.setText(Jsonobj.getString("balance"));
      } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}

