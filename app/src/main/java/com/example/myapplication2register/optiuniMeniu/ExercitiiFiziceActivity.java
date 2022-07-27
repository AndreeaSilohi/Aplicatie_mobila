package com.example.myapplication2register.optiuniMeniu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.AddVideoActivityActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExercitiiFiziceActivity extends AppCompatActivity {
FloatingActionButton addVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercitii_fizice);

        addVideo=findViewById(R.id.add);

        addVideo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExercitiiFiziceActivity.this, AddVideoActivityActivity.class));

            }
        });
    }
}