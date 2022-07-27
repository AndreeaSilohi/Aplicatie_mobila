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

public class MyAdapter extends RecyclerView.Adapter <FoodViewHolder>{
    private Context mContext;
    private List<RetetaPentruAdmin> myFoodList;


    private int lastPosition=-1;

    public MyAdapter(Context mContext, List<RetetaPentruAdmin> myFoodList) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item,parent,false);

        return new FoodViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Glide.with(mContext)
                .load(myFoodList.get(position).getItemImage())
                .into(holder.imageView);

        //holder.imageView.setImageResource(myFoodList.get(position).getItemImage());
         holder.mTitle.setText(myFoodList.get(position).getItemName());
        // holder.mDescription.setText(myFoodList.get(position).getItemDescription());

         holder.mCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(mContext,DetailActivity.class);
                 intent.putExtra("Image",myFoodList.get(holder.getAbsoluteAdapterPosition()).getItemImage());
                 intent.putExtra("Description",myFoodList.get(holder.getAbsoluteAdapterPosition()).getItemDescription());
                 intent.putExtra("keyValueR",myFoodList.get(holder.getAbsoluteAdapterPosition()).getKeyR());
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
        return myFoodList.size();
    }

    public void filteredList(ArrayList<RetetaPentruAdmin> filterList) {

        myFoodList=filterList;
        notifyDataSetChanged();
    }
}

class FoodViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView mTitle;
    TextView mDescription;

    CardView mCardView;

    public FoodViewHolder(View  itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.ivImage);
        mTitle=itemView.findViewById(R.id.tvTitle);
       // mDescription=itemView.findViewById(R.id.tvDescription);

        mCardView=itemView.findViewById(R.id.myCardView);
    }
}
