package com.example.longhengyu.qishouduan.Order.Presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.longhengyu.qishouduan.Base.BasePresenter;
import com.example.longhengyu.qishouduan.NetWorks.RequestBean;
import com.example.longhengyu.qishouduan.NetWorks.RequestCallBack;
import com.example.longhengyu.qishouduan.NetWorks.RequestTools;
import com.example.longhengyu.qishouduan.Order.Bean.OrderListBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderListInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/8.
 */

public class OrderListPresenter extends BasePresenter{

    private OrderListInterface mInterface;

    public OrderListPresenter(OrderListInterface anInterface) {

        mInterface = anInterface;
    }

    public void requestOrderList(String Id, final String orderType){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("hor_id",Id);
        map.put("dispatching",orderType);
        RequestTools.getInstance().postRequest("order_list.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                mInterface.requestOrderListError("请求失败了");
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    List<OrderListBean> list = JSON.parseArray(response.getData(),OrderListBean.class);
                    for (int i=0;i<list.size();i++){
                        OrderListBean bean = list.get(i);
                        bean.setOrderType(Integer.parseInt(orderType));
                    }
                    mInterface.requestOrderListSucess(orderType,list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    mInterface.requestOrderListError(response.getMes());
                }
            }
        });
    }

    public void requestSetOnline(String Id, final String whether){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id",Id);
        map.put("whether",whether);
        RequestTools.getInstance().postRequest("state.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    mInterface.requestSetOnlineSucess(whether);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }

}
