package com.example.malek.tolleapp.remote;

public class APIUtils {

    private  APIUtils(){
    }
    public static final String API_URL = "http://delaroystudios.com/";

    public static  FileService getFileService(){
        return  RetrofitClient.getClient(API_URL).create(FileService.class);
    }

}
