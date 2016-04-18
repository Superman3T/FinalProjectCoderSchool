package com.tam.joblinks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.BackendlessUser;
import com.tam.joblinks.R;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.helpers.SessionPreferencesHelper;
import com.tam.joblinks.helpers.StringHelper;
import com.tam.joblinks.helpers.Validator;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.models.ViewUserLogin;
import com.tam.joblinks.repositories.UserRepository;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A loginAsync screen that offers loginAsync via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    @Bind(R.id.edLoginEmail)
    EditText edLoginEmail;

    @Bind(R.id.edLoginPassword)
    EditText edLoginPassword;

    @Bind(R.id.tvRegisterLink)
    TextView tvRegisterLink;

    @Bind(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    @Bind(R.id.btSignIn)
    Button btSignIn;

    private String email;
    private String password;

    private UserRepositoryInterface userRepo;

    public LoginActivity() {
        this.userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        edLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        tvRegisterLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterAccountActivity.class));
                finish();
            }
        });

        tvForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });

        btSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private boolean validateFields() {
        // Reset errors.
        edLoginEmail.setError(null);
        edLoginPassword.setError(null);

        // Store values at the time of the loginAsync attempt.
        email = edLoginEmail.getText().toString();
        password = edLoginPassword.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!StringHelper.isNullOrEmpty(password) && !isPasswordValid(password)) {
            edLoginPassword.setError(getString(R.string.error_invalid_password));
            edLoginPassword.requestFocus();
            return false;
        }

        // Check for a valid email address.
        if (StringHelper.isNullOrEmpty(email)) {
            edLoginEmail.setError(getString(R.string.email_required));
            edLoginEmail.requestFocus();
            return false;
        } else if (!Validator.isValidEmail(email)) {
            edLoginEmail.setError(getString(R.string.invalid_email));
            edLoginEmail.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Attempts to sign in or registerAsync the account specified by the loginAsync form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual loginAsync attempt is made.
     */
    private void attemptLogin() {
        if (validateFields()) {
            ViewUserLogin model = new ViewUserLogin();
            model.rememberMe = true; // HARD CODE
            model.email = this.email;
            model.password = this.password;
            this.userRepo.loginAsync(model, new ProgressDialogCallBack<BackendlessUser>(this) {
                @Override
                public void handleResponse(BackendlessUser response) {
                    super.handleResponse(response);
                    SessionPreferencesHelper session = new SessionPreferencesHelper(LoginActivity.this);
                    session.createLoginSession(email);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}

