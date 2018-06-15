package com.example.arulr1.news;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Toolbar and tabview variables
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //Control variables
    ImageButton btn_search, btn_close_search, btn_map;
    TextView txt_header,txt_headereprefix;
    AutoCompleteTextView SearchTextView;
    ProgressDialog dialog;

    //variables used to store fetched news and categories
    static String[] NewsCategory = null;
    public static NewsGroup[] newsGroups;
    String[] Category;
    ArrayList<NewsGroup> EntireNewsList = new ArrayList<NewsGroup>();
    ArrayList<String> EntireCategoryList = new ArrayList<String>();


    //variables to store location details
    LocationManager locationManager;
    public static Location currentlocation;
    String place = "";
    String[] locationArray;
    int locationArrayLength;
    int locationArrayIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_close_search = (ImageButton) findViewById(R.id.btn_close_search);
        btn_map = (ImageButton) findViewById(R.id.btn_map);
        txt_header = (TextView) findViewById(R.id.txt_header);
        txt_headereprefix = (TextView) findViewById(R.id.txt_headereprefix);
        SearchTextView = (AutoCompleteTextView) findViewById(R.id.SearchTextView);

        SearchTextView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));

        btn_search.setOnClickListener(new buttonclickLister());
        btn_close_search.setOnClickListener(new buttonclickLister());
        btn_map.setOnClickListener(new buttonclickLister());
        SearchTextView.setOnItemClickListener(new ItemSelectListner());
        //get gps location of user
        getLocation();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //MainActivity Buttons onClick Listners
    public class buttonclickLister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_search:
                    txt_header.setVisibility(v.INVISIBLE);
                    txt_headereprefix.setVisibility(v.INVISIBLE);
                    btn_search.setVisibility(v.INVISIBLE);
                    btn_close_search.setVisibility(v.VISIBLE);
                    SearchTextView.setVisibility(v.VISIBLE);
                    SearchTextView.requestFocus();
                    InputMethodManager immw = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    immw.showSoftInput(SearchTextView, InputMethodManager.SHOW_IMPLICIT);
                    break;

                case R.id.btn_close_search:
                    txt_header.setVisibility(v.VISIBLE);
                    txt_headereprefix.setVisibility(v.VISIBLE);
                    btn_search.setVisibility(v.VISIBLE);
                    btn_close_search.setVisibility(v.INVISIBLE);
                    SearchTextView.setVisibility(v.INVISIBLE);
                    SearchTextView.setText("");
                    //To hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(SearchTextView.getWindowToken(), 0);
                    break;

                case R.id.btn_map:
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    if(currentlocation!=null){
                        intent.putExtra("lat", currentlocation.getLatitude());
                        intent.putExtra("lon", currentlocation.getLongitude());
                    }
                    startActivityForResult(intent,4);
                    break;

                default:
                    break;
            }
        }
    }

    //Auto Complte item select listner
    public class ItemSelectListner implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            place = (String) parent.getItemAtPosition(position);
            txt_header.setVisibility(view.VISIBLE);
            txt_headereprefix.setVisibility(view.VISIBLE);
            btn_search.setVisibility(view.VISIBLE);
            btn_close_search.setVisibility(view.INVISIBLE);
            SearchTextView.setVisibility(view.INVISIBLE);
            SearchTextView.setText("");
            //To hide keyboard after selection
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(SearchTextView.getWindowToken(), 0);
            downloadnews(place);
        }
    }

    //get places from google place api for auto complete
    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place" + "/autocomplete" + "/json");
            sb.append("?key=" + "AIzaSyB094CoZN4ysZT3UPk0Yt1bcSZXxAhB77A");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());

            System.out.println("URL: " + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("NewsApp", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("NewsApp", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e("NewsApp", "Cannot process JSON results", e);
        }
        return resultList;
    }

    //auto complete list adapter
    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    //Set tab panel in main activity
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CategoryFragment(), "Categories");
        adapter.addFragment(new NewsFragment(), "Latest News");
        viewPager.setAdapter(adapter);
    }

    //Adapter for tab
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //Load fetched news to list in Fragments
    public void LoadList() {
        //copy ArrayList of objects to array objects
        newsGroups = EntireNewsList.toArray(new NewsGroup[EntireNewsList.size()]);
        NewsCategory = EntireCategoryList.toArray(new String[EntireCategoryList.size()]);

        //Load all News to listview
        NewsGroupAdapter newsGroupAdapter = new NewsGroupAdapter(this, R.layout.custum_news_list, newsGroups);
        NewsFragment.newsList.setAdapter(newsGroupAdapter);

        //Get Distinct Category
        Category = new HashSet<String>(Arrays.asList(NewsCategory)).toArray(new String[0]);

        //Load all categories to gridview
        CategoryGroupAdapter categoryadapter = new CategoryGroupAdapter(this, R.layout.custum_category_list, Category);
        CategoryFragment.categorygrid.setAdapter(categoryadapter);

        //hide dialog loader
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    //Call AsyncTask method to get news from API
    public void downloadnews(String location) {
        //Clear array list
        EntireNewsList.clear();
        EntireCategoryList.clear();
        //clear listview and grid view
        CategoryFragment.categorygrid.setAdapter(null);
        NewsFragment.newsList.setAdapter(null);
        //Split location names in search key word
        locationArrayIndex=0;
        locationArray = location.split(",");
        locationArrayLength = locationArray.length;

        //Display search keyword to the user
        txt_headereprefix.setText("for " +location);

        //download news for ht first location
        if (Helper.isNetworkAvailable(this)) {
            GetAPIData apithread = new GetAPIData();
            apithread.execute(locationArray[locationArrayIndex]);
        } else {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    //Async Method to get news from gaurdian api
    public class GetAPIData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String place = (String) params[0];
            String url = null;
            url = "http://content.guardianapis.com/search?order-by=newest&page-size=50&q=" + Uri.encode(place)+ "&api-key=4b64e8d2-debe-42eb-b112-ba4b4b2723e8&show-fields=bodyText,thumbnail";
            String fetchedString=Helper.HTTPRequest(url);
            return fetchedString;
        }
        protected void onPreExecute() {
            dialog.setMessage("Fetching News, please wait...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String fetchedString) {
            if (fetchedString != null && fetchedString != "") {
                JSONObject NewsObject, JsonObject = null;
                JSONArray NewsArray = null;
                try {
                    JsonObject = new JSONObject(fetchedString);
                    NewsObject = JsonObject.getJSONObject("response");
                    NewsArray = NewsObject.getJSONArray("results");
                    int n = NewsArray.length();
                    for (int i = 0; i < n; i++) {
                        JSONObject currentEvent = NewsArray.getJSONObject(i);
                        JSONObject det = currentEvent.getJSONObject("fields");
                        //filter blog contents to skip irrelavent news
                        if(!currentEvent.getString("type").equals("liveblog")){
                            if(!currentEvent.getString("sectionId").equals("australia-news")) {
                            EntireCategoryList.add(currentEvent.getString("sectionName"));
                            String thumbnailurl = null;
                            try {
                                thumbnailurl = det.getString("thumbnail");
                            } catch (JSONException e) {
                                thumbnailurl = null;
                            }
                            EntireNewsList.add(new NewsGroup(currentEvent.getString("type"), currentEvent.getString("sectionName"), currentEvent.getString("webPublicationDate"), currentEvent.getString("webTitle"), currentEvent.getString("webUrl"), det.getString("bodyText"), thumbnailurl, locationArray[locationArrayIndex]));
                         }
                        }
                    }

                    //Fetch news for next items in the location array if it is available
                    locationArrayIndex += 1;
                    if(locationArrayIndex < locationArrayLength){
                        GetAPIData apithread = new GetAPIData();
                        apithread.execute(locationArray[locationArrayIndex]);
                    }
                    else{
                        //call method which load all fetched news to the listview and grid view
                        LoadList();
                    }
                } catch (JSONException e) {
                    if(locationArrayIndex < locationArrayLength){
                        GetAPIData apithread = new GetAPIData();
                        apithread.execute(locationArray[locationArrayIndex]);
                        locationArrayIndex += 1;
                    }
                    else {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "No News Found", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                if (locationArrayIndex < locationArrayLength) {
                    GetAPIData apithread = new GetAPIData();
                    apithread.execute(locationArray[locationArrayIndex]);
                    locationArrayIndex += 1;
                } else {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "No News Found", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //News Group class for Complex data
    public class NewsGroup {

        String Type;
        String Category;
        String Date;
        String Title;
        String Url;
        String Details;
        String Imgurl;
        String Location;

        public NewsGroup(String Type, String Category, String Date, String Title, String Url, String Details,String Imgurl,String Location) {
            this.Type = Type;
            this.Category = Category;
            this.Date = Date;
            this.Title = Title;
            this.Url = Url;
            this.Details = Details;
            this.Imgurl = Imgurl;
            this.Location = Location;
        }

        public String getType() {
            return Type;
        }

        public String getCategory() {
            return Category;
        }

        public String getTitle() {
            return Title;
        }

        public String getDate() {
            return Date;
        }

        public String getUrl() {
            return Url;
        }

        public String getDetails() {
            return Details;
        }

        public String getLocation() {
            return Location;
        }

        public String getImageURL() {
            return Imgurl;
        }

    }

    //Bind Fetched news to Custom News Group Adapter
    public class NewsGroupAdapter extends ArrayAdapter<NewsGroup> {

        public NewsGroupAdapter(Context context, int resourse, NewsGroup[] objects) {
            super(context, resourse, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            View customView = inflater.inflate(R.layout.custum_news_list, container, false);

            TextView title = (TextView) customView.findViewById(R.id.title);
            TextView details = (TextView) customView.findViewById(R.id.details);
            TextView category = (TextView) customView.findViewById(R.id.category);
            TextView date = (TextView) customView.findViewById(R.id.date);
            ImageView image = (ImageView) customView.findViewById(R.id.image);

            NewsGroup current = getItem(position);
            title.setText(current.getTitle());
            details.setText(current.getDetails());
            category.setText(current.getLocation());
            date.setText(FormatedDate(current.getDate()));
            if(current.getImageURL() == null)
                image.setImageResource(R.drawable.thumbnail);
            else{
                Picasso.with(getContext()).load(current.getImageURL()).resize(300, 200).into(image);
                image.setTag(current.getImageURL());
                image.setOnClickListener(new ThumbnailClickListner());
            }
            return customView;
        }
    }

    //Event listner for Thumbnail in news list
    public class ThumbnailClickListner implements View.OnClickListener{
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

    //Custom adapter for category
    public class CategoryGroupAdapter extends ArrayAdapter<String> {

        public CategoryGroupAdapter(Context context, int resourse, String[] objects) {
            super(context, resourse, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            View cuistomView = inflater.inflate(R.layout.custum_category_list, container, false);

//            ImageView categoryicon = (ImageView) cuistomView.findViewById(R.id.categoryicon);
            TextView category = (TextView) cuistomView.findViewById(R.id.txtcategory);

            String current = getItem(position);

            category.setText(current);

            return cuistomView;
        }
    }

    //Convert String date to Specific date format
    public static String FormatedDate(String dtStart) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateFormat df = new SimpleDateFormat("E, MMM dd yyyy");
        Date date = null;
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return df.format(date);
    }

    //Get Location
    public void getLocation() {
        try {

            locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

            boolean locationpermissionok = checkLocationPermission();

            if(locationpermissionok){
                boolean isgpsenables = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if(isgpsenables) {
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,1,0,new customlocationlistner());
                    currentlocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    if(currentlocation!=null){
                        GetLocationData apithread=new GetLocationData();
                        apithread.execute(currentlocation.getLatitude(),currentlocation.getLongitude());
                    }
                    else {
                        downloadnews("Dunedin");
                    }
                }
                else {
                    showSettingsAlert();
                }
            }
            else {
                requestLocationPermission();
                downloadnews("Dunedin");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode, String[] permissions,int[] grantResults){
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(checkLocationPermission());
            {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,1,100,new customlocationlistner());
            }
        }
    }

    //checking for location accesss
    public boolean checkLocationPermission(){
        int finelocationOk = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationOk= ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if((finelocationOk!= PackageManager.PERMISSION_GRANTED)||(coarseLocationOk!=PackageManager.PERMISSION_GRANTED))
            return false;
        else
            return true;
    }

    //request user for permissions
    public void requestLocationPermission(){

        String[] permissionIwant = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this,permissionIwant,42);
    }

    //custom location listener
    public class customlocationlistner implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
//            currentlocation.setLatitude(location.getLatitude());
//            currentlocation.setLongitude(location.getLongitude());
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

    //show user  setting dialog
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    //get location Name
    class GetLocationData extends AsyncTask<Double, Void, String> {
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
                downloadnews(city);
            } catch (JSONException e1) {
                downloadnews("Dunedin");
            }
        }

    }

    //Map avtivity result
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intend){
        if((requestCode==4)&&resultCode== Activity.RESULT_OK){
            Bundle bundle=intend.getExtras();
            String maploc = bundle.getString("maploc");
            downloadnews(maploc);
        }
    }

}
