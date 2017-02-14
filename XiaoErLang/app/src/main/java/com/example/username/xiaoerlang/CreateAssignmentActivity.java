package com.example.username.xiaoerlang;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.username.xiaoerlang.util.Util;

import java.util.ArrayList;

public class CreateAssignmentActivity extends Activity {

    private ListView mListView;
    private Button createAssignment;
    private Button deleteAssignment;

    private ArrayList mList = new ArrayList();
    private EditText mAskInput = null;
    private EditText mAnswerInput = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_assignment);

        init();

    }

    private void init(){
        mListView =(ListView)findViewById(R.id.assignments);
        createAssignment = (Button)findViewById(R.id.create_assignment);
        deleteAssignment = (Button)findViewById(R.id.delete_assignments);
        createAssignment.setOnClickListener(mListener);
        deleteAssignment.setOnClickListener(mListener);
        mAskInput = (EditText)findViewById(R.id.ask_input);
        mAnswerInput = (EditText)findViewById(R.id.answer_input);
    }





    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.create_assignment:
                    createNewAssignment();
                    break;
                case R.id.delete_assignments:
                    break;
                case R.id.share_bbs:
                    break;
            }
        }
    };

    private void createNewAssignment(){

        String question = mAskInput.getText().toString();
        String answer = mAnswerInput.getText().toString();
        if(question.length()>0){

        }else{
            Util.showToast(getApplicationContext(),R.string.forget_question);
        }
    }

    private static class ListViewAdapter extends ArrayAdapter<String>{
        Context mContext;

        int layoutResourceId;
        public static class ViewHolder {
            public TextView text;
            public TextView answer;
        }

        public ListViewAdapter(Context context, int layoutResourceId) {
            super(context,layoutResourceId);
            this.mContext = context;
            this.layoutResourceId = layoutResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            if(rowView ==null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(layoutResourceId, null);
                ViewHolder mholder = new ViewHolder();
                mholder.text = (TextView)rowView.findViewById(R.id.question);
                mholder.answer = (TextView)rowView.findViewById(R.id.answer);
                rowView.setTag(mholder);
            }
            ViewHolder holder = (ViewHolder) rowView.getTag();



            return rowView;
        }

    }

}
