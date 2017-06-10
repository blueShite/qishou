package com.example.longhengyu.qishouduan.My.Interface;

import com.example.longhengyu.qishouduan.My.Bean.MyBean;

/**
 * Created by yuanwuji on 2017/6/8.
 */

public interface MyInterface {

    void requestMySucess(MyBean myBean);
    void requestMyError(String errorStr);
    void requestSetOnlineSucess(String whether);
}
