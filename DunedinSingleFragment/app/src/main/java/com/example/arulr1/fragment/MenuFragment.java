package com.example.arulr1.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    View fragmenView = null;
    int layoutId;
    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmenView= inflater.inflate(layoutId, container, false);

        return fragmenView;
    }

    public void setLayout(String menu) {
        switch (menu) {
            case "Services":
                layoutId = R.layout.fragment_services;
                break;
            case "Entertainment":
                layoutId = R.layout.fragment_entertainment;
                break;
            case "Dining":
                layoutId = R.layout.fragment_dining;
                break;
            case "Shopping":
                layoutId = R.layout.fragment_shopping;
                break;

        }
    }
}
