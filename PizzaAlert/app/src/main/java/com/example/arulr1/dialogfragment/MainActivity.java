package com.example.arulr1.dialogfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    ImageView pizzaimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pizzaimg = (ImageView)findViewById(R.id.pizzaimg);
        Button btn_change = (Button) findViewById(R.id.btn_order);
        btn_change.setOnClickListener(new btn_changeHandler());
    }
    public class btn_changeHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager manager = getSupportFragmentManager();
            Fragment frag = manager.findFragmentByTag("fragment_edit_name");
            if (frag != null) {
                manager.beginTransaction().remove(frag).commit();
            }
            BackgroundFragment alertDialogFragment = new BackgroundFragment();
            alertDialogFragment.show(manager, "fragment_edit_name");
        }
    }
    public void methods(String flag){
        if(flag=="OK"){
            pizzaimg.setImageResource(R.drawable.pizza);
        }
        if(flag=="Cancel"){
            pizzaimg.setImageResource(R.drawable.sad_face);
        }

    }
}
