package com.example.apply;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Creates the database
 * Every other interaction with the db is done through the JobViewModel
 */

@Database(entities = {Job.class}, version = 3, exportSchema = false)
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
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final JobDao mDao;
        String[] titles = {"Starbucks Barista", "Retail Assistant", "HELL Pizza Team Member", "Cafe Front of House"};
        String[] employers = {"Hospoworld", "Nike inc", "HELL Pizza", "Picnic Cafe"};
        String[] summaries = {"Develop your barista skills while working in a close knit team dedicated to providing the best customer service possible.",
                "Are you a passionate athlete with a knack for customer service? Come work with the team at Nike today!",
                "Take on many different duties in the role of a shop staff at Hell Pizza.",
                "Bring a sense of character and commitment to the team at Picnic Cafe and enjoy working next to the rose garden."};
        String[] locations = {"Auckland", "Nelson", "Wellington", "Wellington"};
        int[] hours = {12, 7, 8, 6};

        PopulateDbAsync(JobRoomDatabase db){
            mDao = db.jobDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            // if there aren't any jobs in db, then create the initial list of jobs
            if(mDao.getAnyJob().length < 1){
                for(int i = 0; i < titles.length; i++){
                    Job job = new Job(titles[i], employers[i], "temp email", summaries[i], "temp description", locations[i], hours[i]);
                    mDao.insert(job);
                }
            }
            return null;
        }
    }
}
