package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressActivity extends AppCompatActivity {
    TextInputEditText etUserName,etCity,etAddress,etMobileNumber,etNote;
    AppCompatButton btSave;
    String userName,city,address,mobileNumber,note,userId;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    AddressModel addressModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        etUserName = findViewById(R.id.editText_order_name);
        etCity = findViewById(R.id.editText_city);
        etAddress = findViewById(R.id.editText_address);
        etMobileNumber = findViewById(R.id.editText_phone_number);
        etNote = findViewById(R.id.editText_note);
        btSave = findViewById(R.id.button_save);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        toolbar=findViewById(R.id.toolbar_address);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = etUserName.getText().toString();
                city = etCity.getText().toString();
                address = etAddress.getText().toString();
                mobileNumber = etMobileNumber.getText().toString();
                note = etNote.getText().toString();
                userId = firebaseAuth.getCurrentUser().getUid();

                if(!userName.isEmpty() && !city.isEmpty() && !address.isEmpty() && !mobileNumber.isEmpty() ){
                    addressModel = new AddressModel(userName,city,address,mobileNumber,note);

                    databaseReference.child("Address").child(userId).setValue(addressModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddressActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AddressActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else if(userName.isEmpty()){
                    etUserName.setError("Please enter user name");
                }
                else if(city.isEmpty()){
                    etCity.setError("Please enter City");
                }
                else if(address.isEmpty()){
                    etCity.setError("Please enter address");
                }
                else {
                    etMobileNumber.setError("Please enter mobile number");
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