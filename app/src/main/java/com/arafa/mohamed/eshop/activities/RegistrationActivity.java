package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    AppCompatTextView btSignIn,tvLoading,textErrorPassword,textErrorConfirm;
    AppCompatButton btSignUp;
    AppCompatEditText etUserName,etEmailAddress,etPassword,etConfirmPassword;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userName,emailAddress,password,confirmPassword,userId;
    UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btSignUp=findViewById(R.id.button_signUp);
        btSignIn=findViewById(R.id.text_sign_in);
        etUserName=findViewById(R.id.editText_userName);
        etEmailAddress=findViewById(R.id.editText_email);
        etPassword=findViewById(R.id.editText_password);
        etConfirmPassword=findViewById(R.id.editText_confirm_password);
        textErrorPassword = findViewById(R.id.text_error_password);
        textErrorConfirm = findViewById(R.id.text_error_confirm);
                
        progressBar=findViewById(R.id.progress_bar);
        tvLoading=findViewById(R.id.text_loading);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        signIn();
        signUp();
    }

    public void signUp(){
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=etUserName.getText().toString();
                emailAddress=etEmailAddress.getText().toString();
                password=etPassword.getText().toString();
                confirmPassword=etConfirmPassword.getText().toString();


                if(!userName.isEmpty() && !emailAddress.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && password.length() >= 6 && confirmPassword.equals(password)){
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    etConfirmPassword.setCursorVisible(false);
                    closeKeyboard();
                    textErrorPassword.setVisibility(View.GONE);
                    textErrorConfirm.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    tvLoading.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(emailAddress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull  Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userInformation=new UserInformation(userName,emailAddress);
                                userId=firebaseAuth.getCurrentUser().getUid();
                                databaseReference.child("Users").child(userId).child("YourData").setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            tvLoading.setVisibility(View.GONE);
                                            Toast.makeText(RegistrationActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                            finish();
                                        }
                                        
                                    }
                                });
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                tvLoading.setVisibility(View.GONE);
                                etPassword.setCursorVisible(true);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(RegistrationActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
                if(userName.isEmpty()){
                    etUserName.setError("Please enter user name");
                }

                 if(emailAddress.isEmpty()){
                    etEmailAddress.setError("Please enter email address");
                }
                 if(password.isEmpty()){
                    textErrorPassword.setVisibility(View.VISIBLE);
                    textErrorPassword.setText("Please enter password");
                }
                 if (password.length() < 6)
                {
                    textErrorPassword.setVisibility(View.VISIBLE);
                    textErrorPassword.setText("Password too short, Password must be at least 6 characters");
                }

                 if(confirmPassword.isEmpty()){
                    textErrorConfirm.setVisibility(View.VISIBLE);
                    textErrorConfirm.setText("Please enter confirm password");
                }
                 if(!confirmPassword.equals(password)){
                    textErrorConfirm.setVisibility(View.VISIBLE);
                    textErrorConfirm.setText("The confirm password not match with password");
                }
            }
        });
    }

    public void signIn(){
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
            finish();
        }
    }

    public void updateUI() {
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}