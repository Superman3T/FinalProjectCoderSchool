package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tam.joblinks.R;
import com.tam.joblinks.helpers.JobDbHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JobDetailsActivity extends AppCompatActivity {

    @Bind(R.id.tvDetailCity)
    TextView tvDetailCity;

    @Bind(R.id.tvDetailDate)
    TextView tvDetailDate;

    @Bind(R.id.tvDetailsTitle)
    TextView tvDetailsTitle;

    @Bind(R.id.tvDetailDescription)
    TextView tvDetailDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        String description = getIntent().getStringExtra(JobDbHelper.COL_DESCRIPTION);
        String createdBy = getIntent().getStringExtra(JobDbHelper.COL_CREATED_BY);
        String createdDate = getIntent().getStringExtra(JobDbHelper.COL_CREATED_DATE);
        String title = getIntent().getStringExtra(JobDbHelper.COL_TITLE);
        String city = getIntent().getStringExtra(JobDbHelper.COL_JOB_CITY);

        tvDetailCity.setText(city);
        tvDetailDate.setText(createdDate);
        tvDetailDescription.setText(description);
        tvDetailsTitle.setText(title);
    }

}
