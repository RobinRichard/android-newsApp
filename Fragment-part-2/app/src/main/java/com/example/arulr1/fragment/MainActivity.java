package com.example.arulr1.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.landscape);

        Button ShowImageFragment = (Button) findViewById(R.id.ShowImageFragment);
        ShowImageFragment.setOnClickListener(new ShowImageFragmentHandler());
        Button ShowListFragment = (Button) findViewById(R.id.ShowListFragment);
        ShowListFragment.setOnClickListener(new ShowListFragmentHandler());
        Button Clear = (Button) findViewById(R.id.Clear);
        Clear.setOnClickListener(new ClearHandler());


    }

    public class ShowImageFragmentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Fragment dynamicFragment = new ImageFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_right_container, dynamicFragment);
            ft.commit();
        }
    }

    public class ShowListFragmentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Fragment dynamicFragment = new ListFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_left_container, dynamicFragment);
            ft.commit();
        }
    }
    public class ClearHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.fragment_right_container);
            ll.removeAllViews();
            LinearLayout l2 = (LinearLayout) findViewById(R.id.fragment_left_container);
            l2.removeAllViews();
        }
    }
}
