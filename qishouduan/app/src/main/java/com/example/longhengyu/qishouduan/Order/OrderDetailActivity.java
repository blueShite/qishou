package com.example.longhengyu.qishouduan.Order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.Order.Adapter.OrderDetailAdapter;
import com.example.longhengyu.qishouduan.Order.Bean.OrderDetailBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderDetailInterface;
import com.example.longhengyu.qishouduan.Order.Presenter.OrderDetailPresenter;
import com.example.longhengyu.qishouduan.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity implements OrderDetailInterface {

    @BindView(R.id.orderDetail_recycle)
    RecyclerView orderDetailRecycle;
    @BindView(R.id.orderDetail_refresh)
    TwinklingRefreshLayout orderDetailRefresh;

    private ImageView backImageView;
    private String orderId;
    private OrderDetailPresenter mPresenter = new OrderDetailPresenter(this);
    private OrderDetailBean mDetailBean;
    private List<OrderDetailBean.FootBean> mFootBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
        orderId = getIntent().getStringExtra("orderId");
        mPresenter.setContext(OrderDetailActivity.this);
        mPresenter.requestDetailData(orderId);
    }

    private void initView(){


        LinearLayoutManager manager = new LinearLayoutManager(OrderDetailActivity.this);
        orderDetailRecycle.setLayoutManager(manager);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(OrderDetailActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        orderDetailRefresh.setHeaderView(headerView);
        orderDetailRefresh.setEnableLoadmore(false);
        orderDetailRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mPresenter.requestDetailData(orderId);
            }
        });
        backImageView = (ImageView)findViewById(R.id.imageView_orderDetail_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void requestDetailSucess(OrderDetailBean detailBean) {


        orderDetailRefresh.finishRefreshing();
        mDetailBean = detailBean;
        mFootBeanList = detailBean.getFoot();
        OrderDetailBean.FootBean bean = new OrderDetailBean.FootBean();
        mFootBeanList.add(0,bean);
        mFootBeanList.add(bean);
        OrderDetailAdapter adapter = new OrderDetailAdapter(mFootBeanList,mDetailBean,OrderDetailActivity.this,this);
        orderDetailRecycle.setAdapter(adapter);
    }

    @Override
    public void requestDetailError(String errorStr) {
        orderDetailRefresh.finishRefreshing();
    }

    @Override
    public void onClickDistributionBtn(String orderType) {
        mPresenter.requestDistributionWith(mDetailBean.getOrder().getId(),orderType);
    }

    @Override
    public void onClickFootBtn(int index) {
        OrderDetailBean.FootBean footBean = mFootBeanList.get(index);
        mPresenter.requestFootTakeMeal(mDetailBean.getOrder().getId(),footBean.getDish(),"1",index);
    }

    @Override
    public void onClickPhoneBtn(String phone) {

        if (ContextCompat.checkSelfPermission(OrderDetailActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(OrderDetailActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(OrderDetailActivity.this, "请授权！", Toast.LENGTH_LONG).show();
                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }else{
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(OrderDetailActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},110);
            }
        }else {
            // 已经获得授权，可以打电话

            CallPhone();
        }

    }

    private void CallPhone() {
        String number = mDetailBean.getUser().getPhone();
        if (TextUtils.isEmpty(number)) {
            // 提醒用户
            // 注意：在这个匿名内部类中如果用this则表示是View.OnClickListener类的对象，
            // 所以必须用MainActivity.this来指定上下文环境。
            Toast.makeText(OrderDetailActivity.this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + number); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }

    @Override
    public void requestDistributionSucess(String orderType) {
        mPresenter.requestDetailData(orderId);
    }

    @Override
    public void requestFootDishId(int index) {

        mFootBeanList.get(index).setDish_id("2");
        mDetailBean.getFoot().get(index-1).setDish_id("2");
        OrderDetailAdapter adapter = (OrderDetailAdapter)orderDetailRecycle.getAdapter();
        adapter.notifyItemChanged(index);
        adapter.notifyItemChanged(mFootBeanList.size()-1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 110: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }
}
