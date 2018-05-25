package com.example.arulr1.webservicesearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {
    ListView infoList;
    JSONObject ArtistObject,JsonObject,Artist = null;
    String[] TopArtist = null;
    JSONArray ArrayImage,ArtistArray=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_search = (Button)findViewById(R.id.btn_search);
        final EditText editText = (EditText)findViewById(R.id.editText);
        infoList=(ListView) findViewById(R.id.infoList);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artist = editText.getText().toString();
                if(artist!=null){
                    GetAPIData apithread = new GetAPIData();
                    apithread.execute(artist);
                }
            }
        });
    }
    public void LoadList()
    {
        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(this,R.layout.listview,TopArtist);
        infoList.setAdapter(InfoGroupAdapter);
    }
    class GetAPIData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResult = null;
            String artist=(String)params[0];
            String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist="+artist+"&api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json";
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
                ArtistObject= JsonObject.getJSONObject("similarartists");
                ArtistArray = ArtistObject.getJSONArray("artist");
                //Toast.makeText(getApplicationContext(),JsonArray.toString(),Toast.LENGTH_LONG).show();
                int nartists = 10; //ArtistArray.length();
                TopArtist = new String[nartists];
                for(int i = 0; i < nartists; i++){
                    JSONObject currentEvent = ArtistArray.getJSONObject(i);
                    TopArtist[i]=currentEvent.getString("name");
                LoadList();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
