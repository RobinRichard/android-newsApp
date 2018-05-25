package com.example.arulr1.complexdatapart1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadInfoList();
        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setOnItemClickListener(new InfoClickHandler());
    }
    public class InfoClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String Item=(String) parent.getItemAtPosition(position).toString();
            Intent NextActivity;
            switch (Item){
                case "Terrier":
                    NextActivity=new Intent(MainActivity.this,TerrierActivity.class);
                    break;
                default:
                    NextActivity=null;
            }
            if(NextActivity!=null)
                startActivity(NextActivity);
        }
    }
    public void LoadInfoList(){
        String[] InfoGroup={"Sporting","Hound","Working","Terrier","Toy","Non-Sporting","Herding"};
        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(this,R.layout.doggroup,InfoGroup);
        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(InfoGroupAdapter);
    }

}

