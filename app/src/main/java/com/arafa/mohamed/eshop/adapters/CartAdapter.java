package com.arafa.mohamed.eshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.model.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    ArrayList<CartModel> downloadData;
    CartModel cartModel;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userId;
    int totalAmount = 0,retrieveTotalQuantity,totalPrice;
    private OnCartInfoListener onCartInfoListener;


    public CartAdapter(Context context, ArrayList<CartModel> downloadData) {
        this.context = context;
        this.downloadData = downloadData;

        try {
            this.onCartInfoListener = ((OnCartInfoListener)context);

        }catch (ClassCastException e){
            Toast.makeText(context, ""+ new ClassCastException(e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();
        Picasso.get().load(Uri.parse(downloadData.get(position).getImgUrl())).into(holder.imgCart);
        holder.tvProductName.setText(downloadData.get(position).getProductName());
        holder.tvProductPrice.setText(downloadData.get(position).getTotalPrice());
        holder.tvQuantity.setText(downloadData.get(position).getTotalQuantity());
        totalAmount =totalAmount + Integer.parseInt(downloadData.get(position).getTotalPrice());
        retrieveTotalQuantity = Integer.parseInt(downloadData.get(position).getTotalQuantity());
        Intent intentCart=new Intent();
        intentCart.putExtra("totalAmount",String.valueOf(totalAmount));
        onCartInfoListener.onCartInfoListener(intentCart);

        holder.btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadData.get(position).setTotalQuantity(String.valueOf(Integer.parseInt(downloadData.get(position).getTotalQuantity())+1));
                holder.tvQuantity.setText(String.valueOf(downloadData.get(position).getTotalQuantity()));

                    totalPrice = Integer.parseInt(downloadData.get(position).getProductPrice()) * Integer.parseInt(downloadData.get(position).getTotalQuantity());
                    downloadData.get(position).setTotalPrice(String.valueOf(totalPrice));
                    holder.tvProductPrice.setText(downloadData.get(position).getTotalPrice());
                databaseReference.child("AddToCart").child(userId).child(downloadData.get(position).getProductId()).child("totalPrice").setValue(downloadData.get(position).getTotalPrice());
                databaseReference.child("AddToCart").child(userId).child(downloadData.get(position).getProductId()).child("totalQuantity").setValue(downloadData.get(position).getTotalQuantity());

            }
        });

        holder.btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if( Integer.parseInt(downloadData.get(position).getTotalQuantity()) > 1) {
                    downloadData.get(position).setTotalQuantity(String.valueOf(Integer.parseInt(downloadData.get(position).getTotalQuantity())-1));
                    holder.tvQuantity.setText(String.valueOf(downloadData.get(position).getTotalQuantity()));

                    totalPrice = Integer.parseInt(downloadData.get(position).getProductPrice()) * Integer.parseInt(downloadData.get(position).getTotalQuantity());
                    downloadData.get(position).setTotalPrice(String.valueOf(totalPrice));
                    holder.tvProductPrice.setText(String.valueOf(totalPrice));
                    databaseReference.child("AddToCart").child(userId).child(downloadData.get(position).getProductId()).child("totalPrice").setValue(downloadData.get(position).getTotalPrice());
                    databaseReference.child("AddToCart").child(userId).child(downloadData.get(position).getProductId()).child("totalQuantity").setValue(downloadData.get(position).getTotalQuantity());

                }
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("AddToCart").child(userId).child(downloadData.get(position).getProductId())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {

        return downloadData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvProductName,tvProductPrice,tvQuantity;
        AppCompatImageView imgCart;
        ImageButton btDelete,btPlus,btMinus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.product_name_cart);
            tvProductPrice = itemView.findViewById(R.id.product_price_cart);
            tvQuantity = itemView.findViewById(R.id.text_quantity_cart);
            imgCart = itemView.findViewById(R.id.image_my_cart);
            btDelete = itemView.findViewById(R.id.img_delete);
            btPlus = itemView.findViewById(R.id.img_plus);
            btMinus = itemView.findViewById(R.id.img_minus);
        }
    }

    public interface OnCartInfoListener{
        public void onCartInfoListener(Intent intent);
    }
}
