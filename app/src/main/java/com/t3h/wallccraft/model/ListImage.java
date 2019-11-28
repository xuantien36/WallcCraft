package com.t3h.wallccraft.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "image")
public class ListImage implements Serializable {
    @NonNull
    @SerializedName("idImage")
    @PrimaryKey(autoGenerate = true)
    private int idImage;

    public int getIdImage() {
        return idImage;
    }
    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("premium")
    @Expose
    private String premium;
    @SerializedName("url_download")
    @Expose
    private String urlDownload;
    @SerializedName("album_id")
    @Expose
    private int albumId;

    @ColumnInfo(name = "favorite")
    private boolean favorite;


    @ColumnInfo(name = "history")
    private boolean history;

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public ListImage(int id, String title, String thumbUrl) {
        this.id = id;
        this.thumbUrl = thumbUrl;
        this.title = title;
    }

    private int icon;

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getUrlDownload() {
        return urlDownload;
    }

    public void setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return "ListImage{" +
                "idImage=" + idImage +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", premium='" + premium + '\'' +
                ", urlDownload='" + urlDownload + '\'' +
                ", albumId=" + albumId +
                ", favorite=" + favorite +
                ", history=" + history +
                ", icon=" + icon +
                '}';
    }
}



