
package com.example.myapplication2register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication2register.utilizator.Meniu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MeniuZilnic extends AppCompatActivity {

    public EditText aliment1;
    public EditText aliment2;
    public EditText aliment3;
    public EditText aliment4;
    public EditText aliment5;
    public Button b;
    public ListView lv;
    public ArrayList<String>arrayList;
    public ArrayAdapter<String>adapter;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String date = sdf.format(new Date());

    DatabaseReference reference;
    FirebaseDatabase rootNode;

    public String al1,al2,al3,al4,al5,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_menu);
        aliment1 = findViewById(R.id.aliment1);
        aliment2 = findViewById(R.id.aliment2);
        aliment3 = findViewById(R.id.aliment3);
        aliment4 = findViewById(R.id.aliment4);
        aliment5 = findViewById(R.id.aliment5);

        b = findViewById(R.id.Post);
        // retrieve=findViewById(R.id.retrieve);
        lv = findViewById(R.id.list);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MeniuZilnic.this, android.R.layout.simple_list_item_1, arrayList);

        lv.setAdapter(adapter);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("meniu");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("meniu");
                String id = reference.push().getKey();

                al1 = aliment1.getEditableText().toString();
                al2 = aliment2.getEditableText().toString();
                al3 = aliment3.getEditableText().toString();
                al4 = aliment4.getEditableText().toString();
                al5 = aliment5.getEditableText().toString();


                Meniu m = new Meniu(currentuser, date, al1, al2, al3, al4, al5);
                reference.child(al1).setValue(m);
                arrayList.clear();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                    String user = snapshot.child("user").getValue().toString();

                    String a1 = snapshot.child("p1").getValue().toString();
                    String a2 = snapshot.child("p2").getValue().toString();
                    String a3 = snapshot.child("p3").getValue().toString();
                    String a4 = snapshot.child("p4").getValue().toString();
                    String a5 = snapshot.child("p5").getValue().toString();
                    String concat ="În data de "+snapshot.child("date").getValue().toString()+" am consumat: "+ a1 + ", " + a2 + ", " + a3 + ", " + a4 + " ," + a5;
                    if (currentuser.equals(user)) {

                        arrayList.add(concat);
                    }

                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}

