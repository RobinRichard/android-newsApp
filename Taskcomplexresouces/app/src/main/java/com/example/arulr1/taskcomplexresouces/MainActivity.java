package com.example.arulr1.taskcomplexresouces;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myTextView = (TextView) findViewById(R.id.myTextView);
        Resources resourcesResolver = getResources();
        int daysarray[]= resourcesResolver.getIntArray(R.array.FebFridays);
        String FebDays="Februarty Fridays are on : ";
        for (int i=0;i< daysarray.length;i++)
        {
            FebDays += daysarray[i] + " ";
        }
        myTextView.setText(FebDays);

    }
}
