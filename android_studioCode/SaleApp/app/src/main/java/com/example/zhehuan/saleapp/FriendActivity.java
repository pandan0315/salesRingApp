package com.example.zhehuan.saleapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by zhehuan on 20/02/2016.
 */
public class FriendActivity extends AppCompatActivity {
    //ArrayList<FriendDetail> friends;
    ArrayList<FriendDetail> friends;
    ListView friendLV;
    ImageButton homeButton;
    String username;
    String fullname;
    FriendAdapter adapter;
    EditText friendET;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_main);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");


        ImageView homeIV = (ImageView) findViewById(R.id.homeButt);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileButt);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsButt);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostButt);
        ImageView searchIcon = (ImageView) findViewById(R.id.searchIcon);
       // ImageButton addIcon = (ImageButton)findViewById(R.id.addBtn);
       ImageButton addIcon=(ImageButton)findViewById(R.id.addBtn);

        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);
        Glide.with(this).load(R.drawable.searchicon).into(searchIcon);
        Glide.with(this).load(R.drawable.social1).into(addIcon);

       // friends = new ArrayList<FriendDetail>();

      //  friends.add(new FriendDetail("Khalid", "H&M, CK"));
       // friends.add(new FriendDetail("Yuqing", "CHANEL"));
        friends=new ArrayList<>();
        getFriends(username);

       // FriendAdapter adapter = new FriendAdapter(FriendActivity.this,friends);
        //friendLV = (ListView) findViewById(R.id.friendsListView);
        //friendLV.setAdapter(adapter);
    }


    public void addFriend(View v){

        AlertDialog.Builder addDialog = new AlertDialog.Builder(FriendActivity.this);

        addDialog.setTitle("Add a new friend");
        LayoutInflater inflater =this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog,null);
        friendET=(EditText)layout.findViewById(R.id.etname);
        addDialog.setView(layout);

        addDialog.setNegativeButton("Cancel", null);
        addDialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                String newfriend =friendET.getText().toString();
                addFriendtoBacken(newfriend);



            }
        });
        addDialog.show();



    }

    private void addFriendtoBacken( final String newfriend) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("followingUser",username);
            jsonObject.put("followedUser",newfriend);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            StringEntity entity = new StringEntity(jsonObject.toString());
            AsyncHttpClient client= new AsyncHttpClient();
            client.post(getApplicationContext(), "http://" + getString(R.string.IP_address) + ":8080/shares/webapi/" + username + "/profile/friends", entity, "application/json", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                    Toast.makeText(getApplicationContext(), "you successfully add a new friend!", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra("username",username);
                    intent.putExtra("fullname",fullname);

                    intent.setClass(FriendActivity.this, FriendActivity.class);
                    startActivity(intent);

                   // friends.add(new FriendDetail(username,newfriend));
                   // adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "friend already existed or not exiseted in the system!", Toast.LENGTH_LONG).show();

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject responseString) {
                    Toast.makeText(getApplicationContext(), "friend already existed or not exiseted in the system!", Toast.LENGTH_LONG).show();

                }


            });




        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }







    }

    private void getFriends(final String username) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + getString(R.string.IP_address) + ":8080/shares/webapi/" + username + "/profile/friends", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    if (response.has("followedList")) {
                        JSONArray followedFriends = response.getJSONArray("followedList");
                        for (int i = 0; i < followedFriends.length(); i++) {
                           JSONObject object= followedFriends.getJSONObject(i);
                            String friend_username=object.getString("userName");
                            String friend_fullname=object.getString("fullName");
                            friends.add(new FriendDetail(friend_username,friend_fullname));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter = new FriendAdapter(FriendActivity.this, friends);
                friendLV = (ListView) findViewById(R.id.friendsListView);
                friendLV.setAdapter(adapter);
                friendLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {
                       // System.out.println(friends.get(position));
                        AlertDialog.Builder adb = new AlertDialog.Builder(FriendActivity.this);
                        adb.setTitle("Delete?");
                        adb.setMessage("Are you sure you want to delete this friend " + friends.get(position).getFullName());
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               System.out.println(friends.get(position));
                                removeFriend(friends.get(position).getUserName());

                                friends.remove(position);
                                System.out.println(friends);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        adb.show();
                    }


                });


            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(), "Some things goes wrong, internet error, please Login again!", Toast.LENGTH_LONG).show();

                startActivity(new Intent(FriendActivity.this, LoginActivity.class));

            }
            @Override
            public void onFailure(int statusCode,Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(getApplicationContext(), "Some things goes wrong, internet error, please Login again!", Toast.LENGTH_LONG).show();

                startActivity(new Intent(FriendActivity.this, LoginActivity.class));
            }


        });


    }

    private void removeFriend(String friend) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("followingUser",username);
            jsonObject.put("followedUser",friend);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            StringEntity entity = new StringEntity(jsonObject.toString());
            AsyncHttpClient client= new AsyncHttpClient();
            client.delete(getApplicationContext(),"http://" + getString(R.string.IP_address) + ":8080/shares/webapi/" + username + "/friends",entity,"application/json",new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                    Toast.makeText(getApplicationContext(), "you successfully deleted it!", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString,Throwable throwable) {

                }


                });




        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }





    }

    public void friend_jumpToHome(View view) {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(FriendActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void friend_jumpToProfile(View view) {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(FriendActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void friend_jumpToPost(View view) {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);

        intent.setClass(FriendActivity.this, NewPostActivity.class);
        startActivity(intent);
    }
}
