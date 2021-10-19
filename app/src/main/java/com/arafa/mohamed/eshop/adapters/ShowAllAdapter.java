package com.arafa.mohamed.eshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.activities.DetailedActivity;
import com.arafa.mohamed.eshop.activities.ShowAllActivity;
import com.arafa.mohamed.eshop.model.PopularProductsModel;
import com.arafa.mohamed.eshop.model.ShowAllModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.MyViewHolder> {
    Context context;
    ArrayList<ShowAllModel> downloadData;
    DatabaseReference databaseReference;


    public ShowAllAdapter(Context context, ArrayList<ShowAllModel> downloadData) {
        this.context = context;
        this.downloadData = downloadData;
    }

    @NonNull
    @Override
    public ShowAllAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.show_all_item, parent, false);
        return new ShowAllAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdapter.MyViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Picasso.get().load(Uri.parse(downloadData.get(position).getImgUrl())).into(holder.imgItem);
        holder.tvProductName.setText(downloadData.get(position).getNameProduct());
        holder.tvPrice.setText(downloadData.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShow = new Intent(context, DetailedActivity.class);
                intentShow.putExtra("detailed", downloadData.get(position));
                context.startActivity(intentShow);
            }
        });
    }


    @Override
    public int getItemCount() {

        return downloadData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvProductName, tvPrice;
        AppCompatImageView imgItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_name);
            tvPrice = itemView.findViewById(R.id.item_cost);
            imgItem = itemView.findViewById(R.id.item_image);
        }
    }
}
