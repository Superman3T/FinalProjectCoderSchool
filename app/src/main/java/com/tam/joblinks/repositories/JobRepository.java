package com.tam.joblinks.repositories;

import com.tam.joblinks.models.Job;

/**
 * Created by toan on 4/19/2016.
 */
public class JobRepository extends BaseRepository<Job> {
    public JobRepository() {
        super(Job.class);
    }
}
