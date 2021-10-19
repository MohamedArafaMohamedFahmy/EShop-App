package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.arafa.mohamed.eshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    AppCompatTextView btForgotPassword, btSignUp,textError,tvLoading;
    AppCompatButton btSignIn;
    TextInputEditText etEmailAddress,etPassword;
    TextInputLayout showError;
    String emailAddress,password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        etEmailAddress=findViewById(R.id.editText_email);
        etPassword=findViewById(R.id.editText_password);
        showError=findViewById(R.id.password_layout);
        btForgotPassword=findViewById(R.id.text_forgot_password);
        btSignIn=findViewById(R.id.button_signIn);
        btSignUp=findViewById(R.id.text_sign_up);
        tvLoading=findViewById(R.id.text_loading);
        textError=findViewById(R.id.text_error);
        progressBar=findViewById(R.id.progress_bar);


        signIn();
        signUp();
        forgotPassword();

    }

    public void signUp(){
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
    public void signIn(){

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress=etEmailAddress.getText().toString();
                password=etPassword.getText().toString();

                if(!emailAddress.isEmpty() && !password.isEmpty() && password.length() >= 6){
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    etPassword.setCursorVisible(false);
                    closeKeyboard();
                    textError.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    tvLoading.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(emailAddress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                tvLoading.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                tvLoading.setVisibility(View.GONE);
                                etPassword.setCursorVisible(true);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(LoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                 if(emailAddress.isEmpty()){
                    etEmailAddress.setError("Please enter email address");
                }

                 if (password.length() < 6)
                {
                    textError.setVisibility(View.VISIBLE);
                    textError.setText("Password too short, Password must be at least 6 characters");
                }
                if (password.isEmpty()){
                    textError.setVisibility(View.VISIBLE);
                    textError.setText("Please enter password");
                }

            }
        });
    }
    public void forgotPassword(){
        btForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}