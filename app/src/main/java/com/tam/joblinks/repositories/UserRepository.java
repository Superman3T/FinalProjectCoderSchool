package com.tam.joblinks.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.DialogHelper;
import com.tam.joblinks.helpers.NetworkHelper;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.models.User;
import com.tam.joblinks.models.ViewUserLogin;
import com.tam.joblinks.models.ViewUserResgister;

/**
 * Created by toan on 4/12/2016.
 */
public class UserRepository extends BaseRepository<User> implements UserRepositoryInterface {

    private boolean result;

    public UserRepository(Context context) {
        super(User.class);
        this.context = context;
    }

    @Override
    public void login(ViewUserLogin model) {
        try {
            Backendless.UserService.login(model.email, model.password, model.rememberMe);
            result = true;
        } catch (BackendlessException fault) {
            // login failed, to get the error code, call exception.getFault().getCode()
            Log.e("login", "Exception login, code: " + fault.getCode() + ", message: " + fault.getMessage());
            DialogHelper.showErrorMessage(context, "Login failed. Please try again.");
            result = false;
        }
    }

    @Override
    public void loginAsync(ViewUserLogin model) {
        AsyncCallback<BackendlessUser> callback = new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Log.i("LoginAsync", "User has been logged in - " + response.getObjectId());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("LoginAsync", "Exception LoginAsync, code: " + fault.getCode() + ", message: " + fault.getMessage());
                DialogHelper.showErrorMessage(context, "Login failed. Please try again.");
            }
        };
        Backendless.UserService.login(model.email, model.password, callback, true);
    }

    @Override
    public void loginAsync(ViewUserLogin model, ProgressDialogCallBack<BackendlessUser> callBack) {
        Backendless.UserService.login(model.email, model.password, callBack, model.rememberMe);
    }

    @Override
    public void logout() {
        try {
            Backendless.UserService.logout();
            result = true;
        } catch (BackendlessException ex) {
            Log.e("logout", "Error logout, code: " + ex.getCode() + ", message: " + ex.getMessage());
            result = false;
        }
    }

    @Override
    public void logoutAsync() {
        Backendless.UserService.logout(new ProgressDialogCallBack<Void>(context) {
            @Override
            public void handleResponse(Void response) {
                Log.i("logoutAsync", "successfully logout");
                super.handleResponse(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                //something went wrong and logout failed, to get the error code call fault.getCode()
                Log.e("logoutAsync", "Exception logoutAsync, code: " + fault.getCode());
                DialogHelper.showErrorMessage(context, "Something went wrong and logout failed.");
            }
        });
    }

    @Override
    public void logoutAsync(ProgressDialogCallBack<Void> callBack) {
        Backendless.UserService.logout(callBack);
    }

    @Override
    public void register(ViewUserResgister model) {
        BackendlessUser user = new BackendlessUser();
        user.setEmail(model.email);
        user.setPassword(model.password);
        user.setProperty(User.FIRST_NAME_KEY, model.firstName);
        user.setProperty(User.LAST_NAME_KEY, model.lastName);
        try {
            Backendless.UserService.register(user);
            result = true;
        } catch (BackendlessException exception) {
            Log.e("Registration", "Exception registerAsync, code: " + exception.getCode());
            result = false;
        }
    }

    @Override
    public void registerAsync(final ViewUserResgister model) {
        User user = createUser(model);
        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Log.i("Registration", response.getEmail() + " successfully registered");
                JobApplication.currentMail = response.getEmail();
//                SessionPreferencesHelper session = new SessionPreferencesHelper(context);
//                session.createLoginSession(model.getFullName(), model.email);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Registration", "Exception registerAsync, code: " + fault.getCode());
                DialogHelper.showErrorMessage(context, fault.getMessage());
            }
        });
    }

    @Override
    public void registerAsync(ViewUserResgister model, ProgressDialogCallBack<BackendlessUser> callBack) {
        User user = createUser(model);
        Backendless.UserService.register(user, callBack);
    }

    @NonNull
    private User createUser(ViewUserResgister model) {
        User user = new User();
        user.setEmail(model.email);
        user.setLastName(model.lastName);
        user.setFirstName(model.firstName);
        user.setPassword(model.password);
        user.setName(model.firstName);
        user.setMacAddress(NetworkHelper.getMacAddress(context));
        return user;
    }

    @Override
    public boolean getResult() {
        return this.result;
    }

    @Override
    public boolean isValidLogin() {
        return Backendless.UserService.isValidLogin();
    }

    @Override
    public void isValidLogin(ProgressDialogCallBack<Boolean> callBack) {
        Backendless.UserService.isValidLogin(callBack);
    }

    @Override
    public void findById(String currentUserId, ProgressDialogCallBack<BackendlessUser> callBack) {
        Backendless.UserService.findById(currentUserId, callBack);
    }

    @Override
    public BackendlessUser getCurrentUser() {
        String currentUserObjectId = UserIdStorageFactory.instance().getStorage().get();
        BackendlessUser user = Backendless.Data.of(BackendlessUser.class).findById(currentUserObjectId);
        return user;
    }

    @Override
    public void setCurrentUser(BackendlessUser user) {
        Backendless.UserService.setCurrentUser(user);
    }

    @Override
    public void restorePasswordAsync(String email, ProgressDialogCallBack<Void> callBack) {
        Backendless.UserService.restorePassword(email, callBack);
    }
}
