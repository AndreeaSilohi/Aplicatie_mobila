package com.example.myapplication2register.administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2register.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

    TextView foodDescription;
    ImageView foodImage;

    String keyR="";
    String imageRUrl="";
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        foodDescription=findViewById(R.id.txtDesciption);
        foodImage=findViewById(R.id.ivImage2);

        delete=findViewById(R.id.btnDeleteR);

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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("retete");
                FirebaseStorage storage=FirebaseStorage.getInstance();


                StorageReference storageReference=storage.getReferenceFromUrl(imageRUrl);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        reference.child(keyR).removeValue();
                        Toast.makeText(DetailActivity.this,"Reteta stearsa",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),MainActivityReteta.class));
                        finish();
                    }
                });
            }
        });




    }

    

}