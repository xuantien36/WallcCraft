package com.t3h.wallccraft.apialbum;
import com.t3h.wallccraft.model.AlbumRespone;
import com.t3h.wallccraft.model.ListImageRespone;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/album")
    Call<AlbumRespone> getAlbum();


    @GET("/album_detail")
    Call<ListImageRespone> getAlbumDetail(@Query("id") String id);

    @GET("/album_search")
    Call<ListImageRespone> getAlbumSearch(@Query("search") String text);
}
