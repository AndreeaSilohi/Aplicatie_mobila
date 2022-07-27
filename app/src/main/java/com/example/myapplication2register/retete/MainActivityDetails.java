package com.example.myapplication2register.retete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.DetailActivity;
import com.example.myapplication2register.administrator.MainActivityReteta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivityDetails extends AppCompatActivity {
    TextView foodDescription;
    ImageView foodImage;

    String keyR="";
    String imageRUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);
        foodDescription=findViewById(R.id.txtDesciption);
        foodImage=findViewById(R.id.ivImage2);



        Bundle mBundle=getIntent().getExtras();

        if(mBundle!=null){
            foodDescription.setText(mBundle.getString("Description"));
            //foodImage.setImageResource(mBundle.getInt("Image"));
            keyR=mBundle.getString("keyValueR");

            imageRUrl=mBundle.getString("Image");
            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(foodImage);

        }


    }



}