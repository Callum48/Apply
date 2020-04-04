package com.example.apply;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "job_table")
public class Job {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name = "employer")
    private String mEmployer;

    @NonNull
    @ColumnInfo(name = "email")
    private String mEmail;

    @NonNull
    @ColumnInfo(name = "summary")
    private String mSummary;

    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription;

    @NonNull
    @ColumnInfo(name = "location")
    private String mLocation;

    @NonNull
    @ColumnInfo(name = "hours")
    private int mHours;

    public Job(@NonNull String title,
               @NonNull String employer,
               @NonNull String email,
               @NonNull String summary,
               @NonNull String description,
               @NonNull String location,
               @NonNull int hours){
        this.mTitle = title;
        this.mEmployer = employer;
        this.mEmail = email;
        this.mSummary = summary;
        this.mDescription = description;
        this.mLocation = location;
        this.mHours = hours;
    }

    @Ignore
    public Job(int id,
               @NonNull String title,
               @NonNull String employer,
               @NonNull String email,
               @NonNull String summary,
               @NonNull String description,
               @NonNull String location,
               @NonNull int hours){
        this.id = id;
        this.mTitle = title;
        this.mEmployer = employer;
        this.mEmail = email;
        this.mSummary = summary;
        this.mDescription = description;
        this.mLocation = location;
        this.mHours = hours;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEmployer() {
        return mEmployer;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLocation() {
        return mLocation;
    }

    public int getHours() {
        return mHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}