package com.example.zhehuan.saleapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zhehuan on 20/02/2016.
 */
class FriendAdapter extends ArrayAdapter<FriendDetail> {

    ArrayList<FriendDetail> friends;
    Activity mainActivity;

    public FriendAdapter(Activity context, ArrayList<FriendDetail> friends) {
        super(context, R.layout.friend_detail ,friends);
        this.friends = friends;
        this.mainActivity = context;
    }
    public int getCount() {
        return friends.size();
    }

    public FriendDetail getItem(int position) {
        return friends.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position){
        friends.remove(position);
        this.notifyDataSetChanged();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.friend_detail, parent, false);
        TextView userName = (TextView) rowView.findViewById(R.id.usersName);
        TextView fullName = (TextView) rowView.findViewById(R.id.fullName);
        ImageView userPhoto = (ImageView) rowView.findViewById(R.id.userPhoto);
       // Glide.with(mainActivity).load(R.drawable.poster).into(userPhoto);
        Glide.with(mainActivity).load("http://192.168.11.113:8080/shares/image/"+friends.get(position)+".jpeg")
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .error(R.drawable.poster).
                into(userPhoto);

        userName.setText(friends.get(position).getUserName());
       // category.setText(friends.get(position));
        fullName.setText(friends.get(position).getFullName());

        ImageView deleteIB = (ImageView) rowView.findViewById(R.id.deleteIB);
        Glide.with(mainActivity).load(R.drawable.delete7).into(deleteIB);
     /*   deleteIB.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(mainActivity,
                                "ImageButton clicked",
                                Toast.LENGTH_SHORT).show();
                    }
                });
*/
        return rowView;
    }

}
