package com.example.myapplication2register.optiuniMeniu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2register.R;

public class CoordonateUserActivity extends AppCompatActivity {
public Button afla_indice;
public EditText et1;
public EditText et2;
public TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordonate_user);
        afla_indice =  findViewById(R.id.calculeaza_indice);

        et1=findViewById(R.id.inaltime);
        et2=findViewById(R.id.weight);
        tv1 = findViewById(R.id.tv);
        tv2=findViewById(R.id.textView4);



        afla_indice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int v1=0;
                String t1=et1.getText().toString();
                if(!" ".equals(t1)){
                    v1=Integer.parseInt(t1);
                }

                int v2=0;
                String t2=et2.getText().toString();
                if(!" ".equals(t2)){
                    v2=Integer.parseInt(t2);
                }
                float p;
                float indice;
                p=v1*v1;
                indice=v2/p;
                indice=indice*10000;

                tv2.setText("Indicele dumneavoastra de masa este "+indice+".");

                if(indice<18.5) {
                    tv1.setText("Sunteti subponderal.");
                }
                 else if(indice>=18.5&&indice<=24.9)
                { tv1.setText("Aveti o greutate normala.");

                }
                 else if(indice>=25&&indice<=29.9){
                    tv1.setText("Sunteti supraponderal.");
                }
                 else if(indice>=30&&indice<=34.9){
                    tv1.setText("Aveti obezitate de gradul 1.");
                }
                else if(indice>=35&&indice<=39.9){
                    tv1.setText("Aveti obezitate de gradul 2.");
                }
                else if(indice>=40){
                    tv1.setText("Aveti obezitate de gradul 3.");
                }
            }
        });





    }
}



