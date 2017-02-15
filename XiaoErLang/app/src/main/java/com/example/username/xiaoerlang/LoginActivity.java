package com.example.username.xiaoerlang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.username.xiaoerlang.util.Util;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
        login(mUserName.getText().toString(),mPassword.getText().toString());
    }

    private void login(String userName,String password){
        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {

                String email =  avUser.getEmail();
                Log.i("Login email is: ",email);
                if(e ==null){
                    Util.saveSP(getApplicationContext(), Util.email, email);
                    startActivity();
                }else{
                    Util.showToast(LoginActivity.this,e.getMessage());
                }
            }
        });
    }

    public void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
