package com.example.myapplication2register.produse;

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
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.MyAdapterProdus;
import com.example.myapplication2register.administrator.ProdusPentruAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProduseDetoxActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<ProdusPentruAdmin> myProdList;
    ProdusPentruAdmin mProdusPentruAdmin;
   // private FloatingActionButton button;
  AdapterProdusClient myAdapter;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private EditText txt_search;
    private TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produse_detox);


        mRecyclerView=findViewById(R.id.recyclerViewP);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(ProduseDetoxActivity.this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        //button=findViewById(R.id.uploadActivityP);
        txt_search=findViewById(R.id.txt_searchtextp);
        link=findViewById(R.id.hyperlink);

        link.setMovementMethod(LinkMovementMethod.getInstance());

        myProdList=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading items...");



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProduseDetoxActivity.this, UploadProduct.class);
//                startActivity(intent);
//            }
//        });
        myAdapter=new AdapterProdusClient(ProduseDetoxActivity.this,myProdList);
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