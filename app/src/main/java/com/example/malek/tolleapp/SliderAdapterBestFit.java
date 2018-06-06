package com.example.malek.tolleapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SliderAdapterBestFit extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapterBestFit(Context context){

        this.context = context;
    }

    //Arrays
    public int[] slide_images_best_fit = {
        R.drawable.app,
        R.drawable.newpaper,
        R.drawable.logo
    };

    @Override
    public int getCount() {
        return slide_images_best_fit.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object ;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);

        slideImageView.setImageResource(slide_images_best_fit[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
