package com.example.arulr1.practical12b;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resourcesResolver = getResources();
        String papername= resourcesResolver.getString(R.string.papername);
        TextView txtColor = (TextView) findViewById(R.id.txtColor);
        txtColor.setText(papername);
    }
}
