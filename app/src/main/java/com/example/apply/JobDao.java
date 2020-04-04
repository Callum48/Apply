package com.example.apply;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Database Access Object for a job
 * Each method performs a database operation, such as inserting or deleting a word
 */

@Dao
public interface JobDao {

    // Methods for database access object
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Job job);

    @Query("DELETE FROM job_table")
    void deleteAll();

    @Query("SELECT * FROM job_table")
    LiveData<List<Job>> getAllJobs();

    @Query("SELECT * from job_table LIMIT 1")
    Job[] getAnyJob();

    @Delete
    void deleteJob(Job job);

    @Update
    void update(Job... job);
}
