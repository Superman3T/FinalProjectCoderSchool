package com.tam.joblinks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tam.joblinks.R;
import com.tam.joblinks.model.Job;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by toan on 4/13/2016.
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobsViewHolder> {

    private List<Job> jobs;

    public JobsAdapter(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public JobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View viewItemJob = LayoutInflater.from(context).inflate(R.layout.item_job, parent, false);
        JobsViewHolder holder = new JobsViewHolder(viewItemJob);
        return holder;
    }

    @Override
    public void onBindViewHolder(JobsViewHolder holder, int position) {
        final Job job = this.jobs.get(position);
        holder.tvJobCity.setText(job.city);
        holder.tvJobCompany.setText(job.company);
        holder.tvJobTitle.setText(job.title);
        holder.btJobApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btJobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.jobs.size();
    }

    public void clear() {
        this.jobs.clear();
        notifyDataSetChanged();
    }

    public void addRange(List<Job> list) {
        this.jobs.addAll(list);
        notifyDataSetChanged();
    }

    public static class JobsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvJobTitle)
        TextView tvJobTitle;

        @Bind(R.id.tvJobCompany)
        TextView tvJobCompany;

        @Bind(R.id.tvJobCity)
        TextView tvJobCity;

        @Bind(R.id.btJobSave)
        Button btJobSave;

        @Bind(R.id.btJobApply)
        Button btJobApply;

        public JobsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
