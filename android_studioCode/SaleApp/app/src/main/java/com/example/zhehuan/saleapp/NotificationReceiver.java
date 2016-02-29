package com.example.zhehuan.saleapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Dan on 2/27/2016.
 */
public class NotificationReceiver extends BroadcastReceiver {
   String username;
   // ArrayList<String> interestsList;
    SalePost salePost;
    public NotificationReceiver(){
        username=MainActivity.username;

    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        String strAction = intent.getAction();
        Log.d("tag", "action:" + strAction);
        salePost = (SalePost) intent.getSerializableExtra("SalePost");
        final String category=salePost.getCategory();
        AsyncHttpClient client=new AsyncHttpClient();
        client.get("http://" + context.getString(R.string.IP_address) + ":8080/shares/webapi/" + username, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {


                    ArrayList<String> interestList = new ArrayList<>();
                    if (response.has("categoryLists")) {
                        JSONArray interestJSONArray = response.getJSONArray("categoryLists");
                        for (int i = 0; i < interestJSONArray.length(); i++) {
                            interestList.add(interestJSONArray.getString(i));
                        }
                    }

                    for(String interest:interestList) {
                        if (interest.equals(category)) {
                            NotificationManager notificationManager =
                                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                            Intent resultIntent = new Intent();
                            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            resultIntent.putExtra("username", username);
                            resultIntent.putExtra("salePost", salePost);
                            resultIntent.setClass(context, DetailedViewActivity.class);
                            //context.startActivity(resultIntent);
                            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), resultIntent, 0);

                            Notification notification =
                                    new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.signs)
                                            .setContentTitle("Sales Ring")
                                            .setContentText("New Sales in categories you are interested in")
                                            .setContentIntent(pIntent)
                                            .setAutoCancel(true).build();


                            notificationManager.notify(0, notification);
                        }
                    }

                    } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
                Toast.makeText(context, "Some things goes wrong, internet error,profile can not be displaied!", Toast.LENGTH_LONG).show();


            }

            @Override
        public void onFailure(int statuscode, Header[] headers, String errorResponse, Throwable  throwable){
                Toast.makeText(context, "Some things goes wrong, internet error,profile can not be displaied!", Toast.LENGTH_LONG).show();

            }

        });





        }





}
