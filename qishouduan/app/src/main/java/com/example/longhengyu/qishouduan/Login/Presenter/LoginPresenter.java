package com.example.longhengyu.qishouduan.Login.Presenter;

import com.alibaba.fastjson.JSON;
import com.example.longhengyu.qishouduan.Base.BasePresenter;
import com.example.longhengyu.qishouduan.Login.Bean.LoginBean;
import com.example.longhengyu.qishouduan.Login.Interface.LoginInterface;
import com.example.longhengyu.qishouduan.Manage.LoginManage;
import com.example.longhengyu.qishouduan.NetWorks.RequestBean;
import com.example.longhengyu.qishouduan.NetWorks.RequestCallBack;
import com.example.longhengyu.qishouduan.NetWorks.RequestTools;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/9.
 */

public class LoginPresenter extends BasePresenter {

    private LoginInterface mInterface;
    public LoginPresenter(LoginInterface loginInterface){
        mInterface = loginInterface;
    }

    public void requestLogin(String account,String password){

        showDialog();
        Map<String,String>map = new HashMap<>();
        map.put("user_name",account);
        map.put("pass_word",password);
        RequestTools.getInstance().postRequest("login.api.php", false, map, "", new RequestCallBack(mContext) {
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
                    LoginBean bean = JSON.parseArray(response.getData(),LoginBean.class).get(0);
                    LoginManage.getInstance().saveLoginBean(bean);
                    mInterface.requestLoginSucess();
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }

}
