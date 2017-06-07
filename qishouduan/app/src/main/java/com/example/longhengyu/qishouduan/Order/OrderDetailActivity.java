package com.example.longhengyu.qishouduan.Order;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.Order.Adapter.OrderDetailAdapter;
import com.example.longhengyu.qishouduan.Order.Bean.OrderDetailBean;
import com.example.longhengyu.qishouduan.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.orderDetail_recycle)
    RecyclerView orderDetailRecycle;
    @BindView(R.id.orderDetail_refresh)
    TwinklingRefreshLayout orderDetailRefresh;

    private List<OrderDetailBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

        mList = new ArrayList<>();
        for (int i=0;i<5;i++){
            OrderDetailBean bean = new OrderDetailBean();
            if(i==1){
                bean.setBtnType(true);
            }else {
                bean.setBtnType(false);
            }
            bean.setFoot("鱼香肉丝");
            bean.setFootType("农家小炒");
            mList.add(bean);
        }
        mList.add(0,new OrderDetailBean());
        mList.add(new OrderDetailBean());
        LinearLayoutManager manager = new LinearLayoutManager(OrderDetailActivity.this);
        orderDetailRecycle.setLayoutManager(manager);
        OrderDetailAdapter adapter = new OrderDetailAdapter(mList,OrderDetailActivity.this);
        orderDetailRecycle.setAdapter(adapter);
        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(OrderDetailActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        orderDetailRefresh.setHeaderView(headerView);
        orderDetailRefresh.setEnableLoadmore(false);
        orderDetailRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                orderDetailRefresh.finishRefreshing();
            }
        });
    }
}
