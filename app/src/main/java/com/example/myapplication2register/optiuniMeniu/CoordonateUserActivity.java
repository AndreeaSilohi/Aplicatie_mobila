package com.example.myapplication2register.optiuniMeniu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication2register.R;
import com.example.myapplication2register.administrator.VideosActivity;
import com.example.myapplication2register.produse.ProduseDetoxActivity;
import com.example.myapplication2register.video.ShowVideoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class CoordonateUserActivity extends AppCompatActivity {
    public Button afla_indice, catre_exercitii,catre_detox;
    public EditText et1;
    public EditText et2;
    public TextView tv1,tv2;

    Float indice;

    String t1;
    String t2;

    DatabaseReference reference;
    FirebaseDatabase rootNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordonate_user);

        afla_indice =  findViewById(R.id.calculeaza_indice);

        catre_exercitii=findViewById(R.id.catre_exercitii);
        catre_detox=findViewById(R.id.catre_detox);

        et1=findViewById(R.id.inaltime);
        et2=findViewById(R.id.weight);
        tv1 = findViewById(R.id.tv);
        tv2=findViewById(R.id.textView4);
        setTitle("Indice de masa corporala");

        //rootNode = FirebaseDatabase.getInstance();
        //reference = rootNode.getReference("indice_masa");


        afla_indice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (et1.length()==0) {
                    et1.setError("Acest camp este obligatoriu");
                } else if(et2.length()==0)
                {
                    et2.setError("Acest camp este obligatoriu");
                }
                    else {
                    int v1 = 0;
                    t1 = et1.getText().toString();
                    if (!" ".equals(t1)) {
                        v1 = Integer.parseInt(t1);
                    }


                    int v2 = 0;
                    t2 = et2.getText().toString();
                    if (!" ".equals(t2)) {
                        v2 = Integer.parseInt(t2);
                    }
                    float p;

                    p = v1 * v1;
                     indice = v2 / p;
                    indice = indice * 10000;

                    tv2.setText("Indicele dumneavoastra de masa este " + String.format("%.2f", indice) + ".");

                    if (indice < 18.5) {
                        tv1.setText("Sunteti subponderal.");
                    } else if (indice >= 18.5 && indice <= 24.9) {
                        tv1.setText("Aveti o greutate normala.");

                    } else if (indice >= 25 && indice <= 29.9) {
                        tv1.setText("Sunteti supraponderal.");
                    } else if (indice >= 30 && indice <= 34.9) {
                        tv1.setText("Aveti obezitate de gradul 1.");
                    } else if (indice >= 35 && indice <= 39.9) {
                        tv1.setText("Aveti obezitate de gradul 2.");
                    } else if (indice >= 40) {
                        tv1.setText("Aveti obezitate de gradul 3.");


                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("indice_masa");
                        Coordonata c = new Coordonata(t1, t2);
                        reference.child(currentuser).setValue(c);
                    }
                }
            }
        });
        //reference = FirebaseDatabase.getInstance().getReference("masa");
        catre_detox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(CoordonateUserActivity.this, ProduseDetoxActivity.class);
               startActivity(intent);

            }

        });


        catre_exercitii.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CoordonateUserActivity.this, ShowVideoActivity.class);
                startActivity(intent);
            }

        });
    }


}
