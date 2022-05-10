package com.example.myapplication2register.utilUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication2register.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {
    public TextInputEditText _nume;
    public TextInputEditText _eadress;
    public TextInputEditText _username;
    public TextInputEditText _password;

    String _n, _e, _u, _p;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);
        reference = FirebaseDatabase.getInstance().getReference("users");

        _nume = findViewById(R.id.mainname);
        _eadress = findViewById(R.id.mainmail);
        _username = findViewById(R.id.mainusername);
        _password = findViewById(R.id.mainpassword);
        showAllUsers();

    }

    public void showAllUsers() {
        Intent intent = getIntent();
        _u = intent.getStringExtra("user");
        _e = intent.getStringExtra("mail");
        _n = intent.getStringExtra("fname");
        _p = intent.getStringExtra("pass");

        _nume.setText(_u);
        _eadress.setText(_e);
        _password.setText(_p);
        _username.setText(_u);


    }

}


