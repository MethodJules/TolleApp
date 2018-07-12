package com.example.malek.tolleapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainBestFitActivity extends AppCompatActivity {

    private ViewPager mSlideViewPagerBestFit;
    private LinearLayout mDotsLayoutBestFit;
    private TextView[] mdots;

    private SliderAdapterBestFit sliderAdapterBestFit;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_best_fit);

        mSlideViewPagerBestFit = (ViewPager) findViewById(R.id.slideViewPagerBestFit);
        mDotsLayoutBestFit = (LinearLayout) findViewById(R.id.dotsLayoutBestFit);
        sliderAdapterBestFit = new SliderAdapterBestFit(this);
        mSlideViewPagerBestFit.setAdapter(sliderAdapterBestFit);

        addDotsIndicator(0);
        mSlideViewPagerBestFit.addOnPageChangeListener(viewListener);

        button = findViewById(R.id.inDekoButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainInDekoActivity();
            }
        });

    }

    public void openMainInDekoActivity(){

        Intent intent = new Intent(this,MainInDekoActivity.class);
        startActivity(intent);
    }

    public void addDotsIndicator(int position) {

        mdots = new TextView[3];
        mDotsLayoutBestFit.removeAllViews();

        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.red));
            mDotsLayoutBestFit.addView(mdots[i]);
        }
        if(mdots.length > 0){
            mdots[position].setTextColor(getResources().getColor(R.color.DarkGray));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
