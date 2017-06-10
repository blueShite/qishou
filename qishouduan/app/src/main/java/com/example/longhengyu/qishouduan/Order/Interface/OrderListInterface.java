package com.example.longhengyu.qishouduan.Order.Interface;

import com.example.longhengyu.qishouduan.Order.Bean.OrderListBean;

import java.util.List;

/**
 * Created by yuanwuji on 2017/6/6.
 */

public interface OrderListInterface {

    void onClickOrderList(int index);

    void requestOrderListSucess(String orderType, List<OrderListBean> beanList);

    void requestOrderListError(String errorStr);

    void requestSetOnlineSucess(String whether);
}
