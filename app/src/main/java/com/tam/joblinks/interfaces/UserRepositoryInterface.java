package com.tam.joblinks.interfaces;

/**
 * Created by toan on 4/12/2016.
 */
public interface UserRepositoryInterface {
    void login();
    void logout();
    void register();
    boolean getResult();
}
