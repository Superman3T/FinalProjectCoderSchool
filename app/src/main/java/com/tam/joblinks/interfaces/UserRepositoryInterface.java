package com.tam.joblinks.interfaces;

import com.backendless.BackendlessUser;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.models.ViewUserLogin;
import com.tam.joblinks.models.ViewUserResgister;

/**
 * Created by toan on 4/12/2016.
 */
public interface UserRepositoryInterface {
    void login(ViewUserLogin model);
    void loginAsync(ViewUserLogin model);
    void loginAsync(ViewUserLogin model, ProgressDialogCallBack<BackendlessUser> callBack, Boolean remember);
    void logout();
    void logoutAsync();
    void register(ViewUserResgister model);
    void registerAsync(ViewUserResgister model);
    void registerAsync(ViewUserResgister model, ProgressDialogCallBack<BackendlessUser> callBack);
    boolean getResult();
    boolean isValidLogin();
    void isValidLogin(ProgressDialogCallBack<Boolean> callBack);
    void findById(String currentUserId, ProgressDialogCallBack<BackendlessUser> callBack);
    BackendlessUser getCurrentUser();
    void setCurrentUser(BackendlessUser user);
    void restorePasswordAsync(String email, ProgressDialogCallBack<Void> callBack);

}
