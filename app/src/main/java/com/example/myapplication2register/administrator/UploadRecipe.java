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
import android.widget.ProgressBar;
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

public class UploadRecipe extends AppCompatActivity {
    ImageView recipeImage;
    private Button select;
    private Button upload;

    Uri uri;

    EditText txt_name;
    EditText txt_description;

    String img_uri;
    ActivityResultLauncher<Intent>startForResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getData()!=null&&result.getResultCode()==RESULT_OK){

                uri = result.getData().getData();
                recipeImage.setImageURI(uri);

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);

        recipeImage = findViewById(R.id.image);

        txt_name = findViewById(R.id.txt_recipe_name);
        txt_description = findViewById(R.id.txt_description);

        select = findViewById(R.id.btnSelect);
        upload = findViewById(R.id.btnUpload);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startForResult.launch(intent);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StorageReference storageReference = FirebaseStorage.getInstance()
                        .getReference().child("RecipeImage").child(uri.getLastPathSegment());
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                        while (!uriTask.isComplete()) ;
                        Uri uriImage = uriTask.getResult();
                        img_uri = uriImage.toString();
                        uploadRecipe();
                        Toast.makeText(UploadRecipe.this, "Imagine incarcata", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            uri = data.getData();
//            recipeImage.setImageURI(uri);
//        } else {
//            Toast.makeText(this, "Nu ai incarcat nicio imagine", Toast.LENGTH_SHORT).show();
//
//        }
//
//
//    }


    public void uploadRecipe() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Reteta se incarca...");
        progressDialog.show();


        RetetaPentruAdmin adminReteta = new RetetaPentruAdmin(txt_name.getText().toString(), txt_description.getText().toString(), img_uri);
        String myCurrentDatetime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("retete")
                .child(txt_name.getText().toString()).setValue(adminReteta).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadRecipe.this, "Reteta incarcata", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadRecipe.this, "Reteta nu s-a putut incarca", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        });


    }
}