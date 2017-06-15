package com.example.longhengyu.qishouduan.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.Login.Bean.LoginBean;
import com.example.longhengyu.qishouduan.Manage.LoginManage;
import com.example.longhengyu.qishouduan.My.Bean.MyBean;
import com.example.longhengyu.qishouduan.My.Interface.MyInterface;
import com.example.longhengyu.qishouduan.My.Presenter.MyPresenter;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Set.SetActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class MyFragment extends SupportFragment implements MyInterface {

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
    @BindView(R.id.layout_My_onLine)
    RelativeLayout mLayoutMyOnLine;
    @BindView(R.id.image_my_set)
    ImageView mImageMySet;


    private View view;
    private MyPresenter mPresenter = new MyPresenter(this);
    private String personId = LoginManage.getInstance().getLoginBean().getId();

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

    private void customView() {
        //定制刷新加载
        mImageMySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetActivity.class);
                startActivity(intent);
            }
        });
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        myRefresh.setHeaderView(headerView);
        myRefresh.setEnableLoadmore(false);
        myRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mPresenter.requestMyData(personId);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (LoginManage.getInstance().getLoginBean().getWhether().equals("1")) {
            mPresenter.requestMyData(personId);
            mLayoutMyOnLine.setVisibility(View.GONE);
        } else {
            mLayoutMyOnLine.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.button_my_setOnLine)
    public void onViewClicked() {
        mPresenter.requestSetOnline(personId, "1");
    }

    @Override
    public void requestMySucess(MyBean myBean) {
        myRefresh.finishRefreshing();
        textOrderTodayNum.setText(myBean.getToday_d() + "");
        textTodayOrderPrice.setText(myBean.getToday_j());
        textOrderMonNum.setText(myBean.getMoon_d() + "");
        textOrderMonPrice.setText(myBean.getMoon_j());
        textOrderWeekNum.setText(myBean.getWeek_d() + "");
        textOrderWeekPrice.setText(myBean.getWeek_j());
    }

    @Override
    public void requestMyError(String errorStr) {
        myRefresh.finishRefreshing();
    }

    @Override
    public void requestSetOnlineSucess(String whether) {
        LoginBean bean = LoginManage.getInstance().getLoginBean();
        bean.setWhether(whether);
        LoginManage.getInstance().saveLoginBean(bean);
        if (LoginManage.getInstance().getLoginBean().getWhether().equals("1")) {
            mPresenter.requestMyData(personId);
            mLayoutMyOnLine.setVisibility(View.GONE);
        } else {
            mLayoutMyOnLine.setVisibility(View.VISIBLE);
        }
    }
}
