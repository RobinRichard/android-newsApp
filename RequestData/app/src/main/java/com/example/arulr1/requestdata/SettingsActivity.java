package com.example.arulr1.requestdata;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView textViewSettings =(TextView)findViewById(R.id.textViewSettings);
        int clr = textViewSettings.getCurrentTextColor();

        Intent HomeIntent = new Intent(SettingsActivity.this,MainActivity.class);
        HomeIntent.putExtra("Color",clr);
        setResult(Activity.RESULT_OK,HomeIntent);
        finish();
    }
}
