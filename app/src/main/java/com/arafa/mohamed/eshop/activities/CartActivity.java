package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.adapters.CartAdapter;
import com.arafa.mohamed.eshop.adapters.ShowAllAdapter;
import com.arafa.mohamed.eshop.model.CartModel;
import com.arafa.mohamed.eshop.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartInfoListener{
    AppCompatTextView tvNumberOfItems,tvSubTotal,tvShipping,tvTotal;
    ImageButton imgArrowBack;
    AppCompatButton btBuyNow;
    RecyclerView recyclerCart;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userId;
    ArrayList <CartModel> downloadAllCart;
    CartModel cartModel;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvNumberOfItems=findViewById(R.id.text_number_of_items);
        tvSubTotal=findViewById(R.id.text_result_sub_total);
        tvShipping=findViewById(R.id.text_result_shipping);
        tvTotal=findViewById(R.id.text_result_total);
        imgArrowBack=findViewById(R.id.icon_arrow_back);
        recyclerCart=findViewById(R.id.rec_items_cart);
        btBuyNow = findViewById(R.id.button_buy_now);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();
        downloadAllCart=new ArrayList<>();

        imgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i<downloadAllCart.size(); i++){
                    databaseReference.child("BuyNow").child(userId).child(downloadAllCart.get(i).getProductId()).setValue(downloadAllCart.get(i));
                }
                Toast.makeText(CartActivity.this, "Purchased successfully", Toast.LENGTH_SHORT).show();

            }
        });

        databaseReference.child("AddToCart").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                downloadAllCart.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    cartModel = postSnapshot.getValue(CartModel.class);
                    downloadAllCart.add(cartModel);
                }
                if (!downloadAllCart.isEmpty()) {
                    cartAdapter = new CartAdapter(CartActivity.this, downloadAllCart);
                    recyclerCart.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                    recyclerCart.setLayoutManager(new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL,false));
                    tvNumberOfItems.setText(String.valueOf(downloadAllCart.size()+" items"));
                }
                else{
                    Toast.makeText(CartActivity.this, "No items in My Cart", Toast.LENGTH_SHORT).show();
                    tvNumberOfItems.setText(String.valueOf(0+" items"));
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(CartActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onCartInfoListener(Intent intent) {
        tvSubTotal.setText(String.valueOf("$ "+intent.getStringExtra("totalAmount")));
        tvShipping.setText(String.valueOf("$ "+10));
        int total = Integer.parseInt(intent.getStringExtra("totalAmount"))+10;
        tvTotal.setText(String.valueOf("$ "+String.valueOf(total)));
    }
}