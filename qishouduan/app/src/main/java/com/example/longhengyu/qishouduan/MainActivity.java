package com.example.longhengyu.qishouduan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.longhengyu.qishouduan.Base.BaseActivity;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
