package so.xiaolu.xiaolu.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import so.xiaolu.xiaolu.adapter.ProductListAdapter;
import so.xiaolu.xiaolu.customwidget.ScrollGirdView;

/**
 * Created by yann on 15-11-19.
 */
public class GirdUtils {

    public static void setGrideViewHeightBasedOnChildren(ScrollGirdView grid, ProductListAdapter adapter) {
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;

        View listItem = adapter.getView(0, null, grid);
        listItem.measure(0, 0);
        if (adapter.getCount() - 1 < 0) {
            totalHeight = listItem.getMeasuredHeight();
        } else {
            int line = adapter.getCount() / 3;
            if (adapter.getCount() % 3 != 0)
                line = line + 1;
            totalHeight = (listItem.getMeasuredHeight() + 30) * line;
        }

        ViewGroup.LayoutParams params = grid.getLayoutParams();
        params.height = totalHeight + 30;
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        grid.setLayoutParams(params);

    }
}
