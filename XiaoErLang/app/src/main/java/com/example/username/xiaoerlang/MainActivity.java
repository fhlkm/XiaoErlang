package com.example.username.xiaoerlang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    private Button checkAssignment;
    private Button createAssignment;
    private Button share_BBS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
//        testAVO();

    }

    private void initUI(){
        checkAssignment = (Button)findViewById(R.id.check_assignment);
        createAssignment = (Button)findViewById(R.id.create_assignment);
        share_BBS = (Button)findViewById(R.id.share_bbs);
        
        checkAssignment.setOnClickListener(mListener);
        createAssignment.setOnClickListener(mListener);
        share_BBS.setOnClickListener(mListener);
    }


    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.check_assignment:
//                    testAVO();
                        break;
                case R.id.create_assignment:
                    break;
                case R.id.share_bbs:
                    break;
            }
        }
    };

    private void testAVO(){
//        AVUser.logInInBackground("student1", "student1", new LogInCallback<AVUser>() {
//            @Override
//            public void done(AVUser avUser, AVException e) {
//
//               String email =  avUser.getEmail();
//                Log.i("Login email is: ",email);
//            }
//        });
//        AVUser.logInInBackground("student2", "student2", new LogInCallback<AVUser>() {
//            @Override
//            public void done(AVUser avUser, AVException e) {
//
//                String email =  avUser.getEmail();
//                Log.i("Login email is: ",email);
//            }
//        });
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername("teacher");// 设置用户名
        user.setPassword("teacher");// 设置密码
        user.setEmail("fenghanlu@gmail.com");// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                }
            }
        });
         user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername("student1");// 设置用户名
        user.setPassword("student1");// 设置密码
        user.setEmail("fenghanlu@163.com");// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                }
            }
        });

        user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername("student2");// 设置用户名
        user.setPassword("student2");// 设置密码
        user.setEmail("hfeng11@hawk.iit.edu");// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                }
            }
        });
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });
//
//        AVObject todo = new AVObject("Todo");
//        todo.put("title", "工程师周会");
//        todo.put("content", "每周工程师会议，周一下午2点");
//        todo.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    // 存储成功
//                } else {
//                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
//                }
//            }
//        });
    }
}
