package com.example.apply;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Job.class}, version = 1, exportSchema = false)
public abstract class JobRoomDatabase extends RoomDatabase {

    // DAO (database access object) that works with database
    public abstract JobDao jobDao();

    private static JobRoomDatabase INSTANCE;

    // Create a single instance of the database
    public static JobRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (JobRoomDatabase.class){
                if(INSTANCE  == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JobRoomDatabase.class, "job_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
