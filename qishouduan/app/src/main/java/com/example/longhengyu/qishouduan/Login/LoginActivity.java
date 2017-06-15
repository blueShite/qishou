package com.example.longhengyu.qishouduan.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.Login.Interface.LoginInterface;
import com.example.longhengyu.qishouduan.Login.Presenter.LoginPresenter;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Tab.TabActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginInterface {

    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_account)
    EditText mEditAccount;
    @BindView(R.id.button_login)
    Button mButtonLogin;

    private long m_exitTime = 1;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.setContext(LoginActivity.this);
    }

    @OnClick(R.id.button_login)
    public void onViewClicked() {
        mLoginPresenter.requestLogin(mEditAccount.getText().toString(),mEditPassword.getText().toString());
    }

    @Override
    public void requestLoginSucess() {
        Intent intent = new Intent(LoginActivity.this, TabActivity.class);
        startActivity(intent);
        finish();
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
