package com.arafa.mohamed.eshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.arafa.mohamed.eshop.R;
import com.arafa.mohamed.eshop.fragments.AboutFragment;
import com.arafa.mohamed.eshop.fragments.EditAddressFragment;
import com.arafa.mohamed.eshop.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    Fragment fragment;
    AppCompatButton btYes,btNo;
    List<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        //Creating a list of menu Items
         menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home",R.drawable.bg_drawer));
        menuItems.add(new MenuItem("Address",R.drawable.bg_drawer));
        menuItems.add(new MenuItem("About",R.drawable.bg_drawer));
        menuItems.add(new MenuItem("Log Out",R.drawable.bg_drawer));

        //then add them to navigation drawer

        sNavigationDrawer.setMenuItemList(menuItems);
        sNavigationDrawer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        fragmentClass =  HomeFragment.class;
        sNavigationDrawer.setAppbarTitleTV("Home");
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.home_container, fragment).commit();
        }



        //Listener to handle the menu item click. It returns the position of the menu item clicked. Based on that you can switch between the fragments.

        sNavigationDrawer.setOnMenuItemClickListener(position -> {

            switch (position){
                case 0:{
                    fragmentClass = HomeFragment.class;
                    break;
                }
                case 1:{
                    fragmentClass = EditAddressFragment.class;
                    break;
                }
                case 2:{
                    fragmentClass = AboutFragment.class;
                    break;
                }
                case 3:{
                    showCustomDialog();
                    sNavigationDrawer.setAppbarTitleTV("Home");
                    break;
                }
            }

            //Listener for drawer events such as opening and closing.
            sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                @Override
                public void onDrawerOpened() {

                }

                @Override
                public void onDrawerOpening(){

                }

                @Override
                public void onDrawerClosing(){

                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.home_container, fragment).commit();
                    }
                }

                @Override
                public void onDrawerClosed() {

                }
                @Override
                public void onDrawerStateChanged(int newState) {
                    System.out.println("State "+newState);
                }
            });
        });

    }
    public  void showCustomDialog(){

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);

        btYes = dialog.findViewById(R.id.button_yes);
        btNo = dialog.findViewById(R.id.button_no);

        btYes.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        btNo.setOnClickListener(v -> dialog.dismiss());

          dialog.show();

    }
}