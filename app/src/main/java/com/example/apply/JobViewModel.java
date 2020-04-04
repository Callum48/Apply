package com.example.apply;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JobViewModel extends AndroidViewModel {

    private JobRepository mRepository;
    private LiveData<List<Job>> mAllJobs;

    public JobViewModel(Application application){
        super(application);
        mRepository = new JobRepository(application);
        mAllJobs = mRepository.getAllJobs();
    }

    LiveData<List<Job>> getAllJobs() {return mAllJobs;}

    // App accesses repository methods through this view holder class
    public void insert(Job job) {mRepository.insert(job);}

    public void update(Job job) {mRepository.update(job);}

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteJob(Job job) {mRepository.deleteJob(job);}
}
