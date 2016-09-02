package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.adapter.CategoryProductAdapter;
import com.jimei.xiaolumeimei.adapter.ColorTagAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityLadyZoneBinding;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.CategoryTask;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class LadyZoneActivity extends BaseMVVMActivity<ActivityLadyZoneBinding> implements View.OnClickListener, AdapterView.OnItemClickListener {

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
    private String type;
    private String title;
    private int page;

    private CategoryAdapter adapter;
    private Menu menu;
    private CategoryProductAdapter categoryProductAdapter;
    private String cid;
    private String next;
    private String category;

    @Override
    protected void initListener() {
        b.layoutPrice.setOnClickListener(this);
        b.layoutColor.setOnClickListener(this);
        b.gvColor.setOnItemClickListener(this);
        b.gvPrice.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        flag = -1;
        page = 1;
        initPriceData();
        initColorData();
        refreshData(type, true);
    }

    private void initCategory() {
        new CategoryTask(adapter, menu).execute(type,category);
    }

    private void initColorData() {
        colorData = new ArrayList<>();
        setColorData();
        colorAdapter = new ColorTagAdapter(this, colorData, R.layout.item_color_tag, colorFrom, colorTo);
        b.gvColor.setAdapter(colorAdapter);
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
        b.gvPrice.setAdapter(priceAdapter);
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
        if (extras != null) {
            type = extras.getString("type");
            title = extras.getString("title");
            category = extras.getString("category");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lady_zone;
    }

    @Override
    protected void initView() {
        b.title.setName(title);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        b.xrv.setLayoutManager(manager);
        b.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrv.addItemDecoration(new SpaceItemDecoration(10));
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        b.xrv.setArrowImageView(R.drawable.iconfont_downgrey);
        b.xrv.setPullRefreshEnabled(false);
        categoryProductAdapter = new CategoryProductAdapter(new ArrayList<>(), this);
        b.xrv.setAdapter(categoryProductAdapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    refreshData(cid, false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    b.xrv.post(b.xrv::loadMoreComplete);
                }
            }
        });


        GridLayoutManager manager2 = new GridLayoutManager(this, 3);
        b.xrvCategory.setLayoutManager(manager2);
        b.xrvCategory.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.xrvCategory.addItemDecoration(new SpaceItemDecoration(10));
        b.xrvCategory.setPullRefreshEnabled(false);
        b.xrvCategory.setLoadingMoreEnabled(false);
        adapter = new CategoryAdapter(this, new ArrayList<>());
        b.xrvCategory.setAdapter(adapter);
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
                b.gvColor.setVisibility(View.GONE);
                b.tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                b.ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                if (b.gvPrice.getVisibility() == View.VISIBLE) {
                    b.gvPrice.setVisibility(View.GONE);
                    b.tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                    b.ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                } else {
                    b.gvPrice.setVisibility(View.VISIBLE);
                    b.tvPrice.setTextColor(getResources().getColor(R.color.text_color_32));
                    b.ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_up_category));
                    flag = XlmmConst.FLAG_PRICE;
                }
                break;
            case R.id.layout_color:
                b.gvPrice.setVisibility(View.GONE);
                b.tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                b.ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                if (b.gvColor.getVisibility() == View.VISIBLE) {
                    b.gvColor.setVisibility(View.GONE);
                    b.tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                    b.ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                } else {
                    b.gvColor.setVisibility(View.VISIBLE);
                    b.tvColor.setTextColor(getResources().getColor(R.color.text_color_32));
                    b.ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_up_category));
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
                    runOnUiThread(() -> {
                        b.gvPrice.setVisibility(View.GONE);
                        b.tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                        b.ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                    });
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
                    runOnUiThread(() -> {
                        b.gvColor.setVisibility(View.GONE);
                        b.tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                        b.ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                    });
                }).start();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        this.menu = menu;
        initCategory();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category:
                b.gvColor.setVisibility(View.GONE);
                b.gvPrice.setVisibility(View.GONE);
                b.tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                b.ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                b.tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                b.ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                if (b.xrvCategory.getVisibility() == View.VISIBLE) {
                    b.xrvCategory.setVisibility(View.GONE);
                } else {
                    b.xrvCategory.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshData(String cid, boolean clear) {
        this.cid = cid;
        b.emptyLayout.setVisibility(View.GONE);
        if (clear) {
            showIndeterminateProgressDialog(false);
            categoryProductAdapter.clear();
            page = 1;
        }
        b.xrvCategory.setVisibility(View.GONE);
        Subscription subscribe = ProductModel.getInstance()
                .getCategoryProductList(cid, page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CategoryProductListBean>() {
                    @Override
                    public void onNext(CategoryProductListBean categoryProductListBean) {
                        List<CategoryProductListBean.ResultsBean> results = categoryProductListBean.getResults();
                        if (results != null && results.size() > 0) {
                            categoryProductAdapter.update(results);
                        } else {
                            b.emptyLayout.setVisibility(View.VISIBLE);
                        }
                        next = categoryProductListBean.getNext();
                        if (next != null && !"".equals(next)) {
                            page++;
                        }
                        hideIndeterminateProgressDialog();
                        b.xrv.post(b.xrv::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideIndeterminateProgressDialog();
                        b.xrv.post(b.xrv::loadMoreComplete);
                        JUtils.Toast("数据加载有误!");
                    }
                });
        addSubscription(subscribe);
    }
}
