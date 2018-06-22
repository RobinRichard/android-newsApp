package com.example.arulr1.qrreader;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    Button scan;
    TextView result;
    String[] months = {"january", "february", "march","april","may","june","july","august","september","october","november", "december"};
    Map<String,Integer> dict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dict = new HashMap<String,Integer>();
        for (int i = 0, l = months.length; i < l; i++) {
            dict.put(months[i], 0);
        }

        scan = (Button) findViewById(R.id.scan);
        result=(TextView)findViewById(R.id.result);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivityForResult(i,1001);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                // get String data from Intent
                String returnString = data.getStringExtra("MonthName");
                if(returnString.contains("http")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(returnString));
                    startActivity(i);
                }
                else {
                    dict.put(returnString.toLowerCase(), dict.get(returnString.toLowerCase()) + 1);
                    Toast.makeText(this, returnString + dict.get(returnString.toLowerCase()), Toast.LENGTH_LONG).show();
                    String str = "";
                    str += "Just Saw " + returnString + "\n";
                    for (Map.Entry<String, Integer> e : dict.entrySet()) {
                        str += e.getKey().toUpperCase() + " : " + e.getValue() + "\n";
                    }
                    result.setText(str);
                }

            }
        }
    }
}
