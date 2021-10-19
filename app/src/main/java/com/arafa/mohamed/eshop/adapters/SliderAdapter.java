package com.arafa.mohamed.eshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.model.OnBoardingItem;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {
   ArrayList <OnBoardingItem> items ;
    Context context;

    public SliderAdapter( Context context,ArrayList <OnBoardingItem> items) {
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public SliderAdapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sliding_layout, parent, false);
        return new SliderAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  SliderAdapter.MyViewHolder holder, int position) {
        holder.imgSlide.setImageResource(items.get(position).getImage());
        holder.tvHeading.setText(items.get(position).getHeading());
        holder.tvDescription.setText(items.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvHeading,tvDescription;
        AppCompatImageView imgSlide;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeading=itemView.findViewById(R.id.heading);
            tvDescription=itemView.findViewById(R.id.description);
            imgSlide=itemView.findViewById(R.id.slider_img);
        }
    }
}
