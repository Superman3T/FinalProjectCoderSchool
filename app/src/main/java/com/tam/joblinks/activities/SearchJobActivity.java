package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tam.joblinks.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchJobActivity extends AppCompatActivity {

    @Bind(R.id.btSearchJob)
    Button btSearchJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        ButterKnife.bind(this);

        btSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
