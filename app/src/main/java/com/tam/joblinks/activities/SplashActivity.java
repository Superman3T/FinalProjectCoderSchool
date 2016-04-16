package com.tam.joblinks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tam.joblinks.R;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.repositories.UserRepository;

public class SplashActivity extends AppCompatActivity {

    UserRepositoryInterface userRepo;
    public SplashActivity() {
        this.userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
