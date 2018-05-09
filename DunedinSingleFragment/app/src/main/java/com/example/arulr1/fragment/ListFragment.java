package com.example.arulr1.fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        String[] InfoGroup={"Services","Entertainment","Dining","Shopping"} ;
        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(getActivity(),R.layout.listlayout,InfoGroup);
        ListView infoList=(ListView) fragmentView.findViewById(R.id.InfoList);
        infoList.setAdapter(InfoGroupAdapter);

        infoList.setOnItemClickListener(new InfoListHandler());
        return fragmentView;
    }

    public class InfoListHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String Item=(String) parent.getItemAtPosition(position).toString();

            FragmentManager fm = getFragmentManager();
            MenuFragment menuFragment = new MenuFragment();
            menuFragment.setLayout(Item);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_right_container, menuFragment);
            ft.commit();

        }
    }



}
