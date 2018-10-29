package com.example.malek.tolleapp.remote;
//ApiConfig
import com.example.malek.tolleapp.model.FileInfo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface FileService {
    @Multipart
    @POST ("retrofit/image/upload_image.php")
    Call<FileInfo> upload(@Header("Authorization") String Authorization,
                                @PartMap Map<String, RequestBody> map);
}
