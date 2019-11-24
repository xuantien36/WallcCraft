package com.t3h.wallccraft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListAlbumRespone {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_album")
    @Expose
    private List<ListImage> listImage = null;

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

    public List<ListImage> getListImage() {
        return listImage;
    }

    public void setListImage(List<ListImage> listImage) {
        this.listImage = listImage;
    }
}
