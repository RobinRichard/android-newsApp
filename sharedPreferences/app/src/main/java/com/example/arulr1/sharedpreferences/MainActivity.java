package com.example.arulr1.sharedpreferences;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("demoPrefs",MODE_PRIVATE);
        prefsEditor =prefs.edit();
        welcome = (TextView)findViewById(R.id.welcome);
        String Languagepref = prefs.getString("language",null);
        String Colorpref = prefs.getString("color",null);
        if(Languagepref != null){
            welcome.setText(getGreetings(Languagepref));
        }
        if(Colorpref != null) {
            setclr(Colorpref);
        }
        final RadioGroup radiogrp = (RadioGroup) findViewById(R.id.radiogrp);
        final RadioGroup radiogrpclr = (RadioGroup) findViewById(R.id.radiogrpclr);

        final Button buttonSubmit = findViewById(R.id.buttonset);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int selectedId = radiogrp.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String language = (String) radioButton.getText();
                prefsEditor.putString("language",language);
                prefsEditor.commit();
                welcome.setText(getGreetings(language));


                int selectedclr = radiogrpclr.getCheckedRadioButtonId();
                RadioButton radioButtonclr = (RadioButton) findViewById(selectedclr);
                String clr = (String) radioButtonclr.getText();
                prefsEditor.putString("color",clr);
                prefsEditor.commit();
                setclr(clr);
            }
        });
    }

    public String getGreetings(String Language){
        String greetings="";
        switch (Language)
        {
            case "French":
                greetings="Bonjour Le Monde";
                break;
            case "German":
                greetings="Hallo Welt";
                break;
            case "Spanish":
                greetings="Hola Mundo";
                break;
        }
        return greetings;
    }

    public void setclr(String clr){
        switch (clr)
        {
            case "Red":
                welcome.setTextColor(Color.RED);
                break;
            case "Green":
                welcome.setTextColor(Color.GREEN);
                break;
            case "Blue":
                welcome.setTextColor(Color.BLUE);
                break;
        }
    }
}
