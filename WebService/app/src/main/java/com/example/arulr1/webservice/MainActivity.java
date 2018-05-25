package com.example.arulr1.webservice;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class MainActivity extends FragmentActivity {
    ListView infoList;
    JSONObject ArtistObject,JsonObject,Artist = null;
    String[] TopArtist = null;
    JSONArray ArrayImage,ArtistArray=null;
    ArtistGroup[] artistGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_get = (Button)findViewById(R.id.btn_get);
        infoList=(ListView) findViewById(R.id.InfoList);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAPIData apithread = new GetAPIData();
                apithread.execute();
            }
        });



    }
    public class IconClickListner implements View.OnClickListener{
        @Override
        public void onClick(View v){
            PhotoFragment photo = new PhotoFragment();
            Bundle bdl = new Bundle();
            String imgurl=(String) v.getTag();
            bdl.putString("imgurl",imgurl);
            photo.setArguments(bdl);
            FragmentManager fm = getSupportFragmentManager();
            photo.show(fm,"Photo");
        }
    }
    public void LoadArtistList() {
        ArtistGroupAdapter artistGroupAdapter = new ArtistGroupAdapter(this, R.layout.custum_view_list, artistGroups);
        infoList.setAdapter(artistGroupAdapter);
    }

    public class ArtistGroup{

        String Name;
        String listeners;
        String imageURL;
        public ArtistGroup(String Name,String listeners,String imageURL){
            this.Name=Name;
            this.listeners=listeners;
            this.imageURL=imageURL;
        }
        public String getName(){
            return Name;
        }
        public String getListeners(){
            return listeners;
        }
        @Override
        public String toString(){
            return Name;
        }
    }

    public class ArtistGroupAdapter extends ArrayAdapter<ArtistGroup>{

        public ArtistGroupAdapter(Context context, int resourse, ArtistGroup[] objects){
            super(context,resourse,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container){

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

            View cuistomView= inflater.inflate(R.layout.custum_view_list,container,false);

            TextView name_text = (TextView)cuistomView.findViewById(R.id.name_text);
            TextView listner_text = (TextView)cuistomView.findViewById(R.id.listner_text);

            ArtistGroup current = getItem(position);

            name_text.setText(current.getName());
            listner_text.setText(current.getListeners());

            name_text.setTag(current.imageURL);
            name_text.setOnClickListener(new IconClickListner());

            return  cuistomView;
        }
    }
    class GetAPIData extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            String JsonResult = null;
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json";
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

                JsonObject = new JSONObject(fetchedString);
                ArtistObject= JsonObject.getJSONObject("artists");
                ArtistArray = ArtistObject.getJSONArray("artist");
                //Toast.makeText(getApplicationContext(),JsonArray.toString(),Toast.LENGTH_LONG).show();
                int nartists = 20; //ArtistArray.length();
                artistGroups= new ArtistGroup[nartists];
                TopArtist = new String[nartists];
                Artist = ArtistArray.getJSONObject(0);
                for(int i = 0; i < nartists; i++){
                    JSONObject currentEvent = ArtistArray.getJSONObject(i);
                    ArrayImage=currentEvent.getJSONArray("image");
                    JSONObject img = ArrayImage.getJSONObject(4);
                    String imgurl=img.getString("#text");
                    artistGroups[i]=new ArtistGroup(currentEvent.getString("name"),currentEvent.getString("listeners"),img.getString("#text"));
                    TopArtist[i]=currentEvent.getString("name")+" , "+currentEvent.getString("listeners");
                }
                LoadArtistList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
