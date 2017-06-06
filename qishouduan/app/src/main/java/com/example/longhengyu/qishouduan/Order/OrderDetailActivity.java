package com.example.longhengyu.qishouduan.Order;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.orderDetail_recycle)
    RecyclerView orderDetailRecycle;
    @BindView(R.id.orderDetail_refresh)
    TwinklingRefreshLayout orderDetailRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

        LinearLayoutManager manager = new LinearLayoutManager(OrderDetailActivity.this);
        orderDetailRecycle.setLayoutManager(manager);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(OrderDetailActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        orderDetailRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(OrderDetailActivity.this);
        orderDetailRefresh.setBottomView(loadingView);
        orderDetailRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                orderDetailRefresh.finishRefreshing();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderDetailRefresh.finishLoadmore();
                    }
                },2000);
            }
        });
    }
}
