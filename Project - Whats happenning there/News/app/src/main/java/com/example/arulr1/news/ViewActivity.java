package com.example.arulr1.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//Tried with swipe layout to view news but not implemented in this project will use when extending this app.

public class ViewActivity extends AppCompatActivity {

    String categoryname;
    private Toolbar toolbar;
    int categorycount=0;
    MainActivity.NewsGroup[] categorynews,allnews;
    LayoutInflater inflater;    //Used to create individual pages
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        allnews = MainActivity.newsGroups;

        Intent intent = getIntent();
        categoryname = intent.getStringExtra("category");

        allnews = MainActivity.newsGroups;

        for(int i=0; i<allnews.length-1; i++){
            if(allnews[i].getCategory().equals(categoryname))
                categorycount += 1;
        }

        categorynews=new MainActivity.NewsGroup[categorycount];

        int index=0;

        for(int j=0; j<allnews.length-1; j++){
            if(allnews[j].getCategory().equals(categoryname))
                categorynews[index++] = allnews[j];
        }
        //get an inflater to be used to create single pages
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Reference ViewPager defined in activity
        vp=(ViewPager)findViewById(R.id.viewNews);
        //set the adapter that will create the individual pages
        vp.setAdapter(new MyPagesAdapter());
    }

    class MyPagesAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            //Return total pages, here one for each data item
            return categorynews.length;
        }
        //Create the given page (indicated by position)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View page = inflater.inflate(R.layout.news_view, null);
            ImageView Newsimg = (ImageView) page.findViewById(R.id.Newsimg);
            TextView Newstext = page.findViewById(R.id.Newstext);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            getWindowManager().getDefaultDisplay().getSize(size);
            int targetWidth = size.x;
//            int targetWidth = displayMetrics.widthPixels;
            int targetHeight = Newsimg.getHeight();

            Picasso.with(getApplicationContext()).load(categorynews[position].getImageURL()).resize(targetWidth, targetHeight).into(Newsimg);
            Newstext.setText(categorynews[position].getDetails());
            //Add the page to the front of the queue
            ((ViewPager) container).addView(page, 0);
            return page;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //See if object from instantiateItem is related to the given view
            //required by API
            return arg0==(View)arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
            object=null;
        }
    }

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
}
