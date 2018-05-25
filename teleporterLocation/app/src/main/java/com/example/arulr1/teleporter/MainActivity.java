package com.example.arulr1.teleporter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView locationtext,locationcity;
    private ProgressDialog dialog;
    String cityName;
    ImageView photo;
    LocationManager locationManager;
    String providername;
    Criteria criteria;
    Location currentlocation;
    Double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationtext = (TextView)findViewById(R.id.location);

        locationcity= (TextView)findViewById(R.id.locationcity);

        Button btn_get = (Button)findViewById(R.id.btn_get);
        dialog = new ProgressDialog(this);
        photo = (ImageView)findViewById(R.id.imageView);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        boolean locationpermissionok = checkLocationPermission();
        if(locationpermissionok){
            //providername = locationManager.getBestProvider(criteria,false);
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0,new customlocationlistner());
        }
        else {
            requestLocationPermission();
        }

        currentlocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        String curlat =String.valueOf(currentlocation.getLatitude());
        String curlon =String.valueOf(currentlocation.getLongitude());
        locationtext.setText("Latitude : "+currentlocation.getLatitude()+", Longitude : "+currentlocation.getLongitude());

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationCity();
            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestcode, String[] permissions,int[] grantResults){
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            checkLocationPermission();
            {
               // providername = locationManager.getBestProvider(criteria,false);
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0,new customlocationlistner());
            }
        }


    }
    public boolean checkLocationPermission(){
        int finelocationOk = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationOk= ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if((finelocationOk!= PackageManager.PERMISSION_GRANTED)||(coarseLocationOk!=PackageManager.PERMISSION_GRANTED))
            return false;
        else
            return true;
    }

    public void requestLocationPermission(){
        String[] permissionIwant = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        ActivityCompat.requestPermissions(this,permissionIwant,42);
    }

    //Generate Random Location and call for nearer city
    public void getRandomLocation(){
        dialog.setMessage("Getting valid location, please wait.");
        dialog.show();

        String latitude =getRandomDoubleBetweenRange(-90,90);
        String longitude =getRandomDoubleBetweenRange(-180,180);

//        GetAPIData apithread = new GetAPIData();
//        apithread.execute(latitude,longitude);
    }

    public void getLocationCity(){
        dialog.setMessage("Getting city name for current location, please wait.");
        dialog.show();

        Double latitude =currentlocation.getLatitude();
        Double longitude =currentlocation.getLongitude();


        GetAPIData apithread = new GetAPIData();
        apithread.execute(lat,lon);
    }
    //Get Image URL from flickr using cityname
    public void LoadImage(){
        flickrAPI flickrapithread = new flickrAPI();
        flickrapithread.execute(cityName);
    }

    //get Image using URL and display on imageview
    public void showimage(String imgurl){
//        Uri uri = Uri.parse(imgurl); // missing 'http://' will cause crashed
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
        if(imgurl!=null) {
            GetPhotoData apithread = new GetPhotoData();
            apithread.execute(imgurl);
        }
    }

    public class customlocationlistner implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            locationtext.setText("Latitude : "+lat+", Longitude : "+lon);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    public static String getRandomDoubleBetweenRange(double min, double max){
        DecimalFormat df = new DecimalFormat("#.###");
        double x = (Math.random()*((max-min)+1))+min;
        return df.format(x);
    }

    class GetAPIData extends AsyncTask<Double,Void,String> {

        @Override
        protected String doInBackground(Double... param) {
            String JsonResult = null;
            Double latitude =param[0];
            Double longitude=param[1];
            String url = "http://www.geoplugin.net/extras/location.gp?lat="+latitude+"&long="+longitude+"&format=json";
//            String url = "http://www.geoplugin.net/extras/location.gp?lat=45.8741600&long=170.5036100&format=json";
//            String url = "http://www.geoplugin.net/extras/location.gp?lat=-45.8787605&long=170.5027976&format=json";

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
                while ((resstring = bufferedReader.readLine())!=null){
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
                locationcity.setText(city);
                cityName=city;
                if (dialog.isShowing()) {
                   dialog.dismiss();
                }
                LoadImage();
                }
                catch (JSONException e1) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(),"No city forund",Toast.LENGTH_LONG).show();
                //getLocationCity();
            }
        }

    }

    class flickrAPI extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String JsonResult = null;
            String city =param[0];
            String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=eda41a123d459be0f85276d37290651e&tags="+city+"&privacy_filter=1&content_type=4&format=json&nojsoncallback=1";
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
                while ((resstring = bufferedReader.readLine())!=null){
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
                JSONObject PhostsObject= JsonObject.getJSONObject("photos");
                JSONArray PhotoArray = PhostsObject.getJSONArray("photo");
                JSONObject imageobject=PhotoArray.getJSONObject(0);

                int ID =  imageobject.getInt("id");
                int serverID =  imageobject.getInt("server");
                int farmID =  imageobject.getInt("farm");
                String secret =  imageobject.getString("secret");

                String imgurl ="https://farm"+farmID+".staticflickr.com/"+serverID+"/"+ID+"_"+secret+".jpg";
                //String imgurl ="https://farm1.staticflickr.com/956/27308926677_4e9377afe5.jpg";
                //"id":"41460401914","owner":"102857435@N05","secret":"22a3a47a73","server":"830","farm":1,"title":"Aguascalientes","ispublic":1,"isfriend":0,"isfamily":0

                showimage(imgurl);
            }
            catch (JSONException e1) {
                Toast.makeText(getApplicationContext(),"No image forund",Toast.LENGTH_LONG).show();
               // getRandomLocation();
            }
        }

    }

    class GetPhotoData extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... param) {
            Bitmap JsonResult = null;
            String url ="https://farm1.staticflickr.com/956/27308926677_4e9377afe5.jpg";//param[0];
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
