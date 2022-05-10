package com.example.myapplication2register.activitati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication2register.optiuniMeniu.Persoana;
import com.example.myapplication2register.R;

public class MainActivity5 extends AppCompatActivity {
    public static final String NEW_EXPENSE_KEY = "new_expense_key";


    private EditText nume;
    private EditText greutate;
    private EditText inaltime;
    private Button button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initComponets();
        //preluare intent cu care s-a deschis formularul de introducere de date
        intent = getIntent();
    }

    private void initComponets() {
        nume = findViewById(R.id.name);
        greutate = findViewById(R.id.weight);
        inaltime = findViewById(R.id.inaltime);
        button = findViewById(R.id.btn_new_save);
        // addCategoryAdapter();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Persoana expense = createExpenseFromView();
                intent.putExtra(NEW_EXPENSE_KEY, expense);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

//    private boolean validate() {
//
//        //validare pentru campul suma
//        if (greutate.getText() == null || greutate.getText().toString().trim().length() == 0) {
//            Toast.makeText(getApplicationContext(),
//                    R.string.invalid_amount_field_error,
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        //spinnerul Category nu are nevoie de validari deoarece acesta are tot timpul prima valoare selectata
//        //campul descriere este optional in contextul aplicatiei prin urmare nu avem nevoie de validare
//        return true;
//    }

    //construiere obiect Expense cu informatiile populate in componentele vizuale
    private Persoana createExpenseFromView() {

        String num = nume.getText().toString();
        Float inal = Float.parseFloat(inaltime.getText().toString());
        Float greut = Float.parseFloat(greutate.getText().toString());

        return new Persoana(num, inal, greut);
    }

}