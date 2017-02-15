package com.example.username.xiaoerlang;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class StudentListActivity extends Activity{
    private ListView mlist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_layout);

        mlist = (ListView)findViewById(R.id.students);
    }

}
