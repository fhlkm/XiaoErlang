package com.example.username.xiaoerlang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.username.xiaoerlang.data.Question;
import com.example.username.xiaoerlang.util.Util;

import java.util.ArrayList;
import java.util.List;

public class StudentDoHomeWorkActivity extends Activity {
    private ArrayList <Question>mList = new ArrayList();
    private ArrayList <Question>mStudentList = new ArrayList();
    private List<AVObject> teacherList = new ArrayList<>();
    private List<AVObject> studentList = new ArrayList<>();
    private ListView mlistView;
    ListViewAdapter mAdapter;
    private final String QUESTION ="questiontext";
    private final String ANSWER ="answer";
    private final String USEREMAIL = "email";
    private final String STUDENTANSER="studentAnswer";
    private final String COMMENT="comment";
    private final String questionTableName ="QuestionInfo";
    private final String student_questionTableName ="StudentQuestionInfo";
    private ProgressDialog dialog;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_do_homework);

        initUI();

        retriveTeacherList();
    }

    public void initUI(){
        submit = (Button)findViewById(R.id.submit) ;
        submit.setOnClickListener(listener);
        mlistView = (ListView)findViewById(R.id.student_list);
        mAdapter= new ListViewAdapter(this,R.layout.item_assignment);
        mlistView.setAdapter(mAdapter);
        dialog = Util.showDialog(getApplicationContext(),R.string.wait,R.string.connecting_server);
    }
    private void closeDialog(){
        if(null != dialog){
            dialog.dismiss();
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };



    private void createAssignment(String quesiton,String answer){
        AVObject testObject = new AVObject(questionTableName);
        testObject.put(USEREMAIL,Util.getSP(getApplicationContext(),Util.email));
        testObject.put(QUESTION,quesiton);
        testObject.put(ANSWER,answer);
        testObject.put(STUDENTANSER,"");
        testObject.put(COMMENT,"");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
//                    getAssignemnts();

                }else{
                    Util.showToast(getApplicationContext(), e.getMessage());
                }
            }
        });
    }

    private void retriveTeacherList(){
        AVQuery<AVObject> avQuery = new AVQuery<>(student_questionTableName);
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                if (e == null) {
                    if(null != list && list.size()>0){
                        teacherList = list;
                    }
                } else {
                    e.printStackTrace();
                }
                retrieveStudent();
            }

        });
    }

    public void retrieveStudent(){
        AVQuery<AVObject> avQuery = new AVQuery<>(student_questionTableName);
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                closeDialog();
                if (e == null) {
                    if(null != list && list.size()>0){
                        studentList = list;
                    }
                } else {
                    e.printStackTrace();
                }
            }

        });
    }

    public void getExtraList(List<AVObject> tList, List<AVObject> sList){
        List<AVObject> extra = new ArrayList<>();
        for(AVObject teacher: tList)
            for(AVObject student:sList){
                 if(teacher.get())
            }
    }


    private  class ListViewAdapter extends ArrayAdapter<String> {
        Context mContext;
        //        ArrayList<Question> mList = new ArrayList<>();
        int layoutResourceId;
        public  class ViewHolder {
            public TextView text;
            public TextView answer;
            public EditText studentAnswer;
        }

        public ListViewAdapter(Context context , int layoutResourceId) {
            super(context,layoutResourceId);
            this.mContext = context;
            this.layoutResourceId = layoutResourceId;
//            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
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
                mholder.studentAnswer = (EditText)rowView.findViewById(R.id.input_answer);
                rowView.setTag(mholder);
            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.text.setText("问："+mList.get(position).getQuestiontext());
            holder.answer.setVisibility(View.GONE);
            if(null != mList.get(position).getStudentAnswer()){
                if(mList.get(position).getStudentAnswer().equals(mList.get(position).getAnswer())){
                    holder.studentAnswer.setText(mList.get(position).getAnswer());
                    holder.studentAnswer.setEnabled(false);
                }
            }else{
                holder.studentAnswer.setVisibility(View.VISIBLE);
                final int index = position
                holder.studentAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        mList.get(index).setStudentAnswer(editable.toString());
                    }
                });
            }
//            holder.answer.setText("答："+mList.get(position).getAnswer());

            return rowView;
        }

    }
}
