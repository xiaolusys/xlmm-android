package so.xiaolu.xiaolu.control;

import android.graphics.Bitmap;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.customwidget.ScrollGirdView;
import so.xiaolu.xiaolu.utils.ImageCacheUtil;
import so.xiaolu.xiaolu.utils.VolleyApplication;

/**
 * 图片缓存管理类 获取ImageLoader对象
 * Created by yann on 15-11-19.
 */
public class ImageCacheManager {

    private static String TAG = ImageCacheManager.class.getSimpleName();

    // 获取图片缓存类对象
    private static ImageCache mImageCache = new ImageCacheUtil();
    // 获取ImageLoader对象
    public static ImageLoader mImageLoader = new ImageLoader(VolleyRequestQueueManager.mRequestQueue, mImageCache);

    /**
     * 获取ImageListener
     *
     * @param view
     * @param defaultImage
     * @param errorImage
     * @return
     */
    public static ImageListener getImageListener(final ImageView view, final Bitmap defaultImage, final Bitmap errorImage) {

        return new ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // 回调失败
                if (errorImage != null) {
                    view.setImageBitmap(errorImage);
                }
            }

            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                // 回调成功
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImage != null) {
                    view.setImageBitmap(defaultImage);
                }
            }
        };

    }
    /**
     * 获取ImageListener
     *
     * @param mGridView
     * @param defaultImage
     * @param errorImage
     * @return
     */
    public static ImageListener getGirdImageListener(final String url, final GridView mGridView, final Bitmap defaultImage, final Bitmap errorImage) {

        return new ImageListener() {
            ImageView tmpImg = (ImageView) mGridView.findViewWithTag(url);
            @Override
            public void onErrorResponse(VolleyError error) {
                // 回调失败
                if (errorImage != null) {
                    tmpImg.setImageBitmap(errorImage);
                }
            }

            @Override
            public void onResponse(ImageContainer container, boolean isImmediate) {
                // 回调成功
//                if (container.getBitmap() != null) {
//                    mGridView.setImageBitmap(container.getBitmap());
//                } else if (defaultImage != null) {
//                    mGridView.setImageBitmap(defaultImage);
//                }


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

    }
    /**
     * 提供给外部调用方法
     *
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage) {
        mImageLoader.get(url, ImageCacheManager.getImageListener(view, defaultImage, errorImage), 0, 0);
    }

    /**
     * 提供给外部调用方法
     *
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage, int maxWidth, int maxHeight) {
        mImageLoader.get(url, ImageCacheManager.getImageListener(view, defaultImage, errorImage), maxWidth, maxHeight);
    }



    public static void loadGirdImage(final String url, final GridView mGridView, Bitmap defaultImage, Bitmap errorImage) {

        mImageLoader.get(url, ImageCacheManager.getGirdImageListener(url,mGridView, defaultImage, errorImage),0,0);
//        ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
//        ImageListener listener = new ImageListener() {
//            ImageView tmpImg = (ImageView) mGridView.findViewWithTag(url);
//
//            @Override
//            public void onErrorResponse(VolleyError arg0) {
//                tmpImg.setImageBitmap(null);
//            }
//
//            @Override
//            public void onResponse(ImageContainer container, boolean arg1) {
//
//                if (container != null) {
//                    tmpImg = (ImageView) mGridView.findViewWithTag(url);
//                    if (tmpImg != null) {
//                        if (container.getBitmap() == null) {
//                            tmpImg.setImageResource(R.drawable.default_product);
//                        } else {
//                            tmpImg.setImageBitmap(container.getBitmap());
//                        }
//                    }
//                }
//            }
//        };
//        ImageContainer newContainer = imageLoader.get(url, listener, 600, 700);
    }

}