package com.arafa.mohamed.eshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.adapters.SliderAdapter;
import com.arafa.mohamed.eshop.model.OnBoardingItem;
import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    ArrayList <OnBoardingItem> items;
    AppCompatButton btGetStart,btNext;
    Animation animation;
    CardView cardButton;
    MaterialRippleLayout materialRippleLayout;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        viewPager=findViewById(R.id.slider);
        dotsLayout=findViewById(R.id.dots);
        btGetStart=findViewById(R.id.get_started_btn);
        btNext = findViewById(R.id.button_next);
        cardButton=findViewById(R.id.card_button_get_start);
        materialRippleLayout=findViewById(R.id.material_ripple);
        items = new ArrayList<>();

        if(isOpenAlready()){
            startActivity(new Intent(OnBoardingActivity.this,RegistrationActivity.class));
            finish();
        }
        else{
            editor = getSharedPreferences("slide", MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.apply();
        }


        setCurrentOnBoardingIndicator(0);
        setUpOnBoardingItems();
        viewPager.setAdapter(sliderAdapter);
        setUpOnBoardingIndicators();


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentOnBoardingIndicator(position);
                if (position == 0){
                    btGetStart.setVisibility(View.GONE);
                    cardButton.setVisibility(View.GONE);
                    materialRippleLayout.setVisibility(View.GONE);
                }
                else if(position == 1){
                    btGetStart.setVisibility(View.GONE);
                    cardButton.setVisibility(View.GONE);
                    materialRippleLayout.setVisibility(View.GONE);
                }
                else{
                    animation= AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                    btGetStart.setAnimation(animation);
                    cardButton.setAnimation(animation);
                    materialRippleLayout.setAnimation(animation);
                    materialRippleLayout.setVisibility(View.VISIBLE);
                    cardButton.setVisibility(View.VISIBLE);
                    btGetStart.setVisibility(View.VISIBLE);
                    btNext.setVisibility(View.GONE);
                }
                btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(position+1);
                    }
                });
            }
        });



        btGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, RegistrationActivity.class));
                finish();
            }
        });

    }

    public void setUpOnBoardingItems(){
        OnBoardingItem chooseProducts = new OnBoardingItem();
        chooseProducts.setImage(R.drawable.onboardscreen1);
        chooseProducts.setHeading(R.string.first_slider);
        chooseProducts.setDescription(R.string.description);

        OnBoardingItem fastDelivery = new OnBoardingItem();
        fastDelivery.setImage(R.drawable.onboardscreen2);
        fastDelivery.setHeading(R.string.second_slider);
        fastDelivery.setDescription(R.string.description);

        OnBoardingItem goodService = new OnBoardingItem();
        goodService.setImage(R.drawable.onboardscreen3);
        goodService.setHeading(R.string.third_slider);
        goodService.setDescription(R.string.description);

        items.add(chooseProducts);
        items.add(fastDelivery);
        items.add(goodService);

        sliderAdapter = new SliderAdapter(OnBoardingActivity.this,items);

    }

    public void setUpOnBoardingIndicators(){
        ImageView [] indicator = new ImageView[sliderAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i<indicator.length; i++){
            indicator [i] = new ImageView(getApplicationContext());
            indicator [i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            dotsLayout.addView(indicator[i]);
        }
    }

    public void setCurrentOnBoardingIndicator(int index){
        int childCount = dotsLayout.getChildCount();
        for(int i = 0; i<childCount; i++){
            ImageView imageView = (ImageView) dotsLayout.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));
            }
            else{
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            }
        }
    }

    public boolean isOpenAlready(){
        sharedPreferences = getSharedPreferences("slide",MODE_PRIVATE);
        return sharedPreferences.getBoolean("slide",false);
    }
}