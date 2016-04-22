package com.tam.joblinks.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tam.joblinks.R;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.DateHelper;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.interfaces.JobRepositoryInterface;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.repositories.JobRepository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublishJobActivity extends BaseActivity {

    @Bind(R.id.edJobSalary)
    EditText edJobSalary;

    @Bind(R.id.edJobTitle)
    EditText edJobTitle;

    @Bind(R.id.edJobDescription)
    EditText edJobDescription;

    @Bind(R.id.spinnerJobCity)
    Spinner spinnerJobCity;

    @Bind(R.id.btPublishJob)
    Button btPublishJob;

    private JobRepositoryInterface jobRepo;

    public PublishJobActivity() {
        this.jobRepo = new JobRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_job);

        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        btPublishJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishJob();
            }
        });
    }

    private void publishJob() {
        Job newJob = new Job();
        newJob.setCity(spinnerJobCity.getSelectedItem().toString());
        newJob.setTitle(edJobTitle.getText().toString());
        newJob.setSalary(Integer.valueOf(edJobSalary.getText().toString()));
        newJob.setCreatedBy(JobApplication.currentMail);
        newJob.setExpiration_date(DateHelper.addDaysFromCurrentDate(7));
        jobRepo.upsertAsync(newJob, new ProgressDialogCallBack<Job>(PublishJobActivity.this) {
            @Override
            public void handleResponse(Job response) {
                showToast("Add job successfully.");
                super.handleResponse(response);
            }
        });
    }
}
