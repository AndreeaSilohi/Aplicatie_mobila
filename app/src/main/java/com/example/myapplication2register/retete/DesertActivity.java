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

public class DesertActivity extends AppCompatActivity {
    private Button d1;
    private Button d2;
    private Button d3;
    private Button d4;

    String nume1,nume2,nume3,nume4;

    DatabaseReference reference;
    FirebaseDatabase rootNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desert);

        d1=findViewById(R.id.d1);
        d2=findViewById(R.id.d2);
        d3=findViewById(R.id.d3);
        d4=findViewById(R.id.d4);
        nume1=d1.getText().toString();
        nume2=d2.getText().toString();
        nume3=d3.getText().toString();
        nume4=d4.getText().toString();

        reference= FirebaseDatabase.getInstance().getReference("retete");


        d2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume2);
                reference.child(nume2).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BrioseActivity.class);
                startActivity(intent);
            }
        });
        d1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume1);
                reference.child(nume1).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), IaurtActivity.class);
                startActivity(intent);
            }
        });
        d3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume3);
                reference.child(nume3).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BudincaActivity.class);
                startActivity(intent);
            }
        });
        d4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume4);
                reference.child(nume4).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BiscuitiActivity.class);
                startActivity(intent);
            }
        });
    }
}