package com.example.malek.tolleapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SliderAdapterInDeko extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    //constructor of the sliderAdapterIndeko
    public SliderAdapterInDeko(Context context){
        this.context = context;
    }

    //Array that contains the slide images

    public int[] slide_images = {
        R.drawable.folie9,
        R.drawable.pie_chart,
        R.drawable.space
    };

    //Gets the length of the slide image
    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);

        slideImageView.setImageResource(slide_images[position]);

        container.addView(view);

        return  view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
