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
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}

/**
 * TODO: Main activity code for updating rows
 */
