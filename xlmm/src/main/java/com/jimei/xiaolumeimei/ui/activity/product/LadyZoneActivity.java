package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.adapter.CategoryProductAdapter;
import com.jimei.xiaolumeimei.adapter.ColorTagAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

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
    @Bind(R.id.xrv_category)
    XRecyclerView xRecyclerView;
    @Bind(R.id.title)
    XlmmTitleView titleView;

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
    private int type;
    private String title;
    private int page;

    private CategoryAdapter adapter;
    private Menu menu;
    private CategoryProductAdapter categoryProductAdapter;
    private int cid;
    private String next;

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
        page = 1;
        initPriceData();
        initColorData();
    }

    private void initCategory() {
        new CategoryTask(adapter, menu).execute(type);
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
        if (extras != null) {
            type = extras.getInt("type");
            title = extras.getString("title");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lady_zone;
    }

    @Override
    protected void initViews() {
        titleView.setName(title);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setPullRefreshEnabled(false);
        categoryProductAdapter = new CategoryProductAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(categoryProductAdapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    refreshData(cid,false);
                } else {
                    JUtils.Toast("已经到底啦!");
                    recyclerView.post(recyclerView::loadMoreComplete);
                }
            }
        });


        GridLayoutManager manager2 = new GridLayoutManager(this, 3);
        xRecyclerView.setLayoutManager(manager2);
        xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        adapter = new CategoryAdapter(this, new ArrayList<>());
        xRecyclerView.setAdapter(adapter);
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
                    runOnUiThread(() -> {
                        priceGridView.setVisibility(View.GONE);
                        tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                        ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
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
                        colorGridView.setVisibility(View.GONE);
                        tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                        ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
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
                colorGridView.setVisibility(View.GONE);
                priceGridView.setVisibility(View.GONE);
                tvPrice.setTextColor(getResources().getColor(R.color.text_color_A0));
                ivPrice.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                tvColor.setTextColor(getResources().getColor(R.color.text_color_A0));
                ivColor.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_category));
                if (xRecyclerView.getVisibility() == View.VISIBLE) {
                    xRecyclerView.setVisibility(View.GONE);
                } else {
                    xRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshData(int cid,boolean clear) {
        this.cid = cid;
        xRecyclerView.setVisibility(View.GONE);
        showIndeterminateProgressDialog(false);
        Subscription subscribe = ProductModel.getInstance()
                .getCategoryProductList(cid, page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CategoryProductListBean>() {


                    @Override
                    public void onNext(CategoryProductListBean categoryProductListBean) {
                        List<CategoryProductListBean.ResultsBean> results = categoryProductListBean.getResults();
                        if (results != null && results.size() > 0) {
                            categoryProductAdapter.update(results);
                        }
                        next = categoryProductListBean.getNext();
                        if (next != null && !"".equals(next)) {
                            page++;
                        }
                        hideIndeterminateProgressDialog();
                        recyclerView.post(recyclerView::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideIndeterminateProgressDialog();
                        recyclerView.post(recyclerView::loadMoreComplete);
                        JUtils.Toast("数据加载有误!");
                    }
                });
        addSubscription(subscribe);
    }

    private class CategoryTask extends AsyncTask<Integer, Integer, List<CategoryBean.ChildsBean>> {

        private CategoryAdapter adapter;
        private Menu menu;

        public CategoryTask(CategoryAdapter adapter, Menu menu) {
            this.adapter = adapter;
            this.menu = menu;
        }

        @Override
        protected List<CategoryBean.ChildsBean> doInBackground(Integer... params) {
            String categoryStr;
            InputStream in = null;
            String fileaddress = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/xlmmcategory/" + "category.json";
            try {
                if (FileUtils.isFileExist(fileaddress)) {
                    File file = new File(fileaddress);
                    in = new FileInputStream(file);
                } else {
                    return null;
                }
                byte[] arrayOfByte = new byte[in.available()];
                in.read(arrayOfByte);
                categoryStr = new String(arrayOfByte, "UTF-8");
                Gson gson = new Gson();
                List<CategoryBean> list = gson.fromJson(categoryStr, new TypeToken<List<CategoryBean>>() {
                }.getType());

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCid() == params[0]) {
                        return list.get(i).getChilds();
                    }
                }
                return null;
            } catch (Exception e) {
                JUtils.Log(e.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        JUtils.Log(e.getMessage());
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CategoryBean.ChildsBean> list) {
            if (list != null && list.size() > 0) {
                adapter.update(list);
            } else {
                menu.removeItem(R.id.category);
            }
        }
    }
}
