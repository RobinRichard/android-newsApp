package com.example.arulr1.languagetrainer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arulr1.languagetrainer.ResultActivity.AnswerGroup;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    String Noun,Article,Gender,Meaning,Image;
    int i,score=0,id;
    String[] Questions;
    Button submit;
    Button Restart;
    RadioGroup radioGenderGroup ;
    RadioGroup radioArticleGroup;
    TextView txt_question ;
    ImageView imageView;
    TextView txt_score ;
    String Ans_Article;

    AnswerGroup[] answerarray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String nounArr[] = getResources().getStringArray(R.array.Questions);
        List<String> wordList = Arrays.asList(nounArr);
        Collections.shuffle(wordList);
        Questions = wordList.toArray(new String[wordList.size()]);
        i=0;
        submit = (Button)findViewById(R.id.submit);
        Restart = (Button)findViewById(R.id.Restart);
        radioGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
        radioArticleGroup = (RadioGroup) findViewById(R.id.radioArticleGroup);
        txt_question = (TextView) findViewById(R.id.txt_question);
        imageView=(ImageView)findViewById(R.id.imageView);
        txt_score = (TextView) findViewById(R.id.txt_score);

        answerarray= new AnswerGroup[Questions.length];

        LoadQuestions();

        submit.setOnClickListener(new submitclickhandler());
        Restart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recreate();
            }
        });
    }
    public class submitclickhandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (radioGenderGroup.getCheckedRadioButtonId() == -1 || radioArticleGroup.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(getApplicationContext(), "Select both options", Toast.LENGTH_LONG).show();
            }
            else
            {
                int GenderId = radioGenderGroup.getCheckedRadioButtonId();
                int ArticleId = radioArticleGroup.getCheckedRadioButtonId();

                RadioButton radioGender = (RadioButton) findViewById(GenderId);
                String Ans_Gender = (String) radioGender.getText().toString();

                RadioButton radioArticle = (RadioButton) findViewById(ArticleId);
                Ans_Article = (String) radioArticle.getText().toString();

                if (Ans_Gender.equals(Gender) && Ans_Article.equals(Article)) {
                    score += 1;
                    SelectionAction(getString(R.string.correct));
                }
                else {
                    SelectionAction(getString(R.string.wrong));
                }
                if (i < Questions.length) {
                    LoadQuestions();
                }
                else {


                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("answerarray",answerarray);
                    startActivity(i);

                    id = getResources().getIdentifier("end", "drawable", getPackageName());
                    imageView.setImageResource(id);
                    txt_score.setText("");
                    submit.setVisibility(View.GONE);
                    radioGenderGroup.setVisibility(View.GONE);
                    radioArticleGroup.setVisibility(View.GONE);
                    txt_question.setText("Congradulation You Finished the Quiz \n Your Score : "+score + " Out of "+ Questions.length);
                    Restart.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public void LoadQuestions() {
        try {
            JSONObject obj = new JSONObject(Questions[i]);
            Noun = obj.getString("Noun");
            Article = obj.getString("Article");
            Gender = obj.getString("Gender");
            Image=obj.getString("image");
            txt_question.setText(i+1+". Select Gender and Artical of \"" + obj.getString("Noun")+"\"");
            id = getResources().getIdentifier(Image, "drawable", getPackageName());
            imageView.setImageResource(id);

        } catch (Throwable t) {
            Toast.makeText(this, "Error in Load Question", Toast.LENGTH_LONG).show();
        }
    }

    public void SelectionAction(String answer){
        answerarray[i]= new ResultActivity.AnswerGroup(Image,Ans_Article,answer);
        Toast.makeText(getApplicationContext(), answer+" Answer" , Toast.LENGTH_LONG).show();
        radioArticleGroup.clearCheck();
        radioGenderGroup.clearCheck();
        i = i + 1;
        txt_score.setText("Score : " + score + " / " + Questions.length);

    }




}
