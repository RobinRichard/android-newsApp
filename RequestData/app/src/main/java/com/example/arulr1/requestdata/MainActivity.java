package com.example.arulr1.requestdata;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_change_color = (Button)findViewById(R.id.btn_change_color);
        btn_change_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSettings=new Intent(MainActivity.this,SettingsActivity.class);
                startActivityForResult(IntentSettings,0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intend){
        if((requestCode==0)&&resultCode== Activity.RESULT_OK){
            Bundle bundle=intend.getExtras();
            int clr = bundle.getInt("Color");
            TextView textViewMain =(TextView)findViewById(R.id.textViewMain);
            textViewMain.setTextColor(clr);
        }
    }
}
