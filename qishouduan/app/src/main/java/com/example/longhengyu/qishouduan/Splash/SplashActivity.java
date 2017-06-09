package com.example.longhengyu.qishouduan.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.longhengyu.qishouduan.Login.Bean.LoginBean;
import com.example.longhengyu.qishouduan.Login.LoginActivity;
import com.example.longhengyu.qishouduan.Manage.LoginManage;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Tab.TabActivity;
import com.squareup.picasso.Picasso;

public class SplashActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageView = (ImageView)findViewById(R.id.image_splash);
        Picasso.with(SplashActivity.this).load(R.drawable.splash).fit().centerCrop().into(mImageView);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity(){

        LoginBean loginBean = LoginManage.getInstance().getLoginBean();
        if(loginBean==null||loginBean.getId().isEmpty()){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Intent intent = new Intent(SplashActivity.this,TabActivity.class);
        startActivity(intent);
        finish();

    }
}
