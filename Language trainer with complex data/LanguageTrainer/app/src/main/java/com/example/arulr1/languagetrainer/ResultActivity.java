package com.example.arulr1.languagetrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ResultActivity extends AppCompatActivity {
    AnswerGroup[] answerarray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        answerarray = (AnswerGroup[]) getIntent().getSerializableExtra("answerarray");
        ResultGroupAdapter resultadapter = new ResultGroupAdapter(this, R.layout.custom_view_list, answerarray);

        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(resultadapter);
    }

    public class ResultGroupAdapter extends ArrayAdapter<AnswerGroup> {

        public ResultGroupAdapter(Context context, int resourse,AnswerGroup[] objects){
            super(context,resourse,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container){

            LayoutInflater inflater = LayoutInflater.from(ResultActivity.this);

            View cuistomView= inflater.inflate(R.layout.custom_view_list,container,false);

            ImageView cv_img_question = (ImageView)cuistomView.findViewById(R.id.cv_img_question);
            TextView cv_text = (TextView)cuistomView.findViewById(R.id.cv_text);
            ImageView cv_img_answer = (ImageView)cuistomView.findViewById(R.id.cv_img_answer);

            AnswerGroup current = getItem(position);

            cv_img_question.setImageResource(getResources().getIdentifier(current.Question,"drawable",getPackageName()));
            cv_text.setText(current.toString());
            cv_img_answer.setImageResource(getResources().getIdentifier(current.Result,"drawable",getPackageName()));


            return  cuistomView;
        }
    }
    public static class AnswerGroup implements Serializable {

        String Question,Answer,Result;

        public AnswerGroup(String Question, String Answer, String Result){
            this.Question=Question;
            this.Answer=Answer;
            this.Result=Result;
        }
        @Override
        public String toString(){
            return Answer.toUpperCase();
        }
    }


}
