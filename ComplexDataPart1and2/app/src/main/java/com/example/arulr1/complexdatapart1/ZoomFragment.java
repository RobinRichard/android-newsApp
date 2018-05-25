package com.example.arulr1.complexdatapart1;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZoomFragment extends DialogFragment {


    public ZoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView=inflater.inflate(R.layout.fragment_zoom,container,false);

        int img_src= getArguments().getInt("Img_ID");

        //Toast.makeText(getContext(),img_src+" : Id",Toast.LENGTH_LONG).show();
        ImageView zoomimg = (ImageView)fragmentView.findViewById(R.id.zoomimg);
        zoomimg.setImageResource(img_src);
        return fragmentView;
    }

}
