package com.example.zhehuan.saleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.util.UUID;

public class DetailedViewActivity extends AppCompatActivity {
    String username;
    String fullname;
    SalePost salePost;
    TextView nameTV;
    TextView dateTV;
    TextView storeTV;
    TextView priceTV;
    TextView discountTV;
    TextView categoryTV;
    TextView descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");
        salePost=(SalePost)intent.getSerializableExtra("salePost");
        System.out.println(salePost.getImageName());

        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);
       ImageView postPhtoIV = (ImageView)findViewById(R.id.postPhotoUsed);

       // Glide.with(this).load(R.drawable.b).into(postPhtoIV);
        Glide.with(this).load("http://" + getString(R.string.IP_address) + ":8080/shares/image/" + salePost.getImageName()).into(postPhtoIV);
        ImageView posterProfileImage = (ImageView)findViewById(R.id.posterProfileImage);
        Glide.with(this).load("http://" + getString(R.string.IP_address) + ":8080/shares/image/"+username+".jpeg")
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .error(R.drawable.poster)
                .into(posterProfileImage);
        nameTV=(TextView)findViewById(R.id.posterName);
        nameTV.setText(salePost.getPosterfullname());
        dateTV=(TextView)findViewById(R.id.detailedDateTV);
        dateTV.setText(" "+salePost.getPostDate());
        storeTV=(TextView)findViewById(R.id.detailedStoreTV);
        storeTV.setText("Store: "+salePost.getStore());
        priceTV=(TextView)findViewById(R.id.detailedPriceTV);
        if(salePost.getIs_pricebefore().equals("true")){
            priceTV.setText("Price"+"(before discount): "+String.valueOf(salePost.getPrice()));
        }
        else{
            priceTV.setText("Price"+"(after discount): "+String.valueOf(salePost.getPrice()));
        }

        discountTV=(TextView)findViewById(R.id.detailedSaleTV);
        discountTV.setText("Discount: "+salePost.getSaleValue()+" % OFF");
         categoryTV=(TextView)findViewById(R.id.deteiledCategoryTV);
        categoryTV.setText("Category: "+salePost.getCategory());
        descriptionTV=(TextView)findViewById(R.id.detailedDescription);
        descriptionTV.setText("Description: "+salePost.getDescription());

    }

    public void homeClicked(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
       intent.putExtra("fullname",fullname);
        intent.setClass(DetailedViewActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void newPost(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);
        intent.setClass(DetailedViewActivity.this, NewPostActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);
        intent.setClass(DetailedViewActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void listOrAddFriends(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);
        intent.setClass(DetailedViewActivity.this,FriendActivity.class);
        startActivity(intent);
    }

}



