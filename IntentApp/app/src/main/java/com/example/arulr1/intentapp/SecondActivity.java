package com.example.arulr1.intentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button buttonToThirdActivity =(Button) findViewById(R.id.buttonToThirdActivity);
        buttonToThirdActivity.setOnClickListener(new ChangeActivity2ButtonClickHandler());

    }
    public class ChangeActivity2ButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent ChangeActivityIntend = new Intent(SecondActivity.this, ThirdActivity.class);
            startActivity(ChangeActivityIntend);
        }
    }
}
