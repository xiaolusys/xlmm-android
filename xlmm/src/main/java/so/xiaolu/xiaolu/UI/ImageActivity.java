package so.xiaolu.xiaolu.UI;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import so.xiaolu.xiaolu.R;

import so.xiaolu.xiaolu.adapter.ImageAdapter;

public class ImageActivity extends Activity {
    private static final String TAG = "ImageActivity";
    private GridView mGridView;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.imagelayout);
        initViews();
    }

    private void initViews() {
        Log.i(TAG, "initViews");
        mGridView = (GridView) this.findViewById(R.id.grid_image);
        adapter = new ImageAdapter(this, mGridView);
        mGridView.setAdapter(adapter);

    }


}
