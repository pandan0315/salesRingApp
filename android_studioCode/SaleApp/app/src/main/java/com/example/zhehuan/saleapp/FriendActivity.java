package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by zhehuan on 20/02/2016.
 */
public class FriendActivity extends AppCompatActivity {
    ArrayList<FriendDetail> friends;
    ListView friendLV;
    ImageButton homeButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_main);

        ImageView homeIV = (ImageView) findViewById(R.id.homeButt);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileButt);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsButt);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostButt);
        ImageView searchIcon = (ImageView) findViewById(R.id.searchIcon);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);
        Glide.with(this).load(R.drawable.searchicon).into(searchIcon);

        friends = new ArrayList<FriendDetail>();

        friends.add(new FriendDetail("Khalid", "H&M, CK"));
        friends.add(new FriendDetail("Yuqing", "CHANEL"));

        FriendAdapter adapter = new FriendAdapter(FriendActivity.this,friends);
        friendLV = (ListView) findViewById(R.id.friendsListView);
        friendLV.setAdapter(adapter);
    }
    public void friend_jumpToHome(View view) {
        startActivity(new Intent(FriendActivity.this, MainActivity.class));
    }

    public void friend_jumpToProfile(View view) {
        startActivity(new Intent(FriendActivity.this, UserProfileActivity.class));
    }

    public void friend_jumpToPost(View view) {
        startActivity(new Intent(FriendActivity.this, NewPostActivity.class));
    }
}
