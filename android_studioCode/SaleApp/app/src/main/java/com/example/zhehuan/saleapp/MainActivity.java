package com.example.zhehuan.saleapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String username;
    String fullname;
    String category=null;
    ArrayList<SalePost> posts=new ArrayList<>();
    ListView postsLV;
    ImageButton homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");
        category=intent.getStringExtra("category");

        System.out.println(username);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // The code below is for the image loaders

        ImageView homeIV = (ImageView) findViewById(R.id.homeIB);
        ImageView userProfileIV = (ImageView)findViewById(R.id.userProfileIB);
        ImageView friendsIV = (ImageView) findViewById(R.id.friendsIB);
        ImageView newPostIV = (ImageView)findViewById(R.id.newPostIB);
        Glide.with(this).load(R.drawable.internet).into(homeIV);
        Glide.with(this).load(R.drawable.social).into(userProfileIV);
        Glide.with(this).load(R.drawable.editing).into(newPostIV);
        Glide.with(this).load(R.drawable.people).into(friendsIV);

       // posts = new ArrayList<HashMap<String,SalePost>>();


        //posts.add(new SalePost(1,"Khalid","dan","HM","fashion","","12%",12,"","true","5-4-2016 12:32"));
       // posts.add(new SalePost("","Zhehuan","D&G","","33%","33%","1-2-2013 12:41","1-2-2013 12:41",true,"",""));
        //posts.add(new SalePost("","Sunny","HM","","22%","22%","21-1-2016 12:41","21-1-2016 12:41",true,"",""));
        //posts.add(new SalePost("","Dan","Stadium","","44%","44%","21-2-2016 15:41","21-2-2016 15:41",true,"",""));

        getPosts(username,category);
      /*  PostsAdapter adapter = new PostsAdapter(MainActivity.this,posts);
        postsLV = (ListView) findViewById(R.id.postsListView);
        postsLV.setAdapter(adapter);
*/
      /*  postsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity(new Intent(MainActivity.this, DetailedViewActivity.class));
            }
        });

*/


    }
    public String getUsername()
    {
        return username;
    }

    private void getPosts(String username,String category) {



        AsyncHttpClient client=new AsyncHttpClient();
        String url;

        if(category==null){
            url="http://130.229.186.51:8080/shares/webapi/"+username+"/salesinfo";

        }
        else{
        url="http://130.229.186.51:8080/shares/webapi/"+username+"/salesinfo"+"?category="+category;}



        client.get(url,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // called when response HTTP status is "200 OK"

               System.out.println(response.length());

               for(int i= response.length() - 1 ;i >= 0;i--) {
                   try {
                       JSONObject json_data = response.getJSONObject(i);
                       long postID = json_data.getLong("id");
                       String poster = json_data.getString("postUser");
                       String posterfullname=json_data.getString("posterfullname");
                       String taggedUser = json_data.getString("taggedUser");

                       String store = json_data.getString("shop");
                       String category = json_data.getString("category");
                       String Description = json_data.getString("description");
                       String saleValue = json_data.getString("sale_discount");
                       double price = json_data.getDouble("price");
                       String imageName = json_data.getString("imageName");
                       String is_pricebefore = json_data.getString("is_pricebefore");
                       String postDate = json_data.getString("created");

                      posts.add(new SalePost(postID, poster,posterfullname, taggedUser, store, category, Description, saleValue, price, is_pricebefore, imageName,postDate));
                   } catch (JSONException e) {
                       Toast.makeText(getApplicationContext(), "Some things goes wrong, internet error, please Login again!", Toast.LENGTH_LONG).show();
                       startActivity(new Intent(MainActivity.this,LoginActivity.class));
                       e.printStackTrace();
                   }

               }
                PostsAdapter adapter = new PostsAdapter(MainActivity.this,posts);
                postsLV = (ListView) findViewById(R.id.postsListView);
                postsLV.setAdapter(adapter);
                postsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                      // System.out.println(posts.get(position).getImageName() );
                        Intent intent=new Intent();
                        intent.putExtra("username",getUsername());
                        intent.putExtra("fullname",fullname);
                        intent.putExtra("salePost", posts.get(position));
                        intent.setClass(MainActivity.this, DetailedViewActivity.class);
                        startActivity(intent);


                    }
                });



            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(), "Some things goes wrong, internet error, please Login again!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));

            }



            });






    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,  AboutAppActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            // Handle the camera action
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);

            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_beauty) {
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Beauty");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_books) {
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Books");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_electronics) {
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Electronics");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_hobbies) {
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Hobbies");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_fashion) {
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Fashion");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        }else if(id==R.id.nav_media){
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Music and videos");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        }else if(id==R.id.nav_others){
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname",fullname);
            intent.putExtra("category","Others");
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void homeClicked(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);
        intent.setClass(MainActivity.this, MainActivity.class);
        startActivity(intent);
        //startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    public void newPost(View view)

    {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("fullname",fullname);
        intent.setClass(MainActivity.this, NewPostActivity.class);
        startActivity(intent);
       // startActivity(new Intent(MainActivity.this,NewPostActivity.class));
    }

    public void goToProfile(View view)
    {
        Intent intent=new Intent();
        intent.putExtra("username", username);
        intent.putExtra("fullname",fullname);
        intent.setClass(MainActivity.this, UserProfileActivity.class);
        startActivity(intent);
       // startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
    }

    public void listOrAddFriends(View view)

    {
        Intent intent=new Intent();
        intent.putExtra("username", username);
        intent.putExtra("fullname",fullname);
        intent.setClass(MainActivity.this, FriendActivity.class);
        startActivity(intent);
        //startActivity(new Intent(MainActivity.this,FriendActivity.class));
    }

    public void aboutAppClicked(View view)
    {
        startActivity(new Intent(MainActivity.this,AboutAppActivity.class));
    }

}
