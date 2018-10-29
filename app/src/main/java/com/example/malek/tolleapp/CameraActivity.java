package com.example.malek.tolleapp;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.malek.tolleapp.model.FileInfo;
import com.example.malek.tolleapp.remote.APIUtils;
import com.example.malek.tolleapp.remote.FileService;
import com.example.malek.tolleapp.remote.RetrofitClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    FileService fileService;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
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
                //this ignores the Uri exception that happens when firing the camera
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureName = getPictureName();
                File imageFile = new File(pictureDirectory,pictureName);
                Uri pictureUri = Uri.fromFile(imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                startActivityForResult(intent, 1);
            }
        });
        button = findViewById(R.id.shareContent);
        //When clicking on the button teilen, an intent is created to open the camera
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //all good
                Map<String,RequestBody> map = new HashMap<>();
                File file = new File(imagePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
                map.put("file\"; filename=\""+file.getName()+"\"",requestBody);
                FileService getResponse = APIUtils.getFileService();
                Call<FileInfo> call = getResponse.upload("token",map);

                call.enqueue(new Callback<FileInfo>() {
                    @Override
                    public void onResponse(Call<FileInfo> call, Response<FileInfo> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(CameraActivity.this,"Image uploaded successfully", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<FileInfo> call, Throwable t) {
                        Toast.makeText(CameraActivity.this, "Error:" +t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // This event lets the user choose a picture from the galery
        button = findViewById(R.id.chooseFileButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "PlantPlacesImage"+ timestamp + ".jpg";

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
        if ( requestCode == 0 && resultCode == RESULT_OK){
            if ( data == null) {
                Toast.makeText(this, "Unable to choose image!", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            //all good
            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
            assert cursor !=null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);


        }
        else if ( requestCode == 1 && resultCode == RESULT_OK) {
            // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(bitmap);
        }
    }
    private String getRealPathFromUri(Uri uri){

        return null;
    }
}

//  Uri imageUri = data.getData();
            //imagePath = getRealPathFromUri(imageUri);
        //}
        //else if ( requestCode == 1 && resultCode == RESULT_OK) {
           // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(bitmap);
     //   }
   // }
    //private String getRealPathFromUri(Uri uri){
     //   String[] projection = {MediaStore.Images.Media.DATA};
       // CursorLoader loader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        //Cursor cursor = loader.loadInBackground();
        //int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //assert  cursor != null;
        //cursor.moveToFirst();
        //String result = cursor.getString(column_idx);
        //cursor.close();
        //return  result;
//    }
//}
