package so.xiaolu.xiaolu.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yann on 15-11-18.
 */


public class UrlUtils {
    private static final String TAG = "UrlUtils";

    public static String fixPosterUrl(final String urlOrigin) {
        String url = "";
        String[] temp = urlOrigin.split("http://image.xiaolu.so/");
        if (temp.length > 1) {
            try {
                url = "http://image.xiaolu.so/" + URLEncoder.encode(temp[1], "utf-8") + "?imageMogr2/auto-orient/strip/size-limit/100k/q/85/";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Log.d("huangyan",url);
        return url;
    }
}
