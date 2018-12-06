package com.example.malek.tolleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class TakePictureActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        button = findViewById(R.id.backToMainFromPicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        imageView = findViewById(R.id.pictureImageView);

        button = findViewById(R.id.takePictureButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //When clicking on the button take picture, an intent is created to open the camera
            public void onClick(View v) {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void backToMain(){
        Intent intent = new Intent(this, MainInDekoActivity.class);
        startActivity(intent);
    }

    //this method is fired to append the picture to the image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}


