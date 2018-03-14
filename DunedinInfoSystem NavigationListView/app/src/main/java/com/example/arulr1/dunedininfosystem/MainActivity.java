package com.example.arulr1.dunedininfosystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
                case "Services":
                    NextActivity=new Intent(MainActivity.this,ServicesActivity.class);
                    break;
                case "Entertainment":
                    NextActivity=new Intent(MainActivity.this,EntertainmentActivity.class);
                    break;
                case "Dining":
                    NextActivity=new Intent(MainActivity.this,DiningActivity.class);
                    break;
                case "Shopping":
                    NextActivity=new Intent(MainActivity.this,ShoppingActivity.class);
                    break;
                default:
                    NextActivity=null;
            }
            if(NextActivity!=null)
                startActivity(NextActivity);
        }
    }
    public void LoadInfoList(){
        String[] InfoGroup={"Services","Entertainment","Dining","Shopping"};
        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(this,R.layout.item_dunedin_info,InfoGroup);
        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(InfoGroupAdapter);
    }
}
