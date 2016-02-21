package com.example.zhehuan.saleapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView profilePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);


        // temp configuration to be removed later
        ImageView profilePhoto = (ImageView) findViewById(R.id.userProfilePhoto);
        Glide.with(this).load("http://philosophy.ucr.edu/wp-content/uploads/2014/10/no-profile-img.gif").into(profilePhoto);




        //notification test with dummy notification
        Intent resultIntent = new Intent(this, DetailedViewActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), resultIntent, 0);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.signs)
                        .setContentTitle("Sales Ring")
                        .setContentText("New Sales in categories you are interested in")
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);

    }


    public void editProfile(View view)
    {
        startActivity(new Intent(UserProfileActivity.this, EditProfileActivity.class));
    }


    public void changePhoto(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extra = data.getExtras();
            Bitmap photo = (Bitmap) extra.get("data");
            profilePhoto.setImageBitmap(photo);
        }
    }

<<<<<<< HEAD
    public void homeClicked(View view)
    {
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
    }

    public void newPost(View view)
    {
        startActivity(new Intent(UserProfileActivity.this,NewPostActivity.class));
    }

    public void goToProfile(View view)
    {
        startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
    }

    public void listOrAddFriends(View view)
    {
        startActivity(new Intent(UserProfileActivity.this,FriendActivity.class));
=======
    public void jumpToHome(View view) {
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
    }

    public void jumpToFriend(View view) {
        startActivity(new Intent(UserProfileActivity.this, FriendActivity.class));
    }

    public void jumpToPost(View view) {
        startActivity(new Intent(UserProfileActivity.this, NewPostActivity.class));
>>>>>>> 84427ce6b76b0ee642a87e49947978b7392464f9
    }
}
