package com.example.zhehuan.saleapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Base64;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Toast;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io. ByteArrayOutputStream;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class NewPostActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView postPhoto;
    Bitmap photo;

    EditText store;

    EditText labeledprice;

    CheckBox is_discountbefore;

    Spinner seletedcategory;
    EditText discount;
    EditText details;




    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    TextView dateTV;
    String username;
    String fullname;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");
        System.out.println(username);

        postPhoto = (ImageView)findViewById(R.id.postPhoto);
        Glide.with(this).load(R.drawable.signs).into(postPhoto);

        store=(EditText)findViewById(R.id.storeET);
        labeledprice=(EditText)findViewById(R.id.priceET);
        discount=(EditText)findViewById(R.id.discountET);
        details=(EditText)findViewById(R.id.descriptionET);
        is_discountbefore=(CheckBox)findViewById(R.id.beforeDiscountCB);
        seletedcategory=(Spinner)findViewById(R.id.categoryET);



        dateTV = (TextView)findViewById(R.id.selectDateTV);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day + 1);
    }



    public void sendPost(View view)
    {

        //String taggedUser=null;
        String created=dateTV.getText().toString();
        String category=seletedcategory.getSelectedItem().toString();
        String is_pricebefore=String.valueOf(is_discountbefore.isChecked());
        String price=labeledprice.getText().toString();
        String sale_discount=discount.getText().toString();
        String shop=store.getText().toString();
        String encodeImage= encodeImageStr(photo);
        imageName=String.valueOf(shop+price+".jpeg");
        String description=details.getText().toString();

        JSONObject jsonObject = new JSONObject();


        if(isNotNull(created)&&isNotNull(category)&&isNotNull(is_pricebefore)&&isNotNull(price)&&isNotNull(sale_discount)&&isNotNull(shop)&&isNotNull(encodeImage)) {

            try {
                jsonObject.put("postUser", username);
                jsonObject.put("posterfullname",fullname);
                jsonObject.put("taggedUser", "Alice");
                jsonObject.put("created", created);
                jsonObject.put("category", category);
                jsonObject.put("is_pricebefore", is_pricebefore);
                jsonObject.put("price", price);
                jsonObject.put("sale_discount", sale_discount);
                jsonObject.put("shop", shop);
                jsonObject.put("encodeImage", encodeImage);
                jsonObject.put("description", description);
                jsonObject.put("imageName", imageName);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        else{
            Toast.makeText(getApplicationContext(), "Please fill all forms!", Toast.LENGTH_LONG).show();
        }



        uploadSalesinfo(jsonObject);


    }

    private void uploadSalesinfo(JSONObject object) {
        System.out.println( encodeImageStr(photo));
        System.out.println(username);
        System.out.println(fullname);
        try {
            StringEntity entity = new StringEntity(object.toString());
            //ByteArrayEntity entity = new ByteArrayEntity(object.toString().getBytes("UTF-8"));
            //entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            AsyncHttpClient client=new AsyncHttpClient();

            client.post(getApplicationContext(),"http://" + getString(R.string.IP_address) + ":8080/shares/webapi/"+username+"/salesinfo", entity,"application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {

                    Toast.makeText(getApplicationContext(), "You  successfully post new sales information!", Toast.LENGTH_LONG).show();

                  Intent callNotification = new Intent("new_post_available");
                   callNotification.putExtra("SalePost",new SalePost());
                    sendBroadcast(callNotification);

                    Intent intent=new Intent();
                    intent.putExtra("username",username);
                    intent.putExtra("fullname",fullname);
                    intent.setClass(NewPostActivity.this, MainActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString,Throwable throwable) {

                    Toast.makeText(getApplicationContext(), "Something went wrong,try again!", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra("username",username);
                    intent.putExtra("fullname",fullname);

                    intent.setClass(NewPostActivity.this, NewPostActivity.class);
                    startActivity(intent);

                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            intent.putExtra("username",username);
            intent.putExtra("fullname", fullname);

            intent.setClass(NewPostActivity.this, NewPostActivity.class);
            startActivity(intent);


        }


    }

    public boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true:false;
    }


    public void launchCamera(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }


    /*
         String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

     */

    public String encodeImageStr(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str= Base64.encodeToString(byte_arr, 1);
        return image_str;
    }

 /*   public String encodeImageStr(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.signs);
        //BitmapDrawable drawable = (BitmapDrawable) imageofuser.getDrawable();
        //bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str= Base64.encodeToString(byte_arr, 1);
        return image_str;
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extra = data.getExtras();
            photo = (Bitmap) extra.get("data");
            postPhoto.setImageBitmap(photo);
        }
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateTV.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

}
