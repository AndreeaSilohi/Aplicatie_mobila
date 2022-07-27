package com.example.myapplication2register.administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication2register.R;
import com.example.myapplication2register.logare.Authentication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivityReteta extends AppCompatActivity {
    private FloatingActionButton button;
    RecyclerView mRecyclerView;
    List<RetetaPentruAdmin> myFoodList;
    RetetaPentruAdmin mRetetaPentruAdmin;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;

    private EditText txt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reteta);

        mRecyclerView=findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivityReteta.this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_search=findViewById(R.id.txt_searchtext);
        button=findViewById(R.id.uploadActivity);

        myFoodList=new ArrayList<>();
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Se încarcă...");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityReteta.this, UploadRecipe.class);
                startActivity(intent);
            }
        });





       myAdapter=new MyAdapter(MainActivityReteta.this,myFoodList);

        mRecyclerView.setAdapter(myAdapter);



        databaseReference= FirebaseDatabase.getInstance().getReference("retete");

        progressDialog.show();

        eventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myFoodList.clear();

                for(DataSnapshot itemSnapshot :dataSnapshot.getChildren()){
                    RetetaPentruAdmin retetaPentruAdmin=itemSnapshot.getValue(RetetaPentruAdmin.class);
                    retetaPentruAdmin.setKeyR(itemSnapshot.getKey());
                    myFoodList.add(retetaPentruAdmin);
                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
       ArrayList<RetetaPentruAdmin>filterList=new ArrayList<>();

       for (RetetaPentruAdmin item:myFoodList){
           if(item.getItemDescription().toLowerCase().contains(text.toLowerCase())){
               filterList.add(item);
           }
       }
       myAdapter.filteredList(filterList);

    }
}