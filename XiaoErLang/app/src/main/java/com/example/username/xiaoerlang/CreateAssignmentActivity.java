package com.example.username.xiaoerlang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class CreateAssignmentActivity extends Activity {

    private ListView mListView;
    private Button createAssignment;
    private Button deleteAssignment;

    private ArrayList <Question>mList = new ArrayList();
    private EditText mAskInput = null;
    private EditText mAnswerInput = null;
    private final String QUESTION ="questiontext";
    private final String ANSWER ="answer";
    private final String USEREMAIL = "email";
    private final String STUDENTANSER="studentAnswer";
    private final String COMMENT="comment";
    private final String questionTableName ="QuestionInfo";
    ListViewAdapter mAdapter;
    private  ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_assignment);

        init();

        mAdapter= new ListViewAdapter(CreateAssignmentActivity.this,mList,R.layout.item_assignment);
        mListView.setAdapter(mAdapter);
        dialog = Util.showDialog(this, getResources().getString(R.string.wait),getResources().getString(R.string.connecting_server));
        getAssignemnts();
    }

    private void init(){
        mListView =(ListView)findViewById(R.id.assignments);
        createAssignment = (Button)findViewById(R.id.create_new_assignment);
        deleteAssignment = (Button)findViewById(R.id.delete_assignments);
        createAssignment.setOnClickListener(mListener);
        deleteAssignment.setOnClickListener(mListener);
        mAskInput = (EditText)findViewById(R.id.ask_input);
        mAnswerInput = (EditText)findViewById(R.id.answer_input);
    }



    private void closeDialog(){
        if(null != dialog){
            dialog.dismiss();
        }
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.create_new_assignment:
                    dialog =  Util.showDialog(CreateAssignmentActivity.this, getResources().getString(R.string.wait),getResources().getString(R.string.connecting_server));
                    createNewAssignment();
                    break;
                case R.id.delete_assignments:
                    break;
                case R.id.share_bbs:
                    break;
            }
        }
    };
    private void getAssignemnts(){
        AVQuery<AVObject> avQuery = new AVQuery<>(questionTableName);
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                closeDialog();
                if (e == null) {
                    if(null != list && list.size()>0){
                        mList.clear();
                        for(AVObject mObject: list) {
                            Question mQuestion = new Question();
                            mQuestion.setAnswer(mObject.get(ANSWER).toString());
                            mQuestion.setQuestiontext(mObject.get(QUESTION).toString());
                            mList.add(mQuestion);
                            mAdapter.notifyDataSetChanged();

                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }

        });

    }

    private void createNewAssignment(){

        String question = mAskInput.getText().toString();
        String answer = mAnswerInput.getText().toString();
        if(question.length()>0){

            createAssignment(question,answer);
            mAskInput.setText("");
            mAnswerInput.setText("");
        }else{
            Util.showToast(getApplicationContext(),R.string.forget_question);
        }
    }
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
                    getAssignemnts();

                }else{
                    Util.showToast(getApplicationContext(), e.getMessage());
                }
            }
        });
    }

    private  class ListViewAdapter extends ArrayAdapter<String>{
        Context mContext;
        //        ArrayList<Question> mList = new ArrayList<>();
        int layoutResourceId;
        public  class ViewHolder {
            public TextView text;
            public TextView answer;
        }

        public ListViewAdapter(Context context, ArrayList<Question> mList ,int layoutResourceId) {
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
                rowView.setTag(mholder);
            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.text.setText("问："+mList.get(position).getQuestiontext());
            holder.answer.setText("答："+mList.get(position).getAnswer());

            return rowView;
        }

    }

}
