package com.example.zhehuan.saleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Name Edit View Object
    EditText nameET;
    // Email Edit View Object
    EditText emailET;
    // Passwprd Edit View Object
    EditText pwdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Find Error Msg Text View control by ID
        errorMsg = (TextView)findViewById(R.id.register_error);

        nameET=(EditText)findViewById(R.id.registerName);

        emailET=(EditText)findViewById(R.id.registerEmail);

        pwdET=(EditText)findViewById(R.id.regesterPassword);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

    }

    public void registerUser(View view){

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password= pwdET.getText().toString();

        RequestParams params= new RequestParams();

        if(isNotNull(name)&&isNotNull(email)&&isNotNull(password)){

            if(isEmailValid(email)){

                params.put("username",name);
                params.put("email",email);
                params.put("password",password);

                invokeWS(params);
            }
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }



    }

    public void goToLogin(View view) {
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public abstract class AlwaysAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        @Override
        public boolean getUseSynchronousMode() {
            return false;
        }
    }

    public void invokeWS(RequestParams params){

        prgDialog.show();

        AsyncHttpClient client=new AsyncHttpClient();

        client.post("http://130.229.170.156:8080/shares/webapi/signup", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                prgDialog.hide();
                Toast.makeText(getApplicationContext(), "You are successfully registered! Please login in Login page!", Toast.LENGTH_LONG).show();

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,Throwable throwable) {
                prgDialog.hide();
                Toast.makeText(getApplicationContext(), "Something went wrong,maybe username or email already existed,try again!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));

            }
        });
    }

    public boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true:false;
    }
    public boolean isEmailValid(String email) {

        return email.contains("@");
    }
}
