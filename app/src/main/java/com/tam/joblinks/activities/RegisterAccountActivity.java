package com.tam.joblinks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessFault;
import com.tam.joblinks.R;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.models.ViewUserResgister;
import com.tam.joblinks.repositories.UserRepository;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterAccountActivity extends AppCompatActivity {

    @Bind(R.id.btRegister)
    Button btRegister;

    @Bind(R.id.edRegisterFirstName)
    EditText edRegisterFirstName;

    @Bind(R.id.edRegisterLastName)
    EditText edRegisterLastName;

    @Bind(R.id.tvRegisterEmail)
    AutoCompleteTextView tvRegisterEmail;

    @Bind(R.id.edRegisterPassword)
    EditText edRegisterPassword;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private UserRepositoryInterface userRepo;

    public RegisterAccountActivity() {
        this.userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        ButterKnife.bind(this);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        if (validateField()) {
            ViewUserResgister user = new ViewUserResgister();
            user.email = email;
            user.firstName = firstName;
            user.lastName = lastName;
            user.password = password;
            this.userRepo.registerAsync(user, new ProgressDialogCallBack<BackendlessUser>(this) {
                @Override
                public void handleResponse(BackendlessUser response) {
                    super.handleResponse(response);
                    startActivity(new Intent(RegisterAccountActivity.this, HomeActivity.class));
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    super.handleFault(fault);
                }
            });
        }
    }

    private boolean validateField() {
        email = tvRegisterEmail.getText().toString().trim();
        firstName = edRegisterFirstName.getText().toString().trim();
        lastName = edRegisterLastName.getText().toString().trim();
        password = edRegisterPassword.getText().toString().trim();
        if (email.isEmpty()) {
            showToast(getString(R.string.email_required));
            tvRegisterEmail.requestFocus();
            return false;
        }
        if (firstName.isEmpty()) {
            showToast(getString(R.string.first_name_required));
            edRegisterFirstName.requestFocus();
            return false;
        } else {
            if (!isValidName(firstName)) {
                showToast("First" + getString(R.string.invalid_name));
                return false;
            }
        }
        if (lastName.isEmpty()) {
            showToast(getString(R.string.last_name_required));
            edRegisterLastName.requestFocus();
            return false;
        } else {
            if (!isValidName(lastName)) {
                showToast("Last" + getString(R.string.invalid_name));
                return false;
            }
        }
        if (password.isEmpty()) {
            showToast(getString(R.string.password_required));
            edRegisterPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        if (Pattern.matches("[a-zA-Z]+", name)) {
            return true;
        }
        return false;
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
