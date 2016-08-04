package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
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
import com.jimei.xiaolumeimei.adapter.ColorTagAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class LadyZoneActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
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

    public int flag;

    private String[] priceName = {"全部价格", "¥0-25", "¥25-50", "¥50-100", "¥100-150", "¥150-250"};
    private boolean[] priceBack = {false, false, false, false, false, false};
    private String[] colorName = {"全部颜色", "红色", "橙色", "黄色", "绿色", "蓝色", "紫色", "粉色", "黑色", "白色", "灰色"};
    private boolean[] colorBack = {false, false, false, false, false, false, false, false, false, false, false};
    private int[] colorList = {R.color.no_color, R.color.color_1, R.color.color_2, R.color.color_3, R.color.color_4,
            R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8, R.color.color_9, R.color.color_10};

    String[] priceFrom = {"price", "bg"};
    int[] priceTo = {R.id.price, R.id.iv_bg};
    private SimpleAdapter priceAdapter;
    private List<Map<String, Object>> priceData;

    String[] colorFrom = {"img", "color", "bg"};
    int[] colorTo = {R.id.img, R.id.color, R.id.iv_bg};
    private ColorTagAdapter colorAdapter;
    private List<Map<String, Object>> colorData;

    @Override
    protected void setListener() {
        priceLayout.setOnClickListener(this);
        colorLayout.setOnClickListener(this);
        priceGridView.setOnItemClickListener(this);
        colorGridView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        flag = -1;
        initPriceData();
        initColorData();
    }

    private void initColorData() {
        colorData = new ArrayList<>();
        setColorData();
        colorAdapter = new ColorTagAdapter(this, colorData, R.layout.item_color_tag, colorFrom, colorTo);
        colorGridView.setAdapter(colorAdapter);
    }

    private void setColorData() {
        colorData.clear();
        for (int i = 0; i < colorName.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("img", colorList[i]);
            map.put("color", colorName[i]);
            if (colorBack[i]) {
                map.put("bg", R.drawable.bg_tag_selected);
            } else {
                map.put("bg", R.drawable.bg_tag_unselected);
            }
            colorData.add(map);
        }
    }

    private void initPriceData() {
        priceData = new ArrayList<>();
        setPriceData();
        priceAdapter = new SimpleAdapter(this, priceData, R.layout.item_price_tag, priceFrom, priceTo);
        priceGridView.setAdapter(priceAdapter);
    }

    private void setPriceData() {
        priceData.clear();
        for (int i = 0; i < priceName.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("price", priceName[i]);
            if (priceBack[i]) {
                map.put("bg", R.drawable.bg_tag_selected);
            } else {
                map.put("bg", R.drawable.bg_tag_unselected);
            }
            priceData.add(map);
        }
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
                    flag = XlmmConst.FLAG_PRICE;
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
                    flag = XlmmConst.FLAG_COLOR;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (flag) {
            case XlmmConst.FLAG_PRICE:
                for (int i = 0; i < priceBack.length; i++) {
                    priceBack[i] = (position == i);
                }
                setPriceData();
                priceAdapter.notifyDataSetChanged();
                new Thread(() -> {
                    SystemClock.sleep(200);
                    runOnUiThread(() -> priceGridView.setVisibility(View.GONE));
                }).start();
                break;
            case XlmmConst.FLAG_COLOR:
                for (int i = 0; i < colorBack.length; i++) {
                    colorBack[i] = (position == i);
                }
                setColorData();
                colorAdapter.notifyDataSetChanged();
                new Thread(() -> {
                    SystemClock.sleep(200);
                    runOnUiThread(() -> colorGridView.setVisibility(View.GONE));
                }).start();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category:
                // TODO: 16/8/2 分类按钮
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
