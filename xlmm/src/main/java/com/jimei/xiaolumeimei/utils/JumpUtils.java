package com.jimei.xiaolumeimei.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.ui.activity.product.BrandListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.product.TongkuanActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AllCouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.mminfo.MMInfoActivity;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jude.utils.JUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 3/12/16.
 */
public class JumpUtils {

  public static final String TAG = "JumpUtils";

  public static void push_jump_proc(Context context, String recvContent) {
    JUtils.Log(TAG, "push_jump_proc:" + recvContent);

    if (TextUtils.isEmpty(recvContent)) return;

    JumpInfo jumpInfo = get_jump_info(recvContent);
    jumToProc(context, jumpInfo);
  }

  public static void jumToProc(Context context, JumpInfo jumpInfo) {
    Intent intent;
    switch (jumpInfo.getType()) {
      case XlmmConst.JUMP_PROMOTE_TODAY:
        intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        intent.putExtra("fragment", 1);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_PROMOTE_PREVIOUS:
        intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        intent.putExtra("fragment", 2);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_PRODUCT_CHILDLIST:
        intent = new Intent(context, ProductListActivity.class);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        intent.putExtra("fragment", 3);
        Bundle childBundle = new Bundle();
        childBundle.putInt("type",XlmmConst.TYPE_CHILD);
        intent.putExtras(childBundle);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_PRODUCT_LADYLIST:
        intent = new Intent(context, ProductListActivity.class);
        Bundle ladyBundle = new Bundle();
        ladyBundle.putInt("type",XlmmConst.TYPE_LADY);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        intent.putExtra("fragment", 4);
        intent.putExtras(ladyBundle);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_PRODUCT_MODELLIST:
        JUtils.Log(TAG, "jump to tongkuan");
        String model_id = get_jump_arg("model_id", jumpInfo.getUrl());
        JUtils.Log(TAG, "jump to tongkuan:" + model_id);
        if (null != model_id) {
          try {
            intent = new Intent(context, TongkuanActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("model_id", Integer.valueOf(model_id));
            intent.putExtra("name", "同款");
            context.startActivity(intent);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
        break;
      case XlmmConst.JUMP_PRODUCT_DETAIL:
        String product_id = get_jump_arg("product_id", jumpInfo.getUrl());
        if (null != product_id) {
          jumpToWebViewWithCookiesWithTask(context, product_id.replace("\"}", ""), -1,
              ProductPopDetailActvityWeb.class);
        }
        break;

      case XlmmConst.JUMP_PRODUCT_DETAIL_PUSH:
        String product_idpush;
        try {
          product_idpush =
              URLDecoder.decode(get_jump_arg("product_id", jumpInfo.getUrl()), "utf-8");
          JUtils.Log(TAG, "product_idpush==" + product_idpush);
          if (null != product_idpush) {
            jumpToWebViewWithCookiesWithTask(context, product_idpush.replace(" ", ""), -1,
                ProductPopDetailActvityWeb.class);
          }
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }

        break;
      case XlmmConst.JUMP_TRADE_DETAIL:
        String trade_id = get_jump_arg("trade_id", jumpInfo.getUrl());
        if (null != trade_id) {
          try {
            intent = new Intent(context, OrderDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("orderinfo", Integer.valueOf(trade_id));
            intent.putExtra("source", "Main");
            Log.d(TAG, "LinearLayout transfer orderid  " + trade_id + " to OrderDetailActivity");
            context.startActivity(intent);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
        break;
      case XlmmConst.JUMP_USER_COUPON:
        intent = new Intent(context, AllCouponActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_WEBVIEW:
        intent = new Intent(context, CommonWebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences sharedPreferences =
            context.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);

        String cookies = sharedPreferences.getString("cookiesString", "");
        String domain = sharedPreferences.getString("cookiesDomain", "");
        Bundle bundle = new Bundle();
        bundle.putString("cookies", cookies);
        bundle.putString("domain", domain);
        bundle.putString("actlink", jumpInfo.getUrl());
        bundle.putInt("id", jumpInfo.getId());
        intent.putExtras(bundle);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_XIAOLUMAMA:
        intent = new Intent(context, MMInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_XIAOLUMAMA_DAILYPOST:
        intent = new Intent(context, MMNinePicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_REFUNDS:
        intent = new Intent(context, AllRefundsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        break;
      case XlmmConst.JUMP_CARTS:
        if (LoginUtils.checkLoginState(context)) {
          intent = new Intent(context, CartActivity.class);
        } else {
          intent = new Intent(context, LoginActivity.class);
          bundle = new Bundle();
          bundle.putString("login", "cart");
          intent.putExtras(bundle);
        }
        context.startActivity(intent);
        ((Activity) context).finish();
        break;
      case XlmmConst.JUMP_TOPIC:
        intent = new Intent(context, BrandListActivity.class);
        bundle = new Bundle();
        bundle.putString("id", jumpInfo.getId() + "");
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        break;
    }
  }

  public static JumpInfo get_jump_info(String recvContent) {
    JUtils.Log(TAG, "content===" + recvContent);
    JumpInfo jumpInfo = new JumpInfo();
    String[] content = recvContent.split(XlmmConst.JUMP_PREFIX);
    JUtils.Log(TAG, "content[1] ===" + content[1]);
    try {
      if (!content[1].isEmpty()) {
        if (content[1].contains("promote_today")) {
          jumpInfo.setType(XlmmConst.JUMP_PROMOTE_TODAY);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("promote_previous")) {
          jumpInfo.setType(XlmmConst.JUMP_PROMOTE_PREVIOUS);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("childlist")) {
          jumpInfo.setType(XlmmConst.JUMP_PRODUCT_CHILDLIST);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("ladylist")) {
          jumpInfo.setType(XlmmConst.JUMP_PRODUCT_LADYLIST);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("modelist")) {
          jumpInfo.setType(XlmmConst.JUMP_PRODUCT_MODELLIST);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("product_id")) {
          if (content[1].contains("%")) {
            jumpInfo.setType(XlmmConst.JUMP_PRODUCT_DETAIL_PUSH);
          } else {
            jumpInfo.setType(XlmmConst.JUMP_PRODUCT_DETAIL);
          }
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("trade_id")) {
          jumpInfo.setType(XlmmConst.JUMP_TRADE_DETAIL);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("usercoupons")) {
          jumpInfo.setType(XlmmConst.JUMP_USER_COUPON);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("webview")) {
          int id = -1;
          String url = null;
          jumpInfo.setType(XlmmConst.JUMP_WEBVIEW);
          JUtils.Log(TAG, "&=======" + (int) ("&".charAt(0)));
          if (content[1].contains("activity_id")) {
            String urlContent = (content[1].split("webview?"))[1];
            JUtils.Log(TAG, "content[1] ===" + urlContent);

            List<Integer> integers = new ArrayList<>();
            for (int i = 0; i < urlContent.length(); i++) {
              if (content[1].charAt(i) == 38) {
                integers.add(i);
              }
            }

            int integer = integers.get(0);
            JUtils.Log(TAG, "integer ===" + integer);
            List<String> split = new ArrayList<>();
            JUtils.Log(TAG, "urlContent.substring(integer -6)" + urlContent.substring(integer-6,
                urlContent.length()));
            JUtils.Log(TAG,
                "urlContent.substring(0, integer - 7)" + urlContent.substring(1, integer-7));
            split.add(urlContent.substring(integer -6, urlContent.length()));
            split.add(urlContent.substring(1, integer - 7));

            //String[] split = urlContent.split("&");
            for (int i = 0; i < split.size(); i++) {
              JUtils.Log(TAG, "split[i]" + split.get(i));
              if (split.get(i).contains("url")) {
                String idStr[] = split.get(i).split("url=");
                url = idStr[1];
                JUtils.Log(TAG, "url" + url);
              } else if (split.get(i).contains("activity_id")) {
                String idStr[] = split.get(i).split("activity_id=");
                String s = idStr[1];
                id = Integer.parseInt(s);
                JUtils.Log(TAG, "id" + id);
              }
            }
          } else {
            url = content[1].substring(content[1].lastIndexOf("http"));
          }

          //String url1 = content[1].substring(content[1].lastIndexOf("http"));
          //JUtils.Log(TAG, "url1 ===" + url1);
          //if (url1.contains("is_native")) {
          //  String temp[] = url1.split("&is_native=");
          //  url = temp[0];
          //  JUtils.Log(TAG, "url====" + url);
          //}
          //if (content[1].contains("activity_id")) {
          //  String idStr[] = content[1].split("activity_id=");
          //  String s = idStr[1].split("&")[0];
          //  id = Integer.parseInt(s);
          //}
          try {
            jumpInfo.setUrl(URLDecoder.decode(url, "utf-8"));
            if (id != -1) {
              jumpInfo.setId(id);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else if (content[1].contains("vip_home")) {
          jumpInfo.setType(XlmmConst.JUMP_XIAOLUMAMA);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("vip_0day")) {
          jumpInfo.setType(XlmmConst.JUMP_XIAOLUMAMA_DAILYPOST);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("refunds")) {
          jumpInfo.setType(XlmmConst.JUMP_REFUNDS);
          jumpInfo.setUrl(content[1]);
        } else if (content[1].contains("shopping_cart")) {
          jumpInfo.setType(XlmmConst.JUMP_CARTS);
          jumpInfo.setUrl(content[1]);
        }
        //else if (content[1].contains("brand")) {
        //  jumpInfo.setType(XlmmConst.JUMP_TOPIC);
        //  int id;
        //  String idStr = ((content[1].split("brand?"))[1]).split("activity_id=")[1];
        //  id = Integer.parseInt(idStr);
        //  if (id != 0) {
        //    jumpInfo.setId(id);
        //  }
        //  jumpInfo.setUrl(content[1]);
        //}
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      e.printStackTrace();
    }

    JUtils.Log(TAG, jumpInfo.toString());
    return jumpInfo;
  }

  public static String get_jump_arg(String prefix, String recvContent) {
    String[] temp = recvContent.split(prefix + "=");
    if (temp.length > 1) {
      return temp[1];
    } else {
      return null;
    }
  }

  public static void jumpToWebViewWithCookies(Context context, String actlink, int id,
      Class<?> classname) {
    Intent intent = new Intent(context, classname);
    SharedPreferences sharedPreferences =
        context.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
    String cookies = sharedPreferences.getString("cookiesString", "");
    String domain = sharedPreferences.getString("cookiesDomain", "");
    Bundle bundle = new Bundle();
    bundle.putString("cookies", cookies);
    bundle.putString("domain", domain);
    bundle.putString("Cookie", sharedPreferences.getString("Cookie", ""));
    bundle.putString("actlink", actlink);
    bundle.putInt("id", id);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  public static void jumpToWebViewWithCookiesWithTask(Context context, String actlink, int id,
      Class<?> classname) {
    Intent intent = new Intent(context, classname);
    SharedPreferences sharedPreferences =
        context.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
    String cookies = sharedPreferences.getString("cookiesString", "");
    String domain = sharedPreferences.getString("cookiesDomain", "");
    Bundle bundle = new Bundle();
    bundle.putString("cookies", cookies);
    bundle.putString("domain", domain);
    bundle.putString("Cookie", sharedPreferences.getString("Cookie", ""));
    bundle.putString("actlink", actlink);
    bundle.putInt("id", id);
    intent.putExtras(bundle);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void jumpToWebViewWithCookies(Context context, String actlink, int id,
      Class<?> classname, String title) {
    Intent intent = new Intent(context, classname);
    SharedPreferences sharedPreferences =
        context.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
    String cookies = sharedPreferences.getString("cookiesString", "");
    String domain = sharedPreferences.getString("cookiesDomain", "");
    Bundle bundle = new Bundle();
    bundle.putString("cookies", cookies);
    bundle.putString("domain", domain);
    bundle.putString("Cookie", sharedPreferences.getString("Cookie", ""));
    bundle.putString("actlink", actlink);
    bundle.putString("title", title);
    bundle.putInt("id", id);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  public static class JumpInfo {
    int type;
    String url;
    int id;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    @Override public String toString() {
      return "JumpInfo{" +
          "type=" + type +
          ", url='" + url + '\'' +
          '}';
    }
  }
}
