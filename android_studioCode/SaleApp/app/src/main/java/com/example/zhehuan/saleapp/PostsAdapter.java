package com.example.zhehuan.saleapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhehuan.saleapp.R.layout.friendsposts;

class PostsAdapter extends ArrayAdapter<SalePost> {

    ArrayList<SalePost> posts;
    Activity mainActivity;
    List<String> imagefileList;
    public PostsAdapter(Activity context, ArrayList<SalePost> posts) {
        super(context, R.layout.friendsposts, posts);
        this.posts = posts;
        this.mainActivity = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.friendsposts, parent, false);
        TextView userNameTV = (TextView) rowView.findViewById(R.id.userName);
        TextView dateTV = (TextView) rowView.findViewById(R.id.postDate);
        TextView saleTV = (TextView) rowView.findViewById(R.id.saleInfo);
        ImageView userProfilePhoto = (ImageView) rowView.findViewById(R.id.userProfilePhoto);
        ImageView postIV = (ImageView) rowView.findViewById(R.id.postPhoto);
        //Glide.with(mainActivity).load(R.drawable.b).into(postIV);
        Glide.with(mainActivity).load(R.drawable.poster).into(userProfilePhoto);
        System.out.println(posts.get(position).getImageName());

        Glide.with(mainActivity).load("http://192.168.11.113:8080/shares/image/"+posts.get(position).getImageName()).into(postIV);

        userNameTV.setText(posts.get(position).getPoster());
        dateTV.setText(posts.get(position).getPostDate());
        saleTV.setText(posts.get(position).getSaleValue()+" sale at  " +posts.get(position).getStore());

        return rowView;
    }



}
