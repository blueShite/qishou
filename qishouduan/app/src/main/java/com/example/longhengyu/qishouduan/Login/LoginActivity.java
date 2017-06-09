package com.example.longhengyu.qishouduan.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.longhengyu.qishouduan.Base.BaseActivity;
import com.example.longhengyu.qishouduan.Login.Interface.LoginInterface;
import com.example.longhengyu.qishouduan.Login.Presenter.LoginPresenter;
import com.example.longhengyu.qishouduan.R;
import com.example.longhengyu.qishouduan.Tab.TabActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginInterface {

    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_account)
    EditText mEditAccount;
    @BindView(R.id.button_login)
    Button mButtonLogin;

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
    }
}
