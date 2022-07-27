package com.example.myapplication2register.administrator;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication2register.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterProdus extends RecyclerView.Adapter <ProdViewHolder>{
    private Context mContext;
    private List<ProdusPentruAdmin> myProdList;

    private int lastPosition=-1;

    public MyAdapterProdus(Context mContext, List<ProdusPentruAdmin> myProdList) {
        this.mContext = mContext;
        this.myProdList = myProdList;
    }

    @NonNull
    @Override
    public ProdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item_produs,parent,false);

        return new ProdViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdViewHolder holder, int position) {
        Glide.with(mContext)
                .load(myProdList.get(position).getItemImage())
                .into(holder.imageView);

        //holder.imageView.setImageResource(myFoodList.get(position).getItemImage());
       holder.mTitle.setText(myProdList.get(position).getItemName());
      //  holder.mDescription.setText(myProdList.get(position).getItemDescription());


        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,DetailProdActivity.class);
                intent.putExtra("Image",myProdList.get(holder.getAbsoluteAdapterPosition()).getItemImage());
                intent.putExtra("Description",myProdList.get(holder.getAbsoluteAdapterPosition()).getItemDescription());
                intent.putExtra("keyValueP",myProdList.get(holder.getAbsoluteAdapterPosition()).getKeyP());
                mContext.startActivity(intent);
            }
        });

        setAnimation(holder.itemView,position);
    }


    public void setAnimation(View viewToAnimate,int position){
        if(position>lastPosition){
            ScaleAnimation animation= new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(1500);
            viewToAnimate.startAnimation(animation);

            lastPosition=position;
        }
    }
    @Override
    public int getItemCount() {
        return myProdList.size();
    }

    public void filteredList(ArrayList<ProdusPentruAdmin> filtList) {
        myProdList=filtList;
        notifyDataSetChanged();
    }
}

class ProdViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView mTitle;
    TextView mDescription;

    CardView mCardView;


    public ProdViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.ivImage2P);
        mTitle=itemView.findViewById(R.id.tvTitleP);
      //  mDescription=itemView.findViewById(R.id.tvDescriptionP);

        mCardView=itemView.findViewById(R.id.myCardViewP);
    }
}
