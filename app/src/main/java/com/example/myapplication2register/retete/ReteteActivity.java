package com.example.myapplication2register.retete;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication2register.R;

import java.util.ArrayList;
import java.util.List;

public class ReteteActivity extends AppCompatActivity {


    private Button b1;
    private Button b2;
    private Button b3;


    private List<Reteta> reteteList = new ArrayList<>();
    private ActivityResultLauncher<Intent> addRetetaLauncher;
    private ListView lvreteta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete);


        lvreteta = findViewById(R.id.retete_list);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3=findViewById(R.id.b3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReteteActivity.this, "Optiune selectata!", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(getApplicationContext(), ReteteBogateInFibreActivity.class);
                startActivity(intent1);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReteteActivity.this, "Optiune selectata!", Toast.LENGTH_SHORT).show();
                Intent intent2= new Intent(getApplicationContext(), ReteteBogateInProteineActivity.class);
                startActivity(intent2);

            }


        });



        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReteteActivity.this, "Optiune selectata!", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), DesertActivity.class);
                startActivity(intent3);

            }


        });
    }

    private void notifyLvStudentAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvreteta.getAdapter();
        adapter.notifyDataSetChanged();
    }


//    private ActivityResultLauncher<Intent> registerAddSkinResultLaucncher() {
//       // ActivityResultCallback<ActivityResult> callback = getAddReteteActiviyResultCallback();
//        //return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
//    }


//    private ActivityResultCallback<ActivityResult> getAddReteteActiviyResultCallback() {
//        return new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    Reteta r=(SkinCare) result.getData().getSerializableExtra(MainActivity3.ADD_SKINCARE_KEY);
////                    if (r != null) {
////                        reteteList.add(r);
////                        notifyLvStudentAdapter();
////                    }
//
//                }
//            }
//        };
    }



//    private void addLvSkinAdapter() {
////        ArrayAdapter<SkinCare> adapter = new ArrayAdapter<>(getApplicationContext(),
////                android.R.layout.simple_list_item_1, skinCareList);
//        RetetaAdapter retetaAdapter=new RetetaAdapter(getApplicationContext(),
//                R.layout.re,skinCareList,getLayoutInflater());
//        lvreteta.setAdapter(retetaAdapter);
//    }
//
//
//}
