package com.example.arulr1.news;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends DialogFragment {


    String imgurl=null;
    ImageView photo;
    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView=inflater.inflate(R.layout.fragment_photo,container,false);

        imgurl= getArguments().getString("imgurl");
        photo = (ImageView)fragmentView.findViewById(R.id.zoomimg);

        if(imgurl!=null) {
            GetPhotoData apithread = new GetPhotoData();
            apithread.execute();
        }
        return fragmentView;
    }

    //Get the image using url.
    class GetPhotoData extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String url = imgurl;
            try {
                URL urlobject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlobject.openConnection();
                con.connect();
                int responsecode = con.getResponseCode();

                InputStream inputstream = con.getInputStream();

                return BitmapFactory.decodeStream(inputstream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmapimg) {

            photo.setImageBitmap(bitmapimg);
        }

    }

}