package com.example.arulr1.teleporter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView location,imageurl;
    private ProgressDialog dialog;
    String cityName;
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = (TextView)findViewById(R.id.location);
        imageurl = (TextView)findViewById(R.id.imageurl);
        Button btn_get = (Button)findViewById(R.id.btn_get);
        dialog = new ProgressDialog(this);
        photo = (ImageView)findViewById(R.id.imageView);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomLocation();
            }
        });

    }

    //Generate Random Location and call for nearer city
    public void getRandomLocation(){
        dialog.setMessage("Getting valid location, please wait.");
        dialog.show();
        String latitude =getRandomDoubleBetweenRange(-90,90);
        String longitude =getRandomDoubleBetweenRange(-180,180);
        GetAPIData apithread = new GetAPIData();
        apithread.execute(latitude,longitude);
    }

    //Get Image URL from flickr using cityname
    public void LoadImage(){
        flickrAPI flickrapithread = new flickrAPI();
        flickrapithread.execute(cityName);
    }

    //get Image using URL and display on imageview
    public void showimage(String imgurl){
        if(imgurl!=null) {
            GetPhotoData apithread = new GetPhotoData();
            apithread.execute(imgurl);
        }
    }
    public static String getRandomDoubleBetweenRange(double min, double max){
        DecimalFormat df = new DecimalFormat("#.###");
        double x = (Math.random()*((max-min)+1))+min;
        return df.format(x);
    }

    class GetAPIData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String JsonResult = null;
            String latitude =param[0];
            String longitude=param[1];
            String url = "http://www.geoplugin.net/extras/location.gp?lat="+latitude+"&long="+longitude+"&format=json";
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
                location.setText(city);
                cityName=city;
                if (dialog.isShowing()) {
                   dialog.dismiss();
                }
                LoadImage();
                }
                catch (JSONException e1) {
                    getRandomLocation();
            }
        }

    }

    class flickrAPI extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String JsonResult = null;
            String city =param[0];
           // String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=eda41a123d459be0f85276d37290651e&tags="+city+"&privacy_filter=1&content_type=4&format=json&nojsoncallback=1";
            String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=eda41a123d459be0f85276d37290651e&extras="+city+"&format=json&nojsoncallback=1";

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
                JSONObject imageobject=PhotoArray.getJSONObject(PhotoArray.length()/2);

                int ID =  imageobject.getInt("id");
                int serverID =  imageobject.getInt("server");
                int farmID =  imageobject.getInt("farm");
                String secret =  imageobject.getString("secret");

                String imgurl ="https://farm"+farmID+".staticflickr.com/"+serverID+"/"+ID+"_"+secret+".jpg";
                imageurl.setText(imgurl);
                showimage(imgurl);
            }
            catch (JSONException e1) {
                getRandomLocation();
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
