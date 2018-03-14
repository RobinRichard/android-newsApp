package com.example.arulr1.formcontrols;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup radioGroup_instruments = (RadioGroup) findViewById(R.id.radioGroup_instruments);


        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

        final Spinner spinnerMonths = (Spinner) findViewById(R.id.spinnerMonths);
        int lay_ID = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, lay_ID, months);
        spinnerMonths.setAdapter(monthAdapter);


        final Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int selectedId = radioGroup_instruments.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String course = (String) radioButton.getText();
                String month=spinnerMonths.getSelectedItem().toString();
                String str_feedback = "You have Endrolled For "+course+" Lessons in "+month;
                TextView feedback = (TextView)findViewById(R.id.feedback) ;
                feedback.setText(str_feedback);
            }
        });
    }
}