package com.example.apply;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "job_table")
public class Job {

    @PrimaryKey
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
    @ColumnInfo(name = "type")
    private String mType;

    public Job(@NonNull String title,
               @NonNull String employer,
               @NonNull String email,
               @NonNull String summary,
               @NonNull String description,
               @NonNull String location,
               @NonNull String type){
        this.mTitle = title;
        this.mEmployer = employer;
        this.mEmail = email;
        this.mSummary = summary;
        this.mDescription = description;
        this.mLocation = location;
        this.mType = type;
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

    public String getType() {
        return mType;
    }
}
