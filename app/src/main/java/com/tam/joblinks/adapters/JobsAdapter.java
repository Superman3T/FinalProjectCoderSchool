package com.tam.joblinks.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tam.joblinks.R;
import com.tam.joblinks.activities.JobDetailsActivity;
import com.tam.joblinks.helpers.DateHelper;
import com.tam.joblinks.helpers.JobDbHelper;
import com.tam.joblinks.models.Job;

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

    private Job getCurrentJob(int position) {
        return this.jobs.get(position);
    }

    @Override
    public void onBindViewHolder(JobsViewHolder holder, int position) {
        final Job job = getCurrentJob(position);
        holder.tvJobCity.setText(job.getCity());
        holder.tvJobDescription.setText(job.getDescription());
        holder.tvJobTitle.setText(job.getTitle());
        holder.tvDate.setText(DateHelper.formatDate(job.getCreated()));
//        holder.btJobApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        holder.btJobSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
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

    public class JobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tvJobTitle)
        TextView tvJobTitle;

        @Bind(R.id.tvJobCompany)
        TextView tvJobDescription;

        @Bind(R.id.tvJobCity)
        TextView tvJobCity;

        @Bind(R.id.tvDate)
        TextView tvDate;

//        @Bind(R.id.btJobSave)
//        Button btJobSave;
//
//        @Bind(R.id.btJobApply)
//        Button btJobApply;

        public JobsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
//            btJobSave.setOnClickListener(this);
//            btJobApply.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int position = getLayoutPosition(); // gets item position
            Job job = jobs.get(position);
            Intent intent = new Intent(context, JobDetailsActivity.class);
            intent.putExtra(JobDbHelper.COL_TITLE, job.getTitle());
            intent.putExtra(JobDbHelper.COL_JOB_CITY, job.getCity());

            intent.putExtra(JobDbHelper.COL_CREATED_DATE, DateHelper.formatDate(job.getCreated()));
            intent.putExtra(JobDbHelper.COL_DESCRIPTION, job.getDescription());
            intent.putExtra(JobDbHelper.COL_CREATED_BY, job.getCreatedBy());
            context.startActivity(intent);
            //ActivityNewsDetails.navigate((ActivityMain) getActivity(), v.findViewById(R.id.image), obj);
        }

//        @Override
//        public void onClick(View v) {
//            Context context = v.getContext();
//            try {
//
//                if (v.getId() == btJobApply.getId()) {
//                    // apply job
//                } else {
//                    JobDbHelper jobDbHelper = JobDbHelper.getInstance(context);
//                    int position = getLayoutPosition(); // gets item position
//                    final Job job = getCurrentJob(position);
//                    // save job
//                    if (btJobApply.getText().toString().toLowerCase().equals("save")) {
//
//
//                        Toast.makeText(v.getContext(), "Current position: " + String.valueOf(position), Toast.LENGTH_SHORT).show();
//                        jobDbHelper.saveJob(job);
//                        btJobSave.setText("Unsaved");
//                        Toast.makeText(context, context.getString(R.string.save_job), Toast.LENGTH_SHORT).show();
//                    } else {
//                        jobDbHelper.deleteJob(job.getObjectId());
//                        Toast.makeText(context, context.getString(R.string.unsave_job), Toast.LENGTH_SHORT).show();
//                        btJobSave.setText(context.getString(R.string.save));
//                    }
//                }
//            } catch (Exception ex) {
//                Toast.makeText(context, context.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
