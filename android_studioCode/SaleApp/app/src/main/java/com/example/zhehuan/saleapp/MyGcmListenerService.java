package com.example.zhehuan.saleapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Dan on 2/29/2016.
 */
public class MyGcmListenerService extends GcmListenerService {

   String username=LoginActivity.user;

    SalePost newPost=new SalePost();
    public static String post;
    public static String names;
    public static String[] nameArray;
    AsyncHttpClient client=new AsyncHttpClient();
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        //create notification here
        post= data.getString("newpost");
        names=data.getString("notifyuser");

        if((post==null)||(names==null)){
            return;
        }




        nameArray=names.split(",");
        if(username==null){
            return;
        }

        for(int i=0;i<nameArray.length;i++){
            if(username.equals(nameArray[i])){

                try {
                    JSONObject jsonObj=new JSONObject(post);
                    newPost.setPostID(jsonObj.getLong("id"));
                    newPost.setPoster(jsonObj.getString("postUser"));
                    newPost.setTaggedUser(jsonObj.getString("taggedUser"));
                    newPost.setPostDate(jsonObj.getString("created"));
                    newPost.setCategory(jsonObj.getString("category"));
                    newPost.setDescription(jsonObj.getString("description"));
                    newPost.setImageName(jsonObj.getString("imageName"));
                    newPost.setIs_pricebefore(jsonObj.getString("is_pricebefore"));
                    newPost.setPosterfullname(jsonObj.getString("posterfullname"));
                    newPost.setPrice(jsonObj.getString("price"));
                    newPost.setSaleValue(jsonObj.getString("sale_discount"));
                    newPost.setStore(jsonObj.getString("shop"));
                    sendNotification(newPost);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }



    }

    private void sendNotification(SalePost post) {
        Random random=new Random();
        int m=random.nextInt(9999-1000)+1000;


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        resultIntent.putExtra("username", username);
        resultIntent.putExtra("salePost", post);
        resultIntent.setClass(this, DetailedViewActivity.class);
        //context.startActivity(resultIntent);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), resultIntent, 0);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.signs)
                        .setContentTitle("Sales Ring")
                        .setContentText("New Sales in categories you are interested in")
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();


        notificationManager.notify(0, notification);
    }


    }

