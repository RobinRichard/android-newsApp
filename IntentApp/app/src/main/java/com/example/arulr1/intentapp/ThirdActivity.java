package com.example.arulr1.intentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Button buttonToFinalActivity =(Button) findViewById(R.id.buttonToFinalActivity);
        buttonToFinalActivity.setOnClickListener(new ChangeActivity3ButtonClickHandler());
    }
    public class ChangeActivity3ButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Uri myURL= Uri.parse("http://www.google.com");
            Intent ChangeActivityIntend = new Intent(Intent.ACTION_VIEW, myURL);
            startActivity(ChangeActivityIntend);
        }
    }
}
