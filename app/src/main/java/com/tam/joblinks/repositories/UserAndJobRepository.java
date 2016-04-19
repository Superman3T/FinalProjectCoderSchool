package com.tam.joblinks.repositories;

import com.tam.joblinks.interfaces.UserAndJobInterface;
import com.tam.joblinks.models.UserAndJob;

/**
 * Created by toan on 4/19/2016.
 */
public class UserAndJobRepository extends BaseRepository<UserAndJob> implements UserAndJobInterface {
    public UserAndJobRepository() {
        super(UserAndJob.class);
    }
}
