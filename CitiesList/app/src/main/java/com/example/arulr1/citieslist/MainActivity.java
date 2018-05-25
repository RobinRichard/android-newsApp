package com.example.arulr1.citieslist;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(this,R.layout.listview,LoadCities());
        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(InfoGroupAdapter);
    }

    public ArrayList<String> LoadCities()
    {
        ArrayList<String> cities = new ArrayList<String>();
        AssetManager am = getAssets();
        try {
            InputStream is = am.open("cities.txt");
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String currentstr;
            while((currentstr= br.readLine())!=null){
                cities.add(currentstr);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
