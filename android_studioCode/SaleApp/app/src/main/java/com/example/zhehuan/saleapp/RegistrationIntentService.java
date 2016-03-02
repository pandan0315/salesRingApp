package com.example.zhehuan.saleapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Dan on 3/1/2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    String username;
    AsyncHttpClient client=new AsyncHttpClient();

    public RegistrationIntentService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        username=intent.getStringExtra("username");
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //boolean is_getToken=sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER,false);

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);


            sendRegistrationToServer(token);





        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);

        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {


        // Add custom implementation, as needed.
        JSONObject jsonObject=new JSONObject();
        try {
          //  jsonObject.put("username",username);
            jsonObject.put("token",token);
            StringEntity entity = new StringEntity(jsonObject.toString());

            client.post(getApplicationContext(),"http://" + getString(R.string.IP_address) + ":8080/shares/webapi/gcmtoken",entity,"application/json", new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                    Toast.makeText(getApplicationContext(), "send token to the server!", Toast.LENGTH_LONG).show();
                    Log.d("token","send Successful");


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString,Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Something wrong with sending token!", Toast.LENGTH_LONG).show();
                    Log.d("token", "send Falure");

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getApplicationContext(), "Some things goes wrong, internet error, please Login again!", Toast.LENGTH_LONG).show();
                    Log.d("token", "send Falure");



                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




    }


}
