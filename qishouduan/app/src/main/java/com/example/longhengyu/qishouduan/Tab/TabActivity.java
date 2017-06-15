package com.example.longhengyu.qishouduan.Tab;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.longhengyu.qishouduan.My.MyFragment;
import com.example.longhengyu.qishouduan.Order.OrderFragment;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Tools.ActivityCollector;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public class TabActivity extends SupportActivity {

    @BindView(R.id.contentView)
    FrameLayout contentView;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private OrderFragment orderFragment;
    private MyFragment myFragment;

    private long m_exitTime = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
        injectPages();
        initBottomBar();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void injectPages() {

        /*orderFragment = findFragment(OrderFragment.class);
        if(orderFragment==null){
            orderFragment = OrderFragment.newInstance();
        }
        myFragment = findFragment(MyFragment.class);
        if(myFragment==null){
            myFragment = MyFragment.newInstance();
        }*/
        orderFragment = OrderFragment.newInstance();
        myFragment = MyFragment.newInstance();
        loadMultipleRootFragment(R.id.contentView,0,orderFragment,myFragment);

    }
    public BottomBar getBottomBar() {
        return bottomBar;
    }
    private void initBottomBar() {

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_home:
                        orderFragment = findFragment(OrderFragment.class);
                        if(orderFragment!=null){
                            showHideFragment(orderFragment);
                        }
                        break;
                    case R.id.tab_personal_center:
                        myFragment = findFragment(MyFragment.class);
                        if(myFragment!=null){
                            showHideFragment(myFragment);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - m_exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                m_exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
