package com.example.longhengyu.qishouduan.Order.Interface;

import com.example.longhengyu.qishouduan.Order.Bean.OrderDetailBean;

/**
 * Created by longhengyu on 2017/6/8.
 */

public interface OrderDetailInterface {

    void requestDetailSucess(OrderDetailBean detailBean);

    void requestDetailError(String errorStr);

    void onClickDistributionBtn(String orderType);

    void onClickFootBtn(int index);

    void onClickPhoneBtn(String phone);

    void requestDistributionSucess(String orderType);

    void requestFootDishId(int index);

}
