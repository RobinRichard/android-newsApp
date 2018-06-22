package com.example.arulr1.photomosaic;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    File mphoto;
    ImageView  userphoto;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userphoto = (ImageView)findViewById(R.id.userphoto);

        Button startcamera =(Button)findViewById(R.id.startcamera);

        startcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.i("tag", "IOException");
                    }
                    if (photoFile != null) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestcode,int resultcode,Intent data){
        if(requestcode==1){
            if(resultcode==RESULT_OK) {
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                    userphoto.setImageBitmap(createCollage(mImageBitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                Toast.makeText(this,"No photo Available",Toast.LENGTH_LONG).show();
            }
            
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private Bitmap createCollage(Bitmap bitmap) {
        Bitmap collage = null;

        int width, height = 0;

        width = bitmap.getWidth()*2;
        height = bitmap.getHeight()*2;

        collage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(collage);

        comboImage.drawBitmap(bitmap, 0f, 0f, null);
        comboImage.drawBitmap(bitmap, bitmap.getWidth(), 0f, null);
        comboImage.drawBitmap(bitmap, 0f,  bitmap.getHeight(), null);
        comboImage.drawBitmap(bitmap, bitmap.getWidth(),  bitmap.getHeight(), null);
        return collage;
    }
}
