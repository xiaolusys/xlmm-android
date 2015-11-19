package so.xiaolu.xiaolu.UI;

import so.xiaolu.xiaolu.asynctask.ProductDeailAsyncTask;
import so.xiaolu.xiaolu.customwidget.PullPushLayout;
import so.xiaolu.xiaolu.customwidget.PullPushLayout.OnTouchEventMoveListenre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

public class ProductDetail extends Activity {

    private View btnBack;
    private View btnShare;        //标题分享
    private View navBar;
    private View lineNavBar;

    private PullPushLayout mLayout;
    private Drawable bgBackDrawable;
    private Drawable bgShareDrawable;
    private Drawable bgNavBarDrawable;
    private Drawable bglineNavBarDrawable;

    private int alphaMax = 180;

    private String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = (View) inflater.inflate(R.layout.content_product_detail, null, true);
        setContentView(view);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        product_id = data.getString("product_id");
        initView();
        MainUrl url = new MainUrl();
        ProductDeailAsyncTask productDeailAsyncTask = new ProductDeailAsyncTask(view, getApplicationContext(), url.getPRODUCT_URL() + product_id + "/details.json");
        productDeailAsyncTask.execute(1000);

    }

    private void initView() {
        mLayout = (PullPushLayout) this.findViewById(R.id.detail_layout);
        mLayout.setOnTouchEventMoveListenr(new OnTouchEventMoveListenre() {

            @Override
            public void onSlideUp(int mOriginalHeaderHeight, int mHeaderHeight) {

            }

            @Override
            public void onSlideDwon(int mOriginalHeaderHeight, int mHeaderHeight) {

            }

            @Override
            public void onSlide(int alpha) {
                int alphaReverse = alphaMax - alpha;
                if (alphaReverse < 0) {
                    alphaReverse = 0;
                }
                bgBackDrawable.setAlpha(alphaReverse);
                bgShareDrawable.setAlpha(alphaReverse);
                bgNavBarDrawable.setAlpha(alpha);
                bglineNavBarDrawable.setAlpha(alpha);

            }
        });
        navBar = this.findViewById(R.id.nav_bar);
        lineNavBar = this.findViewById(R.id.line_nav_bar);
        btnBack = this.findViewById(R.id.iv_back);
        btnShare = this.findViewById(R.id.iv_share);
        bgBackDrawable = btnBack.getBackground();
        bgBackDrawable.setAlpha(alphaMax);
        bgShareDrawable = btnShare.getBackground();
        bgShareDrawable.setAlpha(alphaMax);
        bgNavBarDrawable = navBar.getBackground();
        bglineNavBarDrawable = lineNavBar.getBackground();
        bgNavBarDrawable.setAlpha(0);
        bglineNavBarDrawable.setAlpha(0);

    }
}