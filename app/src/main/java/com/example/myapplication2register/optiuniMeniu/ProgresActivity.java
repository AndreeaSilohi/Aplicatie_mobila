package com.example.myapplication2register.optiuniMeniu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.github.mikephil.charting.utils.EntryXComparator;

import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.example.myapplication2register.Input;
import com.example.myapplication2register.R;



import java.util.ArrayList;
import java.util.Collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgresActivity extends AppCompatActivity {
    public EditText xValue, yValue;
    public Button btn;
    public int x,y;
    public TextView t1,t2;
    List<Input> myList;

    LineChart lineChart;
    LineDataSet lineDataSet=new LineDataSet(null,null);
    ArrayList<ILineDataSet>lineDataSets=new ArrayList<>();

    LineData lineData;



    FirebaseDatabase rootNode;
    DatabaseReference reference;

    ArrayList barArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progres);

        btn =  findViewById(R.id.genereaza);


        xValue =  findViewById(R.id.id);
        yValue = findViewById(R.id.greutate);

        //t1 = findViewById(R.id.x);
        // t2 = findViewById(R.id.y);
        lineChart=findViewById(R.id.idBarChart);

        reference = FirebaseDatabase.getInstance().getReference("grafic");

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("grafic");



        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("grafic");
                if (yValue.length()>3) {
                    yValue.setError("Numarul de kilograme trebuie sa aiba maxim 3 cifre");
                }
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String id = reference.push().getKey();
                x = Integer.parseInt(xValue.getText().toString());
                y = Integer.parseInt(yValue.getText().toString());


                Input input = new Input(currentuser, x, y);


                reference.child(id).setValue(input);

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //myList=new ArrayList<>();
                ArrayList<Entry>data= new ArrayList<>();

                for(DataSnapshot snapshot:datasnapshot.getChildren()){

                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String user=snapshot.child("user").getValue().toString();
                    String xa = snapshot.child("x").getValue().toString();
                    String ya = snapshot.child("y").getValue().toString();

                    Integer x = Integer.parseInt(xa);
                    Integer y = Integer.parseInt(ya);

                    float ht;
                    int wt;
                    Utils.init(getApplicationContext());
                    ht =  x;
                    wt = y;
                    if(currentuser.equals(user)) {

                        data.add(new Entry(ht, wt));

                    }
                }
                showChart(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    ArrayList<Entry>lista= new ArrayList<>();

    private void showChart(List<Entry> data) {
        Collections.sort(data, new EntryXComparator());

        final LineDataSet set = new LineDataSet(data, "Acuratete");
        Paint paint = lineChart.getRenderer().getPaintRender();
        paint.setShader(new LinearGradient(0, 0, 0, 40, Color.GREEN, Color.CYAN, Shader.TileMode.REPEAT));

        lineChart.getDescription().setText("Evolutia pe saptamani");



        lineDataSets.clear();
        lineDataSet.setValues(data);
        Utils.init(getApplicationContext());
        lineDataSets.add(lineDataSet);
        lineData=new LineData(lineDataSets);
        lineChart.clear();
        //lineChart.setData(lineData);
        set.setMode(LineDataSet.Mode.LINEAR);//horizontal
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setLineWidth(3F);
        set.setDrawCircleHole(true);//aici//aici era left
        set.setDrawCircles(true);
        set.setHighlightEnabled(true);//aici
        set.setDrawFilled(true);//aicis
        final LineData group = new LineData(set);
        group.setDrawValues(true);//aici
        lineChart.setData(group);
        lineChart.invalidate();
    }







}