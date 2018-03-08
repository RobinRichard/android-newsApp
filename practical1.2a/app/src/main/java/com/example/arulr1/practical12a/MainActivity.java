package com.example.arulr1.practical12a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        String dogbreed = "";
        Random rGen = new Random();
        int val = rGen.nextInt(4);
        switch (val)
        {
            case 0 :
                dogbreed="Foodle";
                break;
            case 1 :
                dogbreed="Labrador";
                break;
            case 2 :
                dogbreed="Shar Pei";
                break;
            case 3 :
                dogbreed="NewfoundLand";
                break;

        }
        txtDisplay.setText(dogbreed);

    }
}
