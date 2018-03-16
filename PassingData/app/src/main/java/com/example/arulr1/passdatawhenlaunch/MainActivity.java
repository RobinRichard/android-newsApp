package com.example.arulr1.passdatawhenlaunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtUnameDisplay=(TextView)findViewById(R.id.txtUnameDisplay);

        Intent Date=getIntent();
        String Uname=Date.getStringExtra("Uname");
        if(Uname!=null)
            txtUnameDisplay.setText(Uname);
        else
            txtUnameDisplay.setText("No Username Choosen Yet...");

        Button btnSettings = (Button)findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new SettingsClickHandler());
    }
    public class SettingsClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent settingsIntend =new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(settingsIntend);
        }
    }
}
