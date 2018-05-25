package com.example.arulr1.citylitsqlitedb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase DBcon;

    String[] cityarray;
    String[] countryarray;
    TextView country_lbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDB("CityDB");
        createTable("tbl_city");

        insertdata();


        selectcountry();

        country_lbl = (TextView)findViewById(R.id.country_lbl);
        country_lbl.setText("Please Select Any Country");
        final Spinner spinnerCountry = (Spinner) findViewById(R.id.spinnerMonths);
        int lay_ID = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, lay_ID, countryarray);
        spinnerCountry.setAdapter(countryAdapter);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String country=spinnerCountry.getSelectedItem().toString();
                selectcity(country);
                country_lbl.setText("Cities in "+country+" :");
                ArrayAdapter<String> InfoGroupAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.listview, cityarray);
                ListView infoList = (ListView) findViewById(R.id.InfoList);
                infoList.setAdapter(InfoGroupAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void  createDB(String DBName){
        DBcon = openOrCreateDatabase(DBName,MODE_PRIVATE,null);
    }

    public void  createTable(String tbl_Name){
        String drp_qry = "DROP TABLE IF EXISTS "+tbl_Name;
        DBcon.execSQL(drp_qry);

        String create_qry = "CREATE TABLE IF NOT EXISTS " + tbl_Name + " (city_ID INTEGER PRIMARY KEY AUTOINCREMENT, city_Name TEXT NOT NULL, country_Name TEXT NOT NULL);";
        DBcon.execSQL(create_qry);
    }

    public void insertcity(String city,String country){
        DBcon.execSQL("INSERT INTO tbl_city VALUES(NULL,'"+ city +"','"+country+"')");
    }

    public void selectcountry(){
        String select_qry = "select DISTINCT country_Name from tbl_city";
        Cursor recoed_set = DBcon.rawQuery(select_qry,null);
        int recorst_cnt = recoed_set.getCount();
        countryarray = new String[recorst_cnt];
        int country_index  = recoed_set.getColumnIndex("country_Name");
        recoed_set.moveToFirst();
        for(int row=0 ; row<recorst_cnt ; row++){
            countryarray[row]=recoed_set.getString(country_index);
            recoed_set.moveToNext();
        }
    }
    public void selectcity(String countryName){
        String select_qry = "select * from tbl_city where country_Name='"+countryName+"'";
        Cursor recoed_set = DBcon.rawQuery(select_qry,null);
        int recorst_cnt = recoed_set.getCount();
        cityarray = new String[recorst_cnt];
        int city_index  = recoed_set.getColumnIndex("city_Name");
        recoed_set.moveToFirst();
        for(int row=0 ; row<recorst_cnt ; row++){
            cityarray[row]=recoed_set.getString(city_index);
            recoed_set.moveToNext();
        }
    }
    public void insertdata()
    {
        insertcity("Cairo","Egypt");
        insertcity("Berlin","Germany");
        insertcity("TamilNadu","India");
        insertcity("Delhi","India");
        insertcity("Dunedin","New Zealand");
        insertcity("Amsterdam","The Netherland");
        insertcity("Florida","USA");
    }
}
