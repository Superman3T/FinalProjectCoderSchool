package com.tam.joblinks.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tam.joblinks.R;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.DateHelper;
import com.tam.joblinks.helpers.JobDbHelper;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.interfaces.UserAndJobInterface;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.models.UserAndJob;
import com.tam.joblinks.repositories.UserAndJobRepository;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JobDetailsActivity extends BaseActivity {

    @Bind(R.id.tvDetailCity)
    TextView tvDetailCity;

    @Bind(R.id.tvDetailDate)
    TextView tvDetailDate;

    @Bind(R.id.tvDetailsTitle)
    TextView tvDetailsTitle;

    @Bind(R.id.tvDetailDescription)
    TextView tvDetailDescription;

    @Bind(R.id.btJobSave)
    Button btJobSave;

    @Bind(R.id.btJobApply)
    Button btJobApply;
    private View parentView;
    private UserAndJobInterface userAndJobInterface;

    public JobDetailsActivity() {
        this.userAndJobInterface = new UserAndJobRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        parentView = findViewById(android.R.id.content);
        initToolbar();
        ButterKnife.bind(this);
        final String description = getIntent().getStringExtra(JobDbHelper.COL_DESCRIPTION);
        final String createdBy = getIntent().getStringExtra(JobDbHelper.COL_CREATED_BY);
        final String createdDateString = getIntent().getStringExtra(JobDbHelper.COL_CREATED_DATE);
        final String title = getIntent().getStringExtra(JobDbHelper.COL_TITLE);
        final String city = getIntent().getStringExtra(JobDbHelper.COL_JOB_CITY);
        final String objectId = getIntent().getStringExtra(JobDbHelper.COL_OBJECT_ID);
        tvDetailCity.setText(city);
        tvDetailDate.setText(createdDateString);
        tvDetailDescription.setText(description);
        tvDetailsTitle.setText(title);
        final Job job = new Job();
        job.setCity(city);
        job.setObjectId(objectId);
        job.setCreatedBy(createdBy);
        job.setTitle(title);
        job.setDescription(description);
        btJobApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (JobApplication.isLogined()) {

                    UserAndJob object = new UserAndJob();

                    object.setEmail(JobApplication.currentMail);
                    object.setType(JobApplication.APPLY_JOB);
                    object.setJobId(job.getObjectId());
//                    String whereClause = "jobId = " + job.getObjectId() + " and type = " + JobApplication.PUBLISH_JOB + " and email = " + JobApplication.currentMail;

//                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
//                    dataQuery.setWhereClause(whereClause);
//                    BackendlessCollection<UserAndJob> result = Backendless.Persistence.of(UserAndJob.class).find(dataQuery);

//                    if (result.getData().size() < 1) {
                        userAndJobInterface.upsertAsync(object, new ProgressDialogCallBack<UserAndJob>(JobDetailsActivity.this) {
                            @Override
                            public void handleResponse(UserAndJob response) {
                                super.handleResponse(response);
                                showToast(getString(R.string.apply_job_successfully));
                                try {
//            job = (Job)getIntent().getParcelableExtra("JobInstance");
                                    Date created = DateHelper.parseDate(createdDateString);
                                    if (created == null) {
                                        created = DateHelper.now();
                                    }
                                    job.setCreated(created);
                                    JobDbHelper dbHelper = JobDbHelper.getInstance(JobDetailsActivity.this);
                                    if (dbHelper.isExistingJob(objectId)) {
                                        showToast(getString(R.string.already_save_job));
                                    } else {
                                        dbHelper.addJob(job, true, false, true);
                                        showToast(getString(R.string.save_job));
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    showWrong();
                                }
                            }
                        });
//                    }
                } else {
                    Snackbar.make(parentView, R.string.please_sign_in_to_apply, Snackbar.LENGTH_LONG)
                            .setAction(R.string.action_sign_in, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(JobDetailsActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    //startActivityForResult(intent, REQUEST_CODE);
                                }
                            })  // action text on the right side
                            .setActionTextColor(Color.parseColor("#FFC107"))
                            .setDuration(Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
        btJobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
//            job = (Job)getIntent().getParcelableExtra("JobInstance");
                    Date created = DateHelper.parseDate(createdDateString);
                    if (created == null) {
                        created = DateHelper.now();
                    }
                    job.setCreated(created);
                    JobDbHelper dbHelper = JobDbHelper.getInstance(JobDetailsActivity.this);
                    if (dbHelper.isExistingJob(objectId)) {
                        showToast(getString(R.string.already_save_job));
                    } else {
                        dbHelper.addJob(job, true, true, false);
                        showToast(getString(R.string.save_job));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showWrong();
                }
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Log.d("Test back", "go back");
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
