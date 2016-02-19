package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SalePost> posts;
    ListView postsLV;
    ImageButton homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The code below is for the image loaders

        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);

        posts = new ArrayList<SalePost>();
        homeButton = (ImageButton) findViewById(R.id.homeIB);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DetailedViewActivity.class));
            }
        });

        posts.add(new SalePost("","Khalid","HM","","12%","12%","5-4-2016 12:32","5-4-2016 12:32",true,"",""));
        posts.add(new SalePost("","Zhehuan","D&G","","33%","33%","1-2-2013 12:41","1-2-2013 12:41",true,"",""));
        posts.add(new SalePost("","Sunny","HM","","22%","22%","21-1-2016 12:41","21-1-2016 12:41",true,"",""));
        posts.add(new SalePost("","Dan","Stadium","","44%","44%","21-2-2016 15:41","21-2-2016 15:41",true,"",""));


        PostsAdapter adapter = new PostsAdapter(MainActivity.this,posts);
        postsLV = (ListView) findViewById(R.id.postsListView);
        postsLV.setAdapter(adapter);
    }

    public void newPost(View view)
    {
        startActivity(new Intent(MainActivity.this,NewPostActivity.class));
    }

    public void goToProfile(View view)
    {
        startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
    }

}
