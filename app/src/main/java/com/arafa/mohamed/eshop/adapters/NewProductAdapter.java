package com.arafa.mohamed.eshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.activities.DetailedActivity;
import com.arafa.mohamed.eshop.model.NewProductsModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewHolder> {
    Context context;
    ArrayList<NewProductsModel> downloadData;
    DatabaseReference databaseReference;


    public NewProductAdapter(Context context, ArrayList<NewProductsModel> downloadData) {
        this.context = context;
        this.downloadData = downloadData;
    }

    @NonNull
    @Override
    public NewProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.new_products, parent, false);
        return new NewProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductAdapter.MyViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Picasso.get().load(Uri.parse(downloadData.get(position).getImgUrl())).into(holder.imgProduct);
        holder.tvProductName.setText(downloadData.get(position).getNameProduct());
        holder.tvPrice.setText(downloadData.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetailed=new Intent(context, DetailedActivity.class);
                intentDetailed.putExtra("detailed",downloadData.get(position));
                context.startActivity(intentDetailed);
            }
        });
    }


    @Override
    public int getItemCount() {

        return downloadData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvProductName,tvPrice;
        AppCompatImageView imgProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName=itemView.findViewById(R.id.new_product_name);
            tvPrice=itemView.findViewById(R.id.new_price);
            imgProduct=itemView.findViewById(R.id.new_img);
        }
    }

}
