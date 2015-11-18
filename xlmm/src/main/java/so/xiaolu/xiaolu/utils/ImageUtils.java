package so.xiaolu.xiaolu.utils;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import so.xiaolu.xiaolu.R;

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static void loadImage(final String url, final GridView mGridView) {

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
    public static void loadImageView(final String url, final ImageView mimageView) {
        ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
        ImageListener listener = ImageLoader.getImageListener(mimageView, R.drawable.default_product, R.drawable.default_product);
        imageLoader.get(url, listener);
    }

}
