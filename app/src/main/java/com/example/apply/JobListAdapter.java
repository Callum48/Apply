package com.example.apply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {

    private final LayoutInflater mInflator;
    private List<Job> mJobs; // Cached copy of words
    private List<Job> mFilteredJobs; // Duplicate of job list for filtering after search
    private static ClickListener clickListener;

    JobListAdapter(Context context) {mInflator = LayoutInflater.from(context);}

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflator.inflate(R.layout.recyclerview_item, parent, false);
        return new JobViewHolder(itemView);
    }

    /**
     *This method is responsible for setting data to an individual recycler view item
     */
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

    /**
     * Populate the job list used for the UI with the jobs gotten from the database
     */
    void setJobs(List<Job> jobs){
        mJobs = jobs;
        mFilteredJobs = new ArrayList<>(mJobs);
        notifyDataSetChanged();
    }

    /**
     * Tells the app which job to act on given a recyclerview list item position interacted with
     */
    public Job getJobAtPosition(int pos){
        return mJobs.get(pos);
    }

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

    /**
     * Set the job list to only those with certain fields containing the search string
     */
    public void filter(String searchText){
        // Empty the list for modification
        mJobs.clear();
        if(searchText.isEmpty()){
            // Show all the jobs if there isn't a search string
            mJobs.addAll(mFilteredJobs);
        } else {
            // Find jobs matching the search text and certain info eg location
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

            // Allow clicking the recyclerview job items and the edit job button
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
