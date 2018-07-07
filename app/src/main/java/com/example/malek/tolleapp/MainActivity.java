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

public class MainActivity extends AppCompatActivity {


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = findViewById(R.id.bestFitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainBestFitActivity();
            }
        });

        button = findViewById(R.id.inDekoButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainInDekoActivity();
            }
        });
    }



    //this method opens the activity BestFitActivity
    public void openMainBestFitActivity() {

        Intent intent = new Intent(this, MainBestFitActivity.class);
        startActivity(intent);
    }

    //this method opens the activity MainInDekoActivity
    public void openMainInDekoActivity(){
        Intent intent = new Intent(this,MainInDekoActivity.class);
        startActivity(intent);
    }
}








