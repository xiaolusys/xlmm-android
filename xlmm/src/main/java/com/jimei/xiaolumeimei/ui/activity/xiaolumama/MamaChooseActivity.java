package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChooseAdapter;
import com.jimei.xiaolumeimei.adapter.ChooseListAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityMamaChooseBinding;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16/12/19.
 */

public class MamaChooseActivity extends BaseMVVMActivity<ActivityMamaChooseBinding> implements
        View.OnClickListener, PopupWindow.OnDismissListener {

    public static final String COMMISSION = "rebet_amount";//佣金
    public static final String SALES = "sale_num";//销量
    public static final int UP = 0;//销量
    public static final int DOWN = 1;//销量

    private int page;
    private String sort_field = "";
    private String cid = "";
    private String next;
    private int reverse = UP;
    private ChooseListAdapter adapter;
    private List<String> cidList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private PopupWindow popupWindow;
    private ChooseAdapter chooseAdapter;

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
                            nameList.add(categoryBeanList.get(i).getName());
                        }
                        chooseAdapter.updateWithClear(nameList);
                    }
                }));
        loadData(true);
    }

    @Override
    protected void initViews() {
        cidList.add("");
        nameList.add("全部");
        b.rvChoose.setLayoutManager(new LinearLayoutManager(this));
        b.rvChoose.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        b.rvChoose.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.rvChoose.setRefreshProgressStyle(ProgressStyle.BallPulse);
        adapter = new ChooseListAdapter(this);
        b.rvChoose.setAdapter(adapter);
        b.rvChoose.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                adapter.clear();
                loadData(false);
            }

            @Override
            public void onLoadMore() {
                if (next == null || "".equals(next)) {
                    JUtils.Toast("已经到底啦");
                    b.rvChoose.loadMoreComplete();
                } else {
                    loadData(false);
                }
            }
        });

        View view = LayoutInflater.from(this).inflate(R.layout.pop_choose_list, null);
        RecyclerView recyclerView = ((RecyclerView) view.findViewById(R.id.recycler_view));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        chooseAdapter = new ChooseAdapter(this) {
            @Override
            public void doWhenOnClick(View view, int position) {
                b.tvCommission.setTextColor(getResources().getColor(R.color.text_color_4A));
                b.tvSale.setTextColor(getResources().getColor(R.color.text_color_4A));
                b.tvMain.setText(nameList.get(position));
                sort_field = "";
                cid = cidList.get(position);
                page = 1;
                reverse = UP;
                loadData(true);
                popupWindow.dismiss();
            }
        };
        recyclerView.setAdapter(chooseAdapter);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.choose_pop_bg));
        popupWindow.setOnDismissListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_choose;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    protected void setListener() {
        b.rlMain.setOnClickListener(this);
        b.tvCommission.setOnClickListener(this);
        b.tvSale.setOnClickListener(this);
    }

    private void loadData(boolean isClear) {
        if (isClear) {
            showIndeterminateProgressDialog(false);
        }
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
                        b.rvChoose.loadMoreComplete();
                        b.rvChoose.refreshComplete();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        b.rvChoose.loadMoreComplete();
                        b.rvChoose.refreshComplete();
                        hideIndeterminateProgressDialog();
                        JUtils.Toast("数据加载有误!");
                    }
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commission:
                b.tvCommission.setTextColor(getResources().getColor(R.color.colorAccent));
                b.tvSale.setTextColor(getResources().getColor(R.color.text_color_4A));
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
                b.tvSale.setTextColor(getResources().getColor(R.color.colorAccent));
                b.tvCommission.setTextColor(getResources().getColor(R.color.text_color_4A));
                if (reverse == UP) {
                    reverse = DOWN;
                } else {
                    reverse = UP;
                }
                sort_field = SALES;
                page = 1;
                loadData(true);
                break;
            case R.id.rl_main:
                popupWindow.showAsDropDown(b.tvMain);
                b.img.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDismiss() {
        b.img.setVisibility(View.VISIBLE);
    }
}
