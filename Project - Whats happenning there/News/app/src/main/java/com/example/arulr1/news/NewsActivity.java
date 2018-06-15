package com.example.arulr1.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    //variables to store categoryname and news snippets
    String categoryname;
    ListView newsList;
    private Toolbar toolbar;
    int categorycount=0;
    MainActivity.NewsGroup[] categorynews,allnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        categoryname = intent.getStringExtra("category");


        TextView txt_headereprefix = (TextView) findViewById(R.id.txt_headereprefix);
        txt_headereprefix.setText(" under "+categoryname);
        newsList = (ListView) findViewById(R.id.newsList);

        allnews = MainActivity.newsGroups;

        //get cout of news for selected category
        for(int i=0; i<allnews.length-1; i++){
            if(allnews[i].getCategory().equals(categoryname))
                categorycount += 1;
        }

        categorynews=new MainActivity.NewsGroup[categorycount];

        int index=0;

        //filter the news list by selected category
        for(int j=0; j<allnews.length-1; j++){
            if(allnews[j].getCategory().equals(categoryname))
                categorynews[index++] = allnews[j];
        }

        NewsGroupAdapter artistGroupAdapter = new NewsGroupAdapter(this, R.layout.custum_news_list, categorynews);
        newsList.setAdapter(artistGroupAdapter);

        //on click event for news snippets
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = categorynews[position].getUrl();
                Intent i = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                i.putExtra("url", url);
                startActivity(i);
            }});
    }

    //custum adapter fot list view
    public class NewsGroupAdapter extends ArrayAdapter<MainActivity.NewsGroup> {

        public NewsGroupAdapter(Context context, int resourse, MainActivity.NewsGroup[] objects){
            super(context,resourse,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container){



            LayoutInflater inflater = LayoutInflater.from(getContext());

            View cuistomView= inflater.inflate(R.layout.custum_news_list,container,false);

            TextView title = (TextView)cuistomView.findViewById(R.id.title);
            TextView details = (TextView)cuistomView.findViewById(R.id.details);
            TextView category = (TextView)cuistomView.findViewById(R.id.category);
            TextView date = (TextView)cuistomView.findViewById(R.id.date);
            ImageView image = (ImageView) cuistomView.findViewById(R.id.image);

            MainActivity.NewsGroup current = getItem(position);

            title.setText(current.getTitle());
            details.setText(current.getDetails());
            category.setText(current.getLocation());
            date.setText(MainActivity.FormatedDate(current.getDate()));
            if(current.getImageURL() == null)
                image.setImageResource(R.drawable.thumbnail);
            else{
                Picasso.with(getContext()).load(current.getImageURL()).resize(300, 200).into(image);
                image.setTag(current.getImageURL());
                image.setOnClickListener(new ThumbnailClickListner());
            }
            return cuistomView;
        }
    }

    //listner for back button in tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //listner for tumbnail image click
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
}
