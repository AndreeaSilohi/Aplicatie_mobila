package com.example.myapplication2register.administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2register.R;
import com.example.myapplication2register.logare.Authentication;
import com.google.firebase.auth.FirebaseAuth;

public class  AdministratorActivity extends AppCompatActivity {
    private Button editare_retete;
    private Button editare_produse;
    private Button editare_video;
    private Button logout;


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        auth=FirebaseAuth.getInstance();
        editare_retete=findViewById(R.id.editare_reteta);
        editare_produse=findViewById(R.id.editare_produs);
        editare_video=findViewById(R.id.editare_video);
        logout=findViewById(R.id.btnLogout);


        editare_retete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(AdministratorActivity.this, "Succesfull!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivityReteta.class);
                startActivity(intent);

            }

        });
        editare_produse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(AdministratorActivity.this, "Succesfull!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivityProdus.class);
                 startActivity(intent);

            }

        });

        editare_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(AdministratorActivity.this, "Succesfull!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditareVideoActivity.class);
                startActivity(intent);

            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(AdministratorActivity.this, Authentication.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdministratorActivity.this,"Succesfull",Toast.LENGTH_LONG).show();

            }
        });



    }
}