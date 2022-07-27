package com.example.myapplication2register.logare;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication2register.InformatiiContActivity;
import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.AdministratorActivity;
import com.example.myapplication2register.optiuniMeniu.ExercitiiFiziceActivity;
import com.example.myapplication2register.optiuniMeniu.MainMenuActivity;
import com.example.myapplication2register.utilizator.Utilizator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    public TextInputEditText name;
    public TextInputEditText eadress;
    public TextInputEditText password;
    public TextInputEditText numeUtil;

    RadioGroup radioGroup;
    RadioButton radioButton;


    public MaterialButton register;


    String email;
    String parola;
    String nume;
    String user;

    String emailPattern = "a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;


    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    DatabaseReference reference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        eadress = findViewById(R.id.mail);
        password = findViewById(R.id.pass);
        name=findViewById(R.id.nm);
        numeUtil=findViewById(R.id.unm);

        radioGroup=findViewById(R.id.radio);


        register = findViewById(R.id.main_save);

        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();



        reference = FirebaseDatabase.getInstance().getReference("Utilizator");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = eadress.getEditableText().toString();
                parola = password.getEditableText().toString();
                nume=name.getEditableText().toString();
                user=numeUtil.getEditableText().toString();

                if (email.matches(emailPattern)) {
                    eadress.setError("Introduceti o adresa de email corecta");
                } else if (password.length() < 6) {
                    password.setError("Introduceti o parola care sa contina cel putin 6 caractere");
                } else {
                    progressDialog.setMessage("Asteptati..");
                    progressDialog.setTitle("");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    auth.createUserWithEmailAndPassword(email, parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "Cont creat", Toast.LENGTH_SHORT).show();


                                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("Utilizator");

                                int radioId=radioGroup.getCheckedRadioButtonId();

                                radioButton=findViewById(radioId);
                                String camp=radioButton.getText().toString();

                                if(camp.equals("Utilizator")) {
                                    Utilizator utilizator = new Utilizator(nume, email, user, parola,"1" );
                                    reference.child(nume).setValue(utilizator);
                                    Intent intent=new Intent(Registration.this, MainMenuActivity.class);
                                    startActivity(intent);
                                }
                                else if(camp.equals("Administrator"))

                                {
                                    Utilizator utilizator = new Utilizator(nume, email, user, parola,"0" );
                                    reference.child(nume).setValue(utilizator);
                                    Intent intent=new Intent(Registration.this, AdministratorActivity.class);
                                    startActivity(intent);
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "Nu s-a putut crea contul", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
    public void checkButton(View view){
        int radioId=radioGroup.getCheckedRadioButtonId();

        radioButton=findViewById(radioId);

        Toast.makeText(this,"Ati selectat "+radioButton.getText(),Toast.LENGTH_SHORT).show();

    }
}


