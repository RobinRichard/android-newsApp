package com.example.arulr1.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    static GridView categorygrid;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.fragment_category, container, false);

        categorygrid=(GridView) fragmentView.findViewById(R.id.categorygrid);

        //Category click listener
        categorygrid.setOnItemClickListener(new ItemSelectListner());

        return fragmentView;
    }
    public class ItemSelectListner implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String category = ((MainActivity) getActivity()).Category[position];
            Intent i = new Intent(getContext(), NewsActivity.class);
            i.putExtra("category", category);
            startActivity(i);
        }
    }
}
