package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChooseListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MamaChooseActivity extends BaseSwipeBackCompatActivity implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String COMMISSION = "rebet_amount";//佣金
    public static final String SALES = "sale_num";//销量
    public static final int UP = 0;//销量
    public static final int DOWN = 1;//销量

    @Bind(R.id.spinner)
    Spinner mSpinner;
    @Bind(R.id.tv_commission)
    TextView commissionTv;
    @Bind(R.id.tv_sale)
    TextView saleTv;
    @Bind(R.id.rv_choose)
    XRecyclerView mRecyclerView;
    @Bind(R.id.layout)
    LinearLayout layout;

    private List<String> cidList = new ArrayList<>();
    private int page;
    private String sort_field = "";
    private String cid = "";
    private String next;
    private int reverse = UP;
    private ArrayList<String> strings;
    private ChooseListAdapter adapter;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void setListener() {
        commissionTv.setOnClickListener(this);
        saleTv.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initData() {
        page = 1;
        addSubscription(MamaInfoModel.getInstance()
                .getCategory()
                .subscribe(new ServiceResponse<List<CategoryBean>>() {
                    @Override
                    public void onNext(List<CategoryBean> categoryBeanList) {
                        for (int i = 0; i < categoryBeanList.size(); i++) {
                            cidList.add(categoryBeanList.get(i).getCid());
                            strings.add("  " + categoryBeanList.get(i).getName() + "  ");
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_choose;
    }

    @Override
    protected void initViews() {
        cidList.add("");
        strings = new ArrayList<>();
        strings.add("  " + "全部" + "  ");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_choosespinner, strings);
        arrayAdapter.setDropDownViewResource(R.layout.item_choosespinner_dropdown);
        mSpinner.setAdapter(arrayAdapter);
        adapter = new ChooseListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (next == null || "".equals(next)) {
                    JUtils.Toast("已经到底啦");
                    mRecyclerView.post(mRecyclerView::loadMoreComplete);
                } else {
                    loadData(false);
                }
            }
        });
    }

    private void loadData(boolean isClear) {
        showIndeterminateProgressDialog(false);
        addSubscription(MamaInfoModel.getInstance()
                .getChooseList(page, sort_field, cid, reverse)
                .subscribe(new ServiceResponse<ChooseListBean>() {
                    @Override
                    public void onNext(ChooseListBean chooseListBean) {
                        List<ChooseListBean.ResultsBean> results = chooseListBean.getResults();
                        next = chooseListBean.getNext();
                        if (results != null) {
                            if (isClear) {
                                adapter.updateWithClear(results);
                            } else {
                                adapter.update(results);
                            }
                        }
                        if (next != null && !"".equals(next)) {
                            page++;
                        }
                        hideIndeterminateProgressDialog();
                        mRecyclerView.post(mRecyclerView::loadMoreComplete);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                        mRecyclerView.post(mRecyclerView::loadMoreComplete);
                        JUtils.Toast("数据加载有误!");
                    }
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commission:
                commissionTv.setTextColor(getResources().getColor(R.color.colorAccent));
                saleTv.setTextColor(getResources().getColor(R.color.text_color_4A));
                if (reverse == UP) {
                    reverse = DOWN;
                } else {
                    reverse = UP;
                }
                sort_field = COMMISSION;
                page = 1;
                loadData(true);
                break;
            case R.id.tv_sale:
                saleTv.setTextColor(getResources().getColor(R.color.colorAccent));
                commissionTv.setTextColor(getResources().getColor(R.color.text_color_4A));
                if (reverse == UP) {
                    reverse = DOWN;
                } else {
                    reverse = UP;
                }
                sort_field = SALES;
                page = 1;
                loadData(true);
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        commissionTv.setTextColor(getResources().getColor(R.color.text_color_4A));
        saleTv.setTextColor(getResources().getColor(R.color.text_color_4A));
        sort_field = "";
        cid = cidList.get(position);
        page = 1;
        reverse = UP;
        loadData(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
