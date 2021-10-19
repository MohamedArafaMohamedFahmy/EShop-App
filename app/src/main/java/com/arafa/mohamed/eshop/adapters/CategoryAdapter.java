package com.arafa.mohamed.eshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.activities.DetailedActivity;
import com.arafa.mohamed.eshop.activities.ShowAllActivity;
import com.arafa.mohamed.eshop.model.CategoryModel;
import com.arafa.mohamed.eshop.model.ImagesData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<CategoryModel> downloadData;
    DatabaseReference databaseReference;


    public CategoryAdapter(Context context, ArrayList<CategoryModel> downloadData) {
        this.context = context;
        this.downloadData = downloadData;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_list, parent, false);
        return new CategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Picasso.get().load(Uri.parse(downloadData.get(position).getImgURl())).into(holder.imgCategory);
        holder.tvNameCategory.setText(downloadData.get(position).getCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetailed=new Intent(context, ShowAllActivity.class);
                intentDetailed.putExtra("type",downloadData.get(position).getCategoryName());
                intentDetailed.putExtra("nameActivity","Products Of Category");
                context.startActivity(intentDetailed);
            }
        });
    }

    @Override
    public int getItemCount() {

        return downloadData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvNameCategory;
        AppCompatImageView imgCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory=itemView.findViewById(R.id.cat_name);
            imgCategory=itemView.findViewById(R.id.cat_img);
        }
    }
}

