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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.Login.Bean.LoginBean;
import com.example.longhengyu.qishouduan.Manage.LoginManage;
import com.example.longhengyu.qishouduan.Order.Adapter.OrderAdapter;
import com.example.longhengyu.qishouduan.Order.Bean.OrderListBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderListInterface;
import com.example.longhengyu.qishouduan.Order.Presenter.OrderListPresenter;
import com.example.longhengyu.qishouduan.PushAbout.TagAliasOperatorHelper;
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

import static com.example.longhengyu.qishouduan.PushAbout.TagAliasOperatorHelper.sequence;

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
    @BindView(R.id.text_order_online)
    TextView mTextOrderOnline;
    @BindView(R.id.layout_order_onLine)
    RelativeLayout mLayoutOrderOnLine;

    private OrderAdapter orderAdapter;
    private List<OrderListBean> mList;
    private OrderListPresenter mPresenter = new OrderListPresenter(this);
    private String orderTypeStr;
    private String personId = LoginManage.getInstance().getLoginBean().getId();


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
        mPresenter.setContext(getActivity());
        orderTypeStr = "1";
        return view;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        if (LoginManage.getInstance().getLoginBean().getWhether().equals("1")) {
            mPresenter.requestOrderList(personId, orderTypeStr);
            mLayoutOrderOnLine.setVisibility(View.GONE);
            mTextOrderOnline.setVisibility(View.VISIBLE);
        } else {
            mLayoutOrderOnLine.setVisibility(View.VISIBLE);
            mTextOrderOnline.setVisibility(View.GONE);
        }

    }

    private void initView() {

        mTextOrderOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.requestSetOnline(personId,"2");
            }
        });
        mList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        orderRecycler.setLayoutManager(manager);
        orderAdapter = new OrderAdapter(mList, getContext(), this);
        orderRecycler.setAdapter(orderAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        orderRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        orderRefresh.setBottomView(loadingView);
        orderRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mPresenter.requestOrderList(personId, orderTypeStr);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderRefresh.finishLoadmore();
                    }
                }, 2000);
            }
        });

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
                orderTypeStr = "1";
                mPresenter.requestOrderList(personId, orderTypeStr);
                break;
            case R.id.checkText_order_give:
                checkTextOrderTake.setChecked(false);
                checkTextOrderGive.setChecked(true);
                checkTextOrderSuccess.setChecked(false);
                takeLine.setVisibility(View.INVISIBLE);
                giveLine.setVisibility(View.VISIBLE);
                successLine.setVisibility(View.INVISIBLE);
                orderTypeStr = "2";
                mPresenter.requestOrderList(personId, orderTypeStr);
                break;
            case R.id.checkText_order_success:
                checkTextOrderTake.setChecked(false);
                checkTextOrderGive.setChecked(false);
                checkTextOrderSuccess.setChecked(true);
                takeLine.setVisibility(View.INVISIBLE);
                giveLine.setVisibility(View.INVISIBLE);
                successLine.setVisibility(View.VISIBLE);
                orderTypeStr = "3";
                mPresenter.requestOrderList(personId, orderTypeStr);
                break;
        }
    }

    @OnClick(R.id.button_order_setOnLine)
    public void onViewClicked() {

        mPresenter.requestSetOnline(personId, "1");

    }

    @Override
    public void onClickOrderList(int index) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("orderId", mList.get(index).getId());
        startActivity(intent);
    }

    @Override
    public void requestOrderListSucess(String orderType, List<OrderListBean> beanList) {

        orderRefresh.finishRefreshing();
        mList.clear();
        mList.addAll(beanList);
        orderAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestOrderListError(String errorStr) {

        orderRefresh.finishRefreshing();
        mList.clear();
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestSetOnlineSucess(String whether) {

        LoginBean bean = LoginManage.getInstance().getLoginBean();
        bean.setWhether(whether);
        LoginManage.getInstance().saveLoginBean(bean);
        if (bean.getWhether().equals("1")) {
            mPresenter.requestOrderList(personId, orderTypeStr);
            mLayoutOrderOnLine.setVisibility(View.GONE);
            mTextOrderOnline.setVisibility(View.VISIBLE);
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.setAction(2);
            tagAliasBean.setAlias(LoginManage.getInstance().getLoginBean().getId());
            tagAliasBean.setAliasAction(true);
            sequence++;
            TagAliasOperatorHelper.getInstance().handleAction(getContext(),sequence,tagAliasBean);
        } else {
            mLayoutOrderOnLine.setVisibility(View.VISIBLE);
            mTextOrderOnline.setVisibility(View.GONE);
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.setAction(3);
            tagAliasBean.setAlias(LoginManage.getInstance().getLoginBean().getId());
            tagAliasBean.setAliasAction(true);
            sequence++;
            TagAliasOperatorHelper.getInstance().handleAction(getContext(),sequence,tagAliasBean);
        }
    }
}
