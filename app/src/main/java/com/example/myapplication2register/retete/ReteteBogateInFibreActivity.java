package com.example.myapplication2register.retete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication2register.R;
import com.example.myapplication2register.utilReteta.RetetaP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReteteBogateInFibreActivity extends AppCompatActivity {
    private Button f1;
    private Button f2;
    private Button f3;
    String nume1,nume2 ,nume3;

    DatabaseReference reference;
    FirebaseDatabase rootNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_bogate_in_fibre);
        f1=findViewById(R.id.f1);
        f2=findViewById(R.id.f2);
        f3=findViewById(R.id.f3);

        nume1=f1.getText().toString();
        nume2=f2.getText().toString();
        nume3=f3.getText().toString();
        reference= FirebaseDatabase.getInstance().getReference("retete");


        f1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume1);
                reference.child(nume1).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), PolentaActivity.class);
                startActivity(intent1);
            }
        });

        f2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume2);
                reference.child(nume2).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), PasteActivity.class);
                startActivity(intent2);
            }
        });


        f3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume3);
                reference.child(nume3).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), RetetaPuiActivity.class);
                startActivity(intent3);
            }
        });
    }



    }

