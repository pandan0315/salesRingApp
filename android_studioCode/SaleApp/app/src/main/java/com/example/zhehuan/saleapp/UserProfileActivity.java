package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);


        // temp configuration to be removed later
        ImageView myProfile = (ImageView) findViewById(R.id.imageView7);
        Glide.with(this).load("https://media.licdn.com/mpr/mpr/shrinknp_400_400/p/3/005/0b2/019/14e0f5c.jpg").into(myProfile);

    }

    public void jumpToEdit(View view) {
        startActivity(new Intent(UserProfileActivity.this, EditProfileActivity.class));
    }
}
