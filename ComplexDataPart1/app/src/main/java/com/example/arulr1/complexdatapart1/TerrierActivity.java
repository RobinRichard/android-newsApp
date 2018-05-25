package com.example.arulr1.complexdatapart1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TerrierActivity extends FragmentActivity {
    DogGroup[] dogarray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrier);

        initialisedogarray();


        DogGroupAdapter dogadapter = new DogGroupAdapter(this, R.layout.custum_view_list, dogarray);

        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(dogadapter);
    }



    public class IconClickListner implements View.OnClickListener{
        @Override
        public void onClick(View v){
            int img_res = (int) v.getTag();
            ZoomFragment zoom = new ZoomFragment();
            Bundle bdl = new Bundle();
            bdl.putInt("Img_ID",img_res);
            zoom.setArguments(bdl);
            FragmentManager fm = getSupportFragmentManager();
            zoom.show(fm,"Zoom");
        }
    }
    public class DogGroup{

        Drawable DogImage;
        String DogName;
        public DogGroup(Drawable DogImage,String DogName){
            this.DogImage=DogImage;
            this.DogName=DogName;
        }
        @Override
        public String toString(){
            return DogName.toUpperCase();
        }
    }

    public class DogGroupAdapter extends ArrayAdapter<DogGroup>{

        public DogGroupAdapter(Context context,int resourse,DogGroup[] objects){
            super(context,resourse,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container){

            LayoutInflater inflater = LayoutInflater.from(TerrierActivity.this);

            View cuistomView= inflater.inflate(R.layout.custum_view_list,container,false);

            ImageView cv_img = (ImageView)cuistomView.findViewById(R.id.cv_img);
            TextView cv_text = (TextView)cuistomView.findViewById(R.id.cv_text);

            DogGroup current = getItem(position);

            cv_img.setImageDrawable(current.DogImage);
            cv_text.setText(current.toString());
            //part 2
            int imgres=getResources().getIdentifier(current.DogName,"drawable",getPackageName());
            cv_img.setTag(imgres);
            cv_img.setOnClickListener(new IconClickListner());
            
            return  cuistomView;
        }
    }

    public void initialisedogarray(){
        Resources res = getResources();
        dogarray= new DogGroup[8];
        dogarray[0]=new DogGroup(res.getDrawable(R.drawable.airedale,null),"airedale");
        dogarray[1]=new DogGroup(res.getDrawable(R.drawable.bedlington,null),"bedlington");
        dogarray[2]=new DogGroup(res.getDrawable(R.drawable.bull,null),"bull");
        dogarray[3]=new DogGroup(res.getDrawable(R.drawable.irish,null),"irish");
        dogarray[4]=new DogGroup(res.getDrawable(R.drawable.lakeland,null),"lakeland");
        dogarray[5]=new DogGroup(res.getDrawable(R.drawable.norfolk,null),"norfolk");
        dogarray[6]=new DogGroup(res.getDrawable(R.drawable.russell,null),"russell");
        dogarray[7]=new DogGroup(res.getDrawable(R.drawable.staffordshire,null),"staffordshire");
    }
}



