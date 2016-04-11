package com.tam.joblinks.repositories;

import com.tam.joblinks.interfaces.UserRepositoryInterface;

/**
 * Created by toan on 4/12/2016.
 */
public class UserRepository implements UserRepositoryInterface {

    private boolean result;

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

    @Override
    public void register() {

    }

    @Override
    public boolean getResult() {
        return this.result;
    }
}
