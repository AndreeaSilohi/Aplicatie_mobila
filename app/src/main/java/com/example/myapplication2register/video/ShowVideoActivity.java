package com.example.myapplication2register.video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.AdapterVideo;
import com.example.myapplication2register.administrator.ModelVideo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowVideoActivity extends AppCompatActivity {
    FloatingActionButton addVideosBtn;

    private ArrayList<ModelVideo> videoArrayList;
    private RecyclerView videosRv;

    private AdapterVideoClient adapterVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);

        setTitle("Videoclipuri exercitii fizice");
        addVideosBtn=findViewById(R.id.addVideosBtn);
        videosRv=findViewById(R.id.videosRv);

        //function call, load videos
        loadVideosFromFirebase();

        //handle click
//        addVideosBtn.setOnClickListener((view)->{
//            //start activity to add videos
//            startActivity(new Intent(ShowVideoActivity.this, AddVideoActivityActivity.class));
//        });
    }



    private void loadVideosFromFirebase() {
        //init arraylist
        videoArrayList=new ArrayList<>();
        //db reference
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("videoclipuri");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list before adding data into it
                videoArrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //getdata
                    ModelVideo modelVideo=ds.getValue(ModelVideo.class);
                    //add model/data
                    videoArrayList.add(modelVideo);

                }
                //setup adapter
                adapterVideo=new AdapterVideoClient(ShowVideoActivity.this,videoArrayList);
                videosRv.setAdapter(adapterVideo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}