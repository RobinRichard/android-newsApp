package com.example.arulr1.task21textevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText mytextbox = (EditText)findViewById(R.id.mytextbox);
        mytextbox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if (keyCode == 77) {
                    Toast.makeText(MainActivity.this, "Dont Type @ Symbol", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }
}
