package com.example.apply;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JobRepository {

    private JobDao mJobDao;
    private LiveData<List<Job>> mAllJobs;

    JobRepository(Application application){
        JobRoomDatabase db = JobRoomDatabase.getDatabase(application);
        mJobDao = db.jobDao();
        mAllJobs = mJobDao.getAllJobs();
    }

    LiveData<List<Job>> getAllJobs(){
        return mAllJobs;
    }

    public void insert(Job job){
        new insertAsyncTask(mJobDao).execute(job);
    }

    private static class insertAsyncTask extends AsyncTask<Job, Void, Void>{

        private JobDao mAsyncTaskDao;

        insertAsyncTask(JobDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Job... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
