package com.example.travelil.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPager_Adapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayListfragment = new ArrayList<>();
    public MainViewPager_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return arrayListfragment.get(position);
    }

    @Override
    public int getCount() {

        return arrayListfragment.size();
    }
    public  void addFragment(Fragment faFragment){
        arrayListfragment.add(faFragment);

    }


}
