package com.example.myapplication2register.administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.myapplication2register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivityProdus extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<ProdusPentruAdmin> myProdList;
    ProdusPentruAdmin mProdusPentruAdmin;
    private FloatingActionButton button;
    MyAdapterProdus myAdapter;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private EditText txt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_produs);


        mRecyclerView=findViewById(R.id.recyclerViewP);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivityProdus.this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        button=findViewById(R.id.uploadActivityP);
        txt_search=findViewById(R.id.txt_searchtextp);

        myProdList=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading items...");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityProdus.this, UploadProduct.class);
                startActivity(intent);
            }
        });
        myAdapter=new MyAdapterProdus(MainActivityProdus.this,myProdList);
        mRecyclerView.setAdapter(myAdapter);



        databaseReference= FirebaseDatabase.getInstance().getReference("produse");
        progressDialog.show();

        eventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myProdList.clear();

                for(DataSnapshot itemSnapshot :snapshot.getChildren()){
                    ProdusPentruAdmin p=itemSnapshot.getValue(ProdusPentruAdmin.class);
                    p.setKeyP(itemSnapshot.getKey());
                    myProdList.add(p);


                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        ArrayList<ProdusPentruAdmin>filtList=new ArrayList<>();

        for (ProdusPentruAdmin item:myProdList){
            if(item.getItemDescription().toLowerCase().contains(text.toLowerCase())){
                filtList.add(item);
            }
        }
        myAdapter.filteredList(filtList);


    }
}