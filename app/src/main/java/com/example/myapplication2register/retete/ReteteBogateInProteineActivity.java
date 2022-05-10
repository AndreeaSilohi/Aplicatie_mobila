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

public class ReteteBogateInProteineActivity extends AppCompatActivity {
    private Button p1;
    private Button p2;

    String nume1,nume2;
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_bogate_in_proteine);

        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);

        nume1=p1.getText().toString();
        nume2=p2.getText().toString();

        reference= FirebaseDatabase.getInstance().getReference("retete");
        p1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume1);
                reference.child(nume1).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), ConopidaActivity.class);
                startActivity(intent1);
            }
        });

        p2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("retete");

                //get all the values from the text fields

                RetetaP r=new RetetaP(nume2);
                reference.child(nume2).setValue(r);
                //Toast.makeText(MainActivity4.this,"IT'S OK!",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), SalataActivity.class);
                startActivity(intent2);
            }
        });
    }
}