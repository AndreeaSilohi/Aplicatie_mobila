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

public class DetailProdActivity extends AppCompatActivity {
    TextView prodDescription;
    ImageView prodImage;
    String keyP="";
    String imagePUrl="";

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prod);
        prodDescription=findViewById(R.id.txtDescriptionP);
        prodImage=findViewById(R.id.ivImage2P);

        btn=findViewById(R.id.btn);

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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("produse");
                FirebaseStorage storage=FirebaseStorage.getInstance();


                StorageReference storageReference=storage.getReferenceFromUrl(imagePUrl);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        reference.child(keyP).removeValue();
                        Toast.makeText(DetailProdActivity.this,"Produs sters",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),MainActivityProdus.class));
                        finish();
                    }
                });
            }
        });




    }



}