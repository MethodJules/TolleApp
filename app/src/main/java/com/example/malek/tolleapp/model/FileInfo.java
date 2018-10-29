package com.example.malek.tolleapp.model;



import com.google.gson.annotations.SerializedName;

// This class contains the necessary information about the picture
public class FileInfo {
    @SerializedName("name")
    private String name;

    @SerializedName("fileSize")
    private long fileSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
