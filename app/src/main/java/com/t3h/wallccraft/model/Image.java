package com.t3h.wallccraft.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "image")
public class Image implements Serializable {
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("picture")
    @Expose
    private int image;
    @SerializedName("picture")
    @Expose
    private String name;
    public Image(int id, int image,String name) {
        this.id = id;
        this.image = image;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }
}
