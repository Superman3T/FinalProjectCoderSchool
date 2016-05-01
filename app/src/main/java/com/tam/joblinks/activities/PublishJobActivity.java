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
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.backendless.exceptions.BackendlessFault;
import com.tam.joblinks.R;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.DateHelper;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.helpers.StringHelper;
import com.tam.joblinks.interfaces.JobRepositoryInterface;
import com.tam.joblinks.interfaces.UserAndJobInterface;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.models.UserAndJob;
import com.tam.joblinks.repositories.JobRepository;
import com.tam.joblinks.repositories.UserAndJobRepository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublishJobActivity extends BaseActivity {
    private final int REQUEST_CODE = 20;
    private static String jobCity;
    private static String jobTitle;
    private static int jobSalary;
    private static String jobDescription;
    @Bind(R.id.edJobSalary)
    EditText edJobSalary;

    @Bind(R.id.edJobTitle)
    EditText edJobTitle;

    @Bind(R.id.edJobDescription)
    EditText edJobDescription;

    @Bind(R.id.tvJobCity)
    TextView tvJobCity;

    @Bind(R.id.btPublishJob)
    Button btPublishJob;
    private View parentView;

    private JobRepositoryInterface jobRepositoryInterface;
    private UserAndJobInterface userAndJobInterface;

    public PublishJobActivity() {
        this.jobRepositoryInterface = new JobRepository(this);
        this.userAndJobInterface = new UserAndJobRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_job);
        parentView = findViewById(android.R.id.content);
        initToolbar();
        initUI();
        setJobValues();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent returnBtn = new Intent(getApplicationContext(),
                        MainActivity.class);

                startActivity(returnBtn);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initUI() {
        ButterKnife.bind(this);
        btPublishJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishJob();
            }
        });

        tvJobCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });
    }

    public void showList() {
        new MaterialDialog.Builder(this)
                .title(R.string.prompt_job_city)
                .items(R.array.cities_array)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //showToast(which + ": " + text);
                        tvJobCity.setText(text);
                    }
                })
                .show();
    }


    private void publishJob() {
        if (StringHelper.isNullOrEmpty(JobApplication.currentMail)) {
            Snackbar.make(parentView, R.string.please_sign_in_to_publish, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_sign_in, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JobApplication.previousPage = PublishJobActivity.class.getSimpleName();
                            getJobValues();
                            Intent intent = new Intent(PublishJobActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //startActivityForResult(intent, REQUEST_CODE);
                        }
                    })  // action text on the right side
                    .setActionTextColor(Color.parseColor("#FFC107"))
                    .setDuration(Snackbar.LENGTH_INDEFINITE).show();
        } else {
            Job newJob = new Job();
            getJobValues();
            newJob.setCreated(DateHelper.now());
            newJob.setCity(jobCity);
            newJob.setTitle(jobTitle);
            newJob.setSalary(jobSalary);
            newJob.setDescription(jobDescription);
            newJob.setCreatedBy(JobApplication.currentMail);
            newJob.setExpirationDate(DateHelper.addDaysFromCurrentDate(7));
            jobRepositoryInterface.upsertAsync(newJob, new ProgressDialogCallBack<Job>(PublishJobActivity.this) {
                @Override
                public void handleResponse(Job savedJob) {

                    showToast(getString(R.string.add_job_successfully));
                    UserAndJob userAndJob = new UserAndJob();
                    userAndJob.setJobId(savedJob.getObjectId());
                    //userAndJob.setOwnerId(JobApplication.currentMail);
                    userAndJob.setEmail(JobApplication.currentMail);
                    userAndJob.setType(JobApplication.PUBLISH_JOB);
                    try {
                        userAndJobInterface.upsertAsync(userAndJob, new ProgressDialogCallBack<UserAndJob>(PublishJobActivity.this) {
                            @Override
                            public void handleResponse(UserAndJob response) {
                                super.handleResponse(response);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.d("userAndJob.upsertAsync", "handleFault: " + fault.getMessage() + ", code: " + fault.getCode());
                                super.handleFault(fault);
                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    super.handleResponse(savedJob);
                }
            });
        }
    }

    private void setJobValues() {
        tvJobCity.setText(jobCity);
        edJobTitle.setText(jobTitle);
        edJobDescription.setText(jobDescription);
        if (jobSalary != 0) {
            edJobSalary.setText(String.valueOf(jobSalary));
        }
    }

    private void getJobValues() {
        jobCity = tvJobCity.getText().toString().trim();
        jobTitle = edJobTitle.getText().toString().trim();
        jobDescription = edJobDescription.getText().toString().trim();
        String tempSalary = edJobSalary.getText().toString().trim();
        if (!StringHelper.isNullOrEmpty(tempSalary) && !tempSalary.equals("0")) {
            jobSalary = Integer.valueOf(tempSalary);
        }
    }
}
