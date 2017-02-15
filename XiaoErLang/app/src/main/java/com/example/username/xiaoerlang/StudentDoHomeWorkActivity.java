package com.example.username.xiaoerlang;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.username.xiaoerlang.util.Util;

import java.util.ArrayList;
import java.util.List;

public class StudentDoHomeWorkActivity extends Activity {
    private List<AVObject> teacherList = new ArrayList<>();
    private ListView mlistView;
    ListViewAdapter mAdapter;

    private ProgressDialog dialog;
    private Button submit;
    private List<AVObject> updateList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_do_homework);

        initUI();

        retriveTeacherList();
        if(Util.getSP(StudentDoHomeWorkActivity.this,Util.email).equals("fenghanlu@gmail.com")){
            submit.setVisibility(View.GONE);
        }
    }

    public void initUI(){
        submit = (Button)findViewById(R.id.submit) ;
        submit.setOnClickListener(listener);
        mlistView = (ListView)findViewById(R.id.student_list);
        mAdapter= new ListViewAdapter(this,R.layout.item_assignment);
        mlistView.setAdapter(mAdapter);
        dialog = Util.showDialog(this,R.string.wait,R.string.connecting_server);
    }
    private void closeDialog(){
        if(null != dialog){
            dialog.dismiss();
        }
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            action();

        }
    };

    public void action(){
        dialog.show();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeDialog();
                retriveTeacherList();
            }
        }, 3000);
// teacher
        if(Util.getSP(StudentDoHomeWorkActivity.this,Util.email).equals("fenghanlu@gmail.com")){
            for (AVObject mObject : updateList) {
                if (mObject.get(Util.COMMENT) != null && mObject.get(Util.COMMENT).toString().length() > 0) {
                    mObject.saveInBackground();
                }
            }
        }else {
            // student
            for (AVObject mObject : updateList) {
                if (mObject.get(Util.STUDENTANSER) != null && mObject.get(Util.STUDENTANSER).toString().length() > 0) {
                    mObject.saveInBackground();
                }
            }
        }
        updateList.clear();
    }



    private void retriveTeacherList(){
        AVQuery<AVObject> avQuery = new AVQuery<>(Util.questionTableName);
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                closeDialog();
                if (e == null) {
                    if(null != list && list.size()>0){
                        teacherList = list;
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    e.printStackTrace();
                }
//                retrieveStudent();
            }

        });
    }



    private  class ListViewAdapter extends ArrayAdapter<String> {
        Context mContext;
        //        ArrayList<Question> mList = new ArrayList<>();
        int layoutResourceId;
        public  class ViewHolder {
            public TextView text;
            public TextView answer;
            public EditText studentAnswer;
            public EditText comment;
        }

        public ListViewAdapter(Context context , int layoutResourceId) {
            super(context,layoutResourceId);
            this.mContext = context;
            this.layoutResourceId = layoutResourceId;
//            this.mList = mList;
        }

        @Override
        public int getCount() {
            return teacherList.size();
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = null;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layoutResourceId, null);
            final ViewHolder mholder = new ViewHolder();
            mholder.text = (TextView)rowView.findViewById(R.id.question);
            mholder.answer = (TextView)rowView.findViewById(R.id.answer);
            mholder.studentAnswer = (EditText)rowView.findViewById(R.id.input_answer);
            mholder.studentAnswer.setText("");
            mholder.comment = (EditText)rowView.findViewById(R.id.input_comment);
            final AVObject mObject = teacherList.get(position);
            mholder.text.setText("问："+mObject.get(Util.QUESTION));
            mholder.answer.setVisibility(View.GONE);
            mholder.studentAnswer.setText("");
            mholder.studentAnswer.setBackgroundColor(Color.WHITE);
            //Comment
            if(mObject.get(Util.COMMENT).toString()!=null && mObject.get(Util.COMMENT).toString().length()>0){
                    mholder.comment.setText(mObject.get(Util.COMMENT).toString());
                    mholder.comment.setVisibility(View.VISIBLE);
                     mholder.comment.setEnabled(false);

            }
            //answer
            if(null != mObject.get(Util.STUDENTANSER)&&mObject.get(Util.STUDENTANSER).toString().length()>0){
                mholder.studentAnswer.setText(mObject.get(Util.STUDENTANSER).toString());
                mholder.studentAnswer.setEnabled(false);
                mholder.studentAnswer.setVisibility(View.VISIBLE);
                if(!mObject.get(Util.STUDENTANSER).toString().equals(mObject.get(Util.ANSWER).toString())){
                    mholder.studentAnswer.setBackgroundColor(Color.RED);
                }
                if(Util.getSP(StudentDoHomeWorkActivity.this,Util.email).equals("fenghanlu@gmail.com")){
                   if( mholder.comment.getVisibility() == View.GONE) {
                        mholder.text.setLongClickable(true);
                        mholder.text.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                mholder.comment.setVisibility(View.VISIBLE);
                                customerDialog(mObject);
                                return true;
                            }
                        });
                    }
                }

            }else{
                mholder.studentAnswer.setVisibility(View.VISIBLE);
                mholder.studentAnswer.setText("");
                mholder.studentAnswer.setEnabled(true);
                if(Util.getSP(StudentDoHomeWorkActivity.this,Util.email).equals("fenghanlu@gmail.com")){

                }else {
                    mholder.studentAnswer.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            if(editable.length()>0){
                                mObject.put(Util.STUDENTANSER,editable.toString());
                            }
                        }
                    });
                    if(updateList.indexOf(mObject) ==-1) {
                        updateList.add(mObject);
                    }
                }
            }

            return rowView;
        }

    }

    public void customerDialog(final AVObject mobject){
        final Dialog dialog = new Dialog(StudentDoHomeWorkActivity.this);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("批注");

        final EditText mEditText = (EditText) dialog.findViewById(R.id.add_comment);

        Button dialogButton = (Button) dialog.findViewById(R.id.add_button);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobject.put(Util.COMMENT, mEditText.getText().toString());
                if(updateList.indexOf(mobject) ==-1) {
                    updateList.add(mobject);
                }
                dialog.dismiss();
                action();
            }
        });

        dialog.show();
    }
}
