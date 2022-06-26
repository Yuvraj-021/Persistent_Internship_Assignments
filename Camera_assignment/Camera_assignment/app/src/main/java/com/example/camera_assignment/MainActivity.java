package com.example.camera_assignment;

import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button imagebtn;
    ImageView image;
    String pathtofile;
    TextView Imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>23){
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        image=findViewById(R.id.img1);
        Imagepath=findViewById(R.id.path);
        imagebtn=findViewById(R.id.imgbtn);

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage(); // calling function
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                Bitmap bitmap= BitmapFactory.decodeFile(pathtofile);
                image.setImageBitmap(bitmap);  // loading the image in Imageview
                Imagepath.setText(pathtofile); // printing its path

            }
        }
    }

    private void captureImage() {

        Intent takepic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // intent for capturing image
        if(takepic.resolveActivity(getPackageManager())!=null) // checking if there is any app to handle our intent
        {
            File photofile=null;
            photofile=createPhotoFile();
            if(photofile!=null) // checking if the photofile is created or not.
            {
                pathtofile=photofile.getAbsolutePath();
                Uri photoURI= FileProvider.getUriForFile(MainActivity.this,"com.example.camera_assignment.fileprovider",photofile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takepic,1);
            }

        }
    }

    private File createPhotoFile() {
        String name=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storagedir=getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); // to make it accessible publicly by all other apps
        File image=null;
        try {
             image=File.createTempFile(name,".jpg",storagedir); // creating image file with the date and in jpg format
        } catch (IOException e) {
            Log.d("mylog",e.toString());
        }
        return image;
    }
}