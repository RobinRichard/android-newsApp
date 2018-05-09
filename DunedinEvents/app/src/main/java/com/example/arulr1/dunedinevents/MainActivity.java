package com.example.arulr1.dunedinevents;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ListView infoList;
    JSONObject EventData,EventsObject = null;
    String[] EventGroup,Description = null;
    JSONArray EventArray=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadTxtFile();
        infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setOnItemClickListener(new InfoClickHandler());
        Button loadevents = (Button)findViewById(R.id.loadevents);
        loadevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadEventList();
            }
        });
    }
    public class InfoClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String Item=(String) parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(),Description[position], Toast.LENGTH_LONG).show();
        }
    }
    public void LoadEventList() {
        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(this,R.layout.listview,EventGroup);
        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(InfoGroupAdapter);
    }

    public void ReadTxtFile() {

        String assertfilename = "dunedin_events_2017.txt";
        String JSONinput="";
        AssetManager am = getAssets();
        InputStream is = null;
        int fileSize = 0;
        try {
            is = am.open(assertfilename);
            fileSize = is.available();
            byte[] jsonBuffer = new byte[fileSize];
            is.read(jsonBuffer);
            is.close();
            JSONinput = new String(jsonBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            EventData = new JSONObject(JSONinput);
            EventsObject = new JSONObject(EventData.getString("events"));
            EventArray = EventsObject.getJSONArray("event");
            int nevents = EventArray.length();
            EventGroup = new String[nevents];
            Description = new String[nevents];
            for(int i = 0; i < nevents; i++){
                JSONObject currentEvent = EventArray.getJSONObject(i);
                EventGroup[i]=currentEvent.getString("title");
                Description[i]=currentEvent.getString("description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
