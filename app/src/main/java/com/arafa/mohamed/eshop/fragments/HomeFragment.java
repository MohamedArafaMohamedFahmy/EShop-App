package com.arafa.mohamed.eshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.activities.ShowAllActivity;
import com.arafa.mohamed.eshop.adapters.CategoryAdapter;
import com.arafa.mohamed.eshop.adapters.NewProductAdapter;
import com.arafa.mohamed.eshop.adapters.PopularProductsAdapter;
import com.arafa.mohamed.eshop.model.CategoryModel;
import com.arafa.mohamed.eshop.model.NewProductsModel;
import com.arafa.mohamed.eshop.model.PopularProductsModel;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageSlider imageBanners;
    AppCompatButton tvNewProductSee,tvPopularProductSee;
    AppCompatTextView tvLoading;
    List<SlideModel> downloadUrlBanner;
    DatabaseReference databaseReference;
    RecyclerView recyclerCategory,recyclerNewProducts,recyclerPopular;
    ArrayList<CategoryModel> downloadUrlCategory;
    ArrayList<NewProductsModel> downloadUrlProducts;
    ArrayList<PopularProductsModel> downloadUrlPopular;
    CategoryModel categoryModel;
    NewProductsModel newProductsModel;
    PopularProductsModel popularProductsModel;
    CategoryAdapter categoryAdapter;
    NewProductAdapter newProductAdapter;
    PopularProductsAdapter popularProductsAdapter;
    ProgressBar progressBar;
    LinearLayout linearLayout;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        downloadUrlBanner=new ArrayList<>();
        downloadUrlCategory=new ArrayList<>();
        downloadUrlProducts=new ArrayList<>();
        downloadUrlPopular=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        View root= inflater.inflate(R.layout.fragment_home, container, false);
        imageBanners=root.findViewById(R.id.image_slider);
        tvNewProductSee=root.findViewById(R.id.new_products_see_all);
        tvPopularProductSee=root.findViewById(R.id.popular_see_all);
        recyclerCategory=root.findViewById(R.id.rec_category);
        recyclerNewProducts=root.findViewById(R.id.new_product_rec);
        recyclerPopular=root.findViewById(R.id.popular_rec);
        tvLoading=root.findViewById(R.id.text_loading);
        progressBar=root.findViewById(R.id.progress_bar);
        linearLayout=root.findViewById(R.id.home_layout);

        progressBar.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.VISIBLE);

        tvNewProductSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewProducts=new Intent(getActivity(),ShowAllActivity.class);
                intentNewProducts.putExtra("nameActivity","All New Products");
                startActivity(intentNewProducts);
            }
        });

        tvPopularProductSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPopularProducts=new Intent(getActivity(),ShowAllActivity.class);
                intentPopularProducts.putExtra("nameActivity","All Popular Products");
                startActivity(intentPopularProducts);
            }
        });

        databaseReference.child("UploadImages").child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                downloadUrlCategory.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    categoryModel =postSnapshot.getValue(CategoryModel.class);
                    downloadUrlCategory.add(categoryModel);

                }
                if (!downloadUrlCategory.isEmpty()){
                    categoryAdapter = new CategoryAdapter(getActivity(),downloadUrlCategory);
                    recyclerCategory.setAdapter(categoryAdapter);
                    recyclerCategory.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        databaseReference.child("UploadImages").child("NewProducts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                downloadUrlProducts.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    newProductsModel =postSnapshot.getValue(NewProductsModel.class);
                    downloadUrlProducts.add(newProductsModel);

                }
                if (!downloadUrlProducts.isEmpty()){
                    newProductAdapter = new NewProductAdapter(getActivity(),downloadUrlProducts);
                    recyclerNewProducts.setAdapter(newProductAdapter);
                    recyclerNewProducts.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        databaseReference.child("UploadImages").child("PopularProducts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                downloadUrlPopular.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    popularProductsModel =postSnapshot.getValue(PopularProductsModel.class);
                    downloadUrlPopular.add(popularProductsModel);

                }
                if (!downloadUrlPopular.isEmpty()){
                    popularProductsAdapter = new PopularProductsAdapter(getActivity(),downloadUrlPopular);
                    recyclerPopular.setAdapter(popularProductsAdapter);
                    recyclerPopular.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    progressBar.setVisibility(View.GONE);
                    tvLoading.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        databaseReference.child("UploadImages").child("Banners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                downloadUrlBanner.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    downloadUrlBanner.add(new SlideModel(postSnapshot.child("imgURl").getValue().toString(), ScaleTypes.FIT));
                    imageBanners.setImageList(downloadUrlBanner,ScaleTypes.FIT);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return root;
    }
}