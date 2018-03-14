package com.example.arulr1.intentapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonToSecondActivity = findViewById(R.id.buttonToSecondActivity);
        buttonToSecondActivity.setOnClickListener(new ChangeActivityButtonClickHandler());
    }
    public class ChangeActivityButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent ChangeActivityIntend = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(ChangeActivityIntend);
        }
    }

}
