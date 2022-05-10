package com.example.myapplication2register.produse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2register.R;

public class ProduseDetoxActivity extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produse_detox);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3=findViewById(R.id.b3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProduseDetoxActivity.this, "Optiune selectata!", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(getApplicationContext(), Pachet1Activity.class);
                startActivity(intent1);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProduseDetoxActivity.this, "Optiune selectata!", Toast.LENGTH_SHORT).show();
                Intent intent2= new Intent(getApplicationContext(), Pachet2Activity.class);
                startActivity(intent2);

            }


        });



        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProduseDetoxActivity.this, "Optiune selectata!", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), Pachet3Activity.class);
                startActivity(intent3);

            }


        });
    }

}
