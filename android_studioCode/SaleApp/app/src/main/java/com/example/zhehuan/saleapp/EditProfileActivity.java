package com.example.zhehuan.saleapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by zhehuan on 20/02/2016.
 */
public class EditProfileActivity  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (!item.toString().equals("-----")) {
                    Toast.makeText(EditProfileActivity.this, item.toString() + " added!", Toast.LENGTH_SHORT).show();
                    addButt(item);
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
}
