package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
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

}
