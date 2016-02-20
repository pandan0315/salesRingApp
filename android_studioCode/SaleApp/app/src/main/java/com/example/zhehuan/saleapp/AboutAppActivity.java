package com.example.zhehuan.saleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ImageView ourLogo = (ImageView) findViewById(R.id.appLogoIV);
        Glide.with(this).load(R.drawable.logo).into(ourLogo);
    }
}
