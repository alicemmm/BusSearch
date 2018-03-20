package com.shouyi.xue.bussearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by asia on 2018/3/20.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String ACCOUNT = "admin";
    private static final String PASSWORD = "123456";

    private AppCompatEditText accountEt;
    private AppCompatEditText passwordEt;
    private Button loginBtn;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEt = findViewById(R.id.account_et);
        passwordEt = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);

        progressDialog= new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = accountEt.getText().toString();
                final String password = passwordEt.getText().toString();
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();

                        if (TextUtils.isEmpty(account)) {
                            Toast.makeText(LoginActivity.this, "请输入账号！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (ACCOUNT.equals(account) && PASSWORD.equals(password)) {
                            Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "账号或密码不正确！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 500);


            }
        });
    }
}
