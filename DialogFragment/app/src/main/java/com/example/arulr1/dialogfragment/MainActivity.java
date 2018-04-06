package com.example.arulr1.dialogfragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {
    BackgroundFragment bf;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = (LinearLayout)findViewById(R.id.mainlayout);
        Button btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new btn_changeHandler());
    }
    public class btn_changeHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            bf = new BackgroundFragment();
            bf.show(fm,"AniList");
        }
    }
    public void changeBackground(String  background){
        bf.dismiss();
        int id = getResources().getIdentifier(background, "drawable", getPackageName());
        ll.setBackgroundResource(id);
    }
}
