/**
 * Copyright 2014 Zhenguo Jin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jimei.xiaolumeimei.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.glidemoudle.CropCircleTransformation;
import com.jimei.xiaolumeimei.glidemoudle.GlideRoundTransform;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

/**
 * ViewUtils
 */
public final class ViewUtils {

    private static final String CLASS_NAME_GRID_VIEW = "android.widget.GridView";
    private static final String FIELD_NAME_VERTICAL_SPACING = "mVerticalSpacing";

    /**
     * Don't let anyone instantiate this class.
     */
    private ViewUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * get ListView height according to every children
     */
    public static int getListViewHeightBasedOnChildren(ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        ListAdapter adapter;
        int adapterCount;
        if (view != null
                && (adapter = view.getAdapter()) != null
                && (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    /**
     * get GridView vertical spacing
     */
    public static int getGridViewVerticalSpacing(GridView view) {
        // get mVerticalSpacing by android.widget.GridView
        Class<?> demo = null;
        int verticalSpacing = 0;
        try {
            demo = Class.forName(CLASS_NAME_GRID_VIEW);
            Field field = demo.getDeclaredField(FIELD_NAME_VERTICAL_SPACING);
            field.setAccessible(true);
            verticalSpacing = (Integer) field.get(view);
            return verticalSpacing;
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException,
             * NoSuchFieldException, InstantiationException,
             * IllegalArgumentException, IllegalAccessException,
             * NullPointException
             */
            e.printStackTrace();
        }
        return verticalSpacing;
    }

    /**
     * get AbsListView height according to every children
     */
    public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
        ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }

        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(
                        new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }

    /**
     * get Font height
     */
    public static int getFontHeight(TextView view) {
        Paint paint = new Paint();
        paint.setTextSize(view.getTextSize());
        FontMetrics fm = paint.getFontMetrics();
        return (int) (Math.ceil(fm.descent - fm.ascent));
    }

    /**
     * set view height
     */
    public static void setViewHeight(View view, int height) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    // /**
    // * set GistView height which is calculated by {@link #
    // getGridViewHeightBasedOnChildren(GridView)}
    // *
    // * @param view
    // * @return
    // */
    // public static void setGridViewHeightBasedOnChildren(GridView view) {
    // setViewHeight(view, getGridViewHeightBasedOnChildren(view));
    // }

    /**
     * set ListView height which is calculated by
     * {@link # getListViewHeightBasedOnChildren(ListView)}
     */
    public static void setListViewHeightBasedOnChildren(ListView view) {
        setViewHeight(view, getListViewHeightBasedOnChildren(view));
    }

    /**
     * set AbsListView height which is calculated by
     * {@link # getAbsListViewHeightBasedOnChildren(AbsListView)}
     */
    public static void setAbsListViewHeightBasedOnChildren(AbsListView view) {
        setViewHeight(view, getAbsListViewHeightBasedOnChildren(view));
    }

    /**
     * set SearchView OnClickListener
     */
    public static void setSearchViewOnClickListener(View v, OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    /**
     * get descended views from parent.
     *
     * @param filter          Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass
     *                        of
     *                        filter or not.
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter,
                                                          boolean includeSubClass) {
        List<T> descendedViewList = new ArrayList<T>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childsClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childsClass)) || (!includeSubClass
                    && childsClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                descendedViewList.addAll(getDescendants((ViewGroup) child, filter, includeSubClass));
            }
        }
        return descendedViewList;
    }

    /**
     * Helps determine if the app is running in a Tablet context.
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void loadImgToImgView(Context context, ImageView img, String picPath) {
        if (null == picPath) return;
        if (picPath.contains("wx.qlogo.cn")
                || picPath.contains("7xogkj.com1.z0.glb.clouddn.com")
                || picPath.contains("mmbiz.qlogo.cn")) {
            Glide.with(context)
                    .load(picPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(img);
        } else {
            String head_img;
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher m = p.matcher(picPath);
            if (m.find()) {
                String group = m.group();
                String[] temp = picPath.split(group + "/");
                if (temp.length > 1) {
                    try {
                        head_img = "http://"
                                + group
                                + "/"
                                + URLEncoder.encode(temp[1], "utf-8")
                                + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/80";
                        Glide.with(context)
                                .load(head_img)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .centerCrop()
                                .into(img);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Glide.with(context)
                        .load(picPath)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .into(img);
            }
        }
    }

    public static void loadActivityToImgView(Context context, ImageView img, String picPath) {
        if (null == picPath) return;
        Glide.with(context)
                .load(picPath + "?imageMogr2/format/jpg")
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

    public static void loadImgToImgView(Context context, ImageView img, String picPath, int radius) {
        if (null == picPath) return;
        if (picPath.contains("mmbiz.qlogo.cn") || picPath.contains("wx.qlogo.cn") || picPath.contains(
                "7xogkj.com1.z0.glb.clouddn.com")) {
            Glide.with(context)
                    .load(picPath)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, radius))
                    .into(img);
        } else {
            String head_img;
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher m = p.matcher(picPath);
            if (m.find()) {
                String group = m.group();
                String[] temp = picPath.split(group + "/");
                if (temp.length > 1) {
                    try {
                        head_img = "http://"
                                + group
                                + "/"
                                + URLEncoder.encode(temp[1], "utf-8")
                                + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/80";
                        Glide.with(context)
                                .load(head_img)
                                .thumbnail(0.1f)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .centerCrop()
                                .transform(new GlideRoundTransform(context, radius))
                                .into(img);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Glide.with(context)
                        .load(picPath)
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .transform(new GlideRoundTransform(context, radius))
                        .into(img);
            }
        }
    }

    public static void loadImgToImgViewWithTransformCircle(Context context, ImageView img,
                                                           String picPath) {
        if (null == picPath) return;

        if (picPath.contains("wx.qlogo.cn")
                || picPath.contains("7xogkj.com1.z0.glb.clouddn.com")
                || picPath.contains("mmbiz.qlogo.cn")) {
            Glide.with(context)
                    .load(picPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(img);
        } else {
            String head_img;
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher m = p.matcher(picPath);
            if (m.find()) {
                String group = m.group();
                String[] temp = picPath.split(group + "/");
                if (temp.length > 1) {
                    try {
                        head_img = "http://"
                                + group
                                + "/"
                                + URLEncoder.encode(temp[1], "utf-8")
                                + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/80";
                        Glide.with(context)
                                .load(head_img)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .bitmapTransform(new CropCircleTransformation(context))
                                .into(img);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Glide.with(context)
                        .load(picPath)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(img);
            }
        }
    }

    public static void loadImgToHead(Context context, ImageView img, String picPath) {
        Glide.with(context)
                .load(picPath)
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(img);
    }

    public static void loadImgToImgViewWithPlaceholder(Context context, ImageView img,
                                                       String picPath) {
        if (null == picPath) return;
        if (picPath.contains("wx.qlogo.cn")
                || picPath.contains("7xogkj.com1.z0.glb.clouddn.com")
                || picPath.contains("mmbiz.qlogo.cn")) {
            Glide.with(context)
                    .load(picPath)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(img);
        } else {
            String head_img;
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher m = p.matcher(picPath);
            if (m.find()) {
                String group = m.group();
                String[] temp = picPath.split(group + "/");
                if (temp.length > 1) {
                    try {
                        head_img = "http://"
                                + group
                                + "/"
                                + URLEncoder.encode(temp[1], "utf-8")
                                + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/80";
                        Glide.with(context)
                                .load(head_img)
                                .thumbnail(0.1f)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .centerCrop()
                                .placeholder(R.drawable.parceholder)
                                .into(img);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Glide.with(context)
                        .load(picPath)
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .into(img);
            }
        }
    }

    public static void loadImgToImgViewWithPlaceholderFragment(Fragment context, ImageView img,
                                                               String picPath) {
        if (null == picPath) return;
        if (picPath.contains("wx.qlogo.cn")
                || picPath.contains("7xogkj.com1.z0.glb.clouddn.com")
                || picPath.contains("mmbiz.qlogo.cn")) {
            Glide.with(context)
                    .load(picPath)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(img);
        } else {
            String head_img;
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher m = p.matcher(picPath);
            if (m.find()) {
                String group = m.group();
                String[] temp = picPath.split(group + "/");
                if (temp.length > 1) {
                    try {
                        head_img = "http://"
                                + group
                                + "/"
                                + URLEncoder.encode(temp[1], "utf-8")
                                + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/70";
                        Glide.with(context)
                                .load(head_img)
                                .thumbnail(0.1f)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .centerCrop()
                                .placeholder(R.drawable.parceholder)
                                .into(img);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Glide.with(context)
                        .load(picPath)
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .into(img);
            }
        }
    }

    public static void loadImageWithOkhttp(String picpath, Activity context,
                                           ImageView imageView, int size) {
        OkHttpUtils.get().url(picpath).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Bitmap response, int id) {
                if (null != response) {
                    int width = response.getWidth();
                    int height = response.getHeight();
                    imageView.setAdjustViewBounds(true);
                    int screenWidth = DisplayUtils.getScreenW(context) / size;
                    int value = (int) ((height * 1.0 / width) * 2 * screenWidth / 3 + screenWidth / 5);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth, value);
                    imageView.setLayoutParams(lp);
                    imageView.setMaxWidth(screenWidth);
                    imageView.setMaxHeight(value);
                    imageView.setPadding(screenWidth / 6, screenWidth / 10, screenWidth / 6, screenWidth / 10);
                    imageView.setImageBitmap(response);
                }
            }
        });
    }

    public static RelativeLayout.LayoutParams getLayoutParams(Bitmap bitmap, int screenWidth) {

        float rawWidth = bitmap.getWidth();
        float rawHeight = bitmap.getHeight();

        float width = 0;
        float height = 0;

        if (rawWidth > screenWidth) {
            height = (rawHeight / rawWidth) * screenWidth;
            width = screenWidth;
        } else {
            width = rawWidth;
            height = rawHeight;
        }

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams((int) width, (int) height);

        return layoutParams;
    }
}
