package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllOrderAdapter;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.List;

import rx.Subscription;


public class OrderListFragment extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener {

    private int type;
    private ListView listView;
    private RelativeLayout emptyLayout;
    private Button button;
    private AllOrderAdapter adapter;
    private Subscription subscribe;
    private int page = 2;
    private XlmmLoadingDialog loadingdialog;

    public static OrderListFragment newInstance(int type, String title) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        listView = ((ListView) view.findViewById(R.id.lv));
        emptyLayout = ((RelativeLayout) view.findViewById(R.id.rlayout_order_empty));
        button = ((Button) view.findViewById(R.id.btn_jump));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        adapter = new AllOrderAdapter(getActivity());
        listView.setAdapter(adapter);
        button.setOnClickListener(this);
        listView.setOnScrollListener(this);
    }

    public String getTitle() {
        String title = "";
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        return title;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscribe != null && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    private void loadMoreData(String pageStr) {
        subscribe = TradeModel.getInstance()
                .getOrderList(type, pageStr)
                .subscribe(new ServiceResponse<AllOrdersBean>() {
                    @Override
                    public void onNext(AllOrdersBean allOrdersBean) {
                        if (allOrdersBean != null) {
                            hideIndeterminateProgressDialog();
                            List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
                            if (results.size() == 0 && "1".equals(pageStr)) {
                                emptyLayout.setVisibility(View.VISIBLE);
                            } else {
                                adapter.update(results);
                            }
                            if (null == allOrdersBean.getNext() && !"1".equals(pageStr)) {
                                JUtils.Toast("全部订单加载完成!");
                            }
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        page = 2;
        adapter.clearAll();
        showIndeterminateProgressDialog(false);
        loadMoreData("1");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager =
                    Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void showIndeterminateProgressDialog(boolean horizontal) {
        loadingdialog = XlmmLoadingDialog.create(getActivity())
                .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
                .setCancellable(!horizontal)
                .show();
    }

    public void hideIndeterminateProgressDialog() {
        try {
            loadingdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                loadMoreData(page + "");
                page++;
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
