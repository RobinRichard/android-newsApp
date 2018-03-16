package com.example.arulr1.passdatawhenlaunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnHome = (Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new HomeClickHandler());
    }
    public class HomeClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditText txtUserName =(EditText)findViewById(R.id.txtUserName);
            Intent HomeIntend =new Intent(SettingsActivity.this,MainActivity.class);
            String Uname=txtUserName.getText().toString();
            if(Uname.length()<3)
                Toast.makeText(getApplicationContext(), "User Name Must Be atleast 3 Characters",Toast.LENGTH_LONG).show();
            else {
                HomeIntend.putExtra("Uname", Uname);
                startActivity(HomeIntend);
            }
        }
    }
}
