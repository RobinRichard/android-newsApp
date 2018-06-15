package com.example.arulr1.news;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends AppCompatActivity  {


    private GoogleMap mMap;
    private Toolbar toolbar;
    String City,Region,Currentcity;
    LatLng maplocation;
    Button btn_getnews;
    LatLng currentlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            //set default location if no location data id found
            currentlocation = new LatLng(-34, 151);
        } else {
            Double lat = extras.getDouble("lat");
            Double lon = extras.getDouble("lon");
            currentlocation = new LatLng(lat, lon);
        }

        btn_getnews = (Button)findViewById(R.id.btn_getnews);
        btn_getnews.setOnClickListener(new buttonclickLister());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new onreadylistener());
    }

    //listner for back button in tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //listener for get news button in mapview return the result to main activity onActivityResult method
    public class buttonclickLister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent MainActivityIntent = new Intent(MapsActivity.this,MainActivity.class);
            MainActivityIntent.putExtra("maploc",Currentcity);
            setResult(Activity.RESULT_OK,MainActivityIntent);
            finish();
        }
    }


    public class onreadylistener implements OnMapReadyCallback {
        @Override
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;

            calllocationapi(currentlocation);

            mMap.setOnMapClickListener(new mapclicklistner());
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }
    //Map clcik listner
    public class mapclicklistner implements GoogleMap.OnMapClickListener {
        @Override
        public void onMapClick(LatLng latLng) {
            calllocationapi(latLng);
        }
    }

    //call the assyn method (geoplugin api) with the map location and to get location details for the selected coordinates
    public void calllocationapi(LatLng latLng){
        mMap.clear();
        btn_getnews.setVisibility(View.INVISIBLE);
        City="";
        Region="";
        maplocation=latLng;
        MainActivity.currentlocation.setLatitude(latLng.latitude);
        MainActivity.currentlocation.setLongitude(latLng.longitude);
        GetLocationData apithread = new GetLocationData();
        apithread.execute(latLng.latitude, latLng.longitude);
    }

    //show marker for selected location
    public void showlocation(String Country){

        Currentcity = City+","+Region+","+Country;

        if(Country != "") {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(maplocation, 10));
            mMap.addMarker(new MarkerOptions().position(maplocation).title(Currentcity)).showInfoWindow();
            btn_getnews.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(getApplicationContext(),"Select Some other Location",Toast.LENGTH_LONG).show();
    }

    //get the full name of country for the iso country code returned by geoplugin api
    public void getcountry(String city,String region, String countrycode){
        City=city;
        Region=region;
        GetCountryName apithread = new GetCountryName();
        apithread.execute(countrycode);

    }
    //get location details uisng geoplugin api
    public class GetLocationData extends AsyncTask<Double, Void, String> {
        @Override
        protected String doInBackground(Double... param) {
            String JsonResult = null;
            Double latitude = param[0];
            Double longitude = param[1];
            String url = "http://www.geoplugin.net/extras/location.gp?lat=" + latitude + "&long=" + longitude + "&format=json";

            try {
                URL urlobject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlobject.openConnection();
                con.connect();
                int responsecode = con.getResponseCode();

                InputStream inputstream = con.getInputStream();

                InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
                BufferedReader bufferedReader = new BufferedReader(inputstreamreader);

                String resstring;
                StringBuilder stringBuilder = new StringBuilder();
                while ((resstring = bufferedReader.readLine()) != null) {
                    stringBuilder = stringBuilder.append(resstring);
                }
                JsonResult = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JsonResult;
        }

        @Override
        protected void onPostExecute(String fetchedString) {
            try {
                JSONObject JsonObject = new JSONObject(fetchedString);
                String city = JsonObject.getString("geoplugin_place");
                String region = JsonObject.getString("geoplugin_region");
                String country = JsonObject.getString("geoplugin_countryCode");
                getcountry(city,region,country);
            } catch (JSONException e1) {
                showlocation("");
                Log.e("NewsApp", "No city found", e1);
            }
        }

    }

    //get country's full name from iso 3166 country codes
    public class GetCountryName extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            String JsonResult = null;
            String countrycode = param[0];
            String url = " https://restcountries.eu/rest/v2/alpha/" + countrycode;

            try {
                URL urlobject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlobject.openConnection();
                con.connect();
                int responsecode = con.getResponseCode();

                InputStream inputstream = con.getInputStream();

                InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
                BufferedReader bufferedReader = new BufferedReader(inputstreamreader);

                String resstring;
                StringBuilder stringBuilder = new StringBuilder();
                while ((resstring = bufferedReader.readLine()) != null) {
                    stringBuilder = stringBuilder.append(resstring);
                }
                JsonResult = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JsonResult;
        }

        @Override
        protected void onPostExecute(String fetchedString) {
            try {
                JSONObject JsonObject = new JSONObject(fetchedString);
                String country = JsonObject.getString("name");
                showlocation(country);
            } catch (JSONException e1) {
                showlocation("");
                Log.e("NewsApp", "No city found", e1);
            }
        }

    }

}