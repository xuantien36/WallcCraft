package com.t3h.wallccraft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AlbumRespone {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("album")
    @Expose
    private ArrayList<Album> album = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Album> getAlbum() {
        return album;
    }

    public void setAlbum(ArrayList<Album> album) {
        this.album = album;
    }
}
