package com.tam.joblinks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tam.joblinks.R;
import com.tam.joblinks.helpers.DateHelper;
import com.tam.joblinks.models.Job;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by toan on 4/28/2016.
 */
public class JobAppliedSavedAdapter  extends RecyclerView.Adapter<JobAppliedSavedAdapter.JobStatusViewHolder> {


    private List<Job> jobs;

    public JobAppliedSavedAdapter(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void clear() {
        this.jobs.clear();
        notifyDataSetChanged();
    }

    public void addRange(List<Job> list) {
        this.jobs.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public JobStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View viewItemJob = LayoutInflater.from(context).inflate(R.layout.item_status_job, parent, false);
        JobStatusViewHolder holder = new JobStatusViewHolder(viewItemJob);
        return holder;
    }

    private Job getCurrentJob(int position) {
        return this.jobs.get(position);
    }

    @Override
    public void onBindViewHolder(JobStatusViewHolder holder, int position) {
        final Job job = getCurrentJob(position);
        holder.tvStatusDescription.setText(job.getDescription());
        holder.tvStatusTime.setText(DateHelper.formatDate(job.getCreated()));
        holder.tvStatusTitle.setText(job.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.jobs.size();
    }

    public class JobStatusViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvStatusDescription)
        TextView tvStatusDescription;

        @Bind(R.id.tvStatusTime)
        TextView tvStatusTime;

        @Bind(R.id.tvStatusTitle)
        TextView tvStatusTitle;

        public JobStatusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
