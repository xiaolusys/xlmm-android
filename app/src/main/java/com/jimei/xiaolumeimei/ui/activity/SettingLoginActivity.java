package com.jimei.xiaolumeimei.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class SettingLoginActivity extends AppCompatActivity {

  EditText login_name;
  String login_name_value;
  EditText login_pass;
  String login_pass_value;
  Button login_button;
  String TAG = "SettingLoginActivity";
  private MyHandler myHandler = new MyHandler();//初始化Handler
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_login_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle("登录");
    mShare();
    initView();
    login_button.setOnClickListener(new OnClickListener() {

      @Override public void onClick(View v) {
        // TODO Auto-generated method stub
        Log.d(TAG, "login_button click");
        mThread thread = new mThread();
        thread.start();
      }
    });

    Button set_register = (Button) findViewById(R.id.set_register_button);
    set_register.setOnClickListener(new OnClickListener() {

      @Override public void onClick(View arg0) {
        // TODO Auto-generated method stub
        Intent intent =
            new Intent(SettingLoginActivity.this, SettingRegisterActivity.class);
        startActivity(intent);
      }
    });
  }

  public void initView() {
    login_name = (EditText) findViewById(R.id.set_login_name);

    login_pass = (EditText) findViewById(R.id.set_login_password);

    login_button = (Button) findViewById(R.id.set_login_button);
  }

  @Override protected void onStart() {
    // TODO Auto-generated method stub
    super.onStart();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // TODO Auto-generated method stub
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onStop() {
    super.onStop();
  }

  //		/*******************/
  //		mShare();
  //		String name=sharedPreferences.getString("name", "");
  //		String password=sharedPreferences.getString("password","");
  //		login_name_value=login_name.getText().toString();
  //		login_pass_value=login_pass.getText().toString();
  //
  //		if(login_name_value.equals(name)&&login_pass_value.equals(password)){
  //
  //			//editor.putString("success", "true");
  //			//editor.commit();
  //			Toast toast1=Toast.makeText(this,"登陆成功"+sharedPreferences.getString("name",""), Toast.LENGTH_SHORT);
  //			toast1.show();
  //			editor.putString("success", "true");
  //			editor.commit();
  //			Intent intent=new Intent(this, MainFrameActivity.class);
  //			startActivity(intent);
  //
  //		}else{
  //			Toast toast=Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT);
  //			toast.show();
  //		}

  private int parseRespResult(String str) {

    try {
      Gson gson = new Gson();
      RespData resp = gson.fromJson(str, RespData.class);
      return resp.returnCode;
    } catch (Exception e) {
      Log.e(TAG, "parseRespMsg error");
      e.printStackTrace();
    }
    return 1;
  }

  private void showMsg(String str) {
    Message msg = Message.obtain();
    msg.obj = str;
    msg.setTarget(myHandler);//把message内容放入，handle
    msg.sendToTarget();
  }

  /********
   * init sharePreference value
   ***********/

  public void mShare() {
    sharedPreferences =
        getApplicationContext().getSharedPreferences("login_info", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name", "");
    editor.putString("password", "");
    editor.putString("success", "");
    editor.commit();
  }

  public class mThread extends Thread {

    public void run() {
      try {
        login_name_value = login_name.getText().toString();
        login_pass_value = login_pass.getText().toString();
        //DefaultHttpClient client = new DefaultHttpClient();
        //List<NameValuePair> list = new ArrayList<NameValuePair>();
        //NameValuePair pair1 = new BasicNameValuePair("username", login_name_value);
        //NameValuePair pair2 = new BasicNameValuePair("password", login_pass_value);
        //list.add(pair1);
        //list.add(pair2);
        //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        //
        //MainUrl url = new MainUrl();
        //HttpPost post = new HttpPost(url.getLOGIN_URL());
        //
        //post.setEntity(entity);
        //
        //HttpResponse response = client.execute(post);
        //
        //if (response.getStatusLine().getStatusCode() == 200) {
        //    InputStream in = response.getEntity().getContent();//接收服务器的数据，并在Toast上显示
        //    BufferedReader str = new BufferedReader(
        //            new InputStreamReader(in));
        //    String line = null;
        //    line = str.readLine();
        //    Log.d(TAG, line);
        //    showMsg(line);
        //}

        new OkHttpRequest.Builder().url(XlmmApi.LOGIN_URL)
            .addParams("username", login_name_value)
            .addParams("password", login_pass_value)
            .post(new OkHttpCallback() {
              @Override public void onError(Request request, Exception e) {

              }

              @Override public void onResponse(Response response, Object data) {

              }
            });
      } catch (Exception e) {
        Log.e(TAG, "post login info error");
      }
    }
  }

  //独立线程中无法有更新界面操作，必须Handler
  class MyHandler extends Handler {

    @Override public void handleMessage(Message msg) {
      String str = (String) msg.obj;
      //Toast.makeText(SettingLoginActivity.this, str, Toast.LENGTH_LONG).show();
      Log.d(TAG, "response " + str);

      if (parseRespResult(str) == 0) {

        //mShare();
        editor.putString("success", "true");
        editor.commit();

        Toast.makeText(SettingLoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
        editor.putString("name", login_name_value);
        editor.putString("password", login_pass_value);
        editor.commit();

        Log.d(TAG, "login success, return to MainActivity");
        Intent intent = new Intent(SettingLoginActivity.this, MainActivity.class);
        startActivity(intent);
      } else {

        editor.putString("success", "false");
        editor.commit();
        Toast.makeText(SettingLoginActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
        Log.d(TAG, "login failed ");
      }
    }
  }

  public class RespData {
    int returnCode;
    String result;
    String nextUrl;
  }
}
