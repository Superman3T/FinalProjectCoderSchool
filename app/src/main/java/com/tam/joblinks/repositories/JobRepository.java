package com.tam.joblinks.repositories;

import android.content.Context;

import com.backendless.Backendless;
import com.backendless.persistence.BackendlessDataQuery;
import com.tam.joblinks.helpers.ProgressDialogCollectionCallBack;
import com.tam.joblinks.interfaces.JobRepositoryInterface;
import com.tam.joblinks.models.Job;

/**
 * Created by toan on 4/19/2016.
 */
public class JobRepository extends BaseRepository<Job> implements JobRepositoryInterface {

    public JobRepository(Context context) {
        super(Job.class);
        this.context = context;
    }

    @Override
    public void pagingAsync(BackendlessDataQuery query, ProgressDialogCollectionCallBack<Job> callBack) {
        // https://github.com/Backendless/RestaurantToGo/blob/master/restaurant_list/src/main/java/com/backendless/samples/restaurant/RestaurantListingActivity.java
        Backendless.Data.of(curClass).find(query, callBack);
    }
}
