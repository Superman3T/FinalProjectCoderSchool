package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.backendless.exceptions.BackendlessFault;
import com.tam.joblinks.R;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.repositories.UserRepository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.btLogout)
    Button btLogout;

    UserRepositoryInterface userRepo;

    public HomeActivity() {
        this.userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void logout() {
        this.userRepo.logoutAsync(new ProgressDialogCallBack<Void>(this) {

            @Override
            public void handleFault(BackendlessFault fault) {
                if (fault.getCode().equals("3023")) // Unable to logout: not logged in (session expired, etc.)
                    handleResponse(null);
                else
                    super.handleFault(fault);
            }
        });
    }

}
