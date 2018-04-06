package com.example.arulr1.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView=inflater.inflate(R.layout.fragment_list,container,false);

        //Load List

        String[] AnimalGroup={"Antelope","Buffalo","Coyote","Donkey","Emu","Ferret","Gorilla","Heron"};
        ArrayAdapter<String> AnimalGroupAdapter=new ArrayAdapter<String>(getActivity(),R.layout.listlayout,AnimalGroup);
        ListView infoList=(ListView) fragmentView.findViewById(R.id.AniList);
        infoList.setAdapter(AnimalGroupAdapter);
        return fragmentView;
    }



}
