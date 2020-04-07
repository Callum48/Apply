package com.example.apply;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {

    private final LayoutInflater mInflator;
    private List<Job> mJobs; // Cached copy of words
    private List<Job> mFilteredJobs;
    private static ClickListener clickListener;

    JobListAdapter(Context context) {mInflator = LayoutInflater.from(context);}

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflator.inflate(R.layout.recyclerview_item, parent, false);
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position){
        if(mJobs != null){
            Job current = mJobs.get(position);
            holder.titleView.setText(current.getTitle());
            holder.employerView.setText(current.getEmployer());
            holder.summaryView.setText(current.getSummary());
            holder.locationView.setText(current.getLocation());
            holder.hoursView.setText(String.valueOf(current.getHours()));
        } else {
            // Covers the case of data not being ready
            holder.titleView.setText("No title");
            holder.employerView.setText("No employer");
            holder.summaryView.setText("No summary");
            holder.locationView.setText("No location");
            holder.hoursView.setText("No hours");
        }
    }

    void setJobs(List<Job> jobs){
        mJobs = jobs;
        mFilteredJobs = new ArrayList<>(mJobs);
        notifyDataSetChanged();
    }

    public Job getJobAtPosition(int pos){
        return mJobs.get(pos);
    }

    // getItemCount() is call multiple times, and when it's first called, mJobs is null so we return 0
    @Override
    public int getItemCount(){
        if(mJobs != null){
            return mJobs.size();
        } else {
            return 0;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        JobListAdapter.clickListener = clickListener;
    }

    public void filter(String searchText){
        mJobs.clear();
        if(searchText.isEmpty()){
            mJobs.addAll(mFilteredJobs);
        } else {
            searchText = searchText.toLowerCase();
            for(Job job: mFilteredJobs){
                if(job.getTitle().toLowerCase().contains(searchText) || job.getEmployer().toLowerCase().contains(searchText) || job.getLocation().toLowerCase().contains(searchText)){
                    mJobs.add(job);
                }
            }
        }
        notifyDataSetChanged();
    }

    class JobViewHolder extends RecyclerView.ViewHolder{
        private final TextView titleView;
        private final TextView employerView;
        private final View lineBreak;
        private final TextView summaryView;
        private final TextView locationView;
        private final TextView hoursView;
        private final ImageButton editJobButton;

        private JobViewHolder(View itemView){
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            employerView = itemView.findViewById(R.id.employer);
            lineBreak = itemView.findViewById(R.id.line_break);
            summaryView = itemView.findViewById(R.id.summary);
            locationView = itemView.findViewById(R.id.location);
            hoursView = itemView.findViewById(R.id.hours);
            editJobButton = itemView.findViewById(R.id.edit_job);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
            editJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onEditClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
        void onEditClick(View v, int position);
    }
}
