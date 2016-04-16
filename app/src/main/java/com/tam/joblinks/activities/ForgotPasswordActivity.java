package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tam.joblinks.R;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.repositories.UserRepository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Bind(R.id.edForgotPasswordEmail)
    EditText edForgotPasswordEmail;

    @Bind(R.id.btRestorePassword)
    Button btRestorePassword;

    UserRepositoryInterface userRepo;
    public ForgotPasswordActivity() {
        userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        btRestorePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restorePassword();
            }
        });
    }

    private void restorePassword() {
        String email = edForgotPasswordEmail.getText().toString().trim();
        this.userRepo.restorePasswordAsync(email, new ProgressDialogCallBack<Void>(this) {
            @Override
            public void handleResponse(Void response) {
                super.handleResponse(response);
                //startActivity( new Intent( RestorePasswordActivity.this, PasswordRecoveryRequestedActivity.class ) );
                //finish();
            }
        });
    }
}
