package com.example.longhengyu.qishouduan.Set;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.Login.LoginActivity;
import com.example.longhengyu.qishouduan.Manage.LoginManage;
import com.example.longhengyu.qishouduan.NetWorks.RequestBean;
import com.example.longhengyu.qishouduan.NetWorks.RequestCallBack;
import com.example.longhengyu.qishouduan.NetWorks.RequestTools;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Tools.ActivityCollector;
import com.example.longhengyu.qishouduan.Tools.Common.utils.ABL_AlertDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class SetActivity extends BaseActivity {


    @BindView(R.id.text_set_cache)
    TextView mTextSetCache;
    @BindView(R.id.text_set_setOnline)
    TextView mTextSetSetOnline;
    private String[] onlineArray = new String[]{"在线", "离线"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        String string = LoginManage.getInstance().getLoginBean().getWhether();
        int index = Integer.parseInt(string) - 1;
        mTextSetSetOnline.setText(onlineArray[index]);
    }

    @OnClick({R.id.relative_set_online, R.id.relative_set_us, R.id.relative_set_cache, R.id.button_set_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_set_online:

                new AlertDialog.Builder(this)
                        .setTitle("选择在线状态")
                        .setSingleChoiceItems(onlineArray, Integer.parseInt(LoginManage.getInstance().getLoginBean().getWhether()) - 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestSetOnline(LoginManage.getInstance().getLoginBean().getId(), (which + 1) + "");
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.relative_set_us:
                break;
            case R.id.relative_set_cache:
                break;
            case R.id.button_set_logout:
                loginOut();
                break;
        }
    }

    @OnClick(R.id.imageView_set_back)
    public void onViewClicked() {
        finish();
    }

    private void loginOut() {
        new ABL_AlertDialog(SetActivity.this)
                .builder()
                .setTitle("提示")
                .setMsg("亲,确定退出登录吗?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SetActivity.this, LoginActivity.class);
                        startActivity(intent);
                        LoginManage.getInstance().saveLoginBean(null);
                        ActivityCollector.finishAll();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    private void requestSetOnline(String Id, final String whether) {

        Map<String, String> map = new HashMap<>();
        map.put("id", Id);
        map.put("whether", whether);
        RequestTools.getInstance().postRequest("state.api.php", false, map, "", new RequestCallBack(SetActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if (response.isRes()) {
                    LoginManage.getInstance().getLoginBean().setWhether(whether);
                    int index = Integer.parseInt(LoginManage.getInstance().getLoginBean().getWhether()) - 1;
                    mTextSetSetOnline.setText(onlineArray[index]);
                } else {
                    Toasty.error(SetActivity.this, response.getMes()).show();
                }
            }
        });
    }
}
