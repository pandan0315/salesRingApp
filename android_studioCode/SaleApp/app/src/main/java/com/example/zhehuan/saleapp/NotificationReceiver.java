package com.example.zhehuan.saleapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Dan on 2/27/2016.
 */
public class NotificationReceiver extends BroadcastReceiver {
    String username;
    public NotificationReceiver(){

    }
    public NotificationReceiver(String name){
        super();
        this.username=name;


    }
    @Override
    public void onReceive(Context context, Intent intent) {

        String strAction = intent.getAction();
        Log.d("tag", "action:" + strAction);

            SalePost salePost = (SalePost) intent.getSerializableExtra("salePost");

    }

}
