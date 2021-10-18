package com.example.travelil.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SearchViewPager_Adapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayListfragment = new ArrayList<>();
    private ArrayList<String> stringfrag = new ArrayList<>();
    public SearchViewPager_Adapter(@NonNull FragmentManager fm) {
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
    public  void addFragment(Fragment faFragment, String title){
        arrayListfragment.add(faFragment);
        stringfrag.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return stringfrag.get(position);
    }
}
