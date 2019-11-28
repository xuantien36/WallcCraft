package com.t3h.wallccraft.dao;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.t3h.wallccraft.model.ListImage;

@Database(entities = ListImage.class, version = 9)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "favorite-database"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract ImageDao getImagesDao();

}
