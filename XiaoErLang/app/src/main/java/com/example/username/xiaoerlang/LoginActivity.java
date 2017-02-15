package com.example.username.xiaoerlang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        if(null != Util.getSP(getApplicationContext(),Util.email)){
            startActivity();
//           ? login(Util.getSP(getApplicationContext(),Util.userName),Util.getSP(getApplicationContext(),Util.password));
        }
    }
    private void init(){
        mUserName = (EditText)findViewById(R.id.input_email);
        mPassword = (EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(mUserName.getText().toString(),mPassword.getText().toString());
            }
        });

    }

    private void login(final String userName, final String password){
        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {


                if(e ==null){
                    String email =  avUser.getEmail();
                    Log.i("Login email is: ",email);
                    Util.saveSP(getApplicationContext(), Util.email, email);
                    Util.saveSP(getApplicationContext(), Util.userName, userName);
                    Util.saveSP(getApplicationContext(), Util.password, password);
                    startActivity();
                    finish();

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
    public void finish(){
        this.finish();
    }
}
