package com.example.longhengyu.qishouduan.Set;

import android.os.Bundle;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageView_aboutUs_back)
    public void onViewClicked() {
        finish();
    }
}
