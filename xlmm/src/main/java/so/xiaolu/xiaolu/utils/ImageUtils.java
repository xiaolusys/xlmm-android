package so.xiaolu.xiaolu.utils;
/**
 * Created by yann on 15-11-18.
 * 加载图片（海报，商品图）到widget的工具类
 */

import android.graphics.BitmapFactory;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.graphics.Bitmap;
import android.content.res.Resources;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.control.ImageCacheManager;

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static void loadImage(final String url, final GridView mGridView) {
       // ImageCacheManager.loadGirdImage(url, mGridView, getBitmapFromRes(R.drawable.default_product), getBitmapFromRes(R.drawable.default_product));
        ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
        ImageListener listener = new ImageListener() {
            ImageView tmpImg = (ImageView) mGridView.findViewWithTag(url);

            @Override
            public void onErrorResponse(VolleyError arg0) {
                tmpImg.setImageBitmap(null);
            }

            @Override
            public void onResponse(ImageContainer container, boolean arg1) {

                if (container != null) {
                    tmpImg = (ImageView) mGridView.findViewWithTag(url);
                    if (tmpImg != null) {
                        if (container.getBitmap() == null) {
                            tmpImg.setImageResource(R.drawable.default_product);
                        } else {
                            tmpImg.setImageBitmap(container.getBitmap());
                        }
                    }
                }
            }
        };
        ImageContainer newContainer = imageLoader.get(url, listener, 600, 700);
    }


    public static void cancelAllImageRequests() {
        ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
        RequestQueue requestQueue = VolleyApplication.getInstance().getRequestQueue();
        if (imageLoader != null && requestQueue != null) {
            int num = requestQueue.getSequenceNumber();
            //imageLoader.drain(num);
        }
    }

    public static void loadPoster(final String url, final ImageView mimageView) {

        ImageCacheManager.loadImage(url, mimageView, getBitmapFromRes(R.drawable.default_poster), getBitmapFromRes(R.drawable.default_poster));

//        ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
//        ImageListener listener = ImageLoader.getImageListener(mimageView, R.drawable.default_poster, R.drawable.default_poster);
//        imageLoader.get(url, listener);
    }

    public static void loadHead(final String url, final ImageView mimageView) {
        ImageCacheManager.loadImage(url, mimageView, getBitmapFromRes(R.drawable.default_product), getBitmapFromRes(R.drawable.default_product));

//        ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
//        ImageListener listener = ImageLoader.getImageListener(mimageView, R.drawable.default_product, R.drawable.default_product);
//        imageLoader.get(url, listener);
    }


    public static Bitmap getBitmapFromRes(int resId) {
        Resources res = VolleyApplication.getInstance().getResources();
        return BitmapFactory.decodeResource(res, resId);

    }
}
