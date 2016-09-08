package com.jimei.xiaolumeimei.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by wisdom on 16/9/6.
 */
public class SchemaJumpUtils {
    public static void jump(Context context, String path) {
        if (path == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences preferences =
                context.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        Bundle bundle = new Bundle();
        bundle.putString("cookies", preferences.getString("cookiesString", ""));
        bundle.putString("domain", preferences.getString("cookiesDomain", ""));
        bundle.putString("Cookie", preferences.getString("Cookie", ""));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
