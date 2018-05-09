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

        showMenu();
    }

    public void showMenu() {
        Fragment dynamicFragment = new ListFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_left_container, dynamicFragment);
        ft.commit();
    }

    public void Menu(String menu) {
        Fragment dynamicFragment = null;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (menu) {
            case "Services":
                dynamicFragment = new ServicesFragment();
                break;
            case "Entertainment":
                dynamicFragment = new EntertainmentFragment();
                break;
            case "Dining":
                dynamicFragment = new DiningFragment();
                break;
            case "Shopping":
                dynamicFragment = new ShoppingFragment();
                break;
            default:
                dynamicFragment = null;
        }
        if (dynamicFragment != null)
            ft.replace(R.id.fragment_right_container, dynamicFragment);
        ft.commit();
    }
}

