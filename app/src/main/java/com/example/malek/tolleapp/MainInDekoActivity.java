package com.example.malek.tolleapp;



import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainInDekoActivity extends AppCompatActivity {
    private ViewPager mSlideViewPagerInDeko;
    private LinearLayout mDotsLayoutIndeko;
    private TextView[] mdots;
    private SliderAdapterInDeko sliderAdapterInDeko;
    private Button button, moreButton, calendarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_in_deko);
        mSlideViewPagerInDeko = (ViewPager) findViewById(R.id.slideViewPagerInDeko);
        mDotsLayoutIndeko = (LinearLayout) findViewById(R.id.dotsLayoutInDeko);
        sliderAdapterInDeko = new SliderAdapterInDeko(this);
        mSlideViewPagerInDeko.setAdapter(sliderAdapterInDeko);

        addDotsIndicator(0);
        mSlideViewPagerInDeko.addOnPageChangeListener(viewListener);
        moreButton = findViewById(R.id.more);
        //The button mehr is set to invisible by default
        moreButton.setVisibility(View.INVISIBLE);

        calendarBtn = findViewById(R.id.calendarButton);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarActivity();
            }
        });

        //Sharing Location button
        button = findViewById(R.id.locationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLocationActivity();
            }
        });

    }

    //this method is called by the onClick event of the button and calls the method openBrowserActivity
    public void browser(View view) {
        openBrowserActivity();
    }

    public void openCalendarActivity(){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    //this method opens the Location Activity
    public void openLocationActivity(){
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }


    //This method opens the URL of Indeko in a browser
    public void openBrowserActivity(){
        String url = "http://147.172.96.25/atlas/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        //This exception prevents the app from crashing if there is no browser installed
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    //This method is to generate the dots in the bottom of the slider and indicates the slider's position
    public void addDotsIndicator(int position){
        //mdots textview gets the length of the slider and then removes the views to avoid repetition
        mdots = new TextView[sliderAdapterInDeko.getCount()];
        mDotsLayoutIndeko.removeAllViews();

        for (int i = 0; i < mdots.length; i++){
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.green));
            mDotsLayoutIndeko.addView(mdots[i]);
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
            //when the user reaches the last slide the button "mehr" becomes visible
            if (i == mdots.length-1){

                moreButton.setVisibility(View.VISIBLE);
            }else {
                moreButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

