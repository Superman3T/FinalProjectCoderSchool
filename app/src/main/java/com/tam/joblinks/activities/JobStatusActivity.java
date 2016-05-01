package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tam.joblinks.R;
import com.tam.joblinks.adapters.JobAppliedSavedAdapter;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.JobDbHelper;
import com.tam.joblinks.models.Job;

import java.util.ArrayList;

import butterknife.Bind;

public class JobStatusActivity extends AppCompatActivity {

    private ArrayList<Job> totalJobs = new ArrayList<>();

    private JobAppliedSavedAdapter adapter;

    private LinearLayoutManager linearLayout;
    private ActionBar actionbar;
    private Toolbar toolbar;

    @Bind(R.id.rvJobStatus)
    RecyclerView rvJobStatus;


    public JobStatusActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status);
        initToolbar();
        linearLayout = new LinearLayoutManager(this);
//        String sample = getIntent().getStringExtra(JobApplication.USER_ACTION_JOB);
     //   getData();
        rvJobStatus.setAdapter(adapter);
    }

    private void getData() {
        String value = getIntent().getExtras().get(JobApplication.USER_ACTION_JOB).toString();
        JobDbHelper db = JobDbHelper.getInstance(this);

        if (value.equals(JobApplication.SAVE_JOB)) {
            adapter.addRange(db.getSavedJobs());
        } else {
//            adapter.addRange(db.getAppliedJob());
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }
}
