package com.example.myapplication2register.activitati;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2register.R;
import com.example.myapplication2register.optiuniMeniu.ExercitiiFiziceActivity;
import com.example.myapplication2register.utilUser.UserHelper;
import com.example.myapplication2register.utilUser.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity {
    public TextInputEditText name;
    public TextInputEditText eadress;
    public TextInputEditText username;
    public TextInputEditText password;
    private List<UserHelper> users = new ArrayList<>();
    public Button update;
    public FloatingActionButton register, delete;

    String emailPattern="a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String nume, email, user, pass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = findViewById(R.id.nm);
        //name.setText(nume);
        eadress = findViewById(R.id.mail);
       // eadress.setText(email);
        username = findViewById(R.id.unm);
        //username.setText(user);
        password = findViewById(R.id.pass);
        //password.setText(pass);

        register = findViewById(R.id.main_save);
        delete = findViewById(R.id.main_delete);
        progressDialog=new ProgressDialog(this);

        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                //get all the values from the text fields
                nume = name.getEditableText().toString();
                email = eadress.getEditableText().toString();
                user = username.getEditableText().toString();
                pass = password.getEditableText().toString();

                UserHelper userHelper = new UserHelper(nume, email, user, pass);

                reference.child(user).setValue(userHelper);
                createUser();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                //get all the values from the text fields
                nume = name.getEditableText().toString();
                email = eadress.getEditableText().toString();
                user = username.getEditableText().toString();
                pass = password.getEditableText().toString();
                reference.child(user).removeValue();
            }
        });


    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           progressDialog.dismiss();
                           sendUserToNextActivity();
                           Toast.makeText(Registration.this,"Succes",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           progressDialog.dismiss();
                           Toast.makeText(Registration.this, " "+task.getException(),Toast.LENGTH_SHORT).show();
                       }
            }
        });


    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(Registration.this, ExercitiiFiziceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void isUser() {
        String userEntered = username.getEditableText().toString().trim();
        String passEntered = password.getEditableText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("user").equalTo(userEntered);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    username.setError(null);
                    String passwfromDB = snapshot.child(userEntered).child("pass").getValue(String.class);
                    if (passwfromDB.equals(userEntered)) {
                        String namefromDB = snapshot.child(userEntered).child("fname").getValue(String.class);
                        String emailfromDB = snapshot.child(userEntered).child("eadress").getValue(String.class);
                        String usernamefromDB = snapshot.child(userEntered).child("user").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);

                        intent.putExtra("fname", namefromDB);
                        intent.putExtra("user", usernamefromDB);
                        intent.putExtra("eadress", emailfromDB);
                        intent.putExtra("pass", passwfromDB);

                        startActivity(intent);
                    } else {
                        password.setError("Wron pass");
                        password.requestFocus();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    boolean isEmail(EditText text) {



     CharSequence email = text.getText().toString();
       return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(name)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmail(eadress) == false) {
            eadress.setError("Enter valid email!");
        }

        if (!isEmpty(name) && isEmail(eadress) == true) {
            Toast.makeText(Registration.this, "LOGIN SUCCESSFULLY!", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean validatePassword() {
        String val = password.getEditableText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    public boolean validateUsername() {
        String val = username.getEditableText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateUsername() || !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }


}



