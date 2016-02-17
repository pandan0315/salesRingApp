package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class NewPostActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView postPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        postPhoto = (ImageView)findViewById(R.id.postPhoto);
        Glide.with(this).load(R.drawable.camera2).into(postPhoto);
    }


    public void sendPost(View view)
    {
        Intent intent = new Intent(NewPostActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void backToPreviousView(View view)
    {
        Intent intent = new Intent(NewPostActivity.this,MainActivity.class);
        startActivity(intent);
    }



    public void launchCamera(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extra = data.getExtras();
            Bitmap photo = (Bitmap) extra.get("data");
            postPhoto.setImageBitmap(photo);
        }
    }

}
