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
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class UserProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView profilePhoto;
    String username;
    String fullname;
    Bitmap photo;
    String encodedImage;
    TextView fullnameTV;
    TextView interestTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        fullnameTV=(TextView)findViewById(R.id.userName);
        interestTV=(TextView)findViewById(R.id.interestsTV);
        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");


        // temp configuration to be removed later
        ImageView profilePhoto = (ImageView) findViewById(R.id.userProfilePhoto);
      //  Glide.with(this).load("http://philosophy.ucr.edu/wp-content/uploads/2014/10/no-profile-img.gif").into(profilePhoto);

       Glide.with(this).load("http://" + getString(R.string.IP_address) + ":8080/shares/image/"+username+".jpeg")
                        .signature(new StringSignature(UUID.randomUUID().toString()))
                        .error(R.drawable.poster).
                         into(profilePhoto);

        getUserProfile(username);


        //notification test with dummy notification
    /*    Intent resultIntent = new Intent(this, DetailedViewActivity.class);
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
*/
    }

    private void getUserProfile(String username) {

        AsyncHttpClient client=new AsyncHttpClient();
        client.get("http://" + getString(R.string.IP_address) + ":8080/shares/webapi/" + username+"/profile", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                try {
                    String name=response.getString("fullname");
                    StringBuilder interestString = new StringBuilder("");

                   ArrayList<String> interestList = new ArrayList<>();
                    if (response.has("categoryLists")) {
                        JSONArray interestJSONArray = response.getJSONArray("categoryLists");
                        for (int i = 0; i < interestJSONArray.length(); i++) {
                            interestList.add(interestJSONArray.getString(i));
                        }
                    }
                    if (interestList.size() != 0) {
                        for (String s : interestList) {
                            interestString.append(s);
                            interestString.append(',');
                        }
                    }
                    interestTV.setText(interestString);
                    fullnameTV.setText(name);
                    fullname=name;

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
                Toast.makeText(getApplicationContext(), "Some things goes wrong, internet error,profile can not be displaied!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("username", getUsername());
                intent.putExtra("fullname", fullname);
                intent.setClass(UserProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }

        });

    }


    public String getUsername(){
        return username;
    }
    public void editProfile(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(UserProfileActivity.this,EditProfileActivity.class);
        startActivity(intent);
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
            photo = (Bitmap) extra.get("data");
            profilePhoto.setImageBitmap(photo);
        }
    }


    public void homeClicked(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(UserProfileActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void newPost(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(UserProfileActivity.this,NewPostActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(UserProfileActivity.this,UserProfileActivity.class);
        startActivity(intent);
    }

    public void listOrAddFriends(View view) {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(UserProfileActivity.this,FriendActivity.class);
        startActivity(intent);
    }


    public void logout(View view) {
        startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
    }
}
