package com.t3h.wallccraft.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.t3h.wallccraft.model.ListImage;


@Database(entities = ListImage.class, version = 7)
public abstract class AppDatabase extends RoomDatabase {
        private static AppDatabase appDatabase;

        public static AppDatabase getInstance(Context context) {
            if (appDatabase == null){
                appDatabase = Room.databaseBuilder(
                        context,
                        AppDatabase.class,
                        "news-database"
                )
                        .allowMainThreadQueries()
                        .build();
            }
            return appDatabase;
        }

        public abstract ImageDao getImagesDao();

//    private static AppDatabase appDatabase;
//
//    public static AppDatabase getInstance(Context context) {
//        if (appDatabase == null){
//            appDatabase = Room.databaseBuilder(
//                    context,
//                    AppDatabase.class,
//                    "news-database"
//            )
//                    .allowMainThreadQueries()
//                    .addMigrations(MIGRATION_1_2)
//                    .build();
//        }
//        return appDatabase;
//    }
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//        }
//    };
//
//    public abstract ImageDao getImageDao();

}
