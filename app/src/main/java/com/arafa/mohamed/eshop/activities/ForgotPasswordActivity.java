package com.arafa.mohamed.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputEditText etEmailAddress;
    AppCompatTextView tvMessageReset,tvLoading;
    AppCompatButton btFindAccount;
    FirebaseAuth firebaseauth;
    String emailAddress;
    ProgressBar progressBar;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar=findViewById(R.id.toolbar_forgot_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        firebaseauth=FirebaseAuth.getInstance();
        etEmailAddress=findViewById(R.id.editText_email);
        btFindAccount=findViewById(R.id.button_findAccount);
        tvMessageReset=findViewById(R.id.text_message_reset);
        tvLoading=findViewById(R.id.text_loading);
        progressBar=findViewById(R.id.progress_bar);

        findAccount();

    }

    public void findAccount(){
        btFindAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress=etEmailAddress.getText().toString();

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                etEmailAddress.setCursorVisible(false);
                closeKeyboard();
                progressBar.setVisibility(View.VISIBLE);
                tvLoading.setVisibility(View.VISIBLE);
                if(!emailAddress.isEmpty()){

                    firebaseauth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                tvLoading.setVisibility(View.GONE);
                               tvMessageReset.setText("Reset your password, Please check "+emailAddress+" account");
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                tvLoading.setVisibility(View.GONE);
                                Toast.makeText(ForgotPasswordActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if (emailAddress.isEmpty()){
                    etEmailAddress.setError("Please enter email address");

                }
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

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