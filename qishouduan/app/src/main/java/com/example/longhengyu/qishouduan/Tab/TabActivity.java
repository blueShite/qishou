package com.example.longhengyu.qishouduan.Tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.longhengyu.qishouduan.Manage.LoginManage;
import com.example.longhengyu.qishouduan.My.MyFragment;
import com.example.longhengyu.qishouduan.Order.OrderFragment;
import com.example.longhengyu.qishouduan.PushAbout.ExampleUtil;
import com.example.longhengyu.qishouduan.PushAbout.LocalBroadcastManager;
import com.example.longhengyu.qishouduan.PushAbout.TagAliasOperatorHelper;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Tools.ActivityCollector;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import me.yokeyword.fragmentation.SupportActivity;

import static com.example.longhengyu.qishouduan.PushAbout.TagAliasOperatorHelper.sequence;

public class TabActivity extends SupportActivity {

    @BindView(R.id.contentView)
    FrameLayout contentView;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private OrderFragment orderFragment;
    private MyFragment myFragment;

    private long m_exitTime = 1;
    public static boolean isForeground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
        JPushInterface.init(getApplicationContext());
        injectPages();
        initBottomBar();

    }

    private void setAlias(){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.setAction(2);
        tagAliasBean.setAlias(LoginManage.getInstance().getLoginBean().getId());
        tagAliasBean.setAliasAction(true);
        sequence++;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    private void injectPages() {
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

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.longhengyu.qishouduan.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Log.e("tabActivity",showMsg.toString());
                }
            } catch (Exception e){
            }
        }
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
