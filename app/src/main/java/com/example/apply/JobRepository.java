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

    // Methods to invoke background tasks to operate on database
    public void insert(Job job){
        new insertAsyncTask(mJobDao).execute(job);
    }

    public void update(Job job){
        new updateAsyncTask(mJobDao).execute(job);
    }

    public void deleteAll() {
        new deleteAllJobsAsyncTask(mJobDao).execute();
    }

    public void deleteJob(Job job) {
        new deleteJobAsyncTask(mJobDao).execute(job);
    }

    // Asynchronous tasks calling Dao methods
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

    public static class updateAsyncTask extends AsyncTask<Job, Void, Void>{
        private JobDao mAsyncTaskDao;

        updateAsyncTask(JobDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Job... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public static class deleteAllJobsAsyncTask extends AsyncTask<Void, Void, Void>{
        private JobDao mAsyncTaskDao;

        deleteAllJobsAsyncTask(JobDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public static class deleteJobAsyncTask extends AsyncTask<Job, Void, Void>{
        private JobDao mAsyncTaskDao;

        deleteJobAsyncTask(JobDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Job... params){
            mAsyncTaskDao.deleteJob(params[0]);
            return null;
        }
    }
}
