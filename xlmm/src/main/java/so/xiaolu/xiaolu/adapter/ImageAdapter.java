package so.xiaolu.xiaolu.adapter;

/**
 * Created by yann on 15-11-17.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import so.xiaolu.xiaolu.utils.ImageUtils;
import so.xiaolu.xiaolu.utils.Images;
import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.utils.VolleyApplication;

public class ImageAdapter extends BaseAdapter implements OnScrollListener {
    private static final String TAG = "ImageAdapter";
    private Context context;
    private String[] items = Images.imageThumbUrls;
    private GridView mGridView;

    private boolean isFirstEnter;

    private int firstSeeItem;


    private int orifirstItem;

    private int totalSeeItem;

    public ImageAdapter(Context context, GridView mGridView) {
        this.context = context;
        this.mGridView = mGridView;

        mGridView.setOnScrollListener(this);
        isFirstEnter = true;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.imageitems, null);
        }
        imgView = (ImageView) convertView.findViewById(R.id.photo);
        imgView.setImageResource(R.drawable.default_product);

        imgView.setTag(items[position]);

        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.v(TAG, "imagedown--->onScroll");
        firstSeeItem = firstVisibleItem;
        totalSeeItem = visibleItemCount;

        if (isFirstEnter && visibleItemCount > 0) {
            orifirstItem = firstVisibleItem;
            startLoadImages(firstSeeItem, totalSeeItem);
            isFirstEnter = false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.v(TAG, "imagedown--->onScrollStateChanged");
        if (orifirstItem != firstSeeItem) {
            if (scrollState == SCROLL_STATE_IDLE) {
                startLoadImages(firstSeeItem, totalSeeItem);
                orifirstItem = firstSeeItem;
            } else {
                ImageUtils.cancelAllImageRequests();
            }
        }


    }

    private void startLoadImages(int first, int total) {
        Log.v(TAG, "imagedown--->startLoadImages,first-->" + first + ",total-->" + total);
        for (int i = first; i < first + total; i++) {
            ImageUtils.loadImage(items[i], mGridView);
        }
    }
}
