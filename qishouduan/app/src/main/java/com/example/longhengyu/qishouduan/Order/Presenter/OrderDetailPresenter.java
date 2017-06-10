package com.example.longhengyu.qishouduan.Order.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.longhengyu.qishouduan.Base.BasePresenter;
import com.example.longhengyu.qishouduan.NetWorks.RequestBean;
import com.example.longhengyu.qishouduan.NetWorks.RequestCallBack;
import com.example.longhengyu.qishouduan.NetWorks.RequestTools;
import com.example.longhengyu.qishouduan.Order.Bean.OrderDetailBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderDetailInterface;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/8.
 */

public class OrderDetailPresenter extends BasePresenter {

    private OrderDetailInterface mInterface;

    public OrderDetailPresenter(OrderDetailInterface anInterface) {

        mInterface = anInterface;
    }

    public void requestDetailData(String orderId){

        showDialog();
        Map<String, String>map = new HashMap<>();
        map.put("id",orderId);
        RequestTools.getInstance().postRequest("order_details.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                mInterface.requestDetailError("请求失败");
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    OrderDetailBean bean = JSON.parseObject(response.getData(),OrderDetailBean.class);
                    mInterface.requestDetailSucess(bean);
                }else {
                    mInterface.requestDetailError(response.getMes());
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestDistributionWith(String orderId , final String disId){
        showDialog();
        final Map<String,String> map = new HashMap<>();
        map.put("id",orderId);
        map.put("dispatching",disId);
        RequestTools.getInstance().postRequest("revamp_api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Toasty.success(mContext,"操作成功!").show();
                    mInterface.requestDistributionSucess(disId);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestFootTakeMeal(String orderId, final String dish, String dishId, final int index){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id",orderId);
        map.put("dish",dish);
        map.put("dish_id",dishId);
        RequestTools.getInstance().postRequest("qu_stste.api.php", false, map, "", new RequestCallBack(mContext) {
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
                    mInterface.requestFootDishId(index);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }


}
