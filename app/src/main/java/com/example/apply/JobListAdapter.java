package com.example.apply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {

    private final LayoutInflater mInflator;
    private List<Job> mJobs; // Cached copy of words
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

    class JobViewHolder extends RecyclerView.ViewHolder{
        private final TextView titleView;
        private final TextView employerView;
        private final TextView summaryView;
        private final TextView locationView;
        private final TextView hoursView;

        private JobViewHolder(View itemView){
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            employerView = itemView.findViewById(R.id.employer);
            summaryView = itemView.findViewById(R.id.summary);
            locationView = itemView.findViewById(R.id.location);
            hoursView = itemView.findViewById(R.id.hours);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        JobListAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
    }
}
