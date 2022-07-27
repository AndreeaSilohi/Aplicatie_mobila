package com.example.myapplication2register.logare;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2register.R;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
public MaterialButton sign_up;
public Button sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sign_in =  findViewById(R.id.sign_in);
         sign_up =  findViewById(R.id.sign_up);


         sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, "LOGIN SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);

            }

        });




        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, "LOGIN SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Authentication.class);
                startActivity(intent);

            }

        });

    }
}