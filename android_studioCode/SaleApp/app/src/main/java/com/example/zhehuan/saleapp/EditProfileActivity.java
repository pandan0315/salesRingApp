package com.example.zhehuan.saleapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by zhehuan on 20/02/2016.
 */
public class EditProfileActivity  extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap photo=null;
    ImageView profileIB;
    TextView profilenameET;
    String username;
    String fullname;
    Spinner spinner;
    ArrayList<String> selectedInterests= new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");
        profileIB= (ImageView)findViewById(R.id.userPhoto);
        Glide.with(this).load("http://" + getString(R.string.IP_address) + ":8080/shares/image/" + username + ".jpeg")
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .error(R.drawable.people).into(profileIB);
       // Glide.with(this).load(R.drawable.poster).into(profileIB);
        profilenameET=(TextView)findViewById(R.id.editText);
        profilenameET.setText(fullname);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (!item.toString().equals("-----")) {
                    Toast.makeText(EditProfileActivity.this, item.toString() + " added!", Toast.LENGTH_SHORT).show();
                    addButt(item);
                    spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addButt(Object item){
        final LinearLayout ll = (LinearLayout) findViewById(R.id.addedCate);
        final LinearLayout newLL = new LinearLayout(this);
        Button butt = new Button(this);
        butt.setText(item.toString());
        selectedInterests.add(item.toString());
        butt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.removeView(newLL);
            }
        });
        newLL.addView(butt);
        ll.addView(newLL);
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
            profileIB.setImageDrawable(null);
            profileIB.setImageBitmap(photo);
        }
    }

    public String encodeImageStr(Bitmap bitmap){
        if(bitmap==null){
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str= Base64.encodeToString(byte_arr, 1);
        return image_str;
    }


    public void saveProfile(View view) {

        String encodeimage=encodeImageStr(photo);
        String profilename=profilenameET.getText().toString();
        JSONArray addedInterestList=new JSONArray(selectedInterests);
        System.out.println(addedInterestList);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username",username);
            jsonObject.put("fullname", profilename);
            jsonObject.put("categoryLists",addedInterestList);
            System.out.println(addedInterestList);
            if(encodeimage!=null){
            jsonObject.put("encodeProfileIcon",encodeimage);}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        save(jsonObject);
    }

    private void save(JSONObject jsonObject) {

        try {
            StringEntity entity = new StringEntity(jsonObject.toString());
            AsyncHttpClient client=new AsyncHttpClient();
            client.post(getApplicationContext(),"http://" + getString(R.string.IP_address) + ":8080/shares/webapi/"+username+"/profile",entity,"application/json",new JsonHttpResponseHandler(){


                @Override
                public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {

                    Toast.makeText(getApplicationContext(), "You  successfully edit your profile!", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra("username",username);
                    intent.putExtra("fullname",profilenameET.getText().toString());
                    System.out.println(profilenameET.getText().toString());
                    intent.setClass(EditProfileActivity.this,UserProfileActivity.class);
                    startActivity(intent);

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString,Throwable throwable){
                    Toast.makeText(getApplicationContext(), "internet wrong, try again!", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra("username",username);
                    intent.putExtra("fullname",fullname);
                    intent.setClass(EditProfileActivity.this,EditProfileActivity.class);
                    startActivity(intent);
                }

            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
