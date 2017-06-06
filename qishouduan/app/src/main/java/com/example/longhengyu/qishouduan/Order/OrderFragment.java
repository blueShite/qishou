package com.example.longhengyu.qishouduan.Order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.longhengyu.qishouduan.Order.Adapter.OrderAdapter;
import com.example.longhengyu.qishouduan.Order.Bean.OrderListBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderListInterface;
import com.example.longhengyu.qishouduan.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class OrderFragment extends SupportFragment implements OrderListInterface {

    @BindView(R.id.checkText_order_take)
    CheckedTextView checkTextOrderTake;
    @BindView(R.id.checkText_order_give)
    CheckedTextView checkTextOrderGive;
    @BindView(R.id.checkText_order_success)
    CheckedTextView checkTextOrderSuccess;
    @BindView(R.id.order_recycler)
    RecyclerView orderRecycler;
    @BindView(R.id.take_line)
    View takeLine;
    @BindView(R.id.give_line)
    View giveLine;
    @BindView(R.id.success_line)
    View successLine;
    @BindView(R.id.order_refresh)
    TwinklingRefreshLayout orderRefresh;

    private OrderAdapter orderAdapter;
    private List<OrderListBean> beanList;


    public static OrderFragment newInstance() {

        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        checkTextOrderTake.setChecked(true);
        takeLine.setVisibility(View.VISIBLE);
        initView();
        return view;
    }

    private void initView() {

        beanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OrderListBean bean = new OrderListBean();
            bean.setName("123456");
            bean.setAddress("花园路北三环");
            bean.setPrice("11.1");
            bean.setFootTime("2017-06-06 11:11:11");
            bean.setOrderTime("2017-06-06 11:11:11");
            beanList.add(bean);
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        orderRecycler.setLayoutManager(manager);
        orderAdapter = new OrderAdapter(beanList, getContext(),this);
        orderRecycler.setAdapter(orderAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        orderRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        orderRefresh.setBottomView(loadingView);
        orderRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                orderRefresh.finishRefreshing();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderRefresh.finishLoadmore();
                    }
                },2000);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.checkText_order_take, R.id.checkText_order_give, R.id.checkText_order_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkText_order_take:
                checkTextOrderTake.setChecked(true);
                checkTextOrderGive.setChecked(false);
                checkTextOrderSuccess.setChecked(false);
                takeLine.setVisibility(View.VISIBLE);
                giveLine.setVisibility(View.INVISIBLE);
                successLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.checkText_order_give:
                checkTextOrderTake.setChecked(false);
                checkTextOrderGive.setChecked(true);
                checkTextOrderSuccess.setChecked(false);
                takeLine.setVisibility(View.INVISIBLE);
                giveLine.setVisibility(View.VISIBLE);
                successLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.checkText_order_success:
                checkTextOrderTake.setChecked(false);
                checkTextOrderGive.setChecked(false);
                checkTextOrderSuccess.setChecked(true);
                takeLine.setVisibility(View.INVISIBLE);
                giveLine.setVisibility(View.INVISIBLE);
                successLine.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClickOrderList(int index) {
        Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
        startActivity(intent);
    }
}
