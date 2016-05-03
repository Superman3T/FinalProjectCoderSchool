package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tam.joblinks.R;
import com.tam.joblinks.adapters.JobAppliedSavedAdapter;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.JobDbHelper;
import com.tam.joblinks.models.Job;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class JobStatusActivity extends BaseActivity {

    private List<Job> totalJobs = new ArrayList<>();

    private JobAppliedSavedAdapter adapter;

    private LinearLayoutManager linearLayout;
    private ActionBar actionbar;
    private Toolbar toolbar;

    @Bind(R.id.rvJobStatus)
    RecyclerView rvJobStatus;

    @Bind(R.id.tvResultJobs)
    TextView tvResultJobs;

    public JobStatusActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status);
        setupToolbar();
        ButterKnife.bind(this);
        adapter = new JobAppliedSavedAdapter(totalJobs);
        rvJobStatus.setAdapter(adapter);
        linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        rvJobStatus.setItemAnimator(new SlideInUpAnimator());
        rvJobStatus.setLayoutManager(linearLayout);

        int value = getIntent().getExtras().getInt(JobApplication.USER_ACTION_JOB, JobApplication.UNKNOWN);
        if (value != JobApplication.UNKNOWN) {
            getData(value);
        }
//        } else {
//            rvJobStatus.setVisibility(View.GONE);
//            tvResultJobs.setVisibility(View.VISIBLE);
//            if (value == JobApplication.APPLY_JOB) {
//                tvResultJobs.setText(getString(R.string.no_applied_jobs));
//            } else {
//                tvResultJobs.setText(getString(R.string.no_saved_jobs));
//            }
//        }
    }

    private void getData(int value) {
        JobDbHelper db = JobDbHelper.getInstance(this);
        List<Job> result = new ArrayList<>();
        if (value == JobApplication.SAVE_JOB) {
            totalJobs = db.getSavedJobs();
            actionbar.setTitle(getString(R.string.saved_jobs));
        } else {
            totalJobs = db.getAppliedJob();
            actionbar.setTitle(getString(R.string.applied_jobs));
        }
        if (totalJobs.size() > 0) {
            rvJobStatus.setVisibility(View.VISIBLE);
            tvResultJobs.setVisibility(View.GONE);
            adapter.addRange(totalJobs);

        } else {
            rvJobStatus.setVisibility(View.GONE);
            tvResultJobs.setVisibility(View.VISIBLE);
            if (value == JobApplication.APPLY_JOB) {
                tvResultJobs.setText(getString(R.string.no_applied_jobs));

            } else {
                tvResultJobs.setText(getString(R.string.no_saved_jobs));

            }
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }
}
