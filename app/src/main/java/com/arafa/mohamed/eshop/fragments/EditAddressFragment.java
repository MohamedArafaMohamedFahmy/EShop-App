package com.arafa.mohamed.eshop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAddressFragment extends Fragment {
    TextInputEditText etUserName,etCity,etAddress,etMobileNumber,etNote;
    AppCompatButton btSave;
    String userName,city,address,mobileNumber,note,userId;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    AddressModel addressModel;


    public EditAddressFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootEditAddress=inflater.inflate(R.layout.fragment_edit_address, container, false);
        etUserName = rootEditAddress.findViewById(R.id.editText_order_name);
        etCity = rootEditAddress.findViewById(R.id.editText_city);
        etAddress = rootEditAddress.findViewById(R.id.editText_address);
        etMobileNumber = rootEditAddress.findViewById(R.id.editText_phone_number);
        etNote = rootEditAddress.findViewById(R.id.editText_note);
        btSave = rootEditAddress.findViewById(R.id.button_save);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();



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
                        public void onComplete(@NonNull  Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(), "Save Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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


        return rootEditAddress;
    }


}