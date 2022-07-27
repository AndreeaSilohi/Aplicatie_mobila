package com.example.myapplication2register.produse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.DetailProdActivity;
import com.example.myapplication2register.administrator.MainActivityProdus;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivityDetailsProduse extends AppCompatActivity {
    TextView prodDescription;
    ImageView prodImage;
    String keyP="";
    String imagePUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details_produse);

        prodDescription=findViewById(R.id.txtDescriptionP);
        prodImage=findViewById(R.id.ivImage2P);


        Bundle mBundle=getIntent().getExtras();
        if(mBundle!=null){
            prodDescription.setText(mBundle.getString("Description"));
            // prodImage.setImageResource(mBundle.getInt("Image"));
            keyP=mBundle.getString("keyValueP");
            imagePUrl=mBundle.getString("Image");

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(prodImage);

        }





    }



}