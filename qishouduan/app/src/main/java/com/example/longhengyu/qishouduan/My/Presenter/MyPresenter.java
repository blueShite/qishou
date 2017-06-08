package com.example.longhengyu.qishouduan.My.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.longhengyu.qishouduan.Base.BasePresenter;
import com.example.longhengyu.qishouduan.My.Bean.MyBean;
import com.example.longhengyu.qishouduan.My.Interface.MyInterface;
import com.example.longhengyu.qishouduan.NetWorks.RequestBean;
import com.example.longhengyu.qishouduan.NetWorks.RequestCallBack;
import com.example.longhengyu.qishouduan.NetWorks.RequestTools;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by yuanwuji on 2017/6/8.
 */

public class MyPresenter extends BasePresenter {

    private MyInterface mInterface;
    public MyPresenter(MyInterface myInterface){
        mInterface = myInterface;
    }

    public void requestMyData(String Id){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id",Id);
        RequestTools.getInstance().postRequest("my_list.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                mInterface.requestMyError("请求失败");
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    MyBean bean = JSON.parseObject(response.getData(),MyBean.class);
                    mInterface.requestMySucess(bean);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    mInterface.requestMyError(response.getMes());
                }
            }
        });
    }

}
