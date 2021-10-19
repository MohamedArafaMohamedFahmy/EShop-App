package com.arafa.mohamed.eshop.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.model.ImagesData;
import com.arafa.mohamed.eshop.model.NewProductsModel;
import com.arafa.mohamed.eshop.model.PopularProductsModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UploadImageActivity extends AppCompatActivity {
    public Uri imgUri;
    EditText etNameCategory,etDescription,etRating,etPrice,etCategory;
    AppCompatImageView imgUploadImage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ImagesData imagesData,uploadCategory;
    NewProductsModel newProductsModel;
    PopularProductsModel popularProductsModel;
    String downloadUrl,uploadId,nameImage,description,price,rating,category;
    AppCompatButton btUpload,btUploadCategory,btUploadNewProducts,btUploadPopularProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        imgUploadImage=findViewById(R.id.upload_image);
        btUpload=findViewById(R.id.button_upload);
        btUploadCategory=findViewById(R.id.button_upload_category);
        btUploadNewProducts=findViewById(R.id.button_upload_new_products);
        btUploadPopularProducts=findViewById(R.id.button_upload_popular_products);
        etNameCategory=findViewById(R.id.edit_text_name);
        etDescription=findViewById(R.id.edit_text_description);
        etPrice=findViewById(R.id.edit_text_price);
        etRating=findViewById(R.id.edit_text_rating);
        etCategory=findViewById(R.id.edit_text_category);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        btUploadPopularProducts.setOnClickListener(v -> {
            nameImage=etNameCategory.getText().toString();
            description=etDescription.getText().toString();
            price=etPrice.getText().toString();
            rating=etRating.getText().toString();
            category=etCategory.getText().toString();
            if (imgUri != null && !nameImage.isEmpty() && !description.isEmpty() && !price.isEmpty() && !rating.isEmpty() && !category.isEmpty() ) {
                StorageReference filePath = storageReference.child("UploadImages").child("popular products").child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
                final UploadTask uploadTask = filePath.putFile(imgUri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                            if (!task.isSuccessful()) {
                                Toast.makeText(UploadImageActivity.this, ""+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            downloadUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                downloadUrl = task.getResult().toString();
                                uploadId = databaseReference.push().getKey();
                                popularProductsModel = new PopularProductsModel(description,nameImage,downloadUrl,price,rating, uploadId,category);
                                databaseReference.child("UploadImages").child("PopularProducts").child(uploadId).setValue(popularProductsModel);
                            }

                        });
                    }
                });
            }
        });

        btUploadNewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 nameImage=etNameCategory.getText().toString();
                 description=etDescription.getText().toString();
                 price=etPrice.getText().toString();
                 rating=etRating.getText().toString();
                 category=etCategory.getText().toString();
                if (imgUri != null && !nameImage.isEmpty() && !description.isEmpty() && !price.isEmpty() && !rating.isEmpty() && !category.isEmpty()) {
                    StorageReference filePath = storageReference.child("UploadImages").child("new products").child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
                    final UploadTask uploadTask = filePath.putFile(imgUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    downloadUrl = filePath.getDownloadUrl().toString();
                                    return filePath.getDownloadUrl();
                                }

                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        downloadUrl = task.getResult().toString();
                                        uploadId = databaseReference.push().getKey();
                                        newProductsModel = new NewProductsModel(description,nameImage,downloadUrl,price,rating, uploadId,category);
                                        databaseReference.child("UploadImages").child("NewProducts").child(uploadId).setValue(newProductsModel);
                                        databaseReference.child("UploadImages").child("PopularProducts").child(uploadId).setValue(newProductsModel);
                                    }

                                }
                            });
                        }
                    });
                }
            }
        });

        btUploadCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameImage=etNameCategory.getText().toString();
                if (imgUri != null && !nameImage.isEmpty() ) {
                    StorageReference filePath = storageReference.child("UploadImages").child("category").child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
                    final UploadTask uploadTask = filePath.putFile(imgUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    downloadUrl = filePath.getDownloadUrl().toString();
                                    return filePath.getDownloadUrl();
                                }

                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        downloadUrl = task.getResult().toString();
                                        uploadId = databaseReference.push().getKey();
                                        uploadCategory = new ImagesData(downloadUrl,nameImage, uploadId);
                                        databaseReference.child("UploadImages").child("Category").child(uploadId).setValue(uploadCategory);
                                    }

                                }
                            });
                        }
                    });
                }
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imgUri != null  ) {
                    StorageReference filePath = storageReference.child("UploadImages").child("banner").child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
                    final UploadTask uploadTask = filePath.putFile(imgUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    downloadUrl = filePath.getDownloadUrl().toString();
                                    return filePath.getDownloadUrl();
                                }

                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        downloadUrl = task.getResult().toString();
                                        uploadId = databaseReference.push().getKey();
                                        imagesData = new ImagesData(downloadUrl, uploadId);
                                        databaseReference.child("UploadImages").child("Banners").child(uploadId).setValue(imagesData);
                                    }

                                }
                            });
                        }
                    });
                }
            }
        });



        imgUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


    }


    public void imageChooser() {
        Intent intentImage = new Intent();
        intentImage.setType("image/*");
        intentImage.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intentImage);

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            imgUri = data.getData();
                            Picasso.get().load(imgUri).into(imgUploadImage);
                        }
                    }
                }
            });


    public String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}