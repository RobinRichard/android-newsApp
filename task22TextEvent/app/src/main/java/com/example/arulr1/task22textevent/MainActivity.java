package com.example.arulr1.task22textevent;

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
        final EditText mytextbox = (EditText)findViewById(R.id.mytextbox);
        mytextbox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if (keyCode == 66) {
                    String mystring=mytextbox.getText().toString();

                    if(mystring.length()<8){
                        Toast.makeText(MainActivity.this, "User Name Must be 8 charcters, "+mystring, Toast.LENGTH_LONG).show();
                    }
                    if(mystring.length()==8){
                        Toast.makeText(MainActivity.this, "Thanks for that, "+mystring, Toast.LENGTH_LONG).show();
                    }

                }
                return true;
            }
        });
    }
}
