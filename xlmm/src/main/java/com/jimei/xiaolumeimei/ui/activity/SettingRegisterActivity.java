package com.jimei.xiaolumeimei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class SettingRegisterActivity extends AppCompatActivity {

  EditText re_name;
  EditText re_password;
  EditText re_password2;
  EditText re_Email;
  EditText re_phone;
  EditText re_postalAdress;
  Button re_button;
  String name, password, password2, Email, phone, postalAddress;
  mHandle myHandler = new mHandle();//初始化Handler
  String TAG = "SettingRegisterActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_register_activity);
    this.setTitle("注册");

    re_name = (EditText) findViewById(R.id.set_register_name);
    re_password = (EditText) findViewById(R.id.set_register_password);
    re_password2 = (EditText) findViewById(R.id.set_register_password2);
    re_Email = (EditText) findViewById(R.id.set_register_address);
    re_phone = (EditText) findViewById(R.id.set_register_phone);
    re_postalAdress = (EditText) findViewById(R.id.set_register_postalAddress);
    re_button = (Button) findViewById(R.id.set_register_button);

    re_button.setOnClickListener(view -> {
      // TODO Auto-generated method stub
      name = re_name.getText().toString();
      password = re_password.getText().toString();
      password2 = re_password2.getText().toString();
      Email = re_Email.getText().toString();
      phone = re_phone.getText().toString();
      postalAddress = re_postalAdress.getText().toString();

      Log.d("输入内容", name + password + password2 + Email);
      if (name.isEmpty()
          || password.isEmpty()
          || password2.isEmpty()
          || Email.isEmpty()) {
        Toast toast = Toast.makeText(SettingRegisterActivity.this, "有注册内容未填写",
            Toast.LENGTH_SHORT);
        toast.show();
      } else {
        if (password.indexOf(password2) < 0) {
          Toast toast = Toast.makeText(SettingRegisterActivity.this, "两次输入密码不一致",
              Toast.LENGTH_SHORT);
          toast.show();
        } else {
          ReMyThread th = new ReMyThread();
          th.start();
        }
      }
    });
  }

  public void showMsg(String str) {
    Message msg = new Message();
    msg.obj = str;
    msg.setTarget(myHandler);
    msg.sendToTarget();
    Log.d(TAG, "showMsg " + str);
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

  public class mHandle extends Handler {

    @Override public void handleMessage(Message msg) {
      String str = (String) msg.obj;
      Toast.makeText(SettingRegisterActivity.this, "注册：" + str, Toast.LENGTH_LONG).show();
      String value1 = "success";
      if (str.indexOf(value1) >= 0) {
        Intent intent = new Intent(SettingRegisterActivity.this, MainActivity.class);
        startActivity(intent);
      }
    }
  }

  public class ReMyThread extends Thread {

    @Override public void run() {
      try {

        name = re_name.getText().toString();
        password = re_password.getText().toString();
        password2 = re_password2.getText().toString();
        Email = re_Email.getText().toString();
        phone = re_phone.getText().toString();
        postalAddress = re_postalAdress.getText().toString();

        //DefaultHttpClient client = new DefaultHttpClient();
        //List<NameValuePair> list = new ArrayList<NameValuePair>();
        //NameValuePair pair1 = new BasicNameValuePair("name", name);
        //
        //NameValuePair pair2 = new BasicNameValuePair("password", password);
        //NameValuePair pair3 = new BasicNameValuePair("Email", Email);
        //NameValuePair pair4 = new BasicNameValuePair("phone", phone);
        //NameValuePair pair5 = new BasicNameValuePair("PostalAddress", postalAddress);
        //list.add(pair1);
        //list.add(pair2);
        //list.add(pair3);
        //list.add(pair4);
        //list.add(pair5);
        //
        //Log.d("数据穿入", name);
        //
        //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        //
        //MainUrl url = new MainUrl();
        //HttpPost post = new HttpPost(url.getSETTINGRESGISTER_URL());
        //post.setEntity(entity);
        //
        //HttpResponse response = client.execute(post);
        //if (response.getStatusLine().getStatusCode() == 200) {
        //    InputStream in = response.getEntity().getContent();//接收服务器的数据，并在Toast上显示
        //    BufferedReader str = new BufferedReader(
        //            new InputStreamReader(in));
        //    String line = null;
        //    line = str.readLine();
        //
        //    showMsg(line);
        //}

        new OkHttpRequest.Builder().url(XlmmApi.LOGIN_URL)
            .addParams("name", name)
            .addParams("password", password)
            .addParams("Email", Email)
            .addParams("phone", phone)
            .addParams("PostalAddress", postalAddress)
            .post(new OkHttpCallback() {
              @Override public void onError(Request request, Exception e) {

              }

              @Override public void onResponse(Response response, Object data) {

              }
            });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
