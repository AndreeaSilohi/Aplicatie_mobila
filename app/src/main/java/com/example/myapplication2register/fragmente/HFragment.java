package com.example.myapplication2register.fragmente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication2register.optiuniMeniu.Persoana;
import com.example.myapplication2register.R;
//import com.example.myapplication2register.util.Expense;

import java.util.ArrayList;


public class HFragment extends Fragment {
    private static final String EXPENSES_KEY="EXPENSES_KEY";
    private ArrayList<Persoana> expenses;

    private ListView lv;

    public HFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static HFragment newInstance(ArrayList<Persoana>expenses) {
        HFragment fragment = new HFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXPENSES_KEY,expenses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expenses=getArguments().getParcelableArrayList(EXPENSES_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment, asociaza xml-ul cu fragmentul
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        return view;
    }



    private void initComponents(View view) {
        lv = view.findViewById(R.id.home_lv_dates);
        if (getContext() != null) {
            //getContext este referinta catre activitarea de care apartine fragmentul
            ArrayAdapter<Persoana> adapter = new ArrayAdapter<>(getContext().getApplicationContext(),
                    android.R.layout.simple_list_item_1,expenses);
            lv.setAdapter(adapter);

        }
    }

    public void notifyAdapter() {
        ArrayAdapter<Persoana> adapter = (ArrayAdapter<Persoana>) lv.getAdapter();
        adapter.notifyDataSetChanged();
    }
}