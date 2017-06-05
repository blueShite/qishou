package com.example.longhengyu.qishouduan.My;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.longhengyu.qishouduan.Base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class MyFragment extends SupportFragment {

    public static MyFragment newInstance() {

        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
