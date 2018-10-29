package com.example.malek.tolleapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

public class CameraActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private TextView textView;
    private static final int STORAGE_PERMISSION_CODE = 2000;
    private static final int PICK_IMAGE_REQUEST = 3000;
    private Uri filePath;
    private Bitmap bitmap;
    private static final String UPLOAD_URL = "http://192.168.44.1/upload/upload.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        requestStoragePermission();

        button = findViewById(R.id.backToMainFromCameraButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
        button = findViewById(R.id.shareContent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        button = findViewById(R.id.choosePictureButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        textView = findViewById(R.id.editTextName);


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
    //This methods asks the permission to read the storage content of the phone
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    //this method is called when clicking on choose picture
    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }


    //this method is called when clicking on the button zurück to go back to main
    private void backToMain(){
        Intent intent = new Intent(this, MainInDekoActivity.class);
        startActivity(intent);
    }
    //this method takes a Uri objects and returns its absolute path
    private String getPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        cursor.close();
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID +" = ?", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }
    //this method uploads the image to the server
    private void uploadImage(){
        String name = textView.getText().toString().trim();
        String path = getPath(filePath);
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL).addFileToUpload(path,"image").addParameter("name", name)
                    .setNotificationConfig(new UploadNotificationConfig()).setMaxRetries(2).startUpload();
        }catch (Exception e){}

    }


    //this method is fired to append the picture to the image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
           filePath = data.getData();
           try{
               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
               imageView.setImageBitmap(bitmap);
           }catch (IOException e){}
       }
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        //imageView.setImageBitmap(bitmap);
    }
}
