package com.example.apply;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JobDao {

    @Insert
    void insert(Job job);

    @Query("DELETE FROM job_table")
    void deleteAll();

    @Query("SELECT * FROM job_table")
    LiveData<List<Job>> getAllJobs();
}
