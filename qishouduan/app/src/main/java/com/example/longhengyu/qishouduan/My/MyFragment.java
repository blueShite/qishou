package com.example.longhengyu.qishouduan.My;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.My.Bean.MyBean;
import com.example.longhengyu.qishouduan.My.Interface.MyInterface;
import com.example.longhengyu.qishouduan.My.Presenter.MyPresenter;
import com.example.longhengyu.qishouduan.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class MyFragment extends SupportFragment implements MyInterface {

    @BindView(R.id.text_my_online)
    TextView textMyOnline;
    @BindView(R.id.text_today_order_price)
    TextView textTodayOrderPrice;
    @BindView(R.id.text_order_today_num)
    TextView textOrderTodayNum;
    @BindView(R.id.text_order_week_price)
    TextView textOrderWeekPrice;
    @BindView(R.id.text_order_week_num)
    TextView textOrderWeekNum;
    @BindView(R.id.text_order_mon_price)
    TextView textOrderMonPrice;
    @BindView(R.id.text_order_mon_num)
    TextView textOrderMonNum;
    @BindView(R.id.my_refresh)
    TwinklingRefreshLayout myRefresh;


    private View view;
    private MyPresenter mPresenter = new MyPresenter(this);

    public static MyFragment newInstance() {

        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        mPresenter.setContext(getActivity());
        customView();
        return view;
    }

    private void customView(){
        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        myRefresh.setHeaderView(headerView);
        myRefresh.setEnableLoadmore(false);
        myRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mPresenter.requestMyData("6");
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.requestMyData("6");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void requestMySucess(MyBean myBean) {
        myRefresh.finishRefreshing();
        textOrderTodayNum.setText(myBean.getToday_d()+"");
        textTodayOrderPrice.setText(myBean.getToday_j());
        textOrderMonNum.setText(myBean.getMoon_d()+"");
        textOrderMonPrice.setText(myBean.getMoon_j());
        textOrderWeekNum.setText(myBean.getWeek_d()+"");
        textOrderWeekPrice.setText(myBean.getWeek_j());
    }

    @Override
    public void requestMyError(String errorStr) {
        myRefresh.finishRefreshing();
    }
}
