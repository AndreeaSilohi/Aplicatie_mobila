package com.example.myapplication2register.administrator;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication2register.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadProduct extends AppCompatActivity {
    ImageView productImage;
    private Button select;
    private Button upload;

    Uri uri;

    EditText txt_name;
    EditText txt_description;

    String img_uri;

    ActivityResultLauncher<Intent> startForResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getData()!=null&&result.getResultCode()==RESULT_OK){

                uri = result.getData().getData();
                productImage.setImageURI(uri);

            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        productImage = findViewById(R.id.image2P);
        txt_name = findViewById(R.id.txt_recipe_nameP);
        txt_description = findViewById(R.id.txt_descriptionP);

        select = findViewById(R.id.btnSelectP);
        upload = findViewById(R.id.btnUploadP);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startForResult.launch(intent);
                //startActivityForResult(intent, 1);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StorageReference storageReference = FirebaseStorage.getInstance()
                        .getReference().child("Image").child(uri.getLastPathSegment());
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                        while (!uriTask.isComplete()) ;
                        Uri uriImage = uriTask.getResult();
                        img_uri = uriImage.toString();
                        uploadRecipe();
                        Toast.makeText(UploadProduct.this, "Imagine incarcata", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            productImage.setImageURI(uri);
        } else {
            Toast.makeText(this, "Nu ai incarcat nicio imagine", Toast.LENGTH_SHORT).show();

        }


    }


    public void uploadRecipe() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Produsul se incarca...");
        progressDialog.show();


        ProdusPentruAdmin adminProd = new ProdusPentruAdmin(txt_name.getText().toString(), txt_description.getText().toString(), img_uri);
        FirebaseDatabase.getInstance().getReference("produse")
                .child(txt_name.getText().toString()).setValue(adminProd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadProduct.this, "Informatiile despre produs au fost incarcate", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadProduct.this, "Produsul nu s-a putut incarca", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        });


    }
}