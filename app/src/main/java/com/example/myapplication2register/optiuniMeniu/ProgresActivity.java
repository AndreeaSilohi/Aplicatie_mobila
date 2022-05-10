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

import java.util.List;

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

        btn = (Button) findViewById(R.id.genereaza);


        xValue = (EditText) findViewById(R.id.id);
        yValue = (EditText) findViewById(R.id.greutate);

        t1 = findViewById(R.id.x);
        t2 = findViewById(R.id.y);
        lineChart=findViewById(R.id.idBarChart);

        reference = FirebaseDatabase.getInstance().getReference("chart");

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("chart");



        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode=FirebaseDatabase.getInstance();
                reference = rootNode.getReference("chart");

                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String id=reference.push().getKey();
                x=Integer.parseInt(xValue.getText().toString());
                y=Integer.parseInt(yValue.getText().toString());


                Input input=new Input(currentuser,x,y);

                reference.child(id).setValue(input);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                myList=new ArrayList<>();
                ArrayList<Entry>data= new ArrayList<>();

                for(DataSnapshot snapshot:datasnapshot.getChildren()){

                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String user=snapshot.child("user").getValue().toString();
                    String xa = snapshot.child("x").getValue().toString();
                    String ya = snapshot.child("y").getValue().toString();
                    // System.out.println("x este " + xa + " si y este " + ya);
                    Integer x = Integer.parseInt(xa);
                    Integer y = Integer.parseInt(ya);

                    float ht;
                    int wt;
                    Utils.init(getApplicationContext());
                    ht =  (int)x * ((float) getApplicationContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
                    wt = (int) (y * ((float) getApplicationContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    if(currentuser.equals(user)) {
                        data.add(new Entry(ht, wt));
                    }





                    // Input input = new Input(currentuser, x, y);
                    // System.out.println(input);

                    //myList.add(input);



                }

                //System.out.println(myList);
                //Utils.init(getApplicationContext());

                showChart(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }




    private void showChart(List<Entry> data) {
        Collections.sort(data, new EntryXComparator());
        final LineDataSet set = new LineDataSet(data, "Accuracy");
        Paint paint = lineChart.getRenderer().getPaintRender();
        paint.setShader(new LinearGradient(0, 0, 0, 40, Color.GREEN, Color.RED, Shader.TileMode.REPEAT));




        lineDataSet.setLabel("Evolutia pe saptamani a greutatii");
        lineDataSets.clear();
        lineDataSet.setValues(data);
        Utils.init(getApplicationContext());
        lineDataSets.add(lineDataSet);
        lineData=new LineData(lineDataSets);
        lineChart.clear();
        //lineChart.setData(lineData);
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3F);
        set.setDrawCircleHole(false);
        set.setDrawCircles(false);
        set.setHighlightEnabled(false);
        set.setDrawFilled(true);
        final LineData group = new LineData(set);
        group.setDrawValues(false);

        lineChart.setData(group);
        lineChart.invalidate();
    }

}