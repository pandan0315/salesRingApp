package com.example.zhehuan.saleapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by zhehuan on 20/02/2016.
 */
class FriendAdapter extends ArrayAdapter<FriendDetail> {

    ArrayList<FriendDetail> friends;
    Activity mainActivity;
    public FriendAdapter(Activity context, ArrayList<FriendDetail> friends) {
        super(context, R.layout.friend_detail ,friends);
        this.friends = friends;
        this.mainActivity = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.friend_detail, parent, false);
        TextView userName = (TextView) rowView.findViewById(R.id.usersName);
        TextView category = (TextView) rowView.findViewById(R.id.categories);
        ImageView userPhoto = (ImageView) rowView.findViewById(R.id.userPhoto);
        Glide.with(mainActivity).load(R.drawable.poster).into(userPhoto);

        userName.setText(friends.get(position).getUserName());
        category.setText(friends.get(position).getUserCate());

        return rowView;
    }
}
