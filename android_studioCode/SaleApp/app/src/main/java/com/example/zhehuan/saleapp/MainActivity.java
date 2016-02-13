package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SalePost> posts;
    ListView postsLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posts = new ArrayList<SalePost>();
        posts.add(new SalePost("","Khalid","HM","","12%","12%","5-4-2016 12:32","5-4-2016 12:32",true,"",""));
        posts.add(new SalePost("","Zhehuan","D&G","","33%","33%","1-2-2013 12:41","1-2-2013 12:41",true,"",""));
        posts.add(new SalePost("","Sunny","HM","","22%","22%","21-1-2016 12:41","21-1-2016 12:41",true,"",""));
        posts.add(new SalePost("","Dan","Stadium","","44%","44%","21-2-2016 15:41","21-2-2016 15:41",true,"",""));
        posts.add(new SalePost("","","","","","","","",true,"",""));
        posts.add(new SalePost("","","","","","","","",true,"",""));
        posts.add(new SalePost("","","","","","","","",true,"",""));
        posts.add(new SalePost("","","","","","","","",true,"",""));
        posts.add(new SalePost("","","","","","","","",true,"",""));

        PostsAdapter adapter = new PostsAdapter(MainActivity.this,posts);
        postsLV = (ListView) findViewById(R.id.postsListView);
        postsLV.setAdapter(adapter);

        Intent intent = new Intent(MainActivity.this,DetailedViewActivity.class);
        startActivity(intent);

    }
}
