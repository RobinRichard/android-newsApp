package com.example.arulr1.dialogfragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.DialogFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackgroundFragment extends DialogFragment {

    View fragmentView;
    public BackgroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView=inflater.inflate(R.layout.fragment_background,container,false);
        // Inflate the layout for this fragment
        //Load List
        String[] AnimalGroup={"autumn","spring","summer","winter"};
        ArrayAdapter<String> AnimalGroupAdapter=new ArrayAdapter<String>(getActivity(),R.layout.listlayout,AnimalGroup);
        ListView infoList=(ListView) fragmentView.findViewById(R.id.AniList);
        infoList.setAdapter(AnimalGroupAdapter);

        ListView AniList = (ListView)fragmentView.findViewById(R.id.AniList);
        AniList.setOnItemClickListener(new AniListHandler());
        return fragmentView;
    }
    public class AniListHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String Item=(String) parent.getItemAtPosition(position).toString();
            MainActivity main = (MainActivity) getActivity();
            main.changeBackground(Item);

        }
    }

}
