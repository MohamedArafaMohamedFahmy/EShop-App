package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.adapters.CartAdapter;
import com.arafa.mohamed.eshop.model.CartModel;
import com.arafa.mohamed.eshop.model.NewProductsModel;
import com.arafa.mohamed.eshop.model.PopularProductsModel;
import com.arafa.mohamed.eshop.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {
    AppCompatButton btAddToCart,btBuyNow;
    ImageButton btPlus,btMinus;
    AppCompatTextView tvProductName,tvRating,tvDescription,tvPrice,tvQuantity;
    AppCompatImageView imgDetailed;
    NewProductsModel newProductsModel;
    PopularProductsModel popularProductsModel;
    ShowAllModel showAllModel;
    CartModel cartModel;
    String userId,productId,priceProduct,imgUrl;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    int totalQuantity = 1, totalPrice;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar=findViewById(R.id.toolbar_detailed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        final Object objDetailed=getIntent().getSerializableExtra("detailed");

        if (objDetailed instanceof NewProductsModel){
            newProductsModel=(NewProductsModel) objDetailed;
        }
        else if (objDetailed instanceof PopularProductsModel){
            popularProductsModel=(PopularProductsModel) objDetailed;
        }
        else if(objDetailed instanceof ShowAllModel){
            showAllModel = (ShowAllModel) objDetailed;
        }
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        btAddToCart=findViewById(R.id.button_add_to_cart);
        btBuyNow=findViewById(R.id.button_buy_now);
        btPlus=findViewById(R.id.img_plus);
        btMinus=findViewById(R.id.img_minus);
        tvProductName=findViewById(R.id.product_name_detailed);
        tvRating=findViewById(R.id.text_rating);
        tvDescription=findViewById(R.id.description_detailed);
        tvPrice=findViewById(R.id.text_price);
        tvQuantity=findViewById(R.id.text_quantity);
        imgDetailed=findViewById(R.id.image_detailed_product);

         // New Product
        if(newProductsModel !=null){
            Picasso.get().load(Uri.parse(newProductsModel.getImgUrl())).into(imgDetailed);
            tvProductName.setText(newProductsModel.getNameProduct());
            tvRating.setText(newProductsModel.getRating());
            tvPrice.setText(newProductsModel.getPrice());
            tvDescription.setText(newProductsModel.getDescription());
            productId=newProductsModel.getIdImage();
            imgUrl=newProductsModel.getImgUrl();
            priceProduct=newProductsModel.getPrice();
            totalPrice = Integer.parseInt(newProductsModel.getPrice()) * totalQuantity;
        }
         // Popular Product
        if(popularProductsModel !=null){
            Picasso.get().load(Uri.parse(popularProductsModel.getImgUrl())).into(imgDetailed);
            tvProductName.setText(popularProductsModel.getNameProduct());
            tvRating.setText(popularProductsModel.getRating());
            tvPrice.setText(popularProductsModel.getPrice());
            tvDescription.setText(popularProductsModel.getDescription());
            productId=popularProductsModel.getIdImage();
            imgUrl=popularProductsModel.getImgUrl();
            priceProduct=popularProductsModel.getPrice();
            totalPrice = Integer.parseInt(popularProductsModel.getPrice()) * totalQuantity;
        }
         // Show All Product
        if(showAllModel !=null){
            Picasso.get().load(Uri.parse(showAllModel.getImgUrl())).into(imgDetailed);
            tvProductName.setText(showAllModel.getNameProduct());
            tvRating.setText(showAllModel.getRating());
            tvPrice.setText(showAllModel.getPrice());
            tvDescription.setText(showAllModel.getDescription());
            productId=showAllModel.getIdImage();
            imgUrl=showAllModel.getImgUrl();
            priceProduct=showAllModel.getPrice();
            totalPrice = Integer.parseInt(showAllModel.getPrice()) * totalQuantity;
        }

        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalQuantity++;
                tvQuantity.setText(String.valueOf(totalQuantity));

                if(newProductsModel != null){
                    totalPrice = Integer.parseInt(newProductsModel.getPrice()) * totalQuantity;
                    tvPrice.setText(String.valueOf(totalPrice));
                }
                else if(popularProductsModel != null){
                    totalPrice = Integer.parseInt(popularProductsModel.getPrice()) * totalQuantity;
                    tvPrice.setText(String.valueOf(totalPrice));
                }
                else if(showAllModel != null){
                    totalPrice = Integer.parseInt(showAllModel.getPrice()) * totalQuantity;
                    tvPrice.setText(String.valueOf(totalPrice));
                }
            }

        });

        btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity >1){
                    totalQuantity--;
                    tvQuantity.setText(String.valueOf(totalQuantity));
                    if(newProductsModel != null){
                        totalPrice = Integer.parseInt(newProductsModel.getPrice()) * totalQuantity;
                        tvPrice.setText(String.valueOf(totalPrice));
                    }
                    else if(popularProductsModel != null){
                        totalPrice = Integer.parseInt(popularProductsModel.getPrice()) * totalQuantity;
                        tvPrice.setText(String.valueOf(totalPrice));
                    }
                    else if(showAllModel != null){
                        totalPrice = Integer.parseInt(showAllModel.getPrice()) * totalQuantity;
                        tvPrice.setText(String.valueOf(totalPrice));
                    }
                }
            }
        });

        btAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addToCart();
            }
        });
    }

    public void addToCart(){

        userId=firebaseAuth.getCurrentUser().getUid();
        cartModel=new CartModel(tvProductName.getText().toString(),imgUrl,priceProduct,productId,String.valueOf(totalPrice),String.valueOf(totalQuantity));

        databaseReference.child("AddToCart").child(userId).child(productId).setValue(cartModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DetailedActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

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