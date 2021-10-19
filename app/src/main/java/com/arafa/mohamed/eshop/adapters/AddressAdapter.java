package com.arafa.mohamed.eshop.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;

public class AddressAdapter extends FragmentStateAdapter {
    public final  ArrayList<Fragment> fragments=new ArrayList<>();
    public final ArrayList<String> fragmentTitle = new ArrayList<>();

    public AddressAdapter(@NonNull  FragmentManager fragmentManager, @NonNull  Lifecycle lifecycle) {

        super(fragmentManager, lifecycle);

    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        fragmentTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
