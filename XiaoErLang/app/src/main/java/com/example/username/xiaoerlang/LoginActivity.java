package com.example.username.xiaoerlang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }
    private void init(){
        mUserName = (EditText)findViewById(R.id.input_email);
        mPassword = (EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.btn_login);
    }

}
