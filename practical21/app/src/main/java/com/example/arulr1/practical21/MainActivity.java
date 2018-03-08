package com.example.arulr1.practical21;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText textbox = (EditText)findViewById(R.id.textbox);
        textbox.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if (keyCode == KeyEvent.ACTION_DOWN) {
                    Toast.makeText(MainActivity.this, "Key Down Event", Toast.LENGTH_LONG).show();
                }
                if (keyevent.getAction() == KeyEvent.ACTION_UP) {
                    Toast.makeText(MainActivity.this, "Key up Event", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }
}
