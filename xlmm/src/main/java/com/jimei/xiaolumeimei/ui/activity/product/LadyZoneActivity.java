package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class LadyZoneActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    @Bind(R.id.layout_price)
    LinearLayout priceLayout;
    @Bind(R.id.layout_color)
    LinearLayout colorLayout;
    @Bind(R.id.xrv)
    XRecyclerView recyclerView;
    @Bind(R.id.gv_price)
    GridView priceGridView;
    @Bind(R.id.gv_color)
    GridView colorGridView;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.iv_price)
    ImageView ivPrice;
    @Bind(R.id.tv_color)
    TextView tvColor;
    @Bind(R.id.iv_color)
    ImageView ivColor;

    private String[] priceName = {"全部价格", "¥0-25", "¥25-50", "¥50-100", "¥100-150", "¥150-250"};
    String[] from = {"price"};
    int[] to = {R.id.price};

    @Override
    protected void setListener() {
        priceLayout.setOnClickListener(this);
        colorLayout.setOnClickListener(this);
        priceGridView.setOnItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        List<Map<String, Object>> priceData = new ArrayList<>();
        for (String aPriceName : priceName) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("price", aPriceName);
            priceData.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, priceData, R.layout.item_price_tag, from, to);
        priceGridView.setAdapter(adapter);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lady_zone;
    }

    @Override
    protected void initViews() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_price:
                colorGridView.setVisibility(View.GONE);
                tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                if (priceGridView.getVisibility() == View.VISIBLE) {
                    priceGridView.setVisibility(View.GONE);
                    tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                    ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                } else {
                    priceGridView.setVisibility(View.VISIBLE);
                    tvPrice.setTextColor(getResources().getColor(R.color.text_color_32));
                    ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_up_category));
                }
                break;
            case R.id.layout_color:
                priceGridView.setVisibility(View.GONE);
                tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                if (colorGridView.getVisibility() == View.VISIBLE) {
                    colorGridView.setVisibility(View.GONE);
                    tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                    ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                } else {
                    colorGridView.setVisibility(View.VISIBLE);
                    tvColor.setTextColor(getResources().getColor(R.color.text_color_32));
                    ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_up_category));
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        view.findViewById(R.id.price).setBackgroundResource(R.drawable.bg_tag_selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
