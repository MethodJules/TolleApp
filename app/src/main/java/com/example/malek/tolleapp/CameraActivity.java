package com.example.malek.tolleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        button = findViewById(R.id.backToMainFromCameraButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });

        button = findViewById(R.id.takePictureButton);
        imageView = findViewById(R.id.cameraImageView);
        //When clicking on the button take picture, an intent is created to open the camera
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

    }

    //this method is called when clicking on the button zurück to go back to main
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
