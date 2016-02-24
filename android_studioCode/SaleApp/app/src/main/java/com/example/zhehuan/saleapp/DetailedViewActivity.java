package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailedViewActivity extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");

        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);
        ImageView postPhtoIV = (ImageView)findViewById(R.id.postPhotoUsed);
        Glide.with(this).load(R.drawable.b).into(postPhtoIV);
        ImageView posterProfileImage = (ImageView)findViewById(R.id.posterProfileImage);
        Glide.with(this).load(R.drawable.poster).into(posterProfileImage);
    }

    public void homeClicked(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.setClass(DetailedViewActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void newPost(View view)
    {
        startActivity(new Intent(DetailedViewActivity.this,NewPostActivity.class));
    }

    public void goToProfile(View view)
    {
        startActivity(new Intent(DetailedViewActivity.this,UserProfileActivity.class));
    }

    public void listOrAddFriends(View view)
    {
        startActivity(new Intent(DetailedViewActivity.this,FriendActivity.class));
    }

}



