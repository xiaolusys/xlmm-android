package so.xiaolu.xiaolu.customwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by huangyan on 15-11-16.
 */
public class ScrollGirdView extends GridView {
    public ScrollGirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollGirdView(Context context) {
        super(context);
    }

    public ScrollGirdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}