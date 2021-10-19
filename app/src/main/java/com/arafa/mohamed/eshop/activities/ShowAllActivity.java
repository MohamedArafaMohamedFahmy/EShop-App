package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.adapters.CategoryAdapter;
import com.arafa.mohamed.eshop.adapters.ShowAllAdapter;
import com.arafa.mohamed.eshop.model.CategoryModel;
import com.arafa.mohamed.eshop.model.PopularProductsModel;
import com.arafa.mohamed.eshop.model.ShowAllModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView recyclerItems;
    ShowAllAdapter showAllAdapter;
    ShowAllModel showAllModel;
    ArrayList <ShowAllModel> downloadAllItems;
    DatabaseReference databaseReference;
    String type,determineData,nameActivity;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        recyclerItems = findViewById(R.id.recycler_show_all_item);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        downloadAllItems = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar_show_all);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        type = getIntent().getStringExtra("type");
        nameActivity = getIntent().getStringExtra("nameActivity");

        if (nameActivity.equals("All New Products")) {
            getSupportActionBar().setTitle("All New Products");
            // for New Products
            databaseReference.child("UploadImages").child("NewProducts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    downloadAllItems.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        showAllModel = postSnapshot.getValue(ShowAllModel.class);
                        downloadAllItems.add(showAllModel);

                    }
                    if (!downloadAllItems.isEmpty()) {
                        showAllAdapter = new ShowAllAdapter(ShowAllActivity.this, downloadAllItems);
                        recyclerItems.setAdapter(showAllAdapter);
                        recyclerItems.setLayoutManager(new GridLayoutManager(ShowAllActivity.this, 2));
                        /*relativeLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);*/
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ShowAllActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if(nameActivity.equals("All Popular Products")) {
            getSupportActionBar().setTitle("All Popular Products");
            // for Popular Products
            databaseReference.child("UploadImages").child("PopularProducts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    downloadAllItems.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        showAllModel = postSnapshot.getValue(ShowAllModel.class);
                        downloadAllItems.add(showAllModel);

                    }
                    if (!downloadAllItems.isEmpty()) {
                        showAllAdapter = new ShowAllAdapter(ShowAllActivity.this, downloadAllItems);
                        recyclerItems.setAdapter(showAllAdapter);
                        recyclerItems.setLayoutManager(new GridLayoutManager(ShowAllActivity.this, 2));
                        /*relativeLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);*/
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ShowAllActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(nameActivity.equals("Products Of Category")){
            getSupportActionBar().setTitle("Products Of Category");
            // for Category
            databaseReference.child("UploadImages").child("PopularProducts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    downloadAllItems.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        showAllModel = postSnapshot.getValue(ShowAllModel.class);
                        determineData = showAllModel.getCategory();
                        if (type.toLowerCase().trim().equals(determineData.toLowerCase().trim())) {
                            downloadAllItems.add(showAllModel);
                        }
                    }
                    if (!downloadAllItems.isEmpty()) {
                        showAllAdapter = new ShowAllAdapter(ShowAllActivity.this, downloadAllItems);
                        recyclerItems.setAdapter(showAllAdapter);
                        recyclerItems.setLayoutManager(new GridLayoutManager(ShowAllActivity.this, 2));
                        /*relativeLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);*/
                    } else {
                        Toast.makeText(ShowAllActivity.this, "No products for this is category", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ShowAllActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}