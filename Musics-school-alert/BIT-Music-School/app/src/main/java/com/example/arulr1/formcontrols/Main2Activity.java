package com.example.arulr1.formcontrols;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Button btn_enroll = findViewById(R.id.btn_enroll);
        btn_enroll.setOnClickListener(new ChangeActivityButtonClickHandler());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_enroll.callOnClick();
            }
        }, 5000);
    }

    public class ChangeActivityButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent ChangeActivityIntend = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(ChangeActivityIntend);
        }
    }
}
