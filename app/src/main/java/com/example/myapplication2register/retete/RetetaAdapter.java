package com.example.myapplication2register.retete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication2register.retete.Reteta;

import java.util.List;

public class RetetaAdapter extends ArrayAdapter<Reteta> {

    private Context context;
    private int resource;
    private List<Reteta> retetaList;
    private LayoutInflater inflater;

    public RetetaAdapter(@NonNull Context context, int resource, @NonNull List<Reteta> objects,
                       LayoutInflater inflater) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.retetaList=objects;
        this.inflater=inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=inflater.inflate(resource,parent,false);
       Reteta r=retetaList.get(position);
        if(r==null){
            return view;
        }
        //addSkinName(view,skinCare.getGel());
       // addSkinNameCrema(view,skinCare.getCrema());
        //addSkinNameSolutie(view,skinCare.getSolutie());
        return view;
    }





}
