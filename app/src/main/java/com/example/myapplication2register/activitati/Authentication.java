package com.example.myapplication2register.activitati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2register.optiuniMeniu.ExercitiiFiziceActivity;
import com.example.myapplication2register.optiuniMeniu.MainMenuActivity;
import com.example.myapplication2register.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity {
    public Button signIn;
    EditText email,password;
    ProgressDialog progressDialog;

    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

         email= findViewById(R.id.username);
         password=findViewById(R.id.password);

        signIn= findViewById(R.id.sign);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                logIn();
//                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
//               Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
//                startActivity(intent);
            }
        });
    }





    private void logIn() {

        String em = email.getEditableText().toString();
        String pass = password.getEditableText().toString();

        firebaseAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     progressDialog.dismiss();
                     sendUserToNextActivity();
                     Toast.makeText(Authentication.this,"Succes",Toast.LENGTH_SHORT).show();
                 } else {
                     progressDialog.dismiss();
                     Toast.makeText(Authentication.this, " "+task.getException(),Toast.LENGTH_SHORT).show();
                 }
            }
        });

    }

    private void sendUserToNextActivity() {
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println(currentuser);
        Intent intent=new Intent(Authentication.this, MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}