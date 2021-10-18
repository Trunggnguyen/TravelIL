package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.Choice;
import com.example.travelil.R;

import java.util.ArrayList;

public class Banner_Adapter extends PagerAdapter {
    Context context;
    ArrayList<Choice> arrayListbanner ;

    public Banner_Adapter(Context context, ArrayList<Choice> arrayListbanner) {
        this.context = context;
        this.arrayListbanner = arrayListbanner;
    }


    @Override
    public int getCount() {
        return arrayListbanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.iteam_banner,null);

        ImageView imageViewbanner = view.findViewById(R.id.imagebackround);


        Glide.with(context).load(arrayListbanner.get(position).getImage()).into(imageViewbanner);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
