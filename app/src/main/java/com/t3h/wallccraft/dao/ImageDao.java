package com.t3h.wallccraft.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.t3h.wallccraft.model.ListImage;
import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image")
    List<ListImage> getAll();

    @Query("SELECT * FROM image WHERE favorite = 1")
    List<ListImage> getAllFavorite();

    @Query("SELECT * FROM image WHERE history = 1")
    List<ListImage> getAllHistoty();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListImage... image);
    @Delete
    void deleteFavorite(ListImage... image);

    @Delete
    void deleteHistory(ListImage... image);

    @Query("SELECT * FROM image where id = :id")
    ListImage getImageById(int id);

}
