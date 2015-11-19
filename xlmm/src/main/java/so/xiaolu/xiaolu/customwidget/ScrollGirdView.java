package so.xiaolu.xiaolu.customwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by huangyan on 15-11-16.
 */
public class ScrollGirdView extends GridView {
    boolean expanded = true;

    public ScrollGirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public ScrollGirdView(Context context) {
        super(context);
    }

    public ScrollGirdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (isExpanded()) {
            int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
//
//        int expandSpec = MeasureSpec.makeMeasureSpec(
//                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}