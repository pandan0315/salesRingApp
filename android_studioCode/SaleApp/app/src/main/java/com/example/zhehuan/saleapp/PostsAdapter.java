package com.example.zhehuan.saleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.zhehuan.saleapp.R.layout.friendsposts;

class PostsAdapter extends ArrayAdapter<SalePost> {

    ArrayList<SalePost> posts;
    public PostsAdapter(Context context, ArrayList<SalePost> posts) {
        super(context, R.layout.friendsposts, posts);
        this.posts = posts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.friendsposts, null, true);
        TextView userNameTV = (TextView) rowView.findViewById(R.id.userName);
        TextView dateTV = (TextView) rowView.findViewById(R.id.postDate);
        TextView storeTV = (TextView) rowView.findViewById(R.id.storeInfo);
        TextView saleTV = (TextView) rowView.findViewById(R.id.saleInfo);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.userProfilePhoto);

        userNameTV.setText(posts.get(position).getPoster());
        dateTV.setText(posts.get(position).getPostDate());
        storeTV.setText(posts.get(position).getStore());
        saleTV.setText(posts.get(position).getSaleValue());

        return rowView;
    }
}
