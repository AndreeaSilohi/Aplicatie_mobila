package com.example.myapplication2register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2register.administrator.DetailProdActivity;
import com.example.myapplication2register.administrator.MainActivityProdus;
import com.example.myapplication2register.administrator.ModelVideo;
import com.example.myapplication2register.logare.Authentication;
import com.example.myapplication2register.logare.Registration;
import com.example.myapplication2register.optiuniMeniu.ExercitiiFiziceActivity;
import com.example.myapplication2register.optiuniMeniu.MainMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InformatiiContActivity extends AppCompatActivity {
    public Button delete;
    public Button logout;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

            delete = findViewById(R.id.main_delete);
            logout=findViewById(R.id.btnLogout);

            auth=FirebaseAuth.getInstance();
            firebaseUser=auth.getCurrentUser();
            String id=firebaseUser.getUid();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(InformatiiContActivity.this, Authentication.class);
                startActivity(intent);
                finish();
                Toast.makeText(InformatiiContActivity.this,"Succesfull",Toast.LENGTH_LONG).show();

            }
        });




            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(InformatiiContActivity.this);
                    dialog.setTitle("Are you sure?");
                    dialog.setMessage("You will not access your account in the future.");
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Utilizator");

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(InformatiiContActivity.this,"Account deleted",Toast.LENGTH_LONG).show();
                                        reference.child(id).removeValue();
                                        Intent intent=new Intent(InformatiiContActivity.this, Registration.class);
                                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(InformatiiContActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });

                    dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();
                }
            });


    }

    }

