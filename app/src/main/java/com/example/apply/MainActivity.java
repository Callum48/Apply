package com.example.apply;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_JOB_ACTIVITY_REQUEST_CODE  = 1;
    public static final int UPDATE_JOB_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_TITLE = "extra_title";
    public static final String EXTRA_DATA_EMPLOYER = "extra_employer";
    public static final String EXTRA_DATA_EMAIL = "extra_email";
    public static final String EXTRA_DATA_LOCATION = "extra_location";
    public static final String EXTRA_DATA_HOURS = "extra_hours";
    public static final String EXTRA_DATA_SUMMARY = "extra_summary";
    public static final String EXTRA_DATA_DESCRIPTION = "extra_description";
    public static final String EXTRA_DATA_ID ="extra_id";

    private JobViewModel mJobViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up the floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewJobActivity.class);
                startActivityForResult(intent, NEW_JOB_ACTIVITY_REQUEST_CODE);
                }
        });

        // set up the recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final JobListAdapter adapter = new JobListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mJobViewModel = ViewModelProviders.of(this).get(JobViewModel.class);

        mJobViewModel.getAllJobs().observe(this, new Observer<List<Job>>() {
            @Override
            public void onChanged(@Nullable final List<Job> jobs) {
                // update the cached copy of words
                adapter.setJobs(jobs);
            }
        });

        // Add swipe functionality on recycler view items to delete jobs
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Job currentJob = adapter.getJobAtPosition(position);
                        mJobViewModel.deleteJob(currentJob);
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new JobListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Job job = adapter.getJobAtPosition(position);
                launchViewActivity(job);
            }

            @Override
            public void onEditClick(View v, int position) {
                Job job = adapter.getJobAtPosition(position);
                launchUpdateActivity(job);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data) {
            mJobViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_JOB_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Job job = new Job(data.getExtras().getString(NewJobActivity.EXTRA_REPLY_TITLE),
                            data.getExtras().getString(NewJobActivity.EXTRA_REPLY_EMPLOYER),
                            data.getExtras().getString(NewJobActivity.EXTRA_REPLY_EMAIL),
                            data.getExtras().getString(NewJobActivity.EXTRA_REPLY_SUMMARY),
                            data.getExtras().getString(NewJobActivity.EXTRA_REPLY_DESCRIPTION),
                            data.getExtras().getString(NewJobActivity.EXTRA_REPLY_LOCATION),
                            data.getExtras().getInt(NewJobActivity.EXTRA_REPLY_HOURS));
            mJobViewModel.insert(job);
        } else if(requestCode == UPDATE_JOB_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String title = data.getExtras().getString(NewJobActivity.EXTRA_REPLY_TITLE);
            String employer = data.getExtras().getString(NewJobActivity.EXTRA_REPLY_EMPLOYER);
            String email = data.getExtras().getString(NewJobActivity.EXTRA_REPLY_EMAIL);
            String summary = data.getExtras().getString(NewJobActivity.EXTRA_REPLY_SUMMARY);
            String description = data.getExtras().getString(NewJobActivity.EXTRA_REPLY_DESCRIPTION);
            String location = data.getExtras().getString(NewJobActivity.EXTRA_REPLY_LOCATION);
            int hours = data.getExtras().getInt(NewJobActivity.EXTRA_REPLY_HOURS);
            int id = data.getExtras().getInt(NewJobActivity.EXTRA_REPLY_ID);

            if(id != -1){
                mJobViewModel.update(new Job(id, title, employer, email, summary, description, location, hours));
                Toast.makeText(this, "attempting updating", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to update", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchViewActivity(Job job){
        Intent intent = new Intent(this, ViewJobActivity.class);
        intent.putExtras(getJobBundleData(job));
        startActivity(intent);
    }

    public void launchUpdateActivity(Job job){
        Intent intent = new Intent(this, NewJobActivity.class);
        intent.putExtras(getJobBundleData(job));
        startActivityForResult(intent, UPDATE_JOB_ACTIVITY_REQUEST_CODE);
    }

    public Bundle getJobBundleData(Job job){
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_DATA_TITLE, job.getTitle());
        bundle.putString(EXTRA_DATA_EMPLOYER, job.getEmployer());
        bundle.putString(EXTRA_DATA_EMAIL, job.getEmail());
        bundle.putString(EXTRA_DATA_SUMMARY, job.getSummary());
        bundle.putString(EXTRA_DATA_DESCRIPTION, job.getDescription());
        bundle.putString(EXTRA_DATA_LOCATION, job.getLocation());
        bundle.putInt(EXTRA_DATA_HOURS, job.getHours());
        bundle.putInt(EXTRA_DATA_ID, job.getId());
        return bundle;
    }
}
