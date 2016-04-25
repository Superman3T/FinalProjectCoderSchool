package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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

    @Bind(R.id.tvJobCity)
    TextView tvJobCity;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
        Job newJob = new Job();
        newJob.setCity(tvJobCity.getText().toString());
        newJob.setTitle(edJobTitle.getText().toString());
        newJob.setSalary(Integer.valueOf(edJobSalary.getText().toString()));
        newJob.setCreatedBy(JobApplication.currentMail);
        newJob.setExpiration_date(DateHelper.addDaysFromCurrentDate(7));
        jobRepo.upsertAsync(newJob, new ProgressDialogCallBack<Job>(PublishJobActivity.this) {
            @Override
            public void handleResponse(Job response) {
                showToast(getString(R.string.add_job_successfully));
                super.handleResponse(response);
            }
        });
    }

}
