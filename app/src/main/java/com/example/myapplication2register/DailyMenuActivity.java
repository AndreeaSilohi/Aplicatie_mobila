
package com.example.myapplication2register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication2register.retete.PolentaActivity;
import com.example.myapplication2register.utilReteta.RetetaP;
import com.example.myapplication2register.utilUser.Meniu;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyMenuActivity extends AppCompatActivity {

    public EditText aliment1;
    public EditText aliment2;
    public EditText aliment3;
    public EditText aliment4;
    public Button b,a;
    public ListView lv;
    public ArrayList<String>arrayList;
    public ArrayAdapter<String>adapter;

    SimpleDateFormat sdf = new SimpleDateFormat("dd,MM,yyyy");
    String date = sdf.format(new Date());

    DatabaseReference reference;
    FirebaseDatabase rootNode;

    public String al1,al2,al3,al4,data;

    InputStream inputStreamCounter;
    BufferedReader bufferedReaderCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_menu);
        aliment1 = findViewById(R.id.aliment1);
        aliment2 = findViewById(R.id.aliment2);
        aliment3 = findViewById(R.id.aliment3);
        aliment4 = findViewById(R.id.aliment4);

        b = findViewById(R.id.Post);

        lv=findViewById(R.id.list);

        arrayList=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(DailyMenuActivity.this, android.R.layout.simple_list_item_1,arrayList);

        lv.setAdapter(adapter);



        reference = FirebaseDatabase.getInstance().getReference("meniu");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("meniu");

                al1 = aliment1.getEditableText().toString();
                al2 = aliment2.getEditableText().toString();
                al3 = aliment3.getEditableText().toString();
                al4 = aliment4.getEditableText().toString();

                //get all the values from the text fields

                Meniu m=new Meniu(currentuser,data,al1,al2,al3,al4);
                reference.child(date).setValue(m);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
               // myList=new ArrayList<>();
               // ArrayList<Entry>data=new ArrayList<Entry>();

                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    String a1 = snapshot.child("p1").getValue().toString();
                    String a2 = snapshot.child("p2").getValue().toString();
                    String a3 = snapshot.child("p3").getValue().toString();
                    String a4 = snapshot.child("p4").getValue().toString();
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    String concat = a1 + ", " + a2 + ", " + a3 + ", " + a4;
                   // if(snapshot.child("user").getValue().toString()==currentuser)

                    arrayList.add(concat);

                    adapter.notifyDataSetChanged();


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    }

